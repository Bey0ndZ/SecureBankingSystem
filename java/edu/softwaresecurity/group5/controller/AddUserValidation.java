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
	  
	  AddUserInformation addinfo = (AddUserInformation) info;
    
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "socialSecurityNumber",
		"NotEmpty.AddUserInformation.socialSecurityNumber",
		"SSN must not be Empty.");
	  
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber",
				"NotEmpty.AddUserInformation.contactNumber",
				"Contact must not be Empty.");
	  
	  String userName = addinfo.getUserName();

	  String firstName = addinfo.getFirstName();
	  if ((firstName.length()) > 10) {
      errors.rejectValue("firstName",
          "lengthOfFirst.AddUserInformation.firstName",
          "First Name must not more than 10 characters.");
      }
	  
	  String lastName = addinfo.getLastName();
	  if ((lastName.length()) > 10) {
      errors.rejectValue("lastName",
          "lengthOfLast.AddUserInformation.lastName",
          "Last Name must not more than 10 characters.");
      }
	  
	  String add = addinfo.getAddress();
	  if ((add.length()) > 50) {
      errors.rejectValue("address",
          "lengthOfAddress.AddUserInformation.address",
          "Address must not more than 50 characters.");
      }
	  
	  String ssn = addinfo.getSocialSecurityNumber();
	  
	  for (char c: ssn.toCharArray()) {
		  if(Character.isDigit(c)) {
			  ssnNum++;
		  }
	  }
	  if(ssnNum != 10) {
		  errors.rejectValue("socialSecurityNumber", 
				  "lengthOfSocialSecurityNumber.AddUserInformation.socialSecurityNumber",
				  "SSN must contain exactly 10 digits.");
	  }
	  
	  
	  String pNumber = addinfo.getContactNumber();
	  for (char c: pNumber.toCharArray()) {
		  if(Character.isDigit(c)) {
			  phNum++;
		  }
	  }
	  if(phNum != 10) {
		  errors.rejectValue("contactNumber", "lengthOfPhoneNumber.AddUserInformation.contactNumber",
				  "Phone number must contain exactly 10 digits.");
	  }
	  
	  
	  String pass = addinfo.getPassword();
	  for (char c: pass.toCharArray()) {
		  if (Character.isDigit(c)) {
			  number ++;
		  }
	  }

	  for (char c: pass.toCharArray()) {
		  if (Character.isUpperCase(c)) {
			  count++;
		  }
	  }
	  
	  Pattern p = Pattern.compile("[!@#$%^&*+_.-]");
	  Matcher match = p.matcher(pass.subSequence(0, pass.length()));
	  
	  if (pass.length()<6 || pass.length()>15 ||
				 number<=0 || count<=0 || match.find()==false || userName.length() > 10 || userName.length()==0) {
		  errors.rejectValue("userName",
			  "matchingPassword.AddUserInformation.userName",
			  "User Name or Password is invalid! Please try entering again!");
	  }
	  
	  if (!(addinfo.getPassword()).equals(addinfo.getConfirmPassword())) {
      errors.rejectValue("password",
          "matchingPassword.AddUserInformation.password",
          "Password and Confirm Password Not match.");
      }
   } 
}