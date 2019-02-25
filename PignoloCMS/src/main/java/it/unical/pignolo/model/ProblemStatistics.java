package it.unical.pignolo.model;

public class ProblemStatistics {
	private Problem problem = null;
	private Integer wrong = null;
	private Integer timelimit = null;
	private Integer accepted = null;
	private Integer compilationError = null;
	
	public ProblemStatistics() { }
	
	public ProblemStatistics(Problem problem, Integer wrong, Integer timelimit, Integer accepted,
			Integer compilationError) {
		super();
		this.problem = problem;
		this.wrong = wrong;
		this.timelimit = timelimit;
		this.accepted = accepted;
		this.compilationError = compilationError;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Integer getWrong() {
		return wrong;
	}

	public void setWrong(Integer wrong) {
		this.wrong = wrong;
	}

	public Integer getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(Integer timelimit) {
		this.timelimit = timelimit;
	}

	public Integer getAccepted() {
		return accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	public Integer getCompilationError() {
		return compilationError;
	}

	public void setCompilationError(Integer compilationError) {
		this.compilationError = compilationError;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accepted == null) ? 0 : accepted.hashCode());
		result = prime * result + ((compilationError == null) ? 0 : compilationError.hashCode());
		result = prime * result + ((problem == null) ? 0 : problem.hashCode());
		result = prime * result + ((timelimit == null) ? 0 : timelimit.hashCode());
		result = prime * result + ((wrong == null) ? 0 : wrong.hashCode());
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
		ProblemStatistics other = (ProblemStatistics) obj;
		if (accepted == null) {
			if (other.accepted != null)
				return false;
		} else if (!accepted.equals(other.accepted))
			return false;
		if (compilationError == null) {
			if (other.compilationError != null)
				return false;
		} else if (!compilationError.equals(other.compilationError))
			return false;
		if (problem == null) {
			if (other.problem != null)
				return false;
		} else if (!problem.equals(other.problem))
			return false;
		if (timelimit == null) {
			if (other.timelimit != null)
				return false;
		} else if (!timelimit.equals(other.timelimit))
			return false;
		if (wrong == null) {
			if (other.wrong != null)
				return false;
		} else if (!wrong.equals(other.wrong))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProblemStatistics [problem=" + problem + ", wrong=" + wrong + ", timelimit=" + timelimit + ", accepted="
				+ accepted + ", compilationError=" + compilationError + "]";
	}	
}
