package com.udacity.jwdnd.course1.cloudstorage.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class FileService
{
    private FileMapper fileMapper;
    private UserMapper userMapper;

    public void uploadFile(final MultipartFile multipartFile, String userName) throws IOException
    {
        User user = userMapper.getUser(userName);

        File file =
            new File(user.getUserId(),
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getSize(),
                multipartFile.getBytes());

        fileMapper.insert(file);
    }

    public File getFileByName(String fileName) {
        return fileMapper.getFileByName(fileName);
    }

    public File getFile(final Integer fileId, final String userName)
    {
        User user = userMapper.getUser(userName);
        return fileMapper.getFileByIdAndUser(fileId, user.getUserId());
    }

    public List<File> getAllFiles(final String userName) {
        User user = userMapper.getUser(userName);
        return fileMapper.getAllFiles(user.getUserId());
    }

    public void deleteFile(final Integer fileId, final String userName) {
        User user = userMapper.getUser(userName);
        fileMapper.deleteFile(fileId, user.getUserId());
    }

}
