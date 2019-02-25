package it.unical.pignolo.model;

public class Question {
	private Long id = null; 
	private Problem problem = null;
	private User user = null; 
	private String question = null; 	
	private Boolean answered = false;
	
	public Question() {
		
	}

	public Question(Long id, Problem problem, User user, String question) {
		super();
		this.id = id;
		this.problem = problem;
		this.user = user;
		this.question = question;
	}
	
	public Question(Long id, Problem problem, User user, String question, Boolean answered) {
		super();
		this.id = id;
		this.problem = problem;
		this.user = user;
		this.question = question;
		this.answered = answered;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Boolean getAnswered() {
		return answered;
	}

	public void setAnswered(Boolean answered) {
		this.answered = answered;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", problem=" + problem + ", user=" + user + ", question=" + question
				+ ", answered=" + answered + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answered == null) ? 0 : answered.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((problem == null) ? 0 : problem.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answered == null) {
			if (other.answered != null)
				return false;
		} else if (!answered.equals(other.answered))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (problem == null) {
			if (other.problem != null)
				return false;
		} else if (!problem.equals(other.problem))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}	
}
