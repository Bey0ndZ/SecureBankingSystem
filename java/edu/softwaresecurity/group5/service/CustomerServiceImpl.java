package edu.softwaresecurity.group5.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.softwaresecurity.group5.dao.CustomerDAO;
import edu.softwaresecurity.group5.dto.BillPayDTO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.model.AddUserInformation;
import edu.softwaresecurity.group5.model.ChangePassword;
import edu.softwaresecurity.group5.model.CustomerInformation;

public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDAO custDAO;
	
	public String insertCustomerInformation(CustomerInformation custInfo) throws NoSuchAlgorithmException {
		return custDAO.registerCustomer(custInfo);
	}
	
	public void insertAddUserInformation(AddUserInformation addInfo) throws NoSuchAlgorithmException {
		custDAO.addUser(addInfo);
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

	public String updateAccount(CustomerInformationDTO cust) {
		// TODO Auto-generated method stub
		return custDAO.updateAccount(cust);
	}
	
	public String changeAccountPassword(ChangePassword cust) {
		// TODO Auto-generated method stub
		return custDAO.changeAccountPassword(cust);
	}
	public String unlockAccount(CustomerInformationDTO cust) {
		// TODO Auto-generated method stub
		return custDAO.unlockAccount(cust);
	}

	public boolean processBillPay(String loggedInUser, String accountNumber,
			String amountToBeTransferred) {
		return custDAO.billPayment(loggedInUser, accountNumber, amountToBeTransferred);
	}

	public List<BillPayDTO> returnBillPaymentDetails(String username) {
		return custDAO.getBillPayRequestForCustomer(username);
	}

	public String debitAmountForCustomer(String username, float debitAmount) {
		return custDAO.debitFromUserAccount(username, debitAmount);
	}

	public String creditAmountForCustomer(String usernameLoggedIn,
			Float creditAmountFloat) {
		return custDAO.creditToUserAccount(usernameLoggedIn, creditAmountFloat);
	}
}
