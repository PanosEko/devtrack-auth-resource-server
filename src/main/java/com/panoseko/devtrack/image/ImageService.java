package com.panoseko.devtrack.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


//public Image uploadImage(MultipartFile file, Task task) throws IOException {
//    Image image = imageRepository.save(Image.builder()
//            .name(file.getOriginalFilename())
//            .type(file.getContentType())
//            .imageData(ImageUtils.compressImage(file.getBytes()))
//            .task(task) // Set the task
//            .build());
//    if(image !=null){
//        return image;
//    }
//    return null;
//}

    public Image uploadImage(MultipartFile file) throws IOException {
        byte[] originalImageData = file.getBytes();
        byte[] thumbnailData = ImageUtils.resizeImage(originalImageData);

        Image image = imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(originalImageData))
                .thumbnailData(ImageUtils.compressImage(thumbnailData))
                .build());
        return image;

    }

    public void deleteImage(Long imageID){
        Optional<Image> image = imageRepository.findById(imageID);
        image.ifPresent(imageRepository::delete);
    }

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


    public void deleteImageByTaskId(Long taskID){
        Optional<Image> image = imageRepository.findImageByTask(taskID);
        image.ifPresent(imageRepository::delete);
    }

    public ThumbnailDTO getThumbnail(Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isPresent()) {
            ThumbnailDTO thumbnail = new ThumbnailDTO(image.get().getId().toString(),
                    ImageUtils.decompressImage(image.get().getThumbnailData()));
            return thumbnail;
        }
        return null;
    }
}
