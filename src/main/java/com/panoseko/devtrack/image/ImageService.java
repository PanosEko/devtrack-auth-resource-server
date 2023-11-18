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

    public Image uploadImage(MultipartFile file) throws IOException {
        byte[] originalImageData = file.getBytes();
        byte[] thumbnailData = ImageUtils.resizeImage(originalImageData, file.getContentType());

        return imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(originalImageData))
                .thumbnailData(ImageUtils.compressImage(thumbnailData))
                .build());

    }

    public void deleteImage(Long imageID){
        Optional<Image> image = imageRepository.findById(imageID);
        image.ifPresent(imageRepository::delete);
    }

    public Image downloadImage(Long imageId) {
        Optional<Image> optionalImage = imageRepository.findById(imageId);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            image.setImageData(ImageUtils.decompressImage(image.getImageData()));
            return image;
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

    public Optional<ThumbnailDTO> getThumbnail(Long imageId) {
        return imageRepository.findById(imageId)
                .map(image -> new ThumbnailDTO(
                        image.getId().toString(),
                        ImageUtils.decompressImage(image.getThumbnailData())
                ));
    }

}
