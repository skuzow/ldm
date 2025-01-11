package com.ldm.quicktask.activities;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
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

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ldm.quicktask.activities.SoundManager;

import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private TaskDao taskDao;
    private DarkModeManager darkModeManager;

    private SoundManager soundManager; // Declare SoundManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupStoredDarkMode();

        createDatabaseInstance();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        soundManager = new SoundManager(this); // Initialize the SoundManager
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
        if (task != null) {
            taskDao.update(task);
        } else {
            Log.e("MainActivity", "Attempted to update a null task");
        }
    }

    public void deleteTask(TaskEntity task) {
        taskDao.delete(task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dark_mode) {
            toggleDarkMode();
            return true;
        } else if (id == R.id.action_manual){
            navController.navigate(R.id.navigation_manual);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupStoredDarkMode() {
        darkModeManager = new DarkModeManager(this);

        if (darkModeManager.isDarkModeStored()) {
            boolean isDarkMode = darkModeManager.getDarkMode();

            int mode = isDarkMode ?
                    AppCompatDelegate.MODE_NIGHT_YES :
                    AppCompatDelegate.MODE_NIGHT_NO;

            AppCompatDelegate.setDefaultNightMode(mode);
        }
        else {
            boolean isDarkMode = (getResources().getConfiguration().uiMode &
                    Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;

            darkModeManager.setDarkMode(isDarkMode);
        }
    }

    private void toggleDarkMode() {
        // Switch between light and dark themes
        boolean isDarkMode = darkModeManager.getDarkMode();

        int newMode = isDarkMode ?
                AppCompatDelegate.MODE_NIGHT_NO :
                AppCompatDelegate.MODE_NIGHT_YES;

        AppCompatDelegate.setDefaultNightMode(newMode);

        darkModeManager.setDarkMode(!isDarkMode);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundManager != null) {
            soundManager.release(); // Release SoundManager when the activity is destroyed
        }
    }

    public void playClickSound() {
        if (soundManager != null) {
            soundManager.playClickSound(); // Play sound
        }
    }
}
