<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--  Far comparire lista di domande/risposte in una tabella  -->

<div class="row">
	<div class="col-sm-8">
		<h1>${problem.name}</h1>
		<p>${problem.description}</p>
		
		<form id="file_form" enctype="multipart/form-data">
			<div class="form-group">
			<select name="language" class="form-control">
				<c:forEach var="language" items="${languages}">
					<option value="${language.id}">${language.name}</option>
				</c:forEach>
			</select>
			</div>
			<input type="hidden" name="problem" value="${problem.id}" />
			<div class="custom-file">
				<input class="custom-file-input" type="file" name="source" id="uploadBtn"/>
				<label id="file_label" class="custom-file-label" for="uploadBtn">Sfoglia...</label>
			</div>
			<button class="btn btn-primary my-3" id="submitBtn" disabled>Sottometti soluzione</button>
		</form>
	</div>
	<div class="col-sm-4">
		<h2>Statistiche del problema</h2>
		<table class="table table-responsive">
		  <tbody>
		    <tr>
		      <th scope="row">Sottomissioni corrette</th>
		      <td id="correct-submissions"> - </td>  
		    </tr>
		    <tr>
   		      <th scope="col">Sottomissioni medie</th>
		      <td id="avg-submissions"> - </td>  
		    </tr>
		    <tr>
  		      <th scope="col">Sottomissioni totali</th>	    
		      <td id="total-submissions"> - </td>  
		    </tr>
		    <tr>
		      <th scope="col">Tue sottomissioni</th>
		      <td id="user-submissions"> - </td>  
		    </tr>
		    <tr id="status-row">
		      <th scope="col">Status</th>
		      <td id="user-status"> - </td>  
		    </tr>
		  </tbody>
		</table>
		
		<div>
		<c:if test="${not empty tags}">
		<h2>Tags problema</h2>
			<c:forEach var="tag" items="${tags}">
				<h3><span class="badge badge-pill badge-info">${tag.name}</span></h3>
			</c:forEach>
		</c:if>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-sm-8 p-3 border border-primary rounded">
		<textarea id="question_textarea"></textarea>
		<button class="btn btn-primary my-3" id="question_button">Chiedi un indizio</button>
	</div>
</div>
<div class="row">
	<c:if test="${not empty answers}">
		<div class="col-sm-12 p-3">
			<table id="answers-table" class="table table-bordered table-striped">
				<thead class="thead-dark"> 
					<tr>
						<th>Domanda</th>
						<th>Risposta</th>
						<th>Utente</th>
					</tr>
				</thead>
			
				<c:forEach var="pair" items="${answers}">
					<c:forEach var="answer" items="${pair.value}">
						<tr>
							<td>${pair.key.question}</td>
							<td>${answer.answer}</td>
							<td>${answer.user.username}</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
		</div>
	</c:if>
</div>

<script>
$(function(){
	// Disabilita il pulsante 
	$("#uploadBtn").change( function(e){
		if ($(this).val()) {
			$("#submitBtn").removeAttr("disabled");
			let file_name = $(this).val();
			$("#file_label").text(file_name.substring(file_name.lastIndexOf('\\') + 1));
			console.log("File uploaed"); 
		} else {
			$("#submitBtn").removeAttr("disabled");
		}
	});	
	
	$("#answers-table").DataTable(); 
	
	function loadStatistics() {
		$.ajax({
			url: "problemStatistics",
			type: "GET",
			data: { 'problemId': "${problem.id}" },
			success: function(response) {
				console.log( response )
				$("#correct-submissions").html(response['correct']);
				$("#avg-submissions").html(response['avg']);
				$("#total-submissions").html(response['total']);
				$("#user-submissions").html(response['user']);
				
				$("#status-row").removeClass();
				
				if(response['status']) {
					$("#status-row").addClass("table-success");
					$("#user-status").html("Risolto");
				}
				else if(response['user'] === 0){
					$("#status-row").addClass("table-warning");
					$("#user-status").html("Nessuna sottomissione");
				}
				else {
					$("#status-row").addClass("table-danger");
					$("#user-status").html("Non risolto");
				}
			}
		});
	}
	
	loadStatistics();
	$("#question_textarea").summernote();

	// Sottomette il src e lo fa valutare
	$("#submitBtn").on("click", function(e) {
		e.stopPropagation(); 
		e.preventDefault(); 
		
		$.ajax( {
			url: "submitProblem",
			type: "POST",
			data: new FormData($("#file_form")[0]),
			cache: false,
			processData: false,
			contentType: false,
			enctype: "multipart/form-data",
			beforeSend: function() {
				$("body").css("cursor", "progress");
			},
			success: function(response) {
				console.log("Chiamata AJAX completata");
				console.log(response);
				let status = "";
				if(response === "CORRECT")
					status = "success";
				else if(response === "WRONG")
					status = "error";
				else if(response === "COMPILER_ERROR")
					status = "warn";
				$("#submitBtn").notify(response, status);
			},
			error: function() {
				$("#submitBtn").notify("Problema non sottomesso", "error");
			},				
			complete: function() {
				$("body").css("cursor", "default");
				loadStatistics();
			}
		});
		
		console.log("Submit della soluzione per ${problem.name}");			
	});
	
	$("#question_button").on("click", function(event) {
		event.preventDefault();
		event.stopPropagation();
		
		$.ajax({
			url: "submitQuestion",
			method: "GET",
			data: {
				"problem" : ${problem.id},
				"question" : $("#question_textarea").summernote("code")
			},
			success: function() {
				$.notify("Richiesta inviata", "success");
			},
			error: function() {
				$.notify("Errore interno", "error");
			}
		});
	});
});
</script>