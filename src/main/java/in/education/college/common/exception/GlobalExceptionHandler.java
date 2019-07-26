package in.education.college.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
//@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ModelAndView handleInvalidHeaderFieldException(InvalidHeaderFieldException ex) {
		ModelAndView mav = new ModelAndView("error");	// shows 404.jsp
		mav.addObject("message", ex.getMessage());

		return mav;
	}

	@ExceptionHandler
	public ModelAndView handleNullPointerException(NullPointerException ex) {
		ModelAndView mav = new ModelAndView("error");	// shows 404.jsp
//		mav.addObject("message", ex.toString());
		mav.addObject("message", "Something went wrong. Please contact support");
		return mav;

//		return error(ex, HttpStatus.NOT_FOUND, "error");
	}

	@ExceptionHandler
	public ModelAndView handleIoException(IOException ex) {
		ModelAndView mav = new ModelAndView("error");	// shows error.jsp
		mav.addObject("message", ex.getMessage());
		return mav;
	}

	/*@ExceptionHandler
	public ResponseEntity<String> handleInvalidHeaderFieldException1(InvalidHeaderFieldException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.PRECONDITION_FAILED);
	}*/

	@ExceptionHandler(BindException.class)
	public List<String> handleBindException(BindException ex) {
		return ex.getBindingResult()
				.getAllErrors().stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		ModelAndView mav = new ModelAndView("error");	// shows error.jsp
//		System.out.println(ex.getMessage());
		mav.addObject("message", ex.getMessage());
		return mav;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ModelAndView handleDataIntegrity(DataIntegrityViolationException ex) {
		ModelAndView mav = new ModelAndView("error");	// shows error.jsp
		mav.addObject("message", "Problem in inserting record. Please try again");
		return mav;
	}

	/*private ResponseEntity < ErrorMessage > error(final Exception exception, final HttpStatus httpStatus, final String logRef) {
		final String message = Optional.of(exception.getMessage())
										.orElse(exception.getClass().getSimpleName());
//		final String message = Optional.of(exception.toString())
//				.orElse(exception.getClass().getSimpleName());
		return new ResponseEntity <ErrorMessage> (new ErrorMessage( logRef, message ), httpStatus);
	}*/

	/*class ErrorMessage {
		private String message;
		private String log;

		public ErrorMessage(String message, String log) {
			this.message = message;
			this.log = log;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getLog() {
			return log;
		}

		public void setLog(String log) {
			this.log = log;
		}
	}*/
}
