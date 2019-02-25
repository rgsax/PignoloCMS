<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="problem_creation">
	<h2>Crea problema</h2>
	<div class="form-group">
		<label>Nome</label>
		<input type="text" name="name" class="form-control" required/>
	</div>
	<div class="form-group">
		<label>Descrizione</label>
		<textarea id="summernote" name="description" class="form-control" required></textarea>
	</div>
	<div class="form-group">
		<label>Difficoltà </label>
		<input type="number" name="difficulty" class="form-control" required/>
	</div>
	<div class="form-group">
		<label>Timelimit </label>
		<input type="number" step="0.1" name="timelimit" class="form-control" required/>
	</div>
	<h3>Testcase</h3>
	<button id="create_testcase" class="btn btn-primary">Aggiungi testcase</button>
	
	<div id="tag_selection_div">
		<h3>Tag</h3>
		<div class="form-group form-check">
			<c:forEach var="tag_item" items="${tag_list}" >
				<div id="tag_div_${tag_item.id}">
				<input class="tag_selection form-check-input" type="checkbox" id="tag_checkbox_${tag_item.id}"/>
				<label for="tag_checkbox_${tag_item.id}" class="form-check-label">${tag_item.name}</label>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<input type="submit" class="btn btn-primary" id="submit_new_problem" value="Crea"/>
</form>

<script>
$(function() {
	$("#summernote").summernote();
	
	var incr = (function() {
		var i = 1;
		return function() {
			return i++;
		}
	})();

	$("#create_testcase").on("click", function(event) {
		event.preventDefault();
		event.stopPropagation();
		
		var id = incr();
		
		var delButton = $("<button id='delBtn" + id + "' class='btn btn-danger form-control my-3'>Rimuovi testcase</button>");
		var input = $("<label style='font-weight: bold'>Input </label><textarea class='input form-control' required></textarea>");
		var output = $("<label style='font-weight: bold'>Output </label><textarea class='output form-control' required></textarea>");
		var testcaseDiv = $("<div class='testcase_div form-group'></div>");
		testcaseDiv.append(input, output, delButton);
		
		delButton.on("click", function(event) {
			event.preventDefault();
			event.stopPropagation();
			testcaseDiv.remove();
		});
		
		$(this).before(testcaseDiv);
	});

	$("#problem_creation").on("submit", function(event){
		event.preventDefault();
		event.stopPropagation();
		
		var testcases = [];
		$(".testcase_div").each(function() {
		    var input = $(this).children(".input");
		    var output = $(this).children(".output");
		    testcases.push({"input" : input.val(), "output" : output.val()});
		});
		
		var tags = [];
		$(".tag_selection:checked").each(function() {
			let id = $(this).attr("id");
            let value = $("label[for='" + id + "'").text();
            id = id.substring(id.lastIndexOf("_") + 1);
            tags.push({ "id" : id, "name" : value});
		});
		
		
		
		$.ajax({
			url : "createProblem",
			method : "GET",
			data : {
				name : $("#problem_creation [name='name']").val(),
				description : $("#summernote").summernote("code"),
				difficulty : $("#problem_creation [name='difficulty']").val(),
				timelimit : $("#problem_creation [name='timelimit']").val(),
				"testcases" : JSON.stringify(testcases),
				"tags" : JSON.stringify(tags)
			},
			beforeSend : function() {
				$("body").css("cursor", "progress");
			},
			success : function() {
				$("#submit_new_problem").notify("Problema creato", { position : "right", className : "success"});
			},
			error : function() {
				$("#submit_new_problem").notify("Problema non creato", { position : "right", className : "error"});

			},
			complete : function() {
				$("body").css("cursor", "default");
			}
		});
	});
});
</script>