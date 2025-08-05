package com.tranquangdai.lotusyogaapp.infrastructure.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTeacher(Teacher teacher);

    @Query("SELECT * FROM Teacher")
    List<Teacher> getAllTeachers();

    @Delete
    void deleteTeacher(Teacher teacher);
}
