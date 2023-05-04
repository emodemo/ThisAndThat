package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ServiceResponse<String> handleConflict(RuntimeException ex) {
		ServiceResponse<String> serviceResponse = new ServiceResponse<>();
		serviceResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
		serviceResponse.setErrorMessage(ex.getMessage());
		return serviceResponse;
	}

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ServiceResponse<T> {
	private T object;
	private int errorCode;
	private String errorMessage;
}
