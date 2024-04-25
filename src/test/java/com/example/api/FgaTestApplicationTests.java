package com.example.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"openfga.enabled=false"
})
class FgaTestApplicationTests {

	@Test
	void contextLoads() {
	}

}
