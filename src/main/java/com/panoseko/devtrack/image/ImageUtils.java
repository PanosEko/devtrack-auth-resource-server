package com.panoseko.devtrack.image;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageUtils {
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }


    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] resizeImage(byte[] imageData, String imageType) throws IOException {
        int maxDimension = 500;
        imageType = imageType.split("/")[1];
        if (imageType.equals("png")) {
            return imageData;
        }
        // Create a ByteArrayInputStream from the imageData byte array
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);

        // Read the original image
        BufferedImage originalImage = ImageIO.read(inputStream);

        // Determine the new dimensions while maintaining the aspect ratio
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Check if the original dimensions are smaller than the maximum dimension
        if (originalWidth <= maxDimension && originalHeight <= maxDimension) {
            // Return the original image data without resizing
            return imageData;
        }

        int newWidth;
        int newHeight;

        if (originalWidth > originalHeight) {
            // Landscape image
            newWidth = maxDimension;
            newHeight = (int) ((double) originalHeight / originalWidth * maxDimension);
        } else {
            // Portrait image or square image
            newWidth = (int) ((double) originalWidth / originalHeight * maxDimension);
            newHeight = maxDimension;
        }

        // Create a scaled version of the image
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics2D.dispose();

        // Write the resized image to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, imageType, outputStream);

        // Close the input and output streams
        inputStream.close();
        outputStream.close();

        // Return the resized image data as a byte array
        return outputStream.toByteArray();
    }

}
