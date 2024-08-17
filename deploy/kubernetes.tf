terraform {
  backend "s3" {
    bucket = "brstworkshop1"
    key    = "rst/iac"
    region = "us-east-1"
  }

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.46.0"
    }

    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "2.29.0"
    }
  }

  required_version = "~> 1.7"
}

variable "region" {
  type        = string
  description = "AWS region for all cloud resources"
}

variable "msplay_version" {
  type        = string
  description = "Version of microservice play to deploy"
}

variable "database_hostname" {
  type        = string
  description = "Hostname of database"
  sensitive   = true
}

variable "database_password" {
  type        = string
  description = "Password of database"
  sensitive   = true
}

variable "apigateway_jwt_configuration_audience" {
  type        = string
  description = "Audience of JWT configuration for api gateway"
  sensitive   = true
}

variable "apigateway_jwt_configuration_issuer" {
  type        = string
  description = "Issuer of JWT configuration for api gateway"
  sensitive   = true
}

variable "account_id" {
  type        = string
  description = "AWS account id"
  sensitive   = true
}

provider "aws" {
  region = var.region
}

provider "kubernetes" {
  config_path = "~/.kube/config"
}

data "terraform_remote_state" "integration" {
  backend = "s3"

  config = {
    bucket = "brstworkshop1"
    key    = "env:/workshop1-pro-integration/rst/iac"
    region = var.region
  }
}

resource "kubernetes_deployment" "deployment-msplay" {
  metadata {
    name = "workshop1-deployment-msplay"

    labels = {
      app = "workshop1-backend-msplay"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "workshop1-backend-msplay"
      }
    }

    template {
      metadata {
        labels = {
          app = "workshop1-backend-msplay"
        }
      }

      spec {
        container {
          image = "indevsolutions/workshop1:ms-play_v${var.msplay_version}"
          name  = "customserviceplay"

          env {
            name  = "DATABASE_URL"
            value = "jdbc:postgresql://${var.database_hostname}:5432/workshop"
          }

          env {
            name  = "DATABASE_USER"
            value = "dbadmin"
          }

          env {
            name  = "DATABASE_PASSWORD"
            value = var.database_password
          }

          env {
            name  = "SERVICE_BET_HOSTNAME"
            value = "workshop1-service-msbet.default.svc.cluster.local"
          }

          port {
            container_port = 9090
            name           = "tcp"
          }

          resources {
            limits = {
              cpu    = "0.5"
              memory = "512Mi"
            }

            requests = {
              cpu    = "250m"
              memory = "50Mi"
            }
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "msplay-service" {
  metadata {
    name = "workshop1-service-msplay"

    annotations = {
      "service.beta.kubernetes.io/aws-load-balancer-internal" = "true"
      "service.beta.kubernetes.io/aws-load-balancer-type"     = "nlb"
    }
  }

  spec {
    selector = {
      app = kubernetes_deployment.deployment-msplay.metadata.0.labels.app
    }

    port {
      port        = 80
      target_port = 9090
      protocol    = "TCP"
    }

    type = "LoadBalancer"
  }
}

resource "aws_apigatewayv2_api" "my_apigateway_msplay" {
  name          = "my-http-api-msplay"
  protocol_type = "HTTP"
}

resource "aws_apigatewayv2_stage" "my_apigateway_stage_msplay" {
  api_id      = aws_apigatewayv2_api.my_apigateway_msplay.id
  name        = "$default"
  auto_deploy = true

  default_route_settings {
    throttling_burst_limit = 100
    throttling_rate_limit  = 200
  }
}

resource "aws_apigatewayv2_authorizer" "my_apigateway_authorizer_msplay" {
  name             = "my-apigateway-authorizer-msplay"
  api_id           = aws_apigatewayv2_api.my_apigateway_msplay.id
  authorizer_type  = "JWT"
  identity_sources = ["$request.header.Authorization"]

  jwt_configuration {
    audience = [var.apigateway_jwt_configuration_audience]
    issuer   = var.apigateway_jwt_configuration_issuer
  }
}

locals {
  load_balancer_ingress_hostname = kubernetes_service.msplay-service.status.0.load_balancer.0.ingress.0.hostname
  load_balancer_ids              = split("-", split(".", local.load_balancer_ingress_hostname).0)
}

data "aws_lb_listener" "my_lb_listener_msplay" {
  load_balancer_arn = "arn:aws:elasticloadbalancing:${var.region}:${var.account_id}:loadbalancer/net/${local.load_balancer_ids.0}/${local.load_balancer_ids.1}"
  port              = 80
}

resource "aws_apigatewayv2_integration" "my_apigateway_integration_msplay" {
  api_id             = aws_apigatewayv2_api.my_apigateway_msplay.id
  integration_type   = "HTTP_PROXY"
  integration_uri    = data.aws_lb_listener.my_lb_listener_msplay.arn
  integration_method = "ANY"
  connection_type    = "VPC_LINK"
  connection_id      = data.terraform_remote_state.integration.outputs.my_apigateway_vpc_link_id
}

resource "aws_apigatewayv2_route" "my_apigateway_route_msplay" {
  api_id             = aws_apigatewayv2_api.my_apigateway_msplay.id
  route_key          = "ANY /workshop/plays"
  target             = "integrations/${aws_apigatewayv2_integration.my_apigateway_integration_msplay.id}"
  authorization_type = "JWT"
  authorizer_id      = aws_apigatewayv2_authorizer.my_apigateway_authorizer_msplay.id
}

output "load_balancer_ingress_hostname" {
  value = local.load_balancer_ingress_hostname
}

output "apigateway_integration_uri" {
  value = data.aws_lb_listener.my_lb_listener_msplay.arn
}

output "apigateway_endpoint" {
  value = aws_apigatewayv2_api.my_apigateway_msplay.api_endpoint
}
