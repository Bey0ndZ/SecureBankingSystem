package edu.softwaresecurity.group5.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import edu.softwaresecurity.group5.dto.BillPayDTO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.model.AddUserInformation;
import edu.softwaresecurity.group5.model.ChangePassword;
import edu.softwaresecurity.group5.model.CustomerInformation;

public interface CustomerDAO {

	public String registerCustomer(CustomerInformation custInfo) throws NoSuchAlgorithmException;

	public void addUser(AddUserInformation addInfo) throws NoSuchAlgorithmException;
	public List<CustomerInformationDTO> retrieveUserDetails(String username);
	public List<CustomerInformationDTO> getUserList();  
	public String updateAccount(CustomerInformationDTO custInfo);  
//	public void deleteData(String id);  
	public CustomerInformationDTO getUserFromAccount(String accountNumber);  
	public String changeAccountPassword(ChangePassword custInfo);  
	public String unlockAccount(CustomerInformationDTO custInfo);
	public boolean billPayment(String loggedInUser, String accountNumber,
			String amountToBeTransferred);
	public List<BillPayDTO> getBillPayRequestForCustomer(String customerUsername);
}
