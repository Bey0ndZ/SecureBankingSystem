<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
 pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<html>  
<head>  
<title>ALL Users</title>  
<style>  
body {  
 font-size: 20px;  
 color: teal;  
 font-family: Calibri;  
}  
  
td {  
 font-size: 15px;  
 color: black;  
 width: 100px;  
 height: 22px;  
 text-align: center;  
}  
.heading {  
 font-size: 18px;  
 color: white;  
 font: bold;  
 background-color: orange;  
 border: thick;  
}  
</style>  
</head>  
<body>  
 <center>  
    
   
   
 <b>All Users in the database</b>  
   
  
     
    
  
  <table border="1">  
   <tr>  
    <td class="heading">User Id</td>  
    <td class="heading">First Name</td>  
    <td class="heading">Last Name</td>  
    <td class="heading">Gender</td>  
    <td class="heading">City</td>  
    <td class="heading">Edit</td>  
    <td class="heading">Delete</td>  
   </tr>  
   <c:forEach var="user" items="${userList}">  
    <tr>  
     <td>${user.username}</td>  
     <td>${user.firstname}</td>  
     <td>${user.lastname}</td>   
     <td>${user.lastname}</td>  
     <td><a href="modifyUser?id=${CustomerInformationDTO.username}">Modify</a></td>  
     <td><a href="removeUser?id=${CustomerInformationDTO.username}">Remove user</a></td>  
    </tr>  
   </c:forEach>  
   <tr><td colspan="7"><a href="register">Add New User</a></td></tr>  
  </table>  
  
 </center>  
</body>  
</html>