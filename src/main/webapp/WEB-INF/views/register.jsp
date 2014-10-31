<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
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
    <style>
    body {
        padding-top: 70px;
        /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
    }
    </style>
    
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

        <div class="row">
            <div class="col-lg-12 text-center">
                <h1>Register here</h1>
                <form:form method="POST" action="register" modelAttribute="registerForm">
					<i><b>New User - Sign Up!</b></i> <br/>
					<b>User Name:</b><FONT color="red"><form:errors path="username" /></FONT> <input type="text" name="username" value="${registerForm.username}" id="username" style="color:#999;" /><br/>
					<b>Password:</b><FONT color="red"><form:errors path="password" /></FONT> <input type="password" name="password" id="password" style="color:#999;" /><br/>
					<b>Confirm Password:</b> <input type="password" name="confirmPassword" id="cfrm_pwd" style="color:#999;" /><br/>
					<b>First Name:</b> <FONT color="red"><form:errors path="firstname" /></FONT> <input type="text" name="firstname" value="${registerForm.firstname}" id="f_name" style="color:#999;" /><br/>
					<b>Last Name:</b> <FONT color="red"><form:errors path="lastname" /></FONT><input type="text" name="lastname" value="${registerForm.lastname}" id="l_name" style="color:#999;" /><br/>
					<b>Individual or Merchant:</b>
					<select name="selection">
						<option value="Individual">Individual</option>
						<option value="Merchant">Merchant</option>
					</select><br/><br/>
					<b>Phone Number:</b> <FONT color="red"> <form:errors path="phonenumber" /> </FONT> <input type="text" name="phonenumber" value="${registerForm.phonenumber}" id="contact" style="color:#999;" /><br/>
					<b>Email Address:</b> <input type="email" name="email" id="email" style="color:#999;" /><br/>
					<b>Social Security Number:</b> <FONT color="red"><form:errors path="socialSecurityNumber" /> </FONT> <input type="text" name="socialSecurityNumber" value="${registerForm.socialSecurityNumber}" id="socialSecurityNumber" style="color:#999;" /><br/>
					<b>Address:</b> <FONT color="red"><form:errors path="address" /></FONT> <input type="text" name="address" value="${registerForm.address}" id="add" style="color:#999;" /><br/>	
					<%
          				ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LelnPwSAAAAAEdizJnYFeutV2At7VHSkC9PYxZX", "6LelnPwSAAAAAEIVuVPz5_wWsq3skomEaVJ_5eZH", false);
          				out.print(c.createRecaptchaHtml(null, null));
        			%>							
					<br/> <br/> <input type="submit" style="margin-right: 5%" name="login" id="log_in" value="Register!" />
				</form:form>
            </div>
        </div>
        <!-- /.row -->

    </div>
    <!-- /.container -->

    <!-- jQuery Version 1.11.0 -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</body>

</html>
