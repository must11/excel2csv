package com.github.must11.excel2csv;

import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空，请重新上传";
        }

        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();

            String csvFileName ="/upload/"+fileName.replace(".xlsx",".csv");
            // 获取文件的字节a
            EasyExcel.read(file.getInputStream(), new NoModelDataListener(csvFileName)).sheet().doRead();

            // 这里可以添加你的文件保存逻辑

            return "文件上传成功：" + fileName;
        } catch (Exception e) {
            return "文件上传失败：" + e.getMessage();
        }
    }
}