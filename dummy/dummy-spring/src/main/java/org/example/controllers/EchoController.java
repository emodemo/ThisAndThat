package org.example.controllers;


import org.example.configuration.AppConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("echo")
public class EchoController {

	private AppConfiguration config;

	public EchoController(AppConfiguration config) {
		this.config = config;
	}

	@GetMapping("{string}")
	public String echo(@PathVariable String string){
		return config.getEcho() + " " + string;
	}

}
