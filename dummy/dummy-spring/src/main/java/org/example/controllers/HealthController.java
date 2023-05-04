package org.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthController {

	private int i = 200;

	@GetMapping("/getme")
	public int getPeople() throws Exception {
		if(i == 200) return i;
		throw new Exception();
	}

	@GetMapping("/setme/{i}")
	public int getPeople2(@PathVariable int i){
		this.i = i;
		return this.i;
	}

}
