package com.ldm.quicktask.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ldm.quicktask.MainActivity;
import com.ldm.quicktask.R;
import com.ldm.quicktask.database.TaskEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private final List<TaskEntity> tasks;
    private final LayoutInflater mInflater;
    private final MainActivity mainActivity;
    private final ListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TaskEntity task);
    }

    public ListAdapter(List<TaskEntity> tasks, Context context, MainActivity mainActivity, ListAdapter.OnItemClickListener listener) {
        this.tasks = tasks;
        mInflater = LayoutInflater.from(context);
        this.mainActivity = mainActivity;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = this.mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        holder.bindData(tasks.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox check;
        TextView title, description, day, date;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);

            check = itemView.findViewById(R.id.taskCheckBox);
            title = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            day = itemView.findViewById(R.id.dayTextView);
            date = itemView.findViewById(R.id.dateTextView);
            cv = itemView.findViewById(R.id.cv);
        }

        public void bindData(final TaskEntity task) {
            check.setChecked(task.getCheck());

            String titleText = cutText(task.getTitle());
            title.setText(titleText);

            String descriptionText = cutText(task.getDescription());
            description.setText(descriptionText);

            Date date = task.getDate();
            Locale locale = new Locale("EN");
            String dayText = new SimpleDateFormat("EEEE", locale).format(date);
            day.setText(capitalize(dayText));

            String dateText = new SimpleDateFormat("dd/MM/yy", Locale.US).format(date);
            this.date.setText(dateText);

            itemView.setOnClickListener(view -> listener.onItemClick(task));
            check.setOnCheckedChangeListener((compoundButton, b) -> {
                boolean alternateCheck = !task.getCheck();
                task.setCheck(alternateCheck);

                mainActivity.updateTask(task);

                check.setChecked(alternateCheck);
            });
        }
    }

    private String cutText(String text) {
        if (text.length() > 25) return text.substring(0, 25).trim() + "...";
        return text;
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

}
