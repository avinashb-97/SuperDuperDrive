package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    NoteMapper noteMapper;
    UserMapper userMapper;
    AuthenticationService authenticationService;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper, AuthenticationService authenticationService)
    {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    public int addNote(Note note)
    {
        String username = authenticationService.getCurrentUserName();
        int userid = userMapper.getUser(username).getUserId();
        note.setUserId(userid);
        int id = noteMapper.insert(note);
        return id;
    }

    public int updateNote(Note note)
    {
        return noteMapper.update(note);
    }

    public int deleteNote(int noteId)
    {
        return noteMapper.delete(noteId);
    }

    public List<Note> getAllNotes()
    {
        int userid = authenticationService.getCurrentUserId();
        return noteMapper.getNotes(userid);
    }

}
