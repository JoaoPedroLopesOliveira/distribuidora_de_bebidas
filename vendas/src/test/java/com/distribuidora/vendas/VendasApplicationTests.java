package com.distribuidora.vendas;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class VendasApplicationTests {

	@MockitoBean
	KafkaTemplate<String, String> kafkaTemplate;

	@Test
	void contextLoads() {
	}

}
