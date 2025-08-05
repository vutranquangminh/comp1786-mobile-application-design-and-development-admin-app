package com.tranquangdai.lotusyogaapp.infrastructure;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tranquangdai.lotusyogaapp.infrastructure.dao.CourseDao;
import com.tranquangdai.lotusyogaapp.infrastructure.dao.CustomerDao;
import com.tranquangdai.lotusyogaapp.infrastructure.dao.TeacherDao;
import com.tranquangdai.lotusyogaapp.infrastructure.dao.UserTransactionDao;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Course;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.CourseCustomerCrossRef;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Customer;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Teacher;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.UserTransaction;

@Database(
        entities = {
                Customer.class,
                Course.class,
                CourseCustomerCrossRef.class,
                UserTransaction.class,
                Teacher.class
        },
        version = 2,
        exportSchema = true
)
public abstract class AppDatabase extends RoomDatabase {

    // Define your DAO
    public abstract CourseDao courseDao();
    public abstract CustomerDao customerDao();
    public abstract TeacherDao teacherDao();
    public abstract UserTransactionDao transactionDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Step 1: Rename the old table
            database.execSQL("ALTER TABLE Customer RENAME TO Customer_old");

            // Step 2: Create the new table with the updated schema
            database.execSQL("CREATE TABLE IF NOT EXISTS Customer (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "Name TEXT, " +
                    "Email TEXT, " +
                    "Password TEXT, " +
                    "DateCreated TEXT, " +
                    "PhoneNumber TEXT, " +
                    "DateOfBirth TEXT, " +
                    "ImageUrl TEXT" +
                    ")");

            // Step 3: Copy data from the old table to the new table
            database.execSQL("INSERT INTO Customer (Id, Name, Email, Password, DateCreated, PhoneNumber, DateOfBirth, ImageUrl) " +
                    "SELECT Id, Name, Email, Password, DateCreated, PhoneNumber, DateOfBirth, ImageUrl FROM Customer_old");

            // Step 4: Drop the old table
            database.execSQL("DROP TABLE Customer_old");
        }
    };

//    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Customer ADD COLUMN Balance REAL NOT NULL DEFAULT 0.0");
//        }
//    };

//    public static final Migration MIGRATION_2_3 = new Migration(2,3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("DROP TABLE CourseCustomerCrossRef");
//            database.execSQL("CREATE TABLE IF NOT EXISTS CourseCustomerCrossRef (" +
//                    "CourseId INTEGER NOT NULL, " +
//                    "CustomerId INTEGER NOT NULL, " +
//                    "PRIMARY KEY(CourseId, CustomerId), " +
//                    "FOREIGN KEY(CourseId) REFERENCES Course(CourseId) ON UPDATE NO ACTION ON DELETE CASCADE, " +
//                    "FOREIGN KEY(CustomerId) REFERENCES Customer(Id) ON UPDATE NO ACTION ON DELETE CASCADE" +
//                    ")");
//        }
//    };

    // Singleton instance
    private static volatile AppDatabase INSTANCE;
    public static final RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // Custom logic after DB is opened (optional)
            android.util.Log.d("DB_CALLBACK", "Database opened successfully");
        }
    };

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = Room.databaseBuilder(
                                        context.getApplicationContext(),
                                        AppDatabase.class,
                                        "yoga_app_database"
                                )
                                .addMigrations(MIGRATION_1_2)
                                .addCallback(dbCallback)
                                .build();
                    } catch (Exception e) {
                        android.util.Log.e("AppDatabase", "Error creating database instance", e);
                        throw new RuntimeException("Failed to create database instance", e);
                    }
                }
            }
        }
        return INSTANCE;
    }
}

