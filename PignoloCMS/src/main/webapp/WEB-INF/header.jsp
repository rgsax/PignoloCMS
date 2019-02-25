<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
<div class="jumbotron jumbotron-fluid m-0">
<div class="container"></div>
</div>
<nav id="navbar" class="navbar navbar-expand-sm navbar-dark bg-dark sticky-top m-0">
	<ul class="navbar-nav">
		<li class="nav-item"><a class="nav-link" href="#" id="home_page">Home</a></li>
		<c:if test="${not empty user}">
			<li class="nav-item"><a class="nav-link" href="#" id="show_problems_page">Problemi</a></li>
			<li class="nav-item"><a class="nav-link" href="#" id="show_questions_page">Domande</a></li>
			<li class="nav-item"><a class="nav-link" href="#" id="your_question_page">Le tue domande</a></li>
			<li class="nav-item"><a class="nav-link" href="#" id="scoreboard_page">Scoreboard</a></li>
			<c:if test="${user.isAdmin}">
				<li class="nav-item"><a class="nav-link" href="#" id="problem_creation_page">Crea problema</a></li>
				<li class="nav-item"><a class="nav-link" href="#" id="problem_update_page">Modifica problema</a></li>
			</c:if>
			<li class="nav-item"><a class="nav-link" href="#" id="account_page">Account</a></li>
			<li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
		</c:if>
	</ul>
</nav>
</header>