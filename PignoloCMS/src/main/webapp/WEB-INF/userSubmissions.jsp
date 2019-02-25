<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="table table-striped">
	<tr class="thead-dark">
		<th>Problema</th>
		<th>Linguaggio<th>
		<th>Status</th>
	</tr>
	
	<c:forEach var="submission" items="${submissions}">
		<tr>
			<td>${submission.problem.name}</td>
			<td>${submission.language.name}</td>
			<td>${submission.status.name}</td>
		</tr>
	</c:forEach>
	
</table>