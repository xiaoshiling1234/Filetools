package com.axesplit.image.service.impl;

import com.axesplit.image.service.ImageTransServer;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageTransServerImpl implements ImageTransServer {
    @Override
    public byte[] compressImage(MultipartFile file, int compressionFactor, Long targetSize) throws IOException {
        // Convert the uploaded file to a byte array
        byte[] originalBytes = file.getBytes();

        // Define variables for the compressed image
        byte[] compressedBytes = null;
        double compressionRatio = compressionFactor == 0 ? 1 : 1.0 / compressionFactor;
        double currentSize = originalBytes.length;

        InputStream fileInputStream = file.getInputStream();
        // If a target size is specified, compress the image until it is smaller than the target size
        if (targetSize > 0) {
            while (currentSize > targetSize) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Thumbnails.of(fileInputStream).scale(compressionRatio).outputQuality(0.9).toOutputStream(outputStream);
                compressedBytes = outputStream.toByteArray();
                currentSize = compressedBytes.length;
                compressionRatio = Math.sqrt(targetSize / currentSize);
                fileInputStream = new ByteArrayInputStream(outputStream.toByteArray());
            }
        }
        // If a compression factor is specified, compress the image by the specified factor
        else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(file.getInputStream()).scale(compressionRatio).outputQuality(0.9).toOutputStream(outputStream);
            compressedBytes = outputStream.toByteArray();
        }

        // Return the compressed image
        return compressedBytes;
    }

    @Override
    public byte[] resizeImage(MultipartFile file, int width, int height) throws IOException {
        // Convert the uploaded file to a byte array
        byte[] originalBytes = file.getBytes();

        // Define variables for the resized image
        byte[] resizedBytes = null;

        // Resize the image to the specified dimensions
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream()).size(width, height).outputQuality(0.9).toOutputStream(outputStream);
        resizedBytes = outputStream.toByteArray();

        // Return the resized image
        return resizedBytes;
    }
}
