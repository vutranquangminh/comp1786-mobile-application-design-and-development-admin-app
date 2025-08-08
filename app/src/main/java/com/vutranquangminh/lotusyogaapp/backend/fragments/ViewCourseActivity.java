package com.vutranquangminh.lotusyogaapp.backend.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vutranquangminh.lotusyogaapp.R;
import com.vutranquangminh.lotusyogaapp.backend.adapters.CourseAdapter;
import com.vutranquangminh.lotusyogaapp.backend.courses.CreateCourseActivity;
import com.vutranquangminh.lotusyogaapp.backend.courses.EditCourseActivity;
import com.vutranquangminh.lotusyogaapp.infrastructure.AppDatabase;
import com.vutranquangminh.lotusyogaapp.infrastructure.firebase.FirebaseSyncService;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Course;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.ArrayList;
import java.util.List;

public class ViewCourseActivity extends AppCompatActivity {
    Button btnCreateCourse;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private AppDatabase db;
    public List<Course> courseList;
    public List<Teacher> teacherList;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_fragment);
        btnCreateCourse = findViewById(R.id.btnCreateCourse);
        recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new CourseAdapter(new ArrayList<>(), new ArrayList<>(), ...); // Initialize with empty lists

        adapter = new CourseAdapter(new ArrayList<>(), new ArrayList<>(), new CourseAdapter.OnCourseActionListener() {
            @Override
            public void onEdit(Course course) { }
            @Override
            public void onDelete(Course course) { }
        }); // Initialize with empty lists

        recyclerView.setAdapter(adapter);

        db = AppDatabase.getInstance(getApplicationContext());
        syncDataToFirebase();
        loadData();
        adapter = new CourseAdapter(courseList, teacherList , new CourseAdapter.OnCourseActionListener() {
            @Override
            public void onEdit(Course course) {
                // Example: Start EditTeacherActivity (you need to implement this activity)
                Intent intent = new Intent(ViewCourseActivity.this, EditCourseActivity.class);
                intent.putExtra("courseId", course.Id);
                startActivity(intent);
            }

            @Override
            public void onDelete(Course course) {
                // Set up a confirmation dialog before deleting
                new android.app.AlertDialog.Builder(ViewCourseActivity.this)
                        .setTitle("Delete Course")
                        .setMessage("Are you sure you want to delete this course?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            new Thread(() -> {
                                db.courseDao().deleteCourse(course);
                                runOnUiThread(() -> {
                                    Toast.makeText(ViewCourseActivity.this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
                                    loadData(); // Refresh the course list
                                });
                            }).start();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }


        });
        recyclerView.setAdapter(adapter);

        btnCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCourseActivity.this, CreateCourseActivity.class);
                startActivity(intent);
            }
        });
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseSyncService syncService = new FirebaseSyncService(
                db.transactionDao(),
                db.courseDao(),
                db.customerDao(),
                db.teacherDao()
        );
        java.util.concurrent.Executors.newSingleThreadExecutor().execute(syncService::syncCustomersToCloud);
    }
//    private void loadCourses() {
//        new Thread(() -> {
//            List<Course> courses = db.courseDao().getAllCourses();
//            List<Teacher> teachers = db.teacherDao().getAllTeachers();
//            runOnUiThread(() -> {
//                adapter.setCourseList(courses);
//            });
//        }).start();
//    }
    private void loadData() {
        new Thread(() -> {
            List<Course> courses = db.courseDao().getAllCourses();
            List<Teacher> teachers = db.teacherDao().getAllTeachers();
            runOnUiThread(() -> {
                courseList = courses;
                teacherList = teachers;
//                adapter.setCourseList(courseList);
                adapter.setCourseList(courseList);
                adapter.setTeacherList(teacherList);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    // Function used to sync data from local database to Firebase
    private void syncDataToFirebase() {
        FirebaseSyncService syncService = new FirebaseSyncService(
                db.transactionDao(),
                db.courseDao(),
                db.customerDao(),
                db.teacherDao()
        );
        syncService.syncLocalToCloud();
    }
}
