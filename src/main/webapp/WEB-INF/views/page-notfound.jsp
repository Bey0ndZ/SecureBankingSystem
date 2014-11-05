<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>HTTP Status 404 - Page Not Found</h1>

	<c:choose>
		<c:when test="${empty username}">
			<h2>This page doesn't exist please go back to application.!</h2>
		</c:when>
		<c:otherwise>
			<h2>Username : ${username} <br/>This page doesn't exist please go back to application.!</h2>
		</c:otherwise>
	</c:choose>

</body>
</html>