package com.test.mmm.jcache;

import com.hazelcast.config.Config;
import com.test.mmm.jcache.entity.Person;
import com.test.mmm.jcache.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * Created by Marian_Mykhalchuk on 7/22/2016.
 */
@SpringBootApplication
@EnableCaching
public class Application {

	public static void main(String... s) {
		SpringApplication.run(Application.class, s);
	}

	// do the configuration here
	@Bean
	public Config config() {
		return new Config();
	}

	@Bean
	public CommandLineRunner loadData(PersonService personService) {
		return (args) -> {
			// save a couple of customers
			personService.save(new Person("Jack", "Bauer"));
			personService.save(new Person("Chloe", "O'Brian"));
			personService.save(new Person("Kim", "Bauer"));
			personService.save(new Person("David", "Palmer"));
			personService.save(new Person("Michelle", "Dessler"));
		};
	}
}
