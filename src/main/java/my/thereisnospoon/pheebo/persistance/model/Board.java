package my.thereisnospoon.pheebo.persistance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "boards", schema = "imgboard")
public class Board implements Serializable {

	private String description;
	private String path;

	private Set<Thread> threads;

	public Board() {
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Id
	@Column(name = "path", nullable = false, updatable = true)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	public Set<Thread> getThreads() {
		return threads;
	}

	public void setThreads(Set<Thread> threads) {
		this.threads = threads;
	}

	@Override
	public String toString() {
		return "Board: " + path + " (" + description + ")";
	}
}
