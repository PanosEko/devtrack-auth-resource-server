package com.panoseko.devtrack.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/resources/images")
public class ImageController {

    private ImageService service;

    @Autowired
    public ImageController(ImageService imageService) {
       this.service = imageService;
    }
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        Image image = service.uploadImage(file);
        ThumbnailDTO thumbnail = new ThumbnailDTO(image.getId().toString(),
                ImageUtils.decompressImage(image.getThumbnailData()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(thumbnail);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        service.deleteImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Image deleted successfully");
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getThumbnail(@PathVariable Long imageId) {
        Optional<ThumbnailDTO> thumbnail = service.getThumbnail(imageId);
        return ResponseEntity.ok(thumbnail);
    }

    @GetMapping("download/{imageId}")
    public ResponseEntity<?> downloadImage(@PathVariable Long imageId) {
        Image image = service.downloadImage(imageId);
        String base64ImageData = Base64.getEncoder().encodeToString(image.getImageData());
        MediaType mediaType = MediaType.parseMediaType(image.getType());
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(image.getName())
                                .build().toString())
                .body(base64ImageData);
    }
}
