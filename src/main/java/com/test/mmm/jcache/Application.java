package com.test.mmm.jcache;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ReplicatedMap;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.test.mmm.jcache.entity.Person;
import com.test.mmm.jcache.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Created by Marian_Mykhalchuk on 7/22/2016.
 */
@SpringBootApplication
@EnableCaching
public class Application {

	public static void main(String... s) {
		SpringApplication.run(Application.class, s);
	}

	@Bean
	public HazelcastInstance instance() {
		return Hazelcast.newHazelcastInstance(config());
	}

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
