package com.panoseko.devtrack.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        Long imageId = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageId);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        service.deleteImageById(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Image deleted successfully");
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImagePreview(@PathVariable Long imageId) {
        ImagePreview imagePreview = service.getImagePreview(imageId);
        return ResponseEntity.ok(imagePreview);
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
