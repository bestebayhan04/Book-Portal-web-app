package com.obss.bookWeb.controller;


import com.obss.bookWeb.exception.UserException;
import com.obss.bookWeb.modelDto.UserSignInDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.obss.bookWeb.service.UserService;

@RestController
@RequestMapping("/bookweb")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/signIn")
    public ResponseEntity<UserSignInDetail> getLoggedInCustomerDetailsHandler(Authentication auth) {
        if (auth == null ) {
            throw new UserException("User is not authenticated (null)");
        }
        if ( !auth.isAuthenticated()) {
            throw new UserException("User is not authenticated");
        }

        try {
            var customer = userService.getUserByEmailId(auth.getName());
            UserSignInDetail signinSuccessData = new UserSignInDetail();
            signinSuccessData.setId(customer.getUserId());
            signinSuccessData.setFirstName(customer.getFirstName());
            signinSuccessData.setLastName(customer.getLastName());


            return new ResponseEntity<>(signinSuccessData, HttpStatus.OK);
        } catch (UserException ex) {
            throw new UserException("Invalid Password");
        }
    }
}
