package it.unical.pignolo.model;

import java.util.List;

public class Problem {
	private Long id = null;
	private String name = null;
	private Integer difficulty = null;
	private String description = null;
	private Double timelimit = null;
	
	public Problem() { }
	
	public Problem(Long id, String name, Integer difficulty, 
			String description, Double timelimit) {
		this.id = id;
		this.name = name;
		this.difficulty = difficulty;
		this.description = description;
		this.timelimit = timelimit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(Double timelimit) {
		this.timelimit = timelimit;
	}
	
	public List<Tag> getProblemTags() {
		return null;
	}
	
	public List<TestCase> getProblemTestCases() {
		return null;
	}
	
	public ProblemStatistics getProblemStats() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((timelimit == null) ? 0 : timelimit.hashCode());
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
		Problem other = (Problem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (difficulty == null) {
			if (other.difficulty != null)
				return false;
		} else if (!difficulty.equals(other.difficulty))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timelimit == null) {
			if (other.timelimit != null)
				return false;
		} else if (!timelimit.equals(other.timelimit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Problem [id=" + id + ", name=" + name + ", difficulty=" + difficulty + ", description=" + description
				+ ", timelimit=" + timelimit + "]";
	}
}
