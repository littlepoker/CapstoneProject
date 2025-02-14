package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
public class AdminService {
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private UsersRepository dataRepository;
	@Autowired
	private CartClient cartClient;
	@Autowired
	private WishlistClient wishlistClient;
	
	public List<Users> fetchAllUsers() {
		List<Users> sanitized = dataRepository.findAll()
				.stream()
				.peek(d -> d.setPass("hidden"))
				.toList();
		return sanitized;
	}
	
	public Users updateUser(int id, Users newUser) {
		Optional<Users> user = dataRepository.findById(id);
		if (user.isPresent()) {
			user.get().setEmail(newUser.getEmail());
			user.get().setPass(newUser.getPassword());
			user.get().setAddress(newUser.getAddress());
			return dataRepository.save(user.get());
		}
		else {
			return null;
		}
	}
	
	public void deleteUser(int id) {
		dataRepository.deleteById(id);
		cartClient.deleteCart(id);
		wishlistClient.deleteList(id);
	}
}
