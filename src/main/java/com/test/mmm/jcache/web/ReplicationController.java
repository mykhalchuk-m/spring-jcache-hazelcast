package com.test.mmm.jcache.web;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Marian_Mykhalchuk on 8/3/2016.
 */
@RestController
@RequestMapping("/replication")
public class ReplicationController {

	public static final String MAP_NAME = "values";

	private final HazelcastInstance instance;

	@RequestMapping(method = RequestMethod.POST)
	public void putInCache(@RequestParam("val") String value) {
		Map<String, String> map = instance.getReplicatedMap(MAP_NAME);
		map.put("1", value);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getFromCache() {
		Map<String, String> map = instance.getReplicatedMap(MAP_NAME);
		return map.get("1");
	}

	@Autowired
	public ReplicationController(HazelcastInstance instance) {
		this.instance = instance;
	}
}
