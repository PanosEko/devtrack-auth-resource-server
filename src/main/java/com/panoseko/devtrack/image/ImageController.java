//package com.panoseko.devtrack.image;
//
//import com.panoseko.devtrack.config.JwtService;
//import com.panoseko.devtrack.task.Task;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping(path="api/v1/image")
//public class ImageController {
//
//    private final ImageService imageService;
//    private final JwtService jwtService;
//
//    public ImageController(ImageService imageService, JwtService jwtService) {
//        this.imageService = imageService;
//        this.jwtService = jwtService;
//    }
//
////    @PostMapping
////    public ResponseEntity<?> uploadImage(@CookieValue(name = "token") String jwtToken,
////                                         @RequestParam("image")MultipartFile file) {
////        if (jwtToken == null) {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
////        }
////            try {
////                imageService.uploadImage(file);
////                return ResponseEntity.status(HttpStatus.OK)
////                        .body("Image uploaded successfully");
////            } catch (Exception e) {
////                e.printStackTrace();
////                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                        .body("Error uploading image");
////            }
////
////    }
//
////    @GetMapping("/{fileName}")
////    public ResponseEntity<?> downloadImage(@CookieValue(name = "token") String jwtToken, @PathVariable String fileName) {
////        if (jwtToken == null) {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
////        }
////        try {
////            byte[] imageData = imageService.downloadImage(fileName);
////            return ResponseEntity.status(HttpStatus.OK)
////                    .contentType(MediaType.valueOf("image/png"))
////                    .body(imageData);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body("Error downloading image");
////        }
////    }
//}