package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;


@Mapper
public interface NoteMapper
{
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId} AND userId = #{userId}")
    int update(Note note);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId} AND userId = #{userId}")
    Note getNoteByIdAndUser(Integer noteId, Integer userId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getAllNotes(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId} AND userId = #{userId}")
    void delete(Integer noteId, Integer userId);
}
