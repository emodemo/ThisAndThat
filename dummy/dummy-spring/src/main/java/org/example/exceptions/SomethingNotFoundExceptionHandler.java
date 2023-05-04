package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SomethingNotFoundExceptionHandler {

//	@ExceptionHandler(value = SomethingNotFoundException.class)
//	public ModelAndView handleError(HttpServletRequest req, SomethingNotFoundException ex) {
//		log.error("Request: " + req.getRequestURL() + " raised " + ex);
//
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("somevalue", ex.getCode() + ": " + ex.getMessage());
//		mav.setViewName("error/errorPage");
//		return mav;
//	}

	@ExceptionHandler(value = SomethingNotFoundException.class)
	public String handleError(Model model, SomethingNotFoundException ex) {
		model.addAttribute("somevalue", ex.getCode() + ": " + ex.getMessage());
		return "error/errorPage";
	}



}
