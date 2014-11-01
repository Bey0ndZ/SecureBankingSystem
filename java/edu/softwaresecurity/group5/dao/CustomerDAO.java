package edu.softwaresecurity.group5.dao;

import java.util.List;

import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.model.ChangePassword;
import edu.softwaresecurity.group5.model.CustomerInformation;

public interface CustomerDAO {
	public void registerCustomer(CustomerInformation custInfo);
	public List<CustomerInformationDTO> retrieveUserDetails(String username);
	public List<CustomerInformationDTO> getUserList();  
	public String updateAccount(CustomerInformationDTO custInfo);  
//	public void deleteData(String id);  
	public CustomerInformationDTO getUserFromAccount(String accountNumber);  
	public String changeAccountPassword(ChangePassword custInfo);  
	public String unlockAccount(CustomerInformationDTO custInfo);
}
