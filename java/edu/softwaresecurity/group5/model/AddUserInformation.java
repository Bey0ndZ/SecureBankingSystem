package edu.softwaresecurity.group5.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class AddUserInformation {
	 @NotEmpty(message="Please enter your firstname.")
    private String firstName;
    @NotEmpty(message="Please enter your lastname.")
    private String lastName;
    @NotEmpty(message="Please enter your phonenumber.")
    @Size(min = 10, max = 10, message = "Phone Number must be of exactly 10 digits")
    private String contactNumber;
    @NotEmpty(message="Please enter your address.")
    private String address;
    @Email(message="Your email is invalid.")
    @NotEmpty(message="Please enter your email.")
    private String emailAddress_addUser;
    @NotEmpty(message="Please enter your preferred username.")
    private String userName;
    @NotEmpty(message = "Please enter your password.")
    @Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
    private String password;
    @NotEmpty(message = "Please enter your password again to confirm.")
    @Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
    private String confirmPassword;
    @Size(min = 10, max = 10, message = "SSN must be of exactly 10 digits")
    private String socialSecurityNumber;
    private String selection;
    
    
    
    
    
    private int enabled;
    
    public int getEnabled() {
        return enabled;
    }
    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSelection() {
        return selection;
    }
    public void setSelection(String selection) {
        this.selection = selection;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getEmailAddress_addUser() {
        return emailAddress_addUser;
    }
    public void setEmailAddress_addUser(String emailAddress_addUser) {
        this.emailAddress_addUser = emailAddress_addUser;
    }
    
    public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}
	public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
