package com.tranquangdai.lotusyogaapp.backend.teachers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.tranquangdai.lotusyogaapp.R;
import com.tranquangdai.lotusyogaapp.backend.fragments.ViewTeacherActivity;
import com.tranquangdai.lotusyogaapp.infrastructure.AppDatabase;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Teacher;

public class EditTeacherActivity extends AppCompatActivity {

    private EditText edtTeacherName, edtYearsOfExperience;
    private TextView txtTitle;
    private Button btnSave, btnCancel;
    private AppDatabase db;
    private Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teacher);

        txtTitle = findViewById(R.id.textTeacher);
        edtTeacherName = findViewById(R.id.edtTeacherName);
        edtYearsOfExperience = findViewById(R.id.edtYearsOfExperience);
        btnSave = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setText("Save");
        txtTitle.setText("Edit Teacher");

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "yoga_app_database").allowMainThreadQueries().build();

        int teacherId = getIntent().getIntExtra("teacherId", -1);
        if (teacherId == -1) {
            Toast.makeText(this, "Invalid teacher", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        teacher = getTeacherById(teacherId);
        if (teacher == null) {
            Toast.makeText(this, "Teacher not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        edtTeacherName.setText(teacher.Name);
        edtYearsOfExperience.setText(teacher.Experience);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeacher();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTeacherActivity.this, ViewTeacherActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private Teacher getTeacherById(int id) {
        for (Teacher t : db.teacherDao().getAllTeachers()) {
            if (t.Id == id) return t;
        }
        return null;
    }

    private void saveTeacher() {
        String name = edtTeacherName.getText().toString().trim();
        String experience = edtYearsOfExperience.getText().toString().trim();

        if (name.isEmpty() || experience.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        teacher.Name = name;
        teacher.Experience = experience;

        // Room does not have an @Update in your DAO, so delete and re-insert
        db.teacherDao().deleteTeacher(teacher);
        db.teacherDao().insertTeacher(teacher);

        Toast.makeText(this, "Teacher updated", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EditTeacherActivity.this, ViewTeacherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}