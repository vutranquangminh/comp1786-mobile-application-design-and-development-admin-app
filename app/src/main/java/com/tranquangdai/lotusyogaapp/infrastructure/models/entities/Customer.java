package com.tranquangdai.lotusyogaapp.infrastructure.models.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public String Email;
    public String Password;
    public String DateCreated;
    public String PhoneNumber;
    public String DateOfBirth;
    public String ImageUrl;
    public double Balance;

    public Customer(String name, String email, String password, String dateCreated, String phoneNumber, String dateOfBirth, String imageUrl, double balance) {
        this.Id = 0;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.DateCreated = dateCreated;
        this.PhoneNumber = phoneNumber;
        this.DateOfBirth = dateOfBirth;
        this.ImageUrl = imageUrl;
        this.Balance = balance;
    }
    public Customer() {
        // Default constructor for Room
    }

}
