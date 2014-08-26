package my.thereisnospoon.pheebo.services;

import my.thereisnospoon.pheebo.persistence.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Transactional
public class ImageService {

	@PersistenceContext
	private EntityManager entityManager;

	public Image storeImage(byte[] data) {

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

				return entityManager.merge(image);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			return (Image) imageList.get(0);
		}
	}

	public String computeDigest(byte[] data) {

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
}
