package com.example.weightstracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.Exercise;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Exercise> exerciseList;
    File savedDataFile;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        savedDataFile = new File(getFilesDir(), "ExerciseList");

        try (FileInputStream inFile = new FileInputStream(savedDataFile);
             ObjectInputStream inList = new ObjectInputStream(inFile)) {
            exerciseList = (List<Exercise>) inList.readObject();

            if (exerciseList == null)           //readObject returns null if file is empty, make a new List in that case
                exerciseList = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            exerciseList = new ArrayList<>();
            Toast.makeText(this, "Error: Could not load data.", Toast.LENGTH_LONG).show();
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

    public void saveExercise(Exercise exercise) {
        exerciseList.add(exercise);     //TODO make this only update if exercise already exists
    }

    @Override
    public void onPause() {         //saves data to file on minimize/close
        super.onPause();
        try (FileOutputStream outFile = new FileOutputStream(savedDataFile);
             ObjectOutputStream outList = new ObjectOutputStream(outFile)) {
            outList.writeObject(exerciseList);
        } catch (IOException e) {
            Toast.makeText(this, "Error: Could not save.", Toast.LENGTH_LONG).show();
        }
    }
}
