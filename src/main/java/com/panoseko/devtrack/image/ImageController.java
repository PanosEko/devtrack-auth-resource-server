package com.panoseko.devtrack.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        ThumbnailDTO thumbnail = service.getThumbnail(imageId);
        return ResponseEntity.ok(thumbnail);
    }

//    @GetMapping("/{fileName}")
//    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
//        byte[] imageData=service.downloadImage(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//
//    }
}
