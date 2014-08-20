package my.thereisnospoon.pheebo.persistance.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;

@Entity
@Table(schema = "imgboard", name = "threads")
@DynamicInsert
public class Thread implements Serializable {

	public static class ThreadComparator implements Comparator<Thread> {

		private static final Comparator<Thread> innerComparator = Comparator.comparing(Thread::getCreatedWhen);

		@Override
		public int compare(Thread o1, Thread o2) {
			return innerComparator.compare(o2, o1);
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "thread_id", insertable = false, updatable = false)
	private Long threadId;

	@Column(name = "is_pinned", columnDefinition = "boolean default false", nullable = false)
	private Boolean isPinned;

	@ManyToOne
	@JoinColumn(name = "board_path", nullable = false)
	private Board board;

	@Column(name = "created_when", nullable = false)
	private Date createdWhen;

	@OneToMany(mappedBy = "thread", fetch = FetchType.LAZY)
	@SortComparator(value = Post.PostsComparator.class)
	private SortedSet<Post> posts;

	public Thread() {
	}

	public Date getCreatedWhen() {
		return createdWhen;
	}

	public void setCreatedWhen(Date createdWhen) {
		this.createdWhen = createdWhen;
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

	public SortedSet<Post> getPosts() {
		return posts;
	}
}
