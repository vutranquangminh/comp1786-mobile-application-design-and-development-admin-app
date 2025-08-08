package com.vutranquangminh.lotusyogaapp.infrastructure.models.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// One to many relationship with Teacher
@Entity(
        foreignKeys = @ForeignKey(
                entity = Teacher.class,
                parentColumns = "Id",
                childColumns = "TeacherId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("TeacherId")
)
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public String Description;
    public String DateTime;
    public Integer Duration;
    public Integer Capacity;
    public String Price;
    public String Category;
    public int TeacherId;

    // Constructor, getters, and setters can be added as needed
    public Course(String name, String description, String dateTime, Integer duration, Integer capacity, String price, String category, int teacherId) {
        this.Id = 0;
        this.Name = name;
        this.Description = description;
        this.DateTime = dateTime;
        this.Duration = duration;
        this.Capacity = capacity;
        this.Price = price;
        this.Category = category;
        this.TeacherId = teacherId;
    }
    public Course() {
        // Default constructor for Room
    }
}
