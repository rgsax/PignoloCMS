<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table id="questions_table" class="table table-bordered table-striped">
	<thead class="thead-dark">
		<tr>
			<th>Problema</th>
			<th>Utente</th>
			<th>Domanda</th>
			<th></th>
		</tr>
	</thead>
	<c:forEach var="question" items="${questions}">
		<c:if test="${question.user.id != user.id}">
			<tr>
				<td>${question.problem.name}</td>
				<td>${question.user.username}</td>
				<td id="question_${question.id}">${question.question}</td>
				<td><button class="btn btn-primary answer_button" id="answer_button_${question.id}">Rispondi</button>
			</tr>
		</c:if>
	</c:forEach>
</table>
<div id="form_div">
</div>

<script>
$(function() {
	$("#questions_table").DataTable();
	let form_div = $("#form_div");
	
	$(".answer_button").each(function() {
		$(this).on("click", function(event) {
			event.preventDefault();
			event.stopPropagation();
			
			let form = $("<form></form>");
			
			let id = $(this).attr("id");
			id = id.substring(id.lastIndexOf("_") + 1);
			
			let question_div = $("<div class='form-group'></div>")
			question_div.html($("#question_" + id).html());
			
			let answer = $("<div class='form-group'><textarea id='answer_text'></textarea></div>");
			let submit_btn = $("<input type='submit' class='btn btn-primary my-3'/>");
			
			form.append(question_div, answer, submit_btn);			
			form_div.append(form);
			$("#answer_text").summernote();
			
			$.scrollTo(form);
			form_div.addClass("border border-primary rounded p-3");
			
			
			form.on("submit", function(event) {
				event.preventDefault();
				event.stopPropagation();
				
				let answer_text =  $("#answer_text").summernote("code");
				if(answer_text.length > 0)
					$.ajax({
						url: "createAnswer",
						method: "GET",
						data: {
							question: id,
							answer: answer_text
						},
						success: function() {
							$.notify("Risposta inviata", "success");
							form.remove();
						},
						error: function() {
							submit_btn.notify("Errore interno, risposta non inviata", "error");
						}
					});
				else 
					$.notify("Impossibile mandare una risposta vuota", "error");
			});
		});
	});
});
</script>