package com.vutranquangminh.lotusyogaapp.backend.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vutranquangminh.lotusyogaapp.R;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Course;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public interface OnCourseActionListener {
        void onEdit(Course course);
        void onDelete(Course course);
    }

    private List<Course> courseList;
    private List<Teacher> teacherList;
    private final OnCourseActionListener listener;

    public CourseAdapter(List<Course> courseList,List<Teacher> teacherList, OnCourseActionListener listener) {
        this.courseList = courseList;
        this.listener = listener;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.txtCourseName.setText(course.Name);
        holder.txtTimeStart.setText(course.DateTime);
        holder.txtCandidates.setText(String.valueOf(course.Capacity));  // Convert capacity from int to string. If not, will show ResourcesNotFoundException
        holder.txtTeacherName.setText(getTeacherNameById(course.TeacherId));


        holder.buttonEdit.setOnClickListener(v -> listener.onEdit(course));
        holder.buttonDelete.setOnClickListener(v -> listener.onDelete(course));
    }
    private String getTeacherNameById(int teacherId) {
        if (teacherList == null) return "Unknown";
        for (Teacher t : teacherList) {
            if (t.Id == teacherId) return t.Name;
        }
        return "Unknown";
    }
    @Override
    public int getItemCount() {
        return courseList != null ? courseList.size() : 0;
    }

    public void setCourseList(List<Course> courses) {
        this.courseList = courses;
        notifyDataSetChanged();
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView txtCourseName, txtTimeStart, txtTeacherName, txtCandidates;
        Button buttonEdit, buttonDelete;

        CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCourseName = itemView.findViewById(R.id.txtCourseName);
            txtTimeStart = itemView.findViewById(R.id.txtTimeStart);
            txtTeacherName = itemView.findViewById(R.id.txtDisplayTeacherName);
            txtCandidates = itemView.findViewById(R.id.txtDisplayStudentCount);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
