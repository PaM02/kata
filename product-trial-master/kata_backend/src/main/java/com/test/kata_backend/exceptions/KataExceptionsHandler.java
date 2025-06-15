package  com.test.kata_backend.exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.*;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
@ResponseBody
public class KataExceptionsHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
    }


    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT Token signature invalid");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token malformed");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<String> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token unsupported");
    }

    @ExceptionHandler(value = ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  ResponseEntity<String> handlerPageNotFoundExecption(ChangeSetPersister.NotFoundException ex){
        return ResponseEntity.badRequest().body("Vous n'avez pas la permission Required");
    }

    @ExceptionHandler(KataExceptions.class)
    public ResponseEntity<String> handleGenericApiException(KataExceptions exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        // exception.fillInStackTrace();
        return ResponseEntity.badRequest().body(exception.fillInStackTrace().getMessage());
    }


}

