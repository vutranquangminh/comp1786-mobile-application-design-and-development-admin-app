package com.tranquangdai.lotusyogaapp.backend.courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tranquangdai.lotusyogaapp.R;
import com.tranquangdai.lotusyogaapp.backend.fragments.ViewCourseActivity;
import com.tranquangdai.lotusyogaapp.infrastructure.AppDatabase;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Course;
import com.tranquangdai.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.List;

public class CreateCourseActivity extends androidx.appcompat.app.AppCompatActivity {
    private Spinner spinnerTeacher, spinnerCategory, spinnerDuration, spinnerCapacity;
    private EditText edtName, edtDescription, edtPrice;
    private DatePicker datePickerStartDate;
    private TextView txtTitle;
    private AppDatabase db;
    private List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        txtTitle = findViewById(R.id.TextViewTitle);
        txtTitle.setText("Create new Course");
        spinnerTeacher = findViewById(R.id.spinnerTeacher);
//        db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "yoga_app_database").allowMainThreadQueries().build();

        db = AppDatabase.getInstance(getApplicationContext());

        // In onCreate(), after initializing spinnerTeacher
        new Thread(() -> {
            teacherList = db.teacherDao().getAllTeachers();
            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        getTeacherNames(teacherList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTeacher.setAdapter(adapter);
            });
        }).start();


//        teacherList = db.teacherDao().getAllTeachers();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item,
//                getTeacherNames(teacherList));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerTeacher.setAdapter(adapter);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateCourseActivity.this, ViewCourseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(v -> {
            edtName = findViewById(R.id.edtName);
            edtDescription = findViewById(R.id.edtDescription);
            edtPrice = findViewById(R.id.edtPrice);
            spinnerCategory = findViewById(R.id.spinnerCategory);
            spinnerDuration = findViewById(R.id.spinnerDuration);
            spinnerCapacity = findViewById(R.id.spinnerCapacity);
            spinnerTeacher = findViewById(R.id.spinnerTeacher);
            datePickerStartDate = findViewById(R.id.datePickerStartDate);

            String name = edtName.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            String price = edtPrice.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem() != null ? spinnerCategory.getSelectedItem().toString() : "";
            int durationInput = spinnerDuration.getSelectedItem() != null ? Integer.parseInt(spinnerDuration.getSelectedItem().toString()) : 0;
            int capacityInput = spinnerCapacity.getSelectedItem() != null ? Integer.parseInt(spinnerCapacity.getSelectedItem().toString()) : 0;

            if (name.isEmpty() || description.isEmpty() || price.isEmpty()) {
                // Show error message
                android.widget.Toast.makeText(CreateCourseActivity.this, "Please fill all fields", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }
            int duration = durationInput;
            int capacity = capacityInput;
            int teacherPosition = spinnerTeacher.getSelectedItemPosition();
            int teacherId = teacherList.get(teacherPosition).Id;

            int day = datePickerStartDate.getDayOfMonth();
            int month = datePickerStartDate.getMonth() + 1;
            int year = datePickerStartDate.getYear();
            String dateTime = String.format("%04d-%02d-%02d", year, month, day);

            Course course = new Course(
                    name,
                    description,
                    dateTime,
                    duration,
                    capacity,
                    price,
                    category,
                    teacherId
            );
            new Thread(()->{
                db.courseDao().insertCourse(course);
                runOnUiThread(() ->{
                    Toast.makeText(CreateCourseActivity.this, "Course created successfully!", Toast.LENGTH_SHORT).show();
                });
            }).start();
            Intent intent = new Intent(CreateCourseActivity.this, ViewCourseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
