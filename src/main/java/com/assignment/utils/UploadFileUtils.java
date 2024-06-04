package com.assignment.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadFileUtils {
    public static String uploadFile(MultipartFile file) {
        // Kiểm tra xem file có tồn tại không
        if (file.isEmpty()) {
            return "Vui lòng chọn 1 file để upload";
        }

        try {
            String uploadsDir = "D:\\FPT Polytechnic\\FALL_2023\\SOF3021_Java05\\Exercise\\SolarBachThinh-BackEnd\\src\\main\\resources\\static\\upload";
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = new File(uploadsDir).getAbsolutePath() + File.separator + fileName;
            file.transferTo(new File(filePath));

            return "fileName";
        } catch (IOException e) {
            e.printStackTrace();
            return "Lỗi trong khi upload ảnh";
        }
    }
}
