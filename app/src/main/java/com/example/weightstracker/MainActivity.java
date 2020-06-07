package com.example.weightstracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Exercise> exerciseList;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File savedDataFile = new File(getFilesDir(), "ExerciseList");
        try (FileInputStream inFile = new FileInputStream(savedDataFile);   //reads saved Exercises to a List
             ObjectInputStream inList = new ObjectInputStream(inFile)) {
            exerciseList = (List<Exercise>) inList.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Toast.makeText(this, "Error: Could not load data.", Toast.LENGTH_LONG).show();
        } finally {
            if (exerciseList == null)           //note that readObject returns null if file is empty
                exerciseList = new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);        // Inflate the menu; this adds items to the action bar if it is present.
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

    void saveExercise(Exercise exerciseToSave) {
        if (exerciseToSave.getID() == -1) {                 //if new exercise, give it an ID and save it
            exerciseToSave.setID(exerciseList.size());
            exerciseList.add(exerciseToSave);
            return;
        }

        for (int i = 0; i < exerciseList.size(); i++) {     //otherwise, find matching exercise and update it
            if (exerciseToSave.equals(exerciseList.get(i))) {
                exerciseList.set(i, exerciseToSave);
                return;
            }
        }
    }

    boolean deleteExercise(Exercise exerciseToDelete) {
        if (exerciseToDelete.getID() == -1)                 //no need to iterate through List if exercise hasn't been saved
            return false;
        return exerciseList.remove(exerciseToDelete);
    }

    @Override
    public void onPause() {         //saves data to file on minimize/close
        super.onPause();

        for (int i = 0; i < exerciseList.size(); i++) {     //streamline exercise IDs before saving
            exerciseList.get(i).setID(i);
        }

        File savedDataFile = new File(getFilesDir(), "ExerciseList");
        try (FileOutputStream outFile = new FileOutputStream(savedDataFile);
             ObjectOutputStream outList = new ObjectOutputStream(outFile)) {
            outList.writeObject(exerciseList);
        } catch (IOException e) {
            Toast.makeText(this, "Error: Could not save data.", Toast.LENGTH_LONG).show();
        }
    }
}
