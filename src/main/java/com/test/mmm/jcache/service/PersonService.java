package com.test.mmm.jcache.service;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.test.mmm.jcache.entity.Person;
import com.test.mmm.jcache.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Marian_Mykhalchuk on 7/22/2016.
 */
@Service
public class PersonService {

	private final PersonRepository personRepository;

	@Caching(put = @CachePut(value = "persons", key = "#person.id"),
			evict = @CacheEvict(value = "personsByLastName", key = "#person.lastName"))
	public Person save(Person person) {
		return personRepository.save(person);
	}

	@Caching(evict = {
			@CacheEvict(value = "persons", key = "#person.id"),
			@CacheEvict(value = "personsByLastName", key = "#person.lastName")
	})
	public void delete(Person person) {
		personRepository.delete(person.getId());
	}

	@Cacheable(value = "persons", key = "#id")
	public Person findOne(Long id) {
		return personRepository.findOne(id);
	}

	@Cacheable(value = "personsByLastName", key = "#lastName")
	public List<Person> findByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}

	public List<Person> findAll() {
		return Lists.newArrayList(personRepository.findAll());
	}

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
}
