package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/auth")
@CrossOrigin
public class AuthenticationController {
	@Autowired
	private AuthenticationService authService;
	
	@PostMapping ("/register")
	public String register(@RequestBody Users data) {
		return authService.register(data);
	}
	
	
	@PostMapping ("/login")
	public String login(@RequestBody AuthenticationRequest request) {
		return authService.login(request);
	}
	
	@PostMapping("/id")
	public String getUserId(@RequestParam("token") String token)
	{
		return authService.getId(token);
	}
	
	@PostMapping ("/logout")
	public void logout(@RequestParam("token") String JwtToken) {
		authService.logout(JwtToken);
	}
	
	@PostMapping("/validate")
	public String validate(@RequestParam("token") String jwtToken)
	{
		System.out.println(jwtToken);
		return authService.validate(jwtToken)
				.toString();
	}
	
	@PostMapping("/address")
	public String getAddress(@RequestParam("userId") int userId)
	{
		return authService.getAddress(userId);
	}
	
	@Autowired
	private AdminService adminService;

	@GetMapping("/admin/users")
	public List<Users> getAll()
	{
		return adminService.fetchAllUsers();
	}

	@DeleteMapping("/admin/users/{id}")
	public void delete(@PathVariable int id)
	{
		adminService.deleteUser(id);
	}

	@PutMapping ("/admin/users/{id}")
	public Users update(@PathVariable int id, @RequestBody Users user)
	{
		return adminService.updateUser(id, user);
	}
}
