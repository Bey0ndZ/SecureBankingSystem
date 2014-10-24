package edu.softwaresecurity.group5.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.softwaresecurity.group5.dto.CustomerInformationDTO;

public class UserExtractor implements ResultSetExtractor<CustomerInformationDTO> {
	
	 public CustomerInformationDTO extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException {
		 
	  CustomerInformationDTO custInfoDTO = new CustomerInformationDTO();
	  
	  custInfoDTO.setUsername(resultSet.getString(1));
	  custInfoDTO.setFirstname(resultSet.getString(2));
	  custInfoDTO.setLastname(resultSet.getString(3));
	  custInfoDTO.setSelection(resultSet.getString(4));
	  custInfoDTO.setPhonenumber(resultSet.getString(5));
	  custInfoDTO.setEmail(resultSet.getString(6));
	  custInfoDTO.setAddress(resultSet.getString(7));
	  
	  return custInfoDTO;
	 }
}
