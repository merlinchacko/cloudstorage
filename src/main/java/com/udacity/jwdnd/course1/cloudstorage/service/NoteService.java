package com.udacity.jwdnd.course1.cloudstorage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class NoteService
{
    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public void createNote(final Note note, final String userName)
    {
        User user = userMapper.getUser(userName);
        note.setUserId(user.getUserId());
        noteMapper.insert(note);
    }

    public void updateNote(final Note note, final String userName)
    {
        User user = userMapper.getUser(userName);
        note.setUserId(user.getUserId());
        noteMapper.update(note);
    }

    public void deleteNote(final Integer noteId, final String userName)
    {
        User user = userMapper.getUser(userName);
        noteMapper.delete(noteId, user.getUserId());
    }

    public List<Note> getAllNotes(final String userName)
    {
        User user = userMapper.getUser(userName);
        return noteMapper.getAllNotes(user.getUserId());
    }
}
