CREATE TABLE play_schema.play (
	id int NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	user_id int4 NOT NULL,
	bet_id int4 NOT NULL,
	choice_id int NOT NULL,
	amount numeric(6, 2) NOT NULL,
	registration_date timestamptz NOT NULL,
	CONSTRAINT play_pk PRIMARY KEY (id)
);