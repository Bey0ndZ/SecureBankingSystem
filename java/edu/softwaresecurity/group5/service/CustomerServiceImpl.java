package edu.softwaresecurity.group5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.softwaresecurity.group5.dao.CustomerDAO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.model.CustomerInformation;

public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDAO custDAO;
	
	public void insertCustomerInformation(CustomerInformation custInfo) {
		custDAO.registerCustomer(custInfo);
	}
	
	public List<CustomerInformationDTO> fetchUserDetails(String usernameSearch) {
		return custDAO.retrieveUserDetails(usernameSearch);
	}
	public List<CustomerInformationDTO> getUserList() {  
	  return custDAO.getUserList();  
	 }  
	public CustomerInformationDTO getUserFromAccount(String accountNumber){
		return custDAO.getUserFromAccount(accountNumber);
	}
}
