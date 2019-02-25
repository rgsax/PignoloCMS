<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="WEB-INF/includes.html"%>
<title>Registrati</title>
</head>
<body>

<%@include file="WEB-INF/header.jsp"%>

<div class="container">
	<div id="container" class="row p-3">
		<form id="registration_form" class="col-sm-12" action="register" method="post">
			<div class="form-group">	
				<label>Username </label><input type="text" class="form-control" name="username" id="username_field"/>
			</div>
			<div class="form-group">
				<label>Nome </label><input type="text" class="form-control" name="name" id="name_field"/>
			</div>
			<div class="form-group">
				<label>Cognome </label><input type="text" class="form-control" name="surname" id="surname_field"/>
			</div>
			<div class="form-group">
				<label>Email </label><input type="email" class="form-control" name="email" id="email_field"/>
			</div>
			<div class="form-group">
				<label>Password </label><input type="password" class="form-control" name="password" id="password_field"/>
			</div>
			<input type="submit" class="btn btn-primary" value="Registrati"/><br/>
		</form>
	</div>
</div>
<%@include file="WEB-INF/footer.html" %>
</body>
</html>