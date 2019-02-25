$(function(){
	$("#problem_creation_page").on("click", function(event) {
		event.preventDefault();
		event.stopPropagation();
		$("#container").load("administration");
	});
	
	$("#show_problems_page").on("click", function(e) {
		e.preventDefault(); 
		e.stopPropagation(); 
		
		$("#container").load("problems");
	});
	
	$("#show_questions_page").on("click", function(e) {
		e.preventDefault(); 
		e.stopPropagation(); 
		
		$("#container").load("questions");
	});
	
	$("#your_question_page").on("click", function(e) {
		e.preventDefault(); 
		e.stopPropagation(); 
		
		console.log("Click on question page");
		
		$("#container").load("personalQuestions");
	});
	
	$("#problem_update_page").on("click", function(event) {
		event.preventDefault();
		event.stopPropagation();
		$("#container").load("problemUpdate");
	});
	
	$("#home_page").on("click", function(event) {
		event.preventDefault();
		event.stopPropagation();
		let path = window.location.href;
		window.location.replace(path.substring(0, path.lastIndexOf("/")));
	});
	
	$("#account_page").on("click", function(event) {
		event.preventDefault();
		event.stopPropagation();
		$("#container").load("accountManagement");
	});
	
	$("#scoreboard_page").on("click", function(event) {
		event.preventDefault();
		event.stopPropagation();
		$("#container").load("scoreboard");
	});
	
	$(".problem").each(function() {
		let id = $(this).attr("id");
		id = id.substring(id.lastIndexOf("_") + 1);
		
		$(this).on("click", function(event) {
			event.preventDefault();
			event.stopPropagation();
			$("#container").load("problems", "id="+id);
		});
	});
});