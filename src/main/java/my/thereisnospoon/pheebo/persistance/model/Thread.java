package my.thereisnospoon.pheebo.persistance.model;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "imgboard", name = "threads")
@DynamicInsert
public class Thread implements Serializable {

	private Long threadId;
	private Boolean isPinned;
	private Board board;

	private Set<Post> posts;

	public Thread() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "thread_id", insertable = false, updatable = false)
	public Long getThreadId() {
		return threadId;
	}

	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}

	@Column(name = "is_pinned", columnDefinition = "boolean default false", nullable = false)
	public Boolean isPinned() {
		return isPinned;
	}

	public void setPinned(Boolean isPinned) {
		this.isPinned = isPinned;
	}

	@ManyToOne
	@JoinColumn(name = "board_path", nullable = false)
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	@OneToMany(mappedBy = "thread", fetch = FetchType.EAGER)
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
}
