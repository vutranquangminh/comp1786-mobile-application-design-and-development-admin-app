package com.tranquangdai.lotusyogaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.tranquangdai.lotusyogaapp.backend.fragments.ViewCourseActivity;
import com.tranquangdai.lotusyogaapp.backend.fragments.ViewTeacherActivity;
import com.tranquangdai.lotusyogaapp.infrastructure.AppDatabase;
import com.tranquangdai.lotusyogaapp.infrastructure.dao.CourseDao;
import com.tranquangdai.lotusyogaapp.infrastructure.dao.CustomerDao;
import com.tranquangdai.lotusyogaapp.infrastructure.dao.TeacherDao;
import com.tranquangdai.lotusyogaapp.infrastructure.dao.UserTransactionDao;
import com.tranquangdai.lotusyogaapp.infrastructure.firebase.FirebaseSyncService;

public class MainActivity extends AppCompatActivity {
    Button btnTeacher, btnCourse;
    private AppDatabase db;
    private UserTransactionDao userTransactionDao;
    private CourseDao courseDao;
    private CustomerDao customerDao;
    private TeacherDao teacherDao;
    private FirebaseSyncService syncService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inside onCreate() in MainActivity.java
        android.content.SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        new Thread(() -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "yoga_app_database").build();

             userTransactionDao = db.transactionDao();
             courseDao = db.courseDao();
             customerDao = db.customerDao();
             teacherDao = db.teacherDao();

             syncService = new FirebaseSyncService(
                     userTransactionDao, courseDao, customerDao, teacherDao
            );

            syncService.syncCloudToLocal();
        }).start();
        btnTeacher = findViewById(R.id.btnManageTeacher);
        btnCourse = findViewById(R.id.btnManageCourse);


        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTeacherActivity.class);
                startActivity(intent);
            }
        });

        btnCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewCourseActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        new Thread(() -> {
//            // Re-sync data when returning to the main activity
//            db = Room.databaseBuilder(getApplicationContext(),
//                    AppDatabase.class, "yoga_app_database").build();
//
//            transactionDao = db.transactionDao();
//            courseDao = db.courseDao();
//            customerDao = db.customerDao();
//            teacherDao = db.teacherDao();
//
//            syncService = new FirebaseSyncService(
//                    transactionDao, courseDao, customerDao, teacherDao
//            );
//
//            syncService.syncLocalToCloud();
//        }).start();
//
//    }
}