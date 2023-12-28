package com.panoseko.devtrack.image;

import com.panoseko.devtrack.exception.ImageNotFoundException;
import com.panoseko.devtrack.exception.ImageProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RestController
@RequestMapping(path = "api/v1/resources/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;

    @PostMapping
    public ResponseEntity<ThumbnailDTO> uploadImage(@RequestParam("image") MultipartFile file)
            throws ImageProcessingException {
        ThumbnailDTO thumbnail = service.addImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(thumbnail);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId)
            throws ImageNotFoundException {
        service.delete(imageId);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ThumbnailDTO> getThumbnail(@PathVariable Long imageId)
            throws ImageNotFoundException, ImageProcessingException {
        ThumbnailDTO thumbnail = service.getThumbnail(imageId);
        return ResponseEntity.ok(thumbnail);
    }

    @GetMapping("download/{imageId}")
    public ResponseEntity<String> downloadImage(@PathVariable Long imageId)
            throws ImageNotFoundException, ImageProcessingException {
        Image image = service.download(imageId);
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
