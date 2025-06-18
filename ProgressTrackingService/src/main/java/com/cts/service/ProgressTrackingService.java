package com.cts.service;

import java.util.List;

import com.cts.dto.AdminUserProgressDTO;
import com.cts.dto.CourseDTO;
import com.cts.dto.UserDTO;

public interface ProgressTrackingService {

	public abstract List<CourseDTO> getCourseByUserId(int userId);

	public abstract UserDTO getProgressByUserId(int userId);

    public List<AdminUserProgressDTO> getAllProgressWithUserDetails();

	public abstract List<UserDTO> getAllProgress();
}
