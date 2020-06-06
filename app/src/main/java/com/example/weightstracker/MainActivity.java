package com.example.weightstracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        try (FileInputStream inFile = new FileInputStream(savedDataFile);
             ObjectInputStream inList = new ObjectInputStream(inFile)) {
            exerciseList = (List<Exercise>) inList.readObject();

            if (exerciseList == null)           //readObject returns null if file is empty, make a new List in that case
                exerciseList = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            exerciseList = new ArrayList<>();
            e.printStackTrace();Toast.makeText(this, "Error: Could not load data.", Toast.LENGTH_LONG).show();
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

    public void saveExercise(Exercise savedExercise) {
        if (savedExercise.getID() == 0) {                   //if new exercise, give it an ID and save it
            savedExercise.setID(exerciseList.size());
            exerciseList.add(savedExercise);
            return;
        }

        for (int i = 0; i < exerciseList.size(); i++) {     //otherwise, find matching exercise and update it
            if (savedExercise.equals(exerciseList.get(i))) {
                exerciseList.set(i, savedExercise);
                return;
            }
        }
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
