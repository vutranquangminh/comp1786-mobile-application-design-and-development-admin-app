package com.vutranquangminh.lotusyogaapp.backend.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vutranquangminh.lotusyogaapp.R;
import com.vutranquangminh.lotusyogaapp.infrastructure.models.entities.Teacher;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    public interface OnTeacherActionListener {
        void onEdit(Teacher teacher);
        void onDelete(Teacher teacher);
    }

    private List<Teacher> teacherList;
    private final OnTeacherActionListener listener;

    public TeacherAdapter(List<Teacher> teacherList, OnTeacherActionListener listener) {
        this.teacherList = teacherList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        holder.txtTeacherName.setText(teacher.Name);
        holder.txtDateStartTeaching.setText(teacher.DateStartedTeaching);
        holder.txtExperience.setText(teacher.Experience);
        holder.buttonEdit.setOnClickListener(v -> listener.onEdit(teacher));
        holder.buttonDelete.setOnClickListener(v -> listener.onDelete(teacher));
    }

    @Override
    public int getItemCount() {
        return teacherList != null ? teacherList.size() : 0;
    }

    public void setTeacherList(List<Teacher> teachers) {
        this.teacherList = teachers;
        notifyDataSetChanged();
    }

    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView txtTeacherName, txtDateStartTeaching, txtExperience;
        Button buttonEdit, buttonDelete;

        TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTeacherName = itemView.findViewById(R.id.txtTeacherName);
            txtDateStartTeaching = itemView.findViewById(R.id.txtDateStartTeaching);
            txtExperience = itemView.findViewById(R.id.txtExperience);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
