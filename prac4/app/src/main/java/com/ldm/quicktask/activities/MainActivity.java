package com.ldm.quicktask.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.ldm.quicktask.R;
import com.ldm.quicktask.database.AppDatabase;
import com.ldm.quicktask.database.TaskDao;
import com.ldm.quicktask.database.TaskEntity;
import com.ldm.quicktask.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createDatabaseInstance();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void createDatabaseInstance() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "quicktask_database")
                .allowMainThreadQueries()
                .build();

        taskDao = db.taskDao();

        seedDatabase();
    }

    private void seedDatabase() {
        taskDao.insert(new TaskEntity(0, "test1", "test description 1", new Date()));
        taskDao.insert(new TaskEntity(1, "test2", "test description 2", new Date()));
    }

    public List<TaskEntity> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public TaskEntity findTaskById(int id) {
        return taskDao.findTaskById(id);
    }

    public void createTask(TaskEntity task) {
        taskDao.insert(task);
    }

    public void updateTask(TaskEntity task) {
        taskDao.update(task);
    }

    public void deleteTask(TaskEntity task) {
        taskDao.delete(task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
