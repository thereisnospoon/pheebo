package my.thereisnospoon.pheebo.persistance.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "imgboard", name = "images")
public class Image implements Serializable {

	private Long imageId;
	private String sha;
	private byte[] data;
	private int width;
	private int height;
	private long size;

	public Image() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	@Column(name = "sha256", updatable = false, nullable = false)
	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	@Column(name = "data", updatable = false, nullable = false)
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Column(name = "width", updatable = false, nullable = false)
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Column(name = "height", updatable = false, nullable = false)
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Column(nullable = false, updatable = false)
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
