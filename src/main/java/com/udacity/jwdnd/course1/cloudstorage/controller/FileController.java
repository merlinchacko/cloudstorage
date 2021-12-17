package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;


@Controller
@AllArgsConstructor
public class FileController
{
    private FileService fileService;

    @SneakyThrows
    @PostMapping("/uploadFile")
    public String uploadFile(Authentication authentication, @RequestParam("file") MultipartFile multipartFile, Model model)
    {
        if (fileService.getFileByName(multipartFile.getOriginalFilename()) != null)
        {
            model.addAttribute("result", "error");
            model.addAttribute("message", "File already exists.");
        }
        else
        {
            fileService.uploadFile(multipartFile, authentication.getName());
            model.addAttribute("result", "success");
            model.addAttribute("message", "File saved successfully.");
        }

        return "result";
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<byte[]> downloadFile(Authentication authentication, @PathVariable("id") Integer fileId)
    {
        File file = fileService.getFile(fileId, authentication.getName());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .contentLength(file.getFileSize())
            .body(file.getFileData());
    }

    @GetMapping("/deleteFile/{id}")
    public String deleteFile(Authentication authentication, @PathVariable("id") Integer fileId, Model model)
    {
        fileService.deleteFile(fileId, authentication.getName());
        model.addAttribute("message", "File deleted successfully.");
        model.addAttribute("result", "success");
        return "result";
    }
}
