package com.uniquindio.reporte;

import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReporteApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}
	@Test
	void agregarReporte() {

	}

	@Test
	void saveUser() {
		userRepository.save(new User());
	}

}
