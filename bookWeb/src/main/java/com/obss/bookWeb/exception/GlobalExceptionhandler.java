package com.obss.bookWeb.exception;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionhandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyErrorClass> getException(MethodArgumentNotValidException e, WebRequest req) {

        MyErrorClass e1 = new MyErrorClass();
        e1.setMessage(e.getBindingResult().getFieldError().getDefaultMessage());
        e1.setLocalDateTimes(LocalDateTime.now());
        e1.setDesc(req.getDescription(false));

        return new ResponseEntity<MyErrorClass>(e1, HttpStatus.BAD_GATEWAY);

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<MyErrorClass> getException(NoHandlerFoundException e, WebRequest req) {

        MyErrorClass e1 = new MyErrorClass();
        e1.setMessage("handle not found Exception");
        e1.setLocalDateTimes(LocalDateTime.now());
        e1.setDesc(req.getDescription(false));

        return new ResponseEntity<MyErrorClass>(e1, HttpStatus.BAD_GATEWAY);

    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<MyErrorClass> getException(UserException e, WebRequest req) {

        MyErrorClass e1 = new MyErrorClass();
        e1.setMessage(e.getMessage());
        System.out.println("inside the global orderexception");
        e1.setLocalDateTimes(LocalDateTime.now());
        e1.setDesc(req.getDescription(false));

        return new ResponseEntity<MyErrorClass>(e1, HttpStatus.BAD_GATEWAY);

    }


    @ExceptionHandler(FavListException.class)
    public ResponseEntity<MyErrorClass> getException(FavListException e, WebRequest req) {

        MyErrorClass e1 = new MyErrorClass();
        e1.setMessage(e.getMessage());
        e1.setLocalDateTimes(LocalDateTime.now());
        e1.setDesc(req.getDescription(false));

        return new ResponseEntity<MyErrorClass>(e1, HttpStatus.BAD_GATEWAY);

    }

    @ExceptionHandler(ReadListException.class)
    public ResponseEntity<MyErrorClass> getException(ReadListException e, WebRequest req) {

        MyErrorClass e1 = new MyErrorClass();
        e1.setMessage(e.getMessage());
        e1.setLocalDateTimes(LocalDateTime.now());
        e1.setDesc(req.getDescription(false));

        return new ResponseEntity<MyErrorClass>(e1, HttpStatus.BAD_GATEWAY);

    }


    @ExceptionHandler(ProductException.class)
    public ResponseEntity<MyErrorClass> getException(ProductException e, WebRequest req) {

        MyErrorClass e1 = new MyErrorClass();
        e1.setMessage(e.getMessage());
        e1.setLocalDateTimes(LocalDateTime.now());
        e1.setDesc(req.getDescription(false));

        return new ResponseEntity<MyErrorClass>(e1, HttpStatus.BAD_GATEWAY);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErrorClass> getException(Exception e, WebRequest req) {

        MyErrorClass e1 = new MyErrorClass();
        e1.setMessage(e.getMessage());
        e1.setLocalDateTimes(LocalDateTime.now());
        e1.setDesc(req.getDescription(false));

        return new ResponseEntity<MyErrorClass>(e1, HttpStatus.BAD_GATEWAY);

    }

}
