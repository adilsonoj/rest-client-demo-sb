package com.oliver.demo.restclient;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class RestclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestclientApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(CrudService crudService) {
		return args -> {
			var car1 = new Car("ka", "preto");
			var id = "66030abf1492af03e8f105fe";
			// System.out.println(crudService.createCar(car1));

			// System.out.println(crudService.getCar(id));

			// crudService.updateCar(id, car1);

			// System.out.println(crudService.listCar());

			crudService.deleteCar(id);
		};
	}

	// instancia para injetar
	@Bean
	RestClient crudClient() {
		return RestClient.create("https://crudcrud.com/api/12555205a8a04201b66331b59bfc1445");
	}

}

@Service
class CrudService {
	private final RestClient restClient;

	public CrudService(RestClient restClient) {
		this.restClient = restClient;
	}

	public Car createCar(Car car) {
		return restClient.post()
				.uri("/cars")
				.body(car)
				.retrieve()
				.body(Car.class);
	}

	public void updateCar(String id, Car car) {
		restClient.put()
				.uri("/cars/{id}", id)
				.body(car)
				.retrieve()
				.toBodilessEntity();
	}

	public Car getCar(String id) {
		return restClient.get()
				.uri("/cars/{id}", id)
				.retrieve()
				.body(Car.class);
	}

	public void deleteCar(String id) {
		restClient.delete()
				.uri("/cars/{id}", id)
				.retrieve()
				.toBodilessEntity();
	}

	public List<Car> listCar() {
		return restClient.get()
				.uri("/cars")
				.retrieve()
				.body(new ParameterizedTypeReference<>() {

				});
	}

}

record Car(String model, String color) {
}
