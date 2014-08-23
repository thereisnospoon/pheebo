package my.thereisnospoon.pheebo.persistence.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

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

	@Column(name = "header", nullable = false)
	@Size(min = 3, max = 30)
	private String header;

	@OneToMany(mappedBy = "thread", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@SortComparator(value = Post.PostsComparator.class)
	private SortedSet<Post> posts = new TreeSet<>(Post.COMPARATOR);

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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void addPost(Post post) {
		posts.add(post);
		post.setThread(this);
	}
}
