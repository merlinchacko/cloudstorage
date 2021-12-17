package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.File;


@Mapper
public interface FileMapper
{
    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    File getFileByName(String fileName);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, fileData, userId) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    File getFileByIdAndUser(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getAllFiles(Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    void deleteFile(Integer fileId, Integer userId);
}
