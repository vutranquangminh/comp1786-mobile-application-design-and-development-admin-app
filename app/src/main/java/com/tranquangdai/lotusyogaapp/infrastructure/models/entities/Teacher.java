package com.tranquangdai.lotusyogaapp.infrastructure.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Teacher {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public String Experience;
    public String DateStartedTeaching;

    public Teacher(String name, String experience, String dateStartedTeaching) {
        this.Id = 0;
        this.Name = name;
        this.Experience = experience;
        this.DateStartedTeaching = dateStartedTeaching;
    }
    public Teacher() {
        // Default constructor for Room
    }
}
