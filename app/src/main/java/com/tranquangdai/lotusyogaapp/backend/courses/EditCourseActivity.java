package com.tranquangdai.lotusyogaapp.backend.courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.room.Room;

import com.tranquangdai.lotusyogaapp.R;
import com.tranquangdai.lotusyogaapp.backend.fragments.ViewCourseActivity;
import com.tranquangdai.lotusyogaapp.infrastructure.AppDatabase;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Course;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.List;

public class EditCourseActivity extends androidx.appcompat.app.AppCompatActivity {
    private AppDatabase db;
    private Course course;
    private List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "yoga_app_database").allowMainThreadQueries().build();

        int courseId = getIntent().getIntExtra("courseId", -1);
        course = db.courseDao().getCourseById(courseId);

        // Populate UI with course data
        EditText edtName = findViewById(R.id.edtName);
        EditText edtDescription = findViewById(R.id.edtDescription);
        EditText edtPrice = findViewById(R.id.edtPrice);
        Spinner spinnerTeacher = findViewById(R.id.spinnerTeacher);
        TextView txtTitle = findViewById(R.id.TextViewTitle);

        txtTitle.setText("Edit Course");
        edtName.setText(course.Name);
        edtDescription.setText(course.Description);
        edtPrice.setText(course.Price);

        teacherList = db.teacherDao().getAllTeachers();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getTeacherNames(teacherList));
        spinnerTeacher.setAdapter(adapter);

        // Set spinner to current teacher
        for (int i = 0; i < teacherList.size(); i++) {
            if (teacherList.get(i).Id == course.TeacherId) {
                spinnerTeacher.setSelection(i);
                break;
            }
        }

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCourseActivity.this, ViewCourseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v -> {
            course.Name = edtName.getText().toString();
            course.Description = edtDescription.getText().toString();
            course.Price = edtPrice.getText().toString();
            int teacherPosition = spinnerTeacher.getSelectedItemPosition();
            course.TeacherId = teacherList.get(teacherPosition).Id;

            db.courseDao().updateCourse(course);
            finish();
        });
    }

    private String[] getTeacherNames(List<Teacher> teachers) {
        String[] names = new String[teachers.size()];
        for (int i = 0; i < teachers.size(); i++) {
            names[i] = teachers.get(i).Name;
        }
        return names;
    }
}