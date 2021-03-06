package my.thereisnospoon.pheebo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(schema = "imgboard", name = "threads")
@DynamicInsert
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thread implements Serializable {

	public static class ThreadComparator implements Comparator<Thread> {

		private static final Comparator<Date> innerDateComparator = Comparator.nullsLast((d1, d2) -> d2.compareTo(d1));
		private static final Comparator<Thread> innerComparator = Comparator.comparing(Thread::getLastResponseDate, innerDateComparator);

		@Override
		public int compare(Thread o1, Thread o2) {
			return innerComparator.compare(o1, o2);
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "thread_id", insertable = false, updatable = false)
	private Long threadId;

	@Column(name = "is_pinned", columnDefinition = "boolean default false", nullable = false)
	private Boolean isPinned;

	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@ManyToOne
	@JoinColumn(name = "board_path", nullable = false)
	private Board board;

	@Column(name = "created_when", nullable = false)
	private Date createdWhen;

	@Column(name = "header", nullable = false)
	@Size(min = 1, max = 60)
	private String header;

	@Column(name = "last_response_date")
	private Date lastResponseDate;

	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany(mappedBy = "thread", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@SortComparator(value = Post.PostsComparator.class)
	private SortedSet<Post> posts = new TreeSet<>(Post.COMPARATOR);

	public Thread() {
	}

	public Date getLastResponseDate() {
		return lastResponseDate;
	}

	public void setLastResponseDate(Date lastResponseDate) {
		this.lastResponseDate = lastResponseDate;
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
