package edu.softwaresecurity.group5.dto;

import org.springframework.beans.factory.annotation.Autowired;

import edu.softwaresecurity.group5.dao.CustomerDAO;
import edu.softwaresecurity.group5.model.CustomerInformation;

public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDAO custDAO;
	
	public void insertCustomerInformation(CustomerInformation custInfo) {
		custDAO.registerCustomer(custInfo);
	}
}
