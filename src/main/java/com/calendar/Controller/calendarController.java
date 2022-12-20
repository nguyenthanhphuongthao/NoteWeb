package com.calendar.Controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.calendar.Entity.Account;
import com.calendar.Service.AccountService;

@Controller
public class calendarController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping(value = "/calendar")
	public ModelAndView displayCalendar(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("calendar");
		mv.addObject("loginAccount", session.getAttribute("loginAccount"));
		return mv;
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
			return "redirect:/calendar";
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
		ModelAndView mv = new ModelAndView();
		mv.setViewName("calendar");
		if (accountService.findByUsername(account.getUsername()) == null) {
			accountService.saveAccount(account);
			session.removeAttribute("alert");
			session.setAttribute("loginAccount", account);
			return "redirect:/calendar";
		} else {
			session.setAttribute("alert", "Đã tồn tại tài khoản với tên đăng nhập này");
		}
		return "redirect:/register";
	}
}