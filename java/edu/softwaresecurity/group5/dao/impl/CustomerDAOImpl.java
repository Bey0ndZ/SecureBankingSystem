package edu.softwaresecurity.group5.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.softwaresecurity.group5.dao.CustomerDAO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.jdbc.UserRowMapper;
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
		String registerCustomerQuery = "INSERT into users"
				+ "(username, password, confirmpassword,"
				+ "firstname,"
				+ "lastname, MerchantorIndividual, phonenumber,"
				+ "email, SSN, address, enabled) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		String insertIntoUserRolesTable = "INSERT into user_roles (username, role) "
				+ "VALUES (?,?)";

		JdbcTemplate jdbcTemplateForRegisterCustomer = new JdbcTemplate(
				dataSource);
		JdbcTemplate jdbcTemplateForUserRoles = new JdbcTemplate(dataSource);

		String salt = "$$Random^^^Salt**Valur&&ForBetter&&&&Protection###";
		String saltedString = salt + custInfo.getPassword();
		String hash = DigestUtils.md5Hex(saltedString);

		jdbcTemplateForRegisterCustomer.update(
				registerCustomerQuery,
				new Object[] { custInfo.getUsername(), hash, hash,
						custInfo.getFirstname(), custInfo.getLastname(),
						custInfo.getSelection(), custInfo.getPhonenumber(),
						custInfo.getEmail(), custInfo.getSSN(),
						custInfo.getAddress(), custInfo.getEnabled() });

		jdbcTemplateForUserRoles.update(insertIntoUserRolesTable, new Object[] {
				custInfo.getUsername(), "ROLE_USER" });
	}

	// Privileges - Add
	public List<CustomerInformationDTO> retrieveUserDetails(String username) {
		List<CustomerInformationDTO> customerInformationToDisplay = new ArrayList<CustomerInformationDTO>();
		String retrieveDetailsQuery = "SELECT username, firstname, lastname, "
				+ "MerchantorIndividual, phonenumber, email, "
				+ "address from users where enabled = true and username=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		customerInformationToDisplay = jdbcTemplate.query(retrieveDetailsQuery,
				new Object[] { username }, new UserRowMapper());
		return customerInformationToDisplay;

	}

	public List<CustomerInformationDTO> getUserList() {
		List<CustomerInformationDTO> userList = new ArrayList<CustomerInformationDTO>();

		String sql = "select * from users where enabled = true";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new UserRowMapper());
		return userList;
	}

	public CustomerInformationDTO getUserFromAccount(String accountNumber) {
		List<CustomerInformationDTO> customerInformationToDisplay = new ArrayList<CustomerInformationDTO>();
		String retrieveDetailsQuery = "SELECT users.username, users.firstname, users.lastname, "
				+ "users.MerchantorIndividual, users.phonenumber, users.email, "
				+ "users.address from users inner join account on users.username = account.username where enabled = true and accountnumber=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		customerInformationToDisplay = jdbcTemplate.query(retrieveDetailsQuery,
				new Object[] { accountNumber }, new UserRowMapper());
		return customerInformationToDisplay.get(0);

	}

	public String updateAccount(CustomerInformationDTO custInfo) {
		// TODO Auto-generated method stub
		String sql = "UPDATE users set firstname = ?,lastname = ?, phonenumber = ?, email = ?, address=?"
				+ " where enabled = true  and username = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int status = jdbcTemplate.update(sql,
				new Object[] { custInfo.getFirstname(), custInfo.getLastname(),
						custInfo.getPhonenumber(), custInfo.getEmail(),
						custInfo.getAddress(), custInfo.getUsername() });
		if(status==1){
			return "Updated account details Succesfully";
		}

		return "Databse not updated, please contact Branch Representative";
	}
}