package it.unical.pignolo.model;

public class Language {
	private Long id = null;
	private String name = null;
	private String compileCmd = null;
	private String runCmd = null;
	
	public Language() { }

	public Language(Long id, String name, String compileCmd, String runCmd) {
		super();
		this.id = id;
		this.name = name;
		this.compileCmd = compileCmd;
		this.runCmd = runCmd;
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

	public String getCompileCmd() {
		return compileCmd;
	}

	public void setCompileCmd(String compileCmd) {
		this.compileCmd = compileCmd;
	}
	
	public String getRunCmd() {
		return runCmd;
	}

	public void setRunCmd(String runCmd) {
		this.runCmd = runCmd;
	}

	@Override
	public String toString() {
		return "Language [id=" + id + ", name=" + name + ", compileCmd=" + compileCmd + ", runCmd=" + runCmd + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compileCmd == null) ? 0 : compileCmd.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((runCmd == null) ? 0 : runCmd.hashCode());
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
		Language other = (Language) obj;
		if (compileCmd == null) {
			if (other.compileCmd != null)
				return false;
		} else if (!compileCmd.equals(other.compileCmd))
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
		if (runCmd == null) {
			if (other.runCmd != null)
				return false;
		} else if (!runCmd.equals(other.runCmd))
			return false;
		return true;
	}
	
	
	
}
