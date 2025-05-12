package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AuthRequest;
import com.entity.UserInfo;
import com.exception.UserNotFound;
import com.repository.UserInfoRepository;
import com.service.JwtService;
import com.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserInfoRepository repo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")		//http://localhost:9090/auth/welcome
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")	//http://localhost:9090/auth/new
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }



    @PostMapping("/authenticate")		//http://localhost:9090/auth/authenticate
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
        	UserInfo obj = repo.findByName(authRequest.getUsername()).orElse(null);
            return jwtService.generateToken(authRequest.getUsername(),obj.getRoles());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    
    @GetMapping("/getroles/{username}")		//http://localhost:9090/auth/getroles/{username}
    public String getRoles(@PathVariable String username)
    {
    	return service.getRoles(username);
    }
    
    @PutMapping("/update")
	public UserInfo updateUser(@RequestBody UserInfo user) {
		return service.updateUser(user);
	}
    
    @DeleteMapping("/remove/{uid}")
	public String removeUser(@PathVariable("uid") int userId) {
		return service.removeUser(userId);
	}

	@GetMapping("/fetchById/{uid}")
	public UserInfo getById(@PathVariable("uid") int userId) throws UserNotFound {
		return service.getUserById(userId);
	}
	
	@GetMapping("/checkUserExist/{uid}")
	public Boolean checkUserExist(@PathVariable("uid") int userId) throws UserNotFound {
		return service.checkUserExist(userId);
	}
	
	
	@GetMapping("/fetchByEmail/{uemail}")
	public UserInfo getByEmail(@PathVariable("uemail") String userEmail) {
		return service.getUserByEmail(userEmail);
	}
	
	@GetMapping("/fetchAll")
	public List<UserInfo> getAllUsers() {
		return service.getAllUsers();
	}

    
}
