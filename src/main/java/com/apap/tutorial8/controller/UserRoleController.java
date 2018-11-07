package com.apap.tutorial8.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		userService.addUser(user);
		model.addAttribute("updateResponse", null);
		return "home";
	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	private String updatePassSubmit(Principal principal, HttpServletRequest req, Model model) {
		String username = principal.getName();
		String oldPass = req.getParameter("oldPassword");				//password lama yang diinput dari form
		String newPass = req.getParameter("password");
		String confirmPass = req.getParameter("confirmPassword");
		String currentPass = userService.getUserRoleDb().findByUsername(principal.getName()).getPassword();		//password lama yang ada di database
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if (!passwordEncoder.matches(oldPass, currentPass)) {
			model.addAttribute("updateResponse", "Password lama Anda salah. Silahkan mencoba kembali!");
		}
		else if (!newPass.equalsIgnoreCase(confirmPass)) {
			model.addAttribute("updateResponse", "Password yang anda input tidak sama!");
		}
		else {
			model.addAttribute("updateResponse", "success");
			userService.updatePassword(username, newPass);
		}

		return "home";
	}
}
