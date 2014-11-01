package edu.softwaresecurity.group5.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.softwaresecurity.group5.dao.CustomerDAO;
import edu.softwaresecurity.group5.dto.CustomerInformationDTO;
import edu.softwaresecurity.group5.jdbc.UserRowMapper;
import edu.softwaresecurity.group5.model.AccountAttempts;
import edu.softwaresecurity.group5.model.ChangePassword;
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
		custInfo.setUserLocked(1);
		custInfo.setUserExpired(1);
		custInfo.setUserDetailsExpired(1);
		String registerCustomerQuery = "INSERT into users"
				+ "(username, password, confirmpassword,"
				+ "firstname,"
				+ "lastname, MerchantorIndividual, phonenumber,"
				+ "email, SSN, address, enabled, "
				+ "userExpired, userLocked, userDetailsExpired) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String insertIntoUserRolesTable = "INSERT into user_roles (username, role) "
				+ "VALUES (?,?)";

		JdbcTemplate jdbcTemplateForRegisterCustomer = new JdbcTemplate(
				dataSource);
		JdbcTemplate jdbcTemplateForUserRoles = new JdbcTemplate(dataSource);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hash = passwordEncoder.encode(custInfo.getPassword());

		jdbcTemplateForRegisterCustomer.update(
				registerCustomerQuery,
				new Object[] { custInfo.getUsername(), hash, hash,
						custInfo.getFirstname(), custInfo.getLastname(),
						custInfo.getSelection(), custInfo.getPhonenumber(),
						custInfo.getEmail(),
						custInfo.getSocialSecurityNumber(),
						custInfo.getAddress(), custInfo.getEnabled(),
						custInfo.getUserExpired(), custInfo.getUserLocked(),
						custInfo.getUserDetailsExpired() });

		jdbcTemplateForUserRoles.update(insertIntoUserRolesTable, new Object[] {
				custInfo.getUsername(), "ROLE_USER" });
	}

	// Privileges - Add
	public List<CustomerInformationDTO> retrieveUserDetails(String username) {
		List<CustomerInformationDTO> customerInformationToDisplay = new ArrayList<CustomerInformationDTO>();
		String retrieveDetailsQuery = "SELECT users.username, users.firstname, users.lastname, users.sex, "
				+ "users.MerchantorIndividual, users.phonenumber, users.email, "
				+ "users.address, account.accountnumber from users inner join account on users.username = account.username where users.enabled = true and users.username=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		customerInformationToDisplay = jdbcTemplate.query(retrieveDetailsQuery,
				new Object[] { username }, new UserRowMapper());
		return customerInformationToDisplay;

	}

	public List<CustomerInformationDTO> getUserList() {
		List<CustomerInformationDTO> userList = new ArrayList<CustomerInformationDTO>();

		String sql = "SELECT users.username, users.firstname, users.lastname, users.sex, "
				+ "users.MerchantorIndividual, users.phonenumber, users.email, "
				+ "users.address, account.accountnumber from users inner join account on users.username = account.username where users.enabled = true";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		userList = jdbcTemplate.query(sql, new UserRowMapper());
		return userList;
	}

	public CustomerInformationDTO getUserFromAccount(String accountNumber) {
		List<CustomerInformationDTO> customerInformationToDisplay = new ArrayList<CustomerInformationDTO>();
		String retrieveDetailsQuery = "SELECT users.username, users.firstname, users.lastname, users.sex, "
				+ "users.MerchantorIndividual, users.phonenumber, users.email, "
				+ "users.address, account.accountnumber from users inner join account on users.username = account.username where enabled = true and accountnumber=?";
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
		if (status == 1) {
			return "Updated account details Succesfully";
		}

		return "Databse not updated, please contact Branch Representative";
	}

	public String changeAccountPassword(ChangePassword custInfo) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hash = passwordEncoder.encode(custInfo.getPassword());
		String sql = "UPDATE users set password = ?,confirmpassword = ?"
				+ " where enabled = true  and username = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int status = jdbcTemplate.update(sql, new Object[] { hash, hash,
				custInfo.getUsername() });
		if (status == 1) {
			return "Updated account details Succesfully";
		}

		return "Databse not updated, please contact Branch Representative";

	}

	public String unlockAccount(CustomerInformationDTO custInfo) {

		if (verifyAccountForLock(custInfo)) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			// this otp is hardcoded for now and will be changed later. or else
			// everyone will have same otp. not secure.
			String otp = "Hardcodedfornow";
			String hash = passwordEncoder.encode(otp);
			String sql = "UPDATE users set password = ?,confirmpassword = ?, userLocked= false"
					+ " where enabled = true  and username = ?";
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			int status = jdbcTemplate.update(sql, new Object[] { hash, hash,
					custInfo.getUsername() });
			if (status == 1) {
				return "your new password is " + hash;
			}

			return "Databse not updated, please contact Branch Representative";
		}

		return "User account for "
				+ custInfo.getUsername()
				+ " is not locked, please contact adminstrator if you have login issues.";

	}

	public boolean verifyAccountForLock(CustomerInformationDTO custInfo) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String username = custInfo.getUsername();
		try {

			AccountAttempts userAttempts = jdbcTemplate
					.queryForObject(
							"SELECT username,userLocked  FROM users WHERE username = ?",
							new Object[] { username },
							new RowMapper<AccountAttempts>() {
								public AccountAttempts mapRow(ResultSet rs,
										int rowNum) throws SQLException {

									AccountAttempts user = new AccountAttempts();
									user.setUsername(rs.getString("username"));
									user.setLocked(rs.getInt("userLocked"));

									return user;
								}

							});
			if (userAttempts.isLocked() == 1) {
				return false;
			}
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

	}

}