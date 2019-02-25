<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table id="questions-table" class="table table-bordered table-striped">
	<thead class="thead-dark">
		<tr>
			<th>Problema</th>
			<th>Domanda</th>
			<th></th>
		</tr>
	</thead>
	
	<c:forEach var="question" items="${questions}">
		<tr id="question_row_${question.id}">
			<td>${question.problem.name}</td>
			<td>${question.question}</td>	
			<td><button id="delete_question_${question.id}" class="btn btn-danger btn-delete-question">Elimina</button></td>	
		</tr>
	</c:forEach>
</table>



<script>
$(function() {
	$(".btn-delete-question").each(function() {
		let id = $(this).attr('id'); 
		id = id.substring( id.lastIndexOf("_") + 1 );
		
		$(this).on("click", function(e) {
			e.preventDefault(); 
			e.stopPropagation();  
			
			$.ajax({
				url: "deleteQuestion",
				method: "GET", 
				data: {'question_id': id},
				success: function() {
					$.notify("Domanda eliminata!", "success"); 	
					$("#question_row_"+id).remove(); 
				}, 
				error: function() {
					$.notify("Errore interno, domanda non eliminata!", "error");
				}
			});
		});
	});
});
</script>