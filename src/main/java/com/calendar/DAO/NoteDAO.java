package com.calendar.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.calendar.Entity.Note;

@Repository
public interface NoteDAO extends JpaRepository<Note, Long>{

	@Query("SELECT n FROM Note n WHERE username=?1")
	public List<Note> listNote(String username);

	@Query("SELECT n FROM Note n WHERE id=?1")
	public Note findNote(Integer idNote);
}
