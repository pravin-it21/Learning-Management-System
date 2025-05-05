package com.cts.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERSERVICE",path="/users")
public interface UserClient {
	@GetMapping("/checkUserExist/{uid}")
	public Boolean checkUserExist(@PathVariable("uid") int courseId );
}


