<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
	<div class="col-sm-6">
		<h2>${userProfile.username}</h2>
		<ul class="list-group list-responsive">
			<li class="list-group-item" id="username">Username: <b>${userProfile.name}</b></li>
			<li class="list-group-item" id="name">Nome: <b>${userProfile.name}</b></li>
			<li class="list-group-item" id="surname">Cognome: <b>${userProfile.surname}</b></li>
			<li class="list-group-item" id="email">Email: <b>${userProfile.email}</b></li>
		</ul>
	</div>
	
	<div class="col-sm-6">
		<h2>Sottomissioni</h2>
		<table id="problem_table" class="table table-striped table-responsive">
			<thead class="thead-dark">
				<tr>
					<th>Problema</th>
					<th>Linguaggio</th>
					<th>Status</th>
				</tr>
			</thead>
			
			<c:forEach var="submission" items="${submissions}">
				<tr class="submission_row" id="submission_${submission.id}" data-toggle="tooltip" data-placement="bottom" title="Mostra sottomissione su Pastebin">
					<td>${submission.problem.name}</td>
					<td>${submission.language.name}</td>
					<td class="submission_status">${submission.status.name}</td>
				</tr>
			</c:forEach>			
		</table>
	</div>
</div>

<script>
$(function() {
	$("#problem_table").DataTable();
	$(".submission_row").each(function() {
		let status = $(this).children(".submission_status").text();
		if(status === "CORRECT")
			$(this).addClass("table-success");
		else if(status === "WRONG" || status === "RUN_ERROR")
			$(this).addClass("table-danger");
		else if(status === "COMPILER_ERROR")
			$(this).addClass("table-warning");
	});
	
	$(".submission_row").each( function() {
		$(this).on("click", function (e) {
			e.preventDefault(); 
			e.stopPropagation(); 
			
			let id = $(this).attr('id'); 
			id = id.substring( id.lastIndexOf("_")+1); 
		
			$.ajax({
				url: "getSourcecode",
				method: "GET",
				data: { "submission" : id},
				success: function(response) {
					window.open(response, "_blank");
				}
			});
		});
	});
});
</script>