<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="WEB-INF/includes.html"%>
<title>PignoloCMS</title>

<script>
$(function() {
	$("#login_form").on("submit", function(event) {
		event.preventDefault();
		event.stopPropagation();
		
		$.ajax({
			url : "login",
			method : "POST",
			data : {
				username : $("#username_field").val(),
				password : $("#password_field").val()
			},
			beforeSend : function() {
				$("body").css("cursor", "progress");
			},
			success : function()  {
				window.location.reload();
			},
			error : function() {
				$("#submit_button").notify("Nome utente o password errati", "error");
			},
			complete : function() {
				$("body").css("cursor", "default");
			}
		});
	});
});
</script>

</head>
<body>

<%@include file="WEB-INF/header.jsp"%>

<div class="container">
	<div id="container" class="row p-3">
	<form id="login_form">
		<div class="form-group">
			<label>Username </label><input type="text" class="form-control" name="username" id="username_field"/>
		</div>
		<div class="form-group">
			<label>Password </label><input type="password" class="form-control" name="password" id="password_field"/>
		</div>
		<input id="submit_button" type="submit" class="btn btn-primary" value="Login"/>
	</form>
	</div>
	<div class="row p-3">
		<a href="registration.jsp">Registrati</a>
	</div>
</div>
<%@include file="WEB-INF/footer.html" %>
</body>
</html>