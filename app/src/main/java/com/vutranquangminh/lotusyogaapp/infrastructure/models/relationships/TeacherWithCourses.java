package com.vutranquangminh.lotusyogaapp.infrastructure.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Course;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.List;

public class TeacherWithCourses {
    @Embedded
    public Teacher teacher;

    @Relation(
            parentColumn = "Id",
            entityColumn = "TeacherId")

    public List<Course> courses;
}
