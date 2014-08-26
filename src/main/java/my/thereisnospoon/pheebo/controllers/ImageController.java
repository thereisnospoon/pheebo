package my.thereisnospoon.pheebo.controllers;

import my.thereisnospoon.pheebo.services.ImageService;
import my.thereisnospoon.pheebo.services.JsonMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

	private static final Logger log = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private JsonMapperService mapperService;

	@Autowired
	private ImageService imageService;

	@RequestMapping(value = "/images", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadImage(@RequestParam MultipartFile file) {

		log.debug("File name {}", file.getOriginalFilename());

		try {
			return mapperService.getJson(imageService.storeImage(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
