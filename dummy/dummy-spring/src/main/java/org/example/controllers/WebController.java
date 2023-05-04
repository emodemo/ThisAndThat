package org.example.controllers;

import org.example.exceptions.SomethingNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("testpage")
public class WebController {

	@GetMapping(value = "", produces = MediaType.TEXT_HTML_VALUE)
	public String loadPage(Model model){
		model.addAttribute("somevalue", "my something value");

		throw new SomethingNotFoundException("something went wrong", 900);
		//return "someWebPage";
	}

}
