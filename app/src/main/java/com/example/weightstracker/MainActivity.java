package com.example.weightstracker;

import android.os.Bundle;
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

        //toolbar ended up barely used, but it frames the app nicely and having the name up there is neat
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //read saved Exercises to a List
        File savedDataFile = new File(getFilesDir(), "ExerciseList");
        try (FileInputStream inFile = new FileInputStream(savedDataFile);
             ObjectInputStream inList = new ObjectInputStream(inFile)) {
            exerciseList = (List<Exercise>) inList.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Toast.makeText(this, "Error: Could not load data.", Toast.LENGTH_LONG).show();
        } finally {
            if (exerciseList == null)           //note that readObject returns null if file is empty
                exerciseList = new ArrayList<>();
        }
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
