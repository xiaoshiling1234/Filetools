package com.axesplit.image.uitl;

import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {
    public static String getGenerateFileNameWithFormat(MultipartFile file) {
        String originalFileName = file.getOriginalFilename(); // get the original file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()); // create a time stamp
        String newFileName = originalFileName + "_" + timeStamp + "." + FilenameUtils.getExtension(originalFileName);
        return newFileName;
    }

    public static String getGenerateFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename(); // get the original file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()); // create a time stamp
        String newFileName = originalFileName + "_" + timeStamp;
        return newFileName;
    }
}
