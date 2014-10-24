package edu.softwaresecurity.group5.dao.impl;

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
		String insertQuery = "INSERT into users" + "(username, password, enabled) VALUES (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(insertQuery, new Object[] {custInfo.getUsername(),
				custInfo.getPassword(), custInfo.getEnabled()});
		
		
	}
	
	// Privileges - Add
	public String removeCustomer(String username) {
		String deleteQuery = "DELETE from users where username="+username;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int deletedRows = jdbcTemplate.update(deleteQuery);
		if (deletedRows==1) {
			return "User deleted";
		} else {
			return "User cannot be deleted";
		}
	}
}