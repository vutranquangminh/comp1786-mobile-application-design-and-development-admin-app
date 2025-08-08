package com.vutranquangminh.lotusyogaapp.infrastructure.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Customer;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.UserTransaction;

public class CustomerWithTransaction {
    @Embedded
    public Customer customer;

    @Relation(
            parentColumn = "Id",
            entityColumn = "Id"
    )
    public UserTransaction userTransaction;
}
