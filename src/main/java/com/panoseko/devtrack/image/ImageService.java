package com.panoseko.devtrack.image;

import com.panoseko.devtrack.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

//    public Image uploadImage(MultipartFile file, Task task) throws IOException {
//        Image image = imageRepository.save(Image.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .imageData(ImageUtils.compressImage(file.getBytes()))
//                .task(task) // Set the task
//                .build());
//        if(image !=null){
//            return image;
//        }
//        return null;
//    }
public Image uploadImage(MultipartFile file, Task task) throws IOException {
    Image image = imageRepository.save(Image.builder()
            .name(file.getOriginalFilename())
            .type(file.getContentType())
            .imageData(ImageUtils.compressImage(file.getBytes()))
            .task(task) // Set the task
            .build());
    if(image !=null){
        return image;
    }
    return null;
}

//public MultipartFile getImageFile(Image image){
//    byte[] imageData = ImageUtils.decompressImage(image.getImageData());
//}


    public byte[] downloadImage(Long taskID){
        Optional<Image> image = imageRepository.findImageByTask(taskID);
        if (image.isPresent()) {
            return ImageUtils.decompressImage(image.get().getImageData());
        }
        return null;
    }

    public byte[] decompressImageData(byte[] imageData){
        return ImageUtils.decompressImage(imageData);
    }


    public void deleteImage(Long taskID){
        Optional<Image> image = imageRepository.findImageByTask(taskID);
        image.ifPresent(imageRepository::delete);
    }

}
