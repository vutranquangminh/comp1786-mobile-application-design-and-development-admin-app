package com.tranquangdai.lotusyogaapp.infrastructure.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM Course")
    List<Course> getAllCourses();

    @Query("SELECT * FROM Course WHERE Id = :id")
    Course getCourseById(int id);

    @Update
    void updateCourse(Course course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Delete
    void deleteCourse(Course course);

}
