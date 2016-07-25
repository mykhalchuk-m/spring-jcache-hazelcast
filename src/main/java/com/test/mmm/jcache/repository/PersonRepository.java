package com.test.mmm.jcache.repository;

import com.test.mmm.jcache.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Marian_Mykhalchuk on 7/22/2016.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {
	List<Person> findByLastName(String text);
}
