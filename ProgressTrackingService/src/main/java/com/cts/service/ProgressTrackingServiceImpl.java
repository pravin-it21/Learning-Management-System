package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.CourseDTO;
import com.cts.feignclient.EnrollmentClient;
@Service
public class ProgressTrackingServiceImpl implements ProgressTrackingService {
	@Autowired
	EnrollmentClient enrollmentClient;
	@Override
	public List<CourseDTO> getCourseByUserId(int userId) {
		return enrollmentClient.getCoursesByUserId(userId);
	}

}
