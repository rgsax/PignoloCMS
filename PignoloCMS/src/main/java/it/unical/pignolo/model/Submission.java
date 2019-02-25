package it.unical.pignolo.model;


public class Submission {
	private Long id; 
	private Problem problem; 
	private User user; 
	private String source; 
	private TestCase firstFailure;
	private Status status;
	private Language language;
	
	public Submission(Long id, Problem problem, User user, String source, TestCase firstFailure, Status status,
			Language language) {
		super();
		this.id = id;
		this.problem = problem;
		this.user = user;
		this.source = source;
		this.firstFailure = firstFailure;
		this.status = status;
		this.language = language;
	}
	
	public Submission() {
		super();
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public TestCase getFirstFailure() {
		return firstFailure;
	}

	public void setFirstFailure(TestCase firstFailure) {
		this.firstFailure = firstFailure;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstFailure == null) ? 0 : firstFailure.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((problem == null) ? 0 : problem.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Submission other = (Submission) obj;
		if (firstFailure == null) {
			if (other.firstFailure != null)
				return false;
		} else if (!firstFailure.equals(other.firstFailure))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (problem == null) {
			if (other.problem != null)
				return false;
		} else if (!problem.equals(other.problem))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Submission [id=" + id + ", problem=" + problem + ", user=" + user + ", source=" + source
				+ ", firstFailure=" + firstFailure + ", status=" + status + ", language=" + language + "]";
	}
	
}
