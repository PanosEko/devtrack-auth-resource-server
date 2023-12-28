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
        int maxDimension = 500; // px
        imageType = imageType.split("/")[1];
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData)) {
            BufferedImage originalImage = ImageIO.read(inputStream);

            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            // If the original dimensions are small, return the original image data
            if (originalWidth <= maxDimension && originalHeight <= maxDimension) {
                return imageData;
            }

            int newWidth;
            int newHeight;

            if (originalWidth > originalHeight) {
                // Landscape image
                newWidth = maxDimension;
                newHeight = (int) ((double) originalHeight / originalWidth * maxDimension);
            } else {
                // Portrait image
                newWidth = (int) ((double) originalWidth / originalHeight * maxDimension);
                newHeight = maxDimension;
            }

            // Create a scaled version of the image
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            graphics2D.dispose();

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(resizedImage, imageType, outputStream);

                return outputStream.toByteArray();
            }
        }
    }
}


