package heavenhr.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import heavenhr.response.FailureResponse;

/**
 * The controller advice for all the exception handling.
 * 
 * @author  Deepthi KV
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	/**
	 * Exception handler for ResourceNotFoundException. 
	 * 
	 * @param ex ResourceNotFoundException
	 * @return ResponseEntity which contains the FailureResponseMessage
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<FailureResponse> resourceNotFound(ResourceNotFoundException ex) {
		ResponseEntity<FailureResponse> entity = new ResponseEntity<FailureResponse>(
				convertExceptionToFailureMessage(ex), ex.getHttpStatus());
		return entity;
	}

	/**
	 * Exception handler for BadRequestException.
	 * 
	 * @param ex BadRequestException
	 * @return ResponseEntity which contains the FailureResponseMessage
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<FailureResponse> badRequest(BadRequestException ex) {
		FailureResponse message = convertExceptionToFailureMessage(ex);
		ResponseEntity<FailureResponse> entity = new ResponseEntity<FailureResponse>(message,
				HttpStatus.BAD_REQUEST);
		return entity;
	}

	/**
	 * Exception handler for DuplicateKeyException. 
	 * 
	 * @param exception DuplicateKeyException
	 * @return ResponseEntity which contains the FailureResponseMessage
	 */
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<FailureResponse> duplicateEmail(ConflictException exception) {
		FailureResponse message = convertExceptionToFailureMessage(exception);
		return new ResponseEntity<FailureResponse>(convertExceptionToFailureMessage(exception),
				message.getHttpstatus());
	}

	/**
	 * Any kind of validation fails, this method will be executed.
	 * 
	 * @param exception MethodArgumentNotValidException
	 * @return ResponseEntity which contains the FailureResponseMessage
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<FailureResponse> invalidInput(MethodArgumentNotValidException exception) {
		FailureResponse message = convertValidationToFailureMessage(exception);
		return new ResponseEntity<FailureResponse>(message, message.getHttpstatus());
	}
	
	
	private FailureResponse convertExceptionToFailureMessage(AbstractException ex) {
		return new FailureResponse(ex.getHttpStatus(), "Server error", ex.getMessage());
	}
	
	private FailureResponse convertValidationToFailureMessage(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		String message= null;
		if(result.getAllErrors().size() > 0){
			message = result.getAllErrors().get(0).getDefaultMessage();
		}else{
			message ="";
		}
		return new FailureResponse(HttpStatus.BAD_REQUEST, "Validation Error",
				message);
	}

}
