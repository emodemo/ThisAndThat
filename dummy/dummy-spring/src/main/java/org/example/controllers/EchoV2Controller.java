package org.example.controllers;

import org.example.configuration.AppConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("echov2")
public class EchoV2Controller {

	private AppConfiguration config;

	public EchoV2Controller(AppConfiguration config) {
		this.config = config;
	}

	@GetMapping("{string}")
	public String echo(@PathVariable String string){
		return config.getEcho() + " " + string;
	}

}
