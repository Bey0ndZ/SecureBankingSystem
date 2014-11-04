package edu.softwaresecurity.group5.dto;

public class TicketInformationDTO {
	private String username;
	private int id;
	private boolean requestcompleted;
	private boolean requestapproved;
	private String requesttype;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isRequestcompleted() {
		return requestcompleted;
	}
	public void setRequestcompleted(boolean requestcompleted) {
		this.requestcompleted = requestcompleted;
	}
	public boolean isRequestapproved() {
		return requestapproved;
	}
	public void setRequestapproved(boolean requestapproved) {
		this.requestapproved = requestapproved;
	}
	public String getRequesttype() {
		return requesttype;
	}
	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}
	
}
