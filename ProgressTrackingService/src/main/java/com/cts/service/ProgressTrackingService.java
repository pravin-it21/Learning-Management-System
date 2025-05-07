package com.cts.service;

import java.util.List;

import com.cts.dto.CourseDTO;

public interface ProgressTrackingService {

	public abstract List<CourseDTO> getCourseByUserId(int userId);

}
