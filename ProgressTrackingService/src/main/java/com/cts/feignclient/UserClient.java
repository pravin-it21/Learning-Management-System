package com.cts.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cts.dto.UserInfo;

@FeignClient(name = "SECURITY-SERVICE") // Replace "authentication-service" with the actual name of your authentication service
public interface UserClient {

    @GetMapping("/auth/fetchAll")
    List<UserInfo> getAllUsers();
}