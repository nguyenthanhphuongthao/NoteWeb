package com.calendar.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.DAO.NoteDAO;
import com.calendar.Entity.Note;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteDAO noteDAO;

	@Override
	public void saveNote(Note note) {
		noteDAO.save(note);
	}

	@Override
	public List<Note> listNote(String username) {
		return noteDAO.listNote(username);
	}

	@Override
	public Note findNote(Integer idNote) {
		return noteDAO.findNote(idNote);
	}

	@Override
	public void deleteNote(Note note) {
		noteDAO.delete(note);
	}

}
