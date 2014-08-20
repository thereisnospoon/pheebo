package my.thereisnospoon.pheebo.persistance.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "boards", schema = "imgboard")
public class Board implements Serializable {

	@Column(name = "description", nullable = true)
	@Size(max = 100)
	private String description;

	@Id
	@Column(name = "path", nullable = false, updatable = true)
	@NotBlank(message = "Board should have path")
	@Pattern(regexp = "\\w{1,20}")
	private String path;

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private Set<Thread> threads;

	public Board() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

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
