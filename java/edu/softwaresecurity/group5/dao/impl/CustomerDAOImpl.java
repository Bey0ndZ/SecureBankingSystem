package edu.softwaresecurity.group5.dao.impl;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import edu.softwaresecurity.group5.dao.CustomerDAO;
import edu.softwaresecurity.group5.model.CustomerInformation;

public class CustomerDAOImpl implements CustomerDAO {
		@Autowired private DataSource dataSource;
		public void registerCustomer(CustomerInformation custInfo) {
			try {
				Connection conn = dataSource.getConnection();
				String insertQuery = "INSERT INTO users(username, password, firstname, lastname,"
						+ "phonenumber, email, address"
						+ "values("+custInfo.getUsername()+","+ custInfo.getPassword()+","+
						custInfo.getFirstname()+","+custInfo.getLastname()+","+
						custInfo.getPhonenumber()+","+custInfo.getEmail()+","+custInfo.getAddress()+")";
				
				Statement stmt = conn.createStatement();
				stmt.addBatch(insertQuery);
			} catch (Exception ex) {
				ex.printStackTrace();
			}		
		}
	}
