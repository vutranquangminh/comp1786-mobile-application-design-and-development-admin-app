package com.vutranquangminh.lotusyogaapp.backend.teachers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vutranquangminh.lotusyogaapp.R;
import com.vutranquangminh.lotusyogaapp.backend.fragments.ViewTeacherActivity;
import com.vutranquangminh.lotusyogaapp.infrastructure.AppDatabase;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateTeacherActivity extends AppCompatActivity {

    private EditText edtTeacherName, edtYearsOfExperience;
    private TextView txtTitle;
    private Button btnSubmit, btnCancel;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teacher);

        txtTitle = findViewById(R.id.textTeacher);
        edtTeacherName = findViewById(R.id.edtTeacherName);
        edtYearsOfExperience = findViewById(R.id.edtYearsOfExperience);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        txtTitle.setText("Create new Teacher");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeacher();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to ViewTeacherActivity
                Intent intent = new Intent(CreateTeacherActivity.this, ViewTeacherActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private void addTeacher() {
        String name = edtTeacherName.getText().toString().trim();
        String experience = edtYearsOfExperience.getText().toString().trim();

        if (name.isEmpty() || experience.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Teacher teacher = new Teacher(name, experience, currentDate);

        // Get database instance and insert teacher (should be done off main thread in production)
        db = AppDatabase.getInstance(getApplicationContext());
//        db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "yoga_app_database").allowMainThreadQueries().build();

        new Thread(() -> {
            db.teacherDao().insertTeacher(teacher);
        }).start();

//        TeacherDao teacherDao = db.teacherDao();
//        teacherDao.insertTeacher(teacher);

        Toast.makeText(this, "Teacher added", Toast.LENGTH_SHORT).show();

        // Return to ViewTeacherActivity
        Intent intent = new Intent(CreateTeacherActivity.this, ViewTeacherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
