package com.calendar.Controller;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calendar.Entity.Account;
import com.calendar.Entity.Note;
import com.calendar.Service.AccountService;
import com.calendar.Service.NoteService;

@Controller
public class calendarController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private NoteService noteService;
	
	@GetMapping(value = "/listnote")
	public ModelAndView displayCalendar(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("listnote");
		mv.addObject("loginAccount", session.getAttribute("loginAccount"));
		Account loginAccount = (Account)session.getAttribute("loginAccount");
		List<Note> notes = noteService.listNote(loginAccount.getUsername());
		mv.addObject("notes", notes);
		return mv;
	}
	
	@GetMapping(value = {"/newnote"})
	public ModelAndView newNote() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("createnote");
		mv.addObject("note", new Note());
		return mv;
	}
	
	@PostMapping(value = "/newnote")
	public String createNote(@ModelAttribute("note") Note note, HttpSession session) {
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		note.setAccount((Account)session.getAttribute("loginAccount"));
		note.setDate(date);
		noteService.saveNote(note);
		return "redirect:/listnote";
	}
	
	@GetMapping(value = {"/detailnote-{id}"})
	public ModelAndView detailNote(@PathVariable("id") Integer id, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("detailnote");
		Note note = noteService.findNote(id);
		mv.addObject("note", note);
		return mv;
	}
	
	@GetMapping(value = {"/deletenote-{id}"})
	public String deleteNote(@PathVariable("id") Integer id, HttpSession session) {
		Note note = noteService.findNote(id);
		noteService.deleteNote(note);
		return "redirect:/listnote";
	}
	
	@GetMapping(value = {"/", "/login"})
	public ModelAndView displayLogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		mv.addObject("loginAccount", new Account());
		return mv;
	}
	
	@PostMapping(value = "/login")
	public String login(@ModelAttribute("loginAccount") Account account, HttpSession session, Model model) {
		Account acc = accountService.findByUsernameAndPassword(account.getUsername(), account.getPassword());
		if (Objects.nonNull(acc)) {
			session.removeAttribute("alert");
			session.setAttribute("loginAccount", acc);
			return "redirect:/listnote";
		} else {
			session.setAttribute("alert", "Sai tên đăng nhập hoặc mật khẩu!");
			return "redirect:/login";
		}
	}
	
	@GetMapping(value = {"/register"})
	public ModelAndView displayRegister() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		mv.addObject("account", new Account());
		return mv;
	}
	
	@PostMapping(value = "/register")
	public String register(@ModelAttribute("account") Account account, HttpSession session) {
		if (accountService.findByUsername(account.getUsername()) == null) {
			accountService.saveAccount(account);
			session.removeAttribute("alert");
			session.setAttribute("loginAccount", account);
			return "redirect:/listnote";
		} else {
			session.setAttribute("alert", "Đã tồn tại tài khoản với tên đăng nhập này");
		}
		return "redirect:/register";
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginAccount");
		return "redirect:/login";
	}
}