package com.panoseko.devtrack.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {
    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] tmp = new byte[4 * 1024];
            while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            return outputStream.toByteArray();
        }
    }


    public static byte[] decompress(byte[] data) throws DataFormatException, IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        byte[] tmp = new byte[4 * 1024];
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
            return outputStream.toByteArray();
        }
    }

    public static byte[] resizeForThumbnail(byte[] imageData, String imageType) throws IOException {
        int maxDimension = 500;
        imageType = imageType.split("/")[1];
        if (imageType.equals("/png")) {
            return imageData;
        }
        // Create a ByteArrayInputStream from the imageData byte array
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData)) {
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
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(resizedImage, imageType, outputStream);

                // Return the resized image data as a byte array
                return outputStream.toByteArray();
            }
        }
    }
}


