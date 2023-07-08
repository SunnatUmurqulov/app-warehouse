package uz.pdp.appwarehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.appwarehouse.entity.ApiResponse;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<?> exceptionHandler(DataNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(exception.getMessage(),false));
    }
}
