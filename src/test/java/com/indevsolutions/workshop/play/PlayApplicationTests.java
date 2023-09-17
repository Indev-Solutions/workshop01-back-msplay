package com.indevsolutions.workshop.play;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlayApplicationTests {

	@Test
	void contextLoads() {
		var result = "dummy";
		assertEquals("dummy", result);
	}

}
