<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-sm-12">
<table id="scoreboard" class="table table-striped table-bordered">
<thead class="thead-dark">
	<tr>
		<th>Utente</th>
		<th>Punti</th>
	</tr>
</thead>
<c:forEach var="row" items="${scoreboard}">
	<tr>
		<td><a href="#" class="user_class" id="${row.key}">${row.key}</a></td>
		<td>${row.value}</td>
	</tr>	
</c:forEach>
</table>
</div>
<script>
$(function(){
	$("#scoreboard").DataTable({
		"order" : [[1, "desc"]]
	});
	
	$(".user_class").each(function() {
		$(this).on("click", function(event) {
			event.preventDefault();
			event.stopPropagation();
			let username = $(this).attr("id");
			$("#container").load("userProfile", "username=" + username);
		});
	});
});
</script>