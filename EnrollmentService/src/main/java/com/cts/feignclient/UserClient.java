package com.cts.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="USERSERVICE", url="http://localhost:8083/department")
public interface UserClient {
	@PostMapping("/save")
	public String saveDepartment(@RequestBody Department department);
	
	@GetMapping("/fetchById/{did}")
	public Department getDepartment(@PathVariable("did") int departmentId);
}
