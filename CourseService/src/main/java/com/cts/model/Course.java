package com.cts.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "course__info")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Lob
    private String courseContent; // Field to store the detailed content of the course (can be large text)
    @ElementCollection
    private List<String> youtubeLink;   // Field to store the URL of a YouTube video related to the course
}