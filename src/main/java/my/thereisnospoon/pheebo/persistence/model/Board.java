package my.thereisnospoon.pheebo.persistence.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.SortedSet;

@Entity
@Table(name = "boards", schema = "imgboard")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Board implements Serializable {

	@Column(name = "description", nullable = true)
	@Size(max = 100)
	private String description;

	@Id
	@Column(name = "path", nullable = false, updatable = true)
	@NotBlank(message = "Board should have path")
	@Pattern(regexp = "\\w{1,20}")
	private String path;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	@SortComparator(value = Thread.ThreadComparator.class)
	private SortedSet<Thread> threads;

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

	public SortedSet<Thread> getThreads() {
		return threads;
	}

	@Override
	public String toString() {
		return "Board: " + path + " (" + description + ")";
	}

	public void addThread(Thread thread) {
		threads.add(thread);
		thread.setBoard(this);
	}
}
