package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@Service
public class AuthenticationService {
	@Autowired
	private UsersRepository userDataRepository;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private CartClient cartClient;
	@Autowired
	private WishlistClient wishlistClient;
	
	public String register(Users data) {

		System.out.println(data);
		System.out.println(data.getPassword());
		data.setPass(passwordEncoder.encode(data.getPassword()));
		userDataRepository.save(data);
		cartClient.initCart(data.getId());
		wishlistClient.initList(data.getId());

		return data.toString();
	}
	

	public String login(AuthenticationRequest request) {
		
			String email = request.getEmail();
			String password = request.getPassword();
			Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(
					request.getEmail(), request.getPassword()));
			
			if (auth.isAuthenticated())
			{
				// Find UserEntity
				Users data = userDataRepository.findByEmail(email)
						.get();
				// Invalidate all old tokens
				List<Token> tokens = tokenRepository.findAllValidTokenByUsers(data.getId());
				if (!tokens.isEmpty())
				{
					tokenRepository.saveAll(tokens.stream()
							.peek(d -> d.setValid(false))
							.collect(Collectors.toList()));
				}
				// create and return new valid token
				String token = jwtService.generateToken(request.getEmail());
				tokenRepository.save(Token.builder()
						.token(token)
						.userData(data)
						.valid(true)
						.role(data.getRole())
						.build());
				return token;
			} else
			{
				throw new RuntimeException("Invalid Credentials");
			}
	}
	
	public void logout(String jwtToken)
	{
		Token token = tokenRepository.findByToken(jwtToken)
				.get();
		token.setValid(false);
		tokenRepository.save(token);
		System.out.println("Logged Out");
	}
	
	public String validate(String jwtToken)
	{
		return jwtService.validate(jwtToken);
	}
	
	public String getId(String token)
	{
		return String.valueOf(tokenRepository.findByToken(token)
				.get()
				.getUserData()
				.getId());
	}
	
	public String getAddress(int userId)
	{
		Users ue = userDataRepository.findById(userId)
				.get();
		return ue.getAddress().toString();
	}
}
