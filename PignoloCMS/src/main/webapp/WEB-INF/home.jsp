<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="includes.html"%>
<title>PignoloCMS</title>
</head>
<body>

<%@include file="header.jsp"%>

<div class="container">
<div id="container" class="p-3">
	<div class="row p-3">
		<div class="col-sm-12">
			<h1 class="text-center">Bentornato ${user.username}, ecco le tue statistiche!</h1>
		</div>
	</div>
	
	<hr/>
	
	
	<div class="row">
		<div class="col-sm-6" id="submission-statistics">
			<h3>Problemi suggeriti</h3>
			<table id="suggested-problems-table" class="table table-striped table-bordered">
				<thead class="thead-dark">
					<tr>
						<th>Problema</th>
						<th>Difficoltà</th>
						<th>Sottomissioni</th>
					</tr>
				</thead>
				
				<c:forEach var="pair" items="${hotProblems}">
					<tr class='suggested-problem-row'>
						<td class='problem-link' id='${pair.key.id}'><a href="#">${pair.key.name}</a></td>
						<td>${pair.key.difficulty}</td>
						<td>${pair.value}</td>
					</tr>
				</c:forEach>			
			</table>
		</div>	
		
		<div class="col-sm-6">
			<h3>Sottomissioni</h3>
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

<div class="row">
	<canvas id="pie-chart"> 
	</canvas>
</div>

<script>
$(function() {
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
					console.log(response); 
					window.open(response, "_blank");
				}
			});
		});
	});
	
	
	$(".suggested-problem-row").each( function() {
		let row = $(this).find(".problem-link");
		let id = row.attr('id');
		
		$(this).on("click", function(e) {
			e.preventDefault(); 
			e.stopPropagation(); 
			$('#container').load("problems", "id="+id);
		});
	});
	
	
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

	
	$("#suggested-problems-table").DataTable({
		searching: false, 
		paging: false, 
		info: false,
		order: [[2, "desc"]]
	
	}); 
	
	// TODO: numeretti veri 
	
	$.ajax({
		"url": "userStatistics",
		"method": "GET",
		"success": function(response) {
			console.log(response); 
			
			let chartContext = $("#pie-chart")[0].getContext("2d"); 
			let pieChart = new Chart(chartContext, {
				type: 'pie',
				data: {
					labels: ['Correct', 'Wrong', 'Timelimit', 'Compiler Error', 'Run Error'],
					datasets: [
						{
							label: "User statistics",
							data: [response['accepted'], response['wrong'], response['timelimit'], response['compilationerror'], response['runerror']],
							backgroundColor: [ '#52CA8E', '#FF9180', '#85C3DB', '#F5CC00', '#DDEEEE'],
						}
					],
				}
			});
		},
	"error": function(response) {
		console.log("ERROR");
		console.log(response); 
	}
	});
	
});
</script>
	
</div>
</div>
<%@include file="footer.html" %>
</body>
</html>