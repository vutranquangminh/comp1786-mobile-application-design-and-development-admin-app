package com.tranquangdai.lotusyogaapp.infrastructure.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Course;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.List;

public class TeacherWithCourses {
    @Embedded
    public Teacher teacher;

    @Relation(
            parentColumn = "Id",
            entityColumn = "TeacherId")

    public List<Course> courses;
}
