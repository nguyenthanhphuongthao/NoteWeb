package com.calendar.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.calendar.Entity.Note;

@Service
public interface NoteService {

	void saveNote(Note note);
	List<Note> listNote(String username);
	Note findNote(Integer idNote);
	void deleteNote(Note note);
}
