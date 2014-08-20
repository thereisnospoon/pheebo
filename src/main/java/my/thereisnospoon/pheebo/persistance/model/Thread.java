package my.thereisnospoon.pheebo.persistance.model;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "imgboard", name = "threads")
@DynamicInsert
public class Thread implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "thread_id", insertable = false, updatable = false)
	private Long threadId;

	@Column(name = "is_pinned", columnDefinition = "boolean default false", nullable = false)
	private Boolean isPinned;

	@ManyToOne
	@JoinColumn(name = "board_path", nullable = false)
	private Board board;

	@OneToMany(mappedBy = "thread", fetch = FetchType.EAGER)
	private Set<Post> posts;

	public Thread() {
	}

	public Long getThreadId() {
		return threadId;
	}

	public Boolean isPinned() {
		return isPinned;
	}

	public void setPinned(Boolean isPinned) {
		this.isPinned = isPinned;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
}
