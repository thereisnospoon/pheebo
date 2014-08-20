package my.thereisnospoon.pheebo.persistance.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

@Entity
@Table(schema = "imgboard", name = "posts")
public class Post implements Serializable {

	public static class PostsComparator implements Comparator<Post> {

		private static final Comparator<Post> innerComparator = Comparator.comparing(Post::getPostedWhen);

		@Override
		public int compare(Post o1, Post o2) {
			return innerComparator.compare(o2, o1);
		}
	}

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	@Column
	@Size(max = 30, message = "Header should be less then 30 symbols")
	private String header;

	@Column(nullable = false)
	@NotBlank(message = "Message should be nonempty")
	@Size(min = 1, max = 3000)
	private String message;

	@Column(name = "ip")
	private String ip;

	@Column(name = "posted_when", nullable = false)
	private Date postedWhen;

	@Column(name = "author", nullable = true)
	@Size(max = 15, message = "Name should be less then 15 symbols")
	private String author;

	@ManyToOne
	@JoinColumn(name = "image_id", nullable = true)
	private Image image;

	@ManyToOne
	@JoinColumn(name = "thread_id", nullable = false)
	private Thread thread;

	public Post() {
	}

	public Long getPostId() {
		return postId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getPostedWhen() {
		return postedWhen;
	}

	public void setPostedWhen(Date postedWhen) {
		this.postedWhen = postedWhen;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
