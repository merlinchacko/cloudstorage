package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;


@Controller
@AllArgsConstructor
public class NoteController
{
    private NoteService noteService;

    @SneakyThrows
    @PostMapping(value = "/addNote")
    public String addNote(Authentication authentication, Note note, Model model)
    {
        if (note.getNoteId() != null)
        {
            noteService.updateNote(note, authentication.getName());
            model.addAttribute("message", "Note updated successfully.");
        }
        else
        {
            noteService.createNote(note, authentication.getName());
            model.addAttribute("message", "Note created successfully.");
        }
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping("/deleteNote/{id}")
    public String deleteNote(Authentication authentication, @PathVariable("id") Integer noteId, Model model)
    {
        noteService.deleteNote(noteId, authentication.getName());
        model.addAttribute("message", "Note deleted successfully.");
        model.addAttribute("result", "success");
        return "result";
    }
}
