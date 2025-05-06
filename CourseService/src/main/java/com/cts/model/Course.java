package com.cts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private int courseId; 

    @NotBlank(message = "Course Title can't be NULL or BLANK")
    private String courseTitle;

    @NotBlank(message = "Course Description can't be NULL or BLANK")
    private String courseDescription;

    @NotBlank(message = "Course Category can't be NULL or BLANK")
    private String courseCategory;

    private int instructorId; // Can be optional if mapped to another table

    @NotBlank(message = "Course prerequisites can't be NULL or BLANK")
    private String prerequisites;

    @NotNull(message = "Course duration can't be NULL")
    private int courseDuration; // Fixing missing default value issue
}
