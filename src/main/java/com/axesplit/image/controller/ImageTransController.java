package com.axesplit.image.controller;

import com.axesplit.image.model.enums.CompressionFactor;
import com.axesplit.image.model.enums.ImageFormat;
import com.axesplit.image.model.enums.PhotoSize;
import com.axesplit.image.service.ImageTransServer;
import com.axesplit.image.uitl.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class ImageTransController {
    @Autowired
    private ImageTransServer imageTransServer;

    @PostMapping("/compress-image")
    public ResponseEntity<byte[]> compressImage(@RequestPart("file") MultipartFile file,
                                                @RequestParam(value = "compressionFactor", required = false) CompressionFactor compressionFactor,
                                                @RequestParam(value = "targetSize", required = false) Long targetSize) throws IOException {
        // If a compression factor is specified, use it to compress the image
        if (compressionFactor != null) {
            byte[] compressedImage = imageTransServer.compressImage(file, compressionFactor.getValue(), 0L);
            return byteToFormatFile(compressedImage, file);
        }
        // If a target size is specified, use it to compress the image
        else if (targetSize != null) {
            byte[] compressedImage = imageTransServer.compressImage(file, 0, targetSize);
            return byteToFormatFile(compressedImage, file);
        }
        // If neither a compression factor nor a target size is specified, return a bad request response
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/resize-image")
    public ResponseEntity<byte[]> resizeImage(@RequestPart("file") MultipartFile file, @RequestParam("photoSize") PhotoSize photoSize) throws IOException {
        byte[] resizedImage = imageTransServer.resizeImage(file, photoSize.getWidth(), photoSize.getHeight());
        return byteToFormatFile(resizedImage, file);
    }

    @PostMapping("/convertImage")
    public ResponseEntity<byte[]> convertImage(@RequestPart("file") MultipartFile file, @RequestParam("format") ImageFormat format) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (ImageIO.write(image, format.name(), baos)) {
            byte[] bytes = baos.toByteArray();
            return byteToFormatFile(bytes, file, format);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private static ResponseEntity<byte[]> byteToFormatFile(byte[] bytes, MultipartFile file, ImageFormat format) {
        HttpHeaders headers = new HttpHeaders();
        String generateFileName = FileUtil.getGenerateFileName(file);
        headers.add("Content-Disposition", "attachment; filename=" + generateFileName + "." + format.name().toLowerCase());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    private static ResponseEntity<byte[]> byteToFormatFile(byte[] bytes, MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        String generateFileName = FileUtil.getGenerateFileNameWithFormat(file);
        headers.add("Content-Disposition", "attachment; filename=" + generateFileName.toLowerCase());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }
}
