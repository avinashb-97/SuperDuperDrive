package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constants.CredentialConstants;
import com.udacity.jwdnd.course1.cloudstorage.constants.NoteConstants;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotesController {

    NoteService noteService;

    NotesController(NoteService noteService)
    {
        this.noteService = noteService;
    }

    @PostMapping("/notes")
    public String addNotes(Note note, RedirectAttributes redirectAttributes)
    {
        if(note.getId() == null)
        {
            noteService.addNote(note);
            redirectAttributes.addFlashAttribute("actionMessage", NoteConstants.ADD_SUCCESS);
        }
        else
        {
            noteService.updateNote(note);
            redirectAttributes.addFlashAttribute("actionMessage", NoteConstants.EDIT_SUCCESS);
        }
        redirectAttributes.addFlashAttribute("currentTab","notes");
        redirectAttributes.addFlashAttribute("actionSuccess","true");
        return "redirect:home";
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam(name = "noteId", required = false) Integer noteId, RedirectAttributes redirectAttributes)
    {
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("currentTab","notes");
        redirectAttributes.addFlashAttribute("actionSuccess","true");
        redirectAttributes.addFlashAttribute("actionMessage", NoteConstants.DELETE_SUCCESS);
        return "redirect:home";
    }

}
