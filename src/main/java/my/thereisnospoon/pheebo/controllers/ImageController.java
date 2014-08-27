package my.thereisnospoon.pheebo.controllers;

import my.thereisnospoon.pheebo.persistence.model.Image;
import my.thereisnospoon.pheebo.services.ImageService;
import my.thereisnospoon.pheebo.services.JsonMapperService;
import my.thereisnospoon.pheebo.vo.ErrorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("images")
public class ImageController {

	private static final Logger log = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private JsonMapperService mapperService;

	@Autowired
	private ImageService imageService;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadImage(@RequestParam MultipartFile file) {

		log.debug("File name {} with content type: ", file.getOriginalFilename(), file.getContentType());

		if (!isSupportedType(file)) {
			return mapperService.getJson(new ErrorVO("Unsupported file type"));
		}

		try {
			return mapperService.getJson(imageService.storeImage(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isSupportedType(MultipartFile file) {

		return file.getContentType().contains("image/");
	}

	@RequestMapping(value = "{imageId}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	@ResponseBody
	public byte[] getImage(@PathVariable Long imageId, HttpServletResponse response) {

		log.debug("Request image with id = {}", imageId);

		Image image = imageService.getImage(imageId);
		if (null != image) {

			log.debug("Image found");
			log.debug("Size {} bytes", image.getData().length);
			log.debug("Dimensions: width = {}, height = {}", image.getWidth(), image.getHeight());

			return image.getData();
		}
		return null;
	}
}
