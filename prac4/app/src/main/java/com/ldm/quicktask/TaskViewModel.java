package com.ldm.quicktask;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ldm.quicktask.database.AppDatabase;
import com.ldm.quicktask.database.TaskDao;
import com.ldm.quicktask.database.TaskEntity;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskDao taskDao;
    private LiveData<List<TaskEntity>> allTasks;

    public TaskViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();  // Get all tasks from the database
    }

    public LiveData<List<TaskEntity>> getAllTasks() {
        return allTasks;
    }

    // Method to insert a new task
    public void insert(TaskEntity taskEntity) {
        new Thread(() -> taskDao.insert(taskEntity)).start();
    }

    // Method to update a task
    public void update(TaskEntity taskEntity) {
        new Thread(() -> taskDao.update(taskEntity)).start();
    }

    // Method to delete a task
    public void delete(TaskEntity taskEntity) {
        new Thread(() -> taskDao.delete(taskEntity)).start();
    }
}
