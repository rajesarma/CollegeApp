package in.education.college.common.exception;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ControllerAdvice
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
		mav.addObject("message", ex.getMessage());
//		System.out.println(ex.getMessage());

		return mav;
	}

	@ExceptionHandler
	public ModelAndView handleIoException(IOException ex) {
		ModelAndView mav = new ModelAndView("error");	// shows error.jsp
		mav.addObject("message", ex.getMessage());
//		System.out.println(ex.getMessage());

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

		System.out.println(ex.getMessage());
		mav.addObject("message", ex.getMessage());

		return mav;
	}
}
