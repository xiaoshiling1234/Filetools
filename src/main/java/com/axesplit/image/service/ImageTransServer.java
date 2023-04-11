package com.axesplit.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageTransServer {

    byte[] compressImage(MultipartFile file, int compressionFactor, Long targetSize) throws IOException;

    byte[] resizeImage(MultipartFile file, int width, int height) throws IOException;
}

