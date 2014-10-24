package edu.softwaresecurity.group5.dao.impl;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.softwaresecurity.group5.dao.CustomerDAO;
import edu.softwaresecurity.group5.model.CustomerInformation;

/*Using Spring JDBC Template
  Reasons: Better connection management, no writing XML files
  Cleans up resources by releasing DB connection
  Better error detection 
*/
public class CustomerDAOImpl implements CustomerDAO {
	@Autowired
	DataSource dataSource;
	
	public void registerCustomer(CustomerInformation custInfo) {
		custInfo.setEnabled(1);
		String insertQuery = "INSERT into users" + "(username, password, firstname,"
				+ "lastname, MerchantorIndividual, phonenumber,"
				+ "email, SSN, address, enabled) VALUES (?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(insertQuery, new Object[] {custInfo.getUsername(),
				custInfo.getPassword(), custInfo.getFirstname(),
				custInfo.getLastname(), custInfo.getSelection(),
				custInfo.getPhonenumber(), custInfo.getEmail(), 
				custInfo.getSSN(), custInfo.getAddress(), custInfo.getEnabled()});		
	}
	
//	// Privileges - Add
//	public ArrayList<CustomerInformation> retrieveUserDetails(String username) {
//		
//		
//	}
}