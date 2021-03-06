package my.thereisnospoon.pheebo.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "imgboard", name = "images")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Image implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long imageId;

	@Column(name = "sha256", updatable = false, nullable = false)
	private String sha;

	@JsonIgnore
	@Column(name = "data", updatable = false, nullable = false)
	private byte[] data;

	@Column(name = "width", updatable = false, nullable = false)
	private int width;

	@Column(name = "height", updatable = false, nullable = false)
	private int height;

	@Column(nullable = false, updatable = false)
	private long size;

	@JsonIgnore
	@Column(name = "preview", updatable = false)
	private byte[] preview;

	public Image() {
	}

	public byte[] getPreview() {
		return preview;
	}

	public void setPreview(byte[] preview) {
		this.preview = preview;
	}

	public Long getImageId() {
		return imageId;
	}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}


	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
