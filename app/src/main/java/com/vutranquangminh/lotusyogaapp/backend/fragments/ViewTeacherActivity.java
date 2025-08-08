package com.vutranquangminh.lotusyogaapp.backend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.vutranquangminh.lotusyogaapp.R;
import com.vutranquangminh.lotusyogaapp.backend.adapters.TeacherAdapter;
import com.vutranquangminh.lotusyogaapp.backend.teachers.CreateTeacherActivity;
import com.vutranquangminh.lotusyogaapp.backend.teachers.EditTeacherActivity;
import com.vutranquangminh.lotusyogaapp.infrastructure.AppDatabase;
import com.vutranquangminh.lotusyogaapp.infrastructure.firebase.FirebaseSyncService;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.List;

public class ViewTeacherActivity extends AppCompatActivity {
    private Button btnCreateTeacher;
    private RecyclerView recyclerView;
    private TeacherAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.teacher_fragment);

        btnCreateTeacher = findViewById(R.id.btnCreateTeacher);
        recyclerView = findViewById(R.id.recyclerViewTeachers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "yoga_app_database").allowMainThreadQueries().build();

        adapter = new TeacherAdapter(null, new TeacherAdapter.OnTeacherActionListener() {
            @Override
            public void onEdit(Teacher teacher) {
                // Example: Start EditTeacherActivity (you need to implement this activity)
                Intent intent = new Intent(ViewTeacherActivity.this, EditTeacherActivity.class);
                intent.putExtra("teacherId", teacher.Id);
                startActivity(intent);
            }

            @Override
            public void onDelete(Teacher teacher) {
                // Set up a confirmation dialog before deleting
                new android.app.AlertDialog.Builder(ViewTeacherActivity.this)
                        .setTitle("Delete Teacher")
                        .setMessage("Are you sure you want to delete this teacher?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            new Thread(() -> {
                                db.teacherDao().deleteTeacher(teacher);
                                runOnUiThread(() -> {
                                    Toast.makeText(ViewTeacherActivity.this, "Teacher deleted successfully", Toast.LENGTH_SHORT).show();
                                    loadTeachers(); // Reload the list after deletion
                                });
                            }).start();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

//            private void loadTeachers() {
//                List<Teacher> teachers = db.teacherDao().getAllTeachers();
//                adapter.setTeacherList(teachers);
//            }
        });
        recyclerView.setAdapter(adapter);

        btnCreateTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTeacherActivity.this, CreateTeacherActivity.class);
                startActivity(intent);
            }
        });

        loadTeachers();
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
        syncService.syncLocalToCloud();
    }

    private void loadTeachers() {
        new Thread(() -> {
            List<Teacher> teachers = db.teacherDao().getAllTeachers();
            runOnUiThread(() -> adapter.setTeacherList(teachers));
        }).start();
    }
}
