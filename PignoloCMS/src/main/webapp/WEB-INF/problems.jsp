<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table id="problem_table" class="table table-bordered table-striped">
	<thead class="thead-dark">
		<tr>
			<th>Problema</th>
			<th>Difficoltà</th>
			<th>Timelimit</th>
		</tr>
	</thead>

	<c:forEach var="problem" items="${problems}">
		<tr>
			<td><a class="problem" href="#" id="problem_id_${problem.id}">${problem.name}</a></td>
			<td>${problem.difficulty}</td>
			<td>${problem.timelimit}</td>	
		</tr>
	</c:forEach>
</table>

<script>
	$(function() {
		$("#problem_table").DataTable();
		$(".problem").each(function() {
			$(this).on("click", function(e) {
				e.preventDefault();
				e.stopPropagation();
				let id = $(this).attr("id"); 
				id = id.substring( id.lastIndexOf("_")+1 );
				
				$("#container").load("problems", "id="+id);
				
			});
		});
	});
</script>