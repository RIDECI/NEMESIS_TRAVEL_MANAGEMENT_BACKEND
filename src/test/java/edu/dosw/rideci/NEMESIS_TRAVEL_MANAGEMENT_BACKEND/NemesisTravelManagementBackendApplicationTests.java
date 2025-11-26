package edu.dosw.rideci.NEMESIS_TRAVEL_MANAGEMENT_BACKEND;
import org.junit.jupiter.api. Disabled;
import org.junit. jupiter.api.Test;
import org.springframework.boot.test. context.SpringBootTest;

@SpringBootTest
@Disabled("Aplicación requiere MongoDB y RabbitMQ configurados. Usar solo mvn test -Dtest=TravelControllerTest")
class NemesisTravelManagementBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}