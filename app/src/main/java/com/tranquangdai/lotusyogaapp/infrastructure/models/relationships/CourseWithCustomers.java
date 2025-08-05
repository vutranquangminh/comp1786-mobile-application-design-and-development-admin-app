package com.tranquangdai.lotusyogaapp.infrastructure.models.relationships;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Course;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.CourseCustomerCrossRef;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Customer;

import java.util.List;

public class CourseWithCustomers {
    @Embedded
    public Course course;

    @Relation(
            parentColumn = "Id",
            entityColumn = "Id",
            associateBy = @Junction(
                    value = CourseCustomerCrossRef.class,
                    parentColumn = "courseId",
                    entityColumn = "customerId")
    )
    public List<Customer> customers;
}
