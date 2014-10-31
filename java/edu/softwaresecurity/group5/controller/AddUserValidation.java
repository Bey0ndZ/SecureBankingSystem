package edu.softwaresecurity.group5.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import edu.softwaresecurity.group5.model.AddUserInformation;

@Component("addUserValidator")
public class AddUserValidation {
  public boolean supports(Class<?> c) {
    return AddUserInformation.class.isAssignableFrom(c);
  }

  public static void validateForm(Object info, Errors errors) {
      
      int count = 0;
      int number = 0;
      int ssnNum = 0;
      int phNum = 0;
      
      AddUserInformation auinfo = (AddUserInformation) info;
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
        "NotEmpty.AddUserInformation.username",
        "User Name must not be Empty.");
    
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "socialSecurityNumber",
        "NotEmpty.CustomerInformation.socialSecurityNumber",
        "SSN must not be Empty.");
      
      String userName = auinfo.getUserName();
      if ((userName.length()) > 10) {
      errors.rejectValue("username",
          "lengthOfUser.CustomerInformation.username",
          "User Name must not more than 10 characters.");
      }
      
      String firstName = auinfo.getFirstName();
      if ((firstName.length()) > 10) {
      errors.rejectValue("firstname",
          "lengthOfFirst.CustomerInformation.firstname",
          "First Name must not more than 10 characters.");
      }
      
      String lastName = auinfo.getLastName();
      if ((lastName.length()) > 10) {
      errors.rejectValue("lastname",
          "lengthOfLast.CustomerInformation.lastname",
          "Last Name must not more than 10 characters.");
      }
      
      String add = auinfo.getAddress();
      if ((add.length()) > 50) {
      errors.rejectValue("address",
          "lengthOfAddress.CustomerInformation.address",
          "Address must not more than 50 characters.");
      }
      
      String ssn = auinfo.getSocialSecurityNumber();
      
      for (char c: ssn.toCharArray()) {
          if(Character.isDigit(c)) {
              ssnNum++;
          }
      }
      if(ssnNum != 10) {
          errors.rejectValue("socialSecurityNumber", 
                  "lengthOfSocialSecurityNumber.CustomerInformation.socialSecurityNumber",
                  "SSN must have all numericals.");
      }
      
      
      String pNumber = auinfo.getContactNumber();
      for (char c: pNumber.toCharArray()) {
          if(Character.isDigit(c)) {
              phNum++;
          }
      }
      if(phNum != 10) {
          errors.rejectValue("contactNumber", "lengthOfPhoneNumber.CustomerInformation.phonenumber",
                  "Phone number must be all numbers.");
      }
      
      
      
      if (!(auinfo.getPassword()).equals(auinfo.getConfirmPassword())) {
      errors.rejectValue("password",
          "matchingPassword.CustomerInformation.password",
          "Password and Confirm Password Not match.");
      }
      
      String pass = auinfo.getPassword();
      if(pass.length()<6) { 
          errors.rejectValue("password",
              "matchingPassword.CustomerInformation.password",
              "Password too small");
          }
      
      if(pass.length()>15) { 
          errors.rejectValue("password",
              "matchingPassword.CustomerInformation.password",
              "Password too big");
      }
      
      //-------------------------------------------------------------
      for (char c: pass.toCharArray()) {
          if (Character.isDigit(c)) {
              number ++;
          }
      }
      if(number<=0) {
          errors.rejectValue("password",
              "matchingPassword.CustomerInformation.password",
              "Password has no digit"); 
      }
     //-------------------------------------------------------------
     
      
      for (char c: pass.toCharArray()) {
          if (Character.isUpperCase(c)) {
              count++;
          }
      }
      if(count <= 0) {
          errors.rejectValue("password",
              "matchingPassword.CustomerInformation.password",
              "Password has no capital letter");
      }
      
      Pattern p = Pattern.compile("[!@#$%^&*+_.-]");
      Matcher match = p.matcher(pass.subSequence(0, pass.length()));
      if (match.find() == false) {
          errors.rejectValue("password",
              "matchingPassword.CustomerInformation.password",
              "Password has no special character");
      }
   } 
}