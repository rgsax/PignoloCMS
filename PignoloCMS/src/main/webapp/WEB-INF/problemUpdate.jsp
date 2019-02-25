<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="table table-bordered table-striped table-responsive">
	<tr class="thead-dark">
		<th>Nome</th>
		<th>Difficoltà</th>
		<th>Descrizione</th>
		<th>Timelimit</th>
		<th></th>
	</tr>
	
	<c:forEach var="problem" items="${problems}">
		<tr id="problem_${problem.id}">
			<td class="problem_name">${problem.name}</td>
			<td class="problem_difficulty">${problem.difficulty}</td>
			<td class="problem_description">${problem.description}</td>
			<td class="problem_timelimit">${problem.timelimit}</td>
			
			<td>
				<button class="update_problem_btns btn btn-primary" id="update_problem_btn_${problem.id}">Modifica</button>
			</td>
		</tr>
	</c:forEach>
</table>

<div id="problem_details">
	
</div>

<script>

	function createFormFn(id) {
	
		////
		let form = $('<form id="update_problem"></form>');
		let row = $('#problem_'+id);
		
		let nameLabel = $('<div class="form-group"><label>Nome </label><input class="form-control" type="text" name="name"/></div>');
		nameLabel.children("[name]").val(row.children(".problem_name").html());
		
		let descLabel = $('<div class="form-group"><label>Descrizione </label><textarea class="form-control" id="summernote" name="description"></textarea></div>');
		//descLabel.val(row.children(".problem_description").html());
		
		let diffLabel = $('<div class="form-group"><label>Difficoltà </label><input class="form-control" type="number" name="difficulty"/></div>');
		diffLabel.children("[name]").val(row.children(".problem_difficulty").html());
		
		let timeLabel = $('<div class="form-group"><label>Timelimit </label><input class="form-control" type="number" step="0.1" name="timelimit"/></div>');
		timeLabel.children("[name]").val(row.children(".problem_timelimit").html());
		
		let btnProblemFormGroup = $("<div class='from-group'></div>");
		
		let updateBtn = $("<button class='btn btn-primary m-3' id='modify_problem_" + id + "'>Modifica</button>");
		let deleteBtn = $("<button class='btn btn-danger m-3' id='delete_problem_" + id + "'>Rimuovi problema</button>");
		let newTestcaseBtn = $("<button class='btn btn-primary m-3' id='create_testcase'>Aggiungi testcase</button>");
		
		btnProblemFormGroup.append(updateBtn, deleteBtn, newTestcaseBtn);
		
		let testcases_div = $("<div id='testcases_div' class='form-group'><h2>Testcase</h2></div>");
		
		let tags_div = $("<div id='tags_div' class='form-group form-check'></div>"); 
		
		form.append(nameLabel, descLabel, diffLabel, timeLabel, testcases_div, tags_div);
		
		$.ajax({
			url: "getProblemTags",
			method: "GET",
			data: {'id': id},
			success: function(response) {
				for(tag of response) {
					let parsed = tag; //JSON.parse(tag); 	// !?				
					let checkbox = $("<input class='from-check-input  problem_tag' id='tag_" + parsed['id'] + "' type='checkbox' /> <label class='form-check-label' for='tag_"+parsed['id']+"'>"+  parsed['name'] +  "</label>");					
					if (parsed['is_checked']) {
						checkbox.attr('checked', true);
					}
					
					let checkbox_div = $("<div class='form-group form-check'></div>"); 
					checkbox_div.append(checkbox);
					tags_div.append(checkbox_div); 
				}
			}
		});
		
		$.ajax({
			url: "getTestcases",
			method: "GET", 
			data: {'id': id},
			success: function(response) {
				for (j of response) {
					let parsed = JSON.parse(j); 
					let input = $("<label style='font-weight: bold'>Input </label><textarea class='input form-control'></textarea>");
					input.val(parsed["input"]);
					
					let output = $("<label style='font-weight: bold'>Output </label><textarea class='output form-control'></textarea>");
					output.val(parsed["output"]);
					
					let testDelBtn = $("<button class='btn btn-danger form-control my-3'>Rimuovi testcase</button>");
					
					let testcaseDiv = $("<div class='from-group' id=' testcase_" +  parsed['id'] + "' class='testcase_div'></div>");
					testcaseDiv.append(input, output);
					
					testDelBtn.on("click", function(e) {
						e.stopPropagation(); 
						e.preventDefault(); 
						
						$.ajax({
							'url': "deleteTestcase",
							'method': "GET", 
							'data': {'id': parsed['id']},
							'success': function() {
								testcaseDiv.remove(); 
								$.notify("Testcase eliminato", "success");
							},
							'error': function() {
								$.notify("Testcase non eliminato", "error"); 
							}
						});
					});	
					
					testcaseDiv.append(testDelBtn); 
					
					testcases_div.append(testcaseDiv); 
				}
			},
			complete: function() {
				form.append(updateBtn, deleteBtn, newTestcaseBtn); 
			}
		});
		
		newTestcaseBtn.on("click", function(event) {
			event.preventDefault();
			event.stopPropagation();
			
			var delButton = $("<button class='btn btn-danger form-control my-3'>Rimuovi testcase</button>");
			var input = $("<label style='font-weight: bold'>Input </label><textarea class='input form-control'></textarea>");
			var output = $("<label style='font-weight: bold'>Output </label><textarea class='output form-control'></textarea>");
			var testcase = $("<div class='new_testcase_div form-group'></div>");
			testcase.append(input, output, delButton);
			
			delButton.on("click", function(event) {
				event.preventDefault();
				event.stopPropagation();
				testcase.remove();
			});
			
			testcases_div.append(testcase);
		});
		
		
		deleteBtn.on("click", function(e) {
			e.preventDefault(); 
			e.stopPropagation(); 
			
			$.ajax({
				url: "deleteProblem",
				method: "GET",
				beforeSend: function() {
					$('body').css('cursor', 'progress');
				},
				success: function() {
					$.notify("Problema eliminato", "success");
					row.remove(); 
					form.remove();
				},
				error: function() {
					$.notify("Problema non eliminato", "error");
				},
				complete: function() {
					$('body').css('cursor', 'default');
				},
				data: {
					'id': id
				}
			});
		});
		
		updateBtn.on("click", function(e) {
			e.preventDefault(); 
			e.stopPropagation(); 
			
			let testcases = []; 
			$(".testcase_div").each( function() {
				let input = $(this).children(".input").val();  
				let output = $(this).children(".output").val(); 
				let id = $(this).attr("id");
				id = id.substring( id.lastIndexOf("_")+1 ); 
				
				testcases.push( {'id': id, 'input': input, 'output': output});
			});
			
			let tags= [];
			$(".problem_tag:checked").each( function() {
				let tag_id = $(this).attr('id'); 
				tag_id = tag_id.substring( tag_id.lastIndexOf('_')+1); 
				tags.push( {'id': tag_id} ); 
			});
			
			let new_testcases = [];
			$(".new_testcase_div").each(function() {
				let input = $(this).children(".input").val();
				let output = $(this).children(".output").val();
				new_testcases.push({'input' : input, 'output' : output});
			});
			
			$.ajax({
				url: "updateProblem",
				method: "GET",
				beforeSend: function() {
					$('body').css('cursor', 'progress');
				},
				success: function() {
					row.children(".problem_name").html(nameLabel.children("[name]").val());
					row.children(".problem_difficulty").html(diffLabel.children("[name]").val());
					row.children(".problem_description").html($("#summernote").summernote("code"));
					row.children(".problem_timelimit").html(timeLabel.children("[name]").val());
					$.notify("Problema modificato", "success");
				},
				error: function() {
					$.notify("Problema non modificato", "error");
				},
				complete: function() {
					$('body').css('cursor', 'default');
				},
				data: {
					'id': id,
					'name': nameLabel.children("[name]").val(),
					'description': $("#summernote").summernote("code"),
					'difficulty': diffLabel.children("[name]").val(),
					'timelimit': timeLabel.children("[name]").val(), 
					'testcases': JSON.stringify(testcases),
					'tags': JSON.stringify(tags), 
					'new_testcases': JSON.stringify(new_testcases)
				}
			});
		});
		
		return form; 
		////
	}

	$(function() {
		$.extend($.scrollTo.defaults, {
		    axis: 'y',
		    duration: 100
		});
		
		$(".update_problem_btns").each( function() {
			$(this).on("click", function() {								
				let id = $(this).attr('id');
				id = id.substring(id.lastIndexOf('_')+1);
				
				let form = createFormFn(id);
				let form_div = $("#problem_details");
				form_div.children().remove();
				form_div.append(form); 		
				$("#summernote").summernote();
				$("#summernote").summernote("code", $("#problem_" + id).children(".problem_description").html());
				$("body").scrollTo("#problem_details");
			});	
		});
	});



</script>