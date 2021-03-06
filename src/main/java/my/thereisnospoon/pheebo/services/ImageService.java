package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistence.model.Image;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Transactional
public class ImageService {

	private static final Logger log = LoggerFactory.getLogger(ImageService.class);

	public static final int PREVIEW_WIDTH = 200;

	@PersistenceContext
	private EntityManager entityManager;

	public static String computeDigest(byte[] data) {

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(data);

			byte[] hash = messageDigest.digest();
			StringBuilder sb = new StringBuilder();

			for (byte byteValue : hash) {
				if ((0xff & byteValue) < 0x10) {
					sb.append("0").append(Integer.toHexString((0xff & byteValue)));
				} else {
					sb.append(Integer.toHexString(0xff & byteValue));
				}
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public Image storeImage(byte[] data) {

		log.debug("Image size: {} bytes", data.length);

		Image image = new Image();
		image.setData(data);
		image.setSha(computeDigest(data));

		List imageList = entityManager.createQuery("select i from Image i where i.sha = :sha").setParameter("sha", image.getSha()).getResultList();
		if (imageList.isEmpty()) {

			try {
				BufferedImage awtImage = ImageIO.read(new ByteArrayInputStream(data));
				image.setHeight(awtImage.getHeight());
				image.setWidth(awtImage.getWidth());
				image.setSize(data.length);

				if (image.getWidth() > PREVIEW_WIDTH) {
					image.setPreview(resizeImage(awtImage, image));
				} else {
					image.setPreview(image.getData());
				}

				return entityManager.merge(image);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return (Image) imageList.get(0);
		}
	}

	public Image getImage(Long imageId) {
		return entityManager.find(Image.class, imageId);
	}

	private byte[] resizeImage(BufferedImage bufferedImage, Image image) {

		int resizedImageHeight = (int) (1.*PREVIEW_WIDTH/image.getWidth()*image.getHeight());

		log.debug("Resized image height = {}", resizedImageHeight);

		BufferedImage thumbnail =
				Scalr.resize(bufferedImage, PREVIEW_WIDTH, resizedImageHeight,
						Scalr.OP_ANTIALIAS);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(thumbnail, "jpeg", os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return os.toByteArray();
	}

	@Scheduled(fixedDelay = 60*60000)
	public void deleteOrphanImages() {

		int deleted = entityManager.createQuery("delete from Image i where i not in (select p.image from Post p)").executeUpdate();

		log.debug("{} orphan images was deleted during cleanup", deleted);
	}
}
