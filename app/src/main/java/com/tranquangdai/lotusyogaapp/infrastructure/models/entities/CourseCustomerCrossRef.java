package com.tranquangdai.lotusyogaapp.infrastructure.models.entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"customerId", "courseId"})

public class CourseCustomerCrossRef {
//    public int Id;
    public int customerId;
    public int courseId;


    public CourseCustomerCrossRef() {
        // Default constructor for Room
    }
    public CourseCustomerCrossRef(int customerId, int courseId) {
//        this.Id = 0; // Auto-generated primary key, if needed
        this.customerId = customerId;
        this.courseId = courseId;
    }
}
