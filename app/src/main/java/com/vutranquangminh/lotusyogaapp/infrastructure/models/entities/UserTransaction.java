package com.vutranquangminh.lotusyogaapp.infrastructure.models.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// One to one relationship with Customer
@Entity(
        foreignKeys = @ForeignKey(
                entity = Customer.class,
                parentColumns = "Id",
                childColumns = "CustomerId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index("Id")
)
public class UserTransaction {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public int CustomerId;
    public String DateTime;
    public String Amount;
    public String PaymentMethod;
    public boolean Status;

    public UserTransaction(String dateTime, String amount, String paymentMethod, boolean status, int customerId) {
        Id = 0;
        DateTime = dateTime;
        Amount = amount;
        PaymentMethod = paymentMethod;
        CustomerId = customerId;
        Status = status;
    }
    public UserTransaction() {
    }
}
