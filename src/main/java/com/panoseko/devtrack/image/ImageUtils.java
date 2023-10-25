package com.panoseko.devtrack.image;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
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

//    public static File convertImageToBlobFile(Image image) {
//        byte[] imageData = Base64.getDecoder().decode(image.getImageData());
//        try {
//            File imageFile = File.createTempFile(image.getName(), null);
//            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//                fos.write(imageData);
//            }
//            return imageFile;
//        }catch (IOException e){
//            e.printStackTrace();
//            return null;
//        }
//    }

//        public static File convertImageToFile(Image image) throws IOException {
//            File file = new File(image.getName());
//            try (FileOutputStream fos = new FileOutputStream(file)) {
//                fos.write(image.getImageData());
//            }
//            return file;
//        }

}
