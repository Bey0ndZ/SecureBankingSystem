package edu.softwaresecurity.group5.service;

import java.util.List;

import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.model.CustomerInformation;

public interface CustomerService {
	public void insertCustomerInformation(CustomerInformation custInfo);
	public List<CustomerInformationDTO> fetchUserDetails(String usernameSearch);
	public List<CustomerInformationDTO> getUserList();
	public CustomerInformationDTO getUserFromAccount(String accountNumber);  
	public String updateAccount(CustomerInformationDTO cust);  
}
