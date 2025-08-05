package com.tranquangdai.lotusyogaapp.infrastructure.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.CourseCustomerCrossRef;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Customer;

import java.util.List;

@Dao
public interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCustomer(Customer customer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCourseCustomerCrossRef(CourseCustomerCrossRef courseCustomerCrossRef);

    @Query("SELECT * FROM Customer")
    public List<Customer> getAllCustomers();

    @Query("SELECT * FROM CourseCustomerCrossRef")
    List<CourseCustomerCrossRef> getAllCourseCustomerCrossRef();
}
