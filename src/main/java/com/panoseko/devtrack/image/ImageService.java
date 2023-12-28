package com.panoseko.devtrack.image;

import com.panoseko.devtrack.exception.ImageNotFoundException;
import com.panoseko.devtrack.exception.ImageProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public ThumbnailDTO addImage(MultipartFile file) throws ImageProcessingException {
        byte[] originalImageData;
        byte[] thumbnailData;
        try {
            originalImageData = file.getBytes();
            thumbnailData = ImageUtils.resizeForThumbnail(originalImageData, file.getContentType());
            Image image = imageRepository.save(Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtils.compress(originalImageData))
                    .thumbnailData(ImageUtils.compress(thumbnailData))
                    .build());
            return new ThumbnailDTO(image.getId().toString(), thumbnailData);
        } catch (IOException e) {
            throw new ImageProcessingException("Failed to process image with parameters {name="
                    + file.getOriginalFilename() + "}. Image was not saved. " + e.getMessage());
        }
    }

    public void delete(Long imageID) throws ImageNotFoundException {
        Image image = imageRepository.findById(imageID).orElseThrow(() ->
                new ImageNotFoundException("Image was not found for parameters {id=" + imageID + "}"));
        imageRepository.delete(image);
    }

    public Image download(Long imageId)
            throws ImageNotFoundException, ImageProcessingException {
        Image image = imageRepository.findById(imageId).orElseThrow(()
                -> new ImageNotFoundException("Image was not found for parameters {id=" + imageId + "}"));
        try {
            image.setImageData(ImageUtils.decompress(image.getImageData()));
        } catch (IOException | DataFormatException e) {
            throw new ImageProcessingException("Failed to process image for parameters {id " +
                    imageId + "}." + e.getMessage());
        }
        return image;
    }

    public ThumbnailDTO getThumbnail(Long imageId)
            throws ImageNotFoundException, ImageProcessingException {
        Image image = imageRepository.findById(imageId).orElseThrow(() ->
                new ImageNotFoundException("Image was not found for parameters {id=" + imageId + "}"));
        try {
            return new ThumbnailDTO(image.getId().toString(),
                    ImageUtils.decompress(image.getThumbnailData()));
        } catch (IOException | DataFormatException e) {
            throw new ImageProcessingException("Failed to process image for parameters {id " +
                    imageId + "}." + e.getMessage());
        }
    }

}
