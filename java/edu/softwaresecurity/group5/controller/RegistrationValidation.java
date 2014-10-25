package edu.softwaresecurity.group5.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import edu.softwaresecurity.group5.model.CustomerInformation;

@Component("registrationValidator")
public class RegistrationValidation {
  public boolean supports(Class<?> klass) {
    return CustomerInformation.class.isAssignableFrom(klass);
  }

  public static void validate(Object target, Errors errors) {
	  CustomerInformation cinfo = (CustomerInformation) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
        "NotEmpty.CustomerInformation.username",
        "User Name must not be Empty.");
    String userName = cinfo.getUsername(); //-----
    System.out.println("CHECKING IN VALIDATION FILE: "+userName);
    if ((userName.length()) > 50) {
      errors.rejectValue("username",
          "lengthOfUser.CustomerInformation.username",
          "User Name must not more than 50 characters.");
      System.out.println("CHECKING 1");
    }
    if (!(cinfo.getPassword()).equals(cinfo
        .getConfirmPassword())) {
      errors.rejectValue("password",
          "matchingPassword.CustomerInformation.password",
          "Password and Confirm Password Not match.");
      System.out.println("CHECKING 2");
    }
  }
}