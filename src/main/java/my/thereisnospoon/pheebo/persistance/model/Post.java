package my.thereisnospoon.pheebo.persistance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(schema = "imgboard", name = "posts")
public class Post implements Serializable {

	private Long postId;
	private String header;
	private String message;
	private String ip;
	private Date postedWhen;
	private String author;

	private Image image;
	private Thread thread;

	public Post() {
	}

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	@Column
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Column(nullable = false)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "posted_when", nullable = false)
	public Date getPostedWhen() {
		return postedWhen;
	}

	public void setPostedWhen(Date postedWhen) {
		this.postedWhen = postedWhen;
	}

	@Column(name = "author", nullable = true)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@ManyToOne
	@JoinColumn(name = "image_id", nullable = true)
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@ManyToOne
	@JoinColumn(name = "thread_id", nullable = false)
	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
