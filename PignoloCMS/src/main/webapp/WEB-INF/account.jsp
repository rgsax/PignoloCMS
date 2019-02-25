<form id="credentials_form">
	<div class="form-group">
		<label>Username</label>
		<input type="text" class="form-control" name="username" value="${user.username}" disabled/>
	</div>
	<div class="form-group">
		<label>Nome</label>
		<input type="text" class="form-control" name="name" value="${user.name}"/>
	</div>
	<div class="form-group">
		<label>Cognome</label>
		<input type="text" class="form-control" name="surname" value="${user.surname}"/>
	</div>
	<div class="form-group">
		<label>Email</label>
		<input type="email" class="form-control" name="email" value="${user.email}"/>
	</div>
	<div class="form-group">
		<label>Vecchia password</label>
		<input type="password" class="form-control" name="old_password" required/>
	</div>
	<div class="form-group">
		<label>Nuova password</label>
		<input type="password" class="form-control" name="new_password"/>
	</div>
	<input type="submit" class="btn btn-success" id="submit_button" value="Salva"/>
</form>

<script>
$(function() {
	
	$("#credentials_form").on("submit", function(event) {	
		event.preventDefault();
		event.stopPropagation();
		
		let data = {
			'username' : $("[name='username']").val(),
			'name' : $("[name='username']").val(),
			'surname' : $("[name='surname']").val(),
			'email' : $("[name='email']").val(),
			'old_password' : $("[name='old_password']").val()
		};
		
		let new_password = $("[name='new_password']").val();
		if(new_password.length > 0)
			data['new_password'] = new_password;
		
		$.ajax({
			url : "updateUser",
			method : "POST",
			'data' : data,
			success : function(response) {
				if(response.length > 0)//password sbagliata
					$("#submit_button").notify(response, "error");
				else
					$("#submit_button").notify("Credenziali salvate", "success");	
			},
			error : function() {
				$("#submit_button").notify("Problema interno", "error");
			}
		});
	});
});
</script>