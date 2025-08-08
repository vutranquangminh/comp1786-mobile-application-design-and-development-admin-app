package com.vutranquangminh.lotusyogaapp.infrastructure.models.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Course;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.CourseCustomerCrossRef;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Customer;

import java.util.List;

public class CustomerWithCourses {
    @Embedded
    public Customer customer;

    @Relation(
            parentColumn = "Id",
            entityColumn = "Id",
            associateBy = @Junction(
                    value = CourseCustomerCrossRef.class,
                    parentColumn = "customerId",
                    entityColumn = "courseId")
    )
    public List<Course> courses;
}
