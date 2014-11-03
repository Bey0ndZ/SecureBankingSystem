<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Registration page</title>

    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/sb-admin.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${pageContext.request.contextPath}/resources/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
    body {
        padding-top: 70px;
        /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
    }
    </style>
    
    <script>
    	function check() {
    		 document.getElementById("male").checked = true;
    	}
    	
    	function uncheck() {
   		 document.getElementById("female").checked = false;
   		}
    </script>
    
</head>

<body>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Register</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#">About</a>
                    </li>
                    <li>
                        <a href="#">Services</a>
                    </li>
                    <li>
                        <a href="#">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                          Register
                        </h1>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-bar-chart-o fa-fw"></i> Enter your information or <a href="index">Sign In</a></h3>
                            </div>
            
                            <div class="panel-body">
                                <div id="morris-area-chart">
                                <form:form method="POST" action="register" modelAttribute="registerForm">
									<i><b>New User - Sign Up!</b></i> <br/>
									<c:if test="${not empty errorMsg}">
										<h3> ${errorMsg} </h3>
                                    </c:if>
									<b>User Name:</b><FONT color="red"><form:errors path="username" /></FONT><br/> <input type="text" name="username" id="username" style="color:#999;" /><br/> <br/>
									<b>Password:</b> <FONT color="red"><form:errors path="password" /></FONT><br/><input type="password" name="password" id="password" style="color:#999;" /><br/> <br/>
									<b>Confirm Password:</b><br/> <input type="password" name="confirmPassword" id="cfrm_pwd" style="color:#999;" /><br/><br/>
									<b>First Name:</b>  <FONT color="red"><form:errors path="firstname" /></FONT><br/> <input type="text" name="firstname" id="f_name" style="color:#999;" /><br/><br/>
									<b>Last Name:</b>  <FONT color="red"><form:errors path="lastname" /></FONT><br/><input type="text" name="lastname" id="l_name" style="color:#999;" /><br/><br/>
									<b>Sex: </b><br/> <input type="radio" name="sex" value="Male" id="male"/> Male <br/>
									<input type="radio" name="sex" value="Female" id="male"/> Female <br/><br/>
									<b>Individual or Merchant:</b><br/>
									<select name="selection">
										<option value="Individual">Individual</option>
										<option value="Merchant">Merchant</option>
									</select><br/><br/>
									<b>Phone Number:</b> <FONT color="red"> <form:errors path="phonenumber" /> </FONT><br/><input type="text" name="phonenumber" id="contact" style="color:#999;" /><br/><br/>
									<b>Email Address:</b> <br/><input type="email" name="email" id="email" style="color:#999;" /><br/><br/>
									<b>Social Security Number:</b> <FONT color="red"><form:errors path="socialSecurityNumber" /> </FONT><br/><input type="text" name="socialSecurityNumber" id="socialSecurityNumber" style="color:#999;" /><br/><br/>
									<b>Address:</b><FONT color="red"> <form:errors path="address" /></FONT><br/><input type="text" name="address" id="add" style="color:#999;" /><br/><br/>
									<b>Captcha:</b><br/>	
									<%
				          				ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LelnPwSAAAAAEdizJnYFeutV2At7VHSkC9PYxZX", "6LelnPwSAAAAAEIVuVPz5_wWsq3skomEaVJ_5eZH", false);
				          				out.print(c.createRecaptchaHtml(null, null));
				        			%>							
									<br/><h4><input type="submit" style="margin-right: 5%" name="login" id="log_in" value="Register" /></h4>
									
									<c:if test="${not empty successMsg}">
										<h4> ${successMsg} <a href = "index"> Click here to LogIn!</a> </h4>
                                    </c:if>
								</form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
                
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /.container -->

    <!-- jQuery Version 1.11.0 -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</body>

</html>
