<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE HTML>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<!--Google Fonts-->
	<link href='http://fonts.googleapis.com/css@family=Open+Sans_3A400,300,300italic,400italic,600,600italic,700,700italic' rel='stylesheet' type='text/css'>
	<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/resources/css/jquery.fancybox.css" rel="stylesheet" type="text/css" />
	
	<title>Bank of G5!</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
	<link rel="shortcut icon" href="img/favicon.png" />

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.flexslider-min.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/hoverIntent.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.sfmenu.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/retina.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/custom.js"> </script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.fancybox.js"> </script>

<script src=" https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"></script>


	<script type="text/javascript">
	jQuery(window).load(function() {
		jQuery("#pendingButton").css("display","none");
	//jQuery('.slider').flexslider();
	 
	// Create the dropdown base
	jQuery("<select />").appendTo(".topmenu");
	
	// Create default option "Go to..."
	jQuery("<option />", {
	 "selected": "selected",
	 "value"   : "",
	 "text"    : "Menu"
	}).appendTo(".topmenu select");
	
	jQuery("#transfer_amount").change(function()
			{
		
		var value=jQuery("#transfer_amount").val();
		
		if(value<10000)
			{
			jQuery("#transferButton").css("display","block");
			jQuery("#pendingButton").css("display","none");
			//<input type="submit" name="pending" id="pendingButton" value="Approve Transfer" />
			
			//jQuery.append("<input type="submit" name="transfer" id="transferButton" value="Request" />")
			}
		else
			{
			jQuery("#pendingButton").css("display","block");
			jQuery("#transferButton").css("display","none");
			//jQuery.append("<input type="submit" name="pending" id="pendingButton" value="Approve Transfer" />")
			}
	});
	
	// Populate dropdown with menu items
	jQuery(".topmenu a").each(function() {
	var el = jQuery(this);
	jQuery("<option />", {
	   "value"   : el.attr("href"),
	   "text"    : el.text()
	}).appendTo(".topmenu select");
	});

	jQuery(".topmenu select").change(function() {
	window.location = jQuery(this).find("option:selected").val();
	});
	
	//jQuery('.fancybox').fancybox();	
		
	});
	
	</script>
</head>

<body>

	<!-- Start of top wrapper -->
	<div id="top_wrapper">
		<!-- Start of content wrapper -->
		<div class="content_wrapper">
		</div>
		<!-- End of content wrapper -->
	
		<!-- Clear Fix -->
		<div class="clear"></div>
	</div><!-- End of top wrapper -->
	
	<!-- Start of header wrapper -->
	<div id="header_wrapper">
	
		<!-- Start of content wrapper -->
		<div class="content_wrapper">
		
			<!-- Start of logo -->
			<div id="logo">
				<a href="#"><img src="${pageContext.request.contextPath}/resources/img/bank_logo.png" width="250" height="80" /></a>
			</div><!-- End of logo -->
	
			<!-- Start of top menu wrapper -->
			<div class="topmenuwrapper">		
				<!-- Start of topmenu -->
				<nav class="topmenu"> 
					<ul class="sf-menu">
						<li><a href="accountSummary.jsp">Accounts</a></li>  
						<li><a href="billPay.jsp">Bill Pay</a></li>
						<li><a href="transferMoney.jsp">Transfer Money</a>
							<ul>
								<li><a href="transferMoney.jsp"> Make A Transfer</a></li>
								<li><a href="transferActivity.jsp"> View Transfer Activity</a></li>
							</ul>
						</li>
						<li><a href="">Debit/Credit Funds</a>
							<ul>
								<li><a href="debitAmount.jsp"> Debit Money</a></li>
								<li><a href="creditAmount.jsp"> Credit Money</a></li>
							</ul>
						</li>
						<li><a href="helpSupport.jsp">Help and Support</a></li>
					</ul>
					
				</nav><!-- End of topmenu -->
	
				<!-- Start of header phone -->
				<div class="header_phone">
				Contact: (000) 000-0000
				</div>
				<!-- End of header phone -->
				<!-- Clear Fix -->
				<div class="clear"></div>
			</div><!-- End of top menu wrapper -->
		</div><!-- End of content wrapper -->

		<!-- Clear Fix --><div class="clear"></div>

	</div><!-- End of header wrapper -->

	
	<!-- Start of content wrapper -->
	<div id="contentwrapper">
		<!-- Start of content wrapper -->
		<div class="content_wrapper">
			<div class="contentright" style="width: 100%">
			<form:form method="post" action="transferMoney" modelAttribute="accountNumber">	
			<i><b>Transfer Money at Bank of G5!</b></i>				
<<<<<<< Updated upstream
					<b>Account Number :</b> <br/> <input type="text" name="verifyUser" value="${param.verifyUser}" id="accountNumber_RemoveUser" style="color:#999;" /><br/>
=======
					<b>Account Number :</b> <br/> <input type="text" name="verifyUser" id="accountNumber_RemoveUser"  style="color:#999;" /><br/>
>>>>>>> Stashed changes
					<a> <input type="submit" style="margin-right: 5%" name="SearchUser" id="searchUserButton" value="Search User" /></a> <br/> <br/> <br/> <br/>
				</form:form>
				<c:if test="${not empty customerDetails}">
					  
				<b>First Name:</b> ${customerDetails.firstname}<br/>
				<b>Last Name:</b> ${customerDetails.lastname}<br/>
				<b>Contact:</b> ${customerDetails.phonenumber}<br/> <br/>
				<b>Address:</b> ${customerDetails.address}<br/> <br/>
				<b>Email Address:</b> ${customerDetails.email}<br/><br/>
				<b>Account no:</b> ${param.verifyUser}<br/><br/>
				
			
			<!-- commented by shivam, to enable above form temporary for chaitali to start working. -->
				<form:form method="post" action="transferMoneyConfirmation" modelAttribute="transfer" >
					<i><b>Transfer Money at Bank of G5!</b></i> <br/>					
					<p> Current Account Balance: PRINT BALANCE HERE --</p>
	
					<input type="text" name="accountNo" id="accountNo" value="${param.verifyUser}" style="color:#999" visibility:"hidden" /><br/>
<!-- 					<h6>Transfer Money To:</h6><input type="number" name="transferTo" id="receiver" style="color:#999;" /><br/> -->
					<h6>Amount:</h6><input type="number" name="amount" id="transfer_amount" style="color:#999;" /> <br/>
					<input type="submit" name="action" id="transferButton" value="Transfer" />
					<input type="submit" name="action" id="pendingButton" value="Approve Transfer" />
				</form:form>
				
				</c:if>
				<c:if test="${not empty submitMessage}">
											${submitMessage}
										</c:if>
				
				
			</div>
	
			<div class="clear"></div>
		</div><!-- End of content wrapper -->

		<!-- Clear Fix --><div class="clear"></div>

	</div><!-- End of content wrapper -->

	<!-- Start of bottom wrapper -->
	<div id="bottom_wrapper">

		<!-- Start of content wrapper -->
		<div class="content_wrapper">
		
			<!-- Start of one fourth first -->
			<div class="one_fourth_first">
			<h4>FTR_TEXT_1</h4>
				<ul>
					<li><a href="#">LINK_1</a></li>
					<li><a href="#">LINK_2</a></li>			
				</ul>
			</div><!-- End of one fourth first -->
	
			<!-- Start of one fourth -->
			<div class="one_fourth">
				<h4>FTR_TEXT_2</h4>	
				<ul>
					<li><a href="#">LINK_1_1</a></li>
					<li><a href="#">LINK_2_1</a></li>			
				</ul>	
			</div><!-- End of one fourth -->
	
			<!-- Start of one fourth -->
			<div class="one_fourth">
			<h4>FTR_TEXT_3</h4>		
				<ul>
					<li><a href="#">LINK_1_2</a></li>
					<li><a href="#">LINK_2_2</a></li>			
				</ul>
			</div><!-- End of one fourth -->

		</div><!-- End of content wrapper -->

		<!-- Clear Fix --><div class="clear"></div>

	</div><!-- End of bottom wrapper -->
</body>
</html>
