package com.example.weightstracker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class ExpandedFragment extends Fragment {
    private MainActivity activity;
    private Exercise exercise;
    private TextInputEditText nameField;
    private EditText weightField;
    private EditText incrementField;
    private TextInputEditText noteField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expanded, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity)
            activity = (MainActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get current exercise
        if (getArguments() == null)
            return;
        exercise = ExpandedFragmentArgs.fromBundle(getArguments()).getExercise();

        //identify input fields
        nameField = view.findViewById(R.id.nameField);
        weightField = view.findViewById(R.id.weightField);
        incrementField = view.findViewById(R.id.incrementField);
        noteField = view.findViewById(R.id.noteField);
        populateDisplay();

        BottomAppBar bottomAppBar = activity.findViewById(R.id.bottomAppBar);
        bottomAppBar.replaceMenu(R.menu.bottomappbar_expanded_menu);

        //make delete button
        View deleteExerciseButton = activity.findViewById(R.id.deleteExerciseButton);
        deleteExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Hold to delete exercise.", Toast.LENGTH_SHORT).show();
            }
        });
        deleteExerciseButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (activity.deleteExercise(exercise))
                    Toast.makeText(activity, exercise.getName() + " deleted", Toast.LENGTH_SHORT).show();
                activity.onBackPressed();
                return true;
            }
        });

        //make save button
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save, activity.getTheme()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!updateItem())
                    return;
                activity.saveExercise(exercise);
                Toast.makeText(activity, exercise.getName() + " saved.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateDisplay() {        //fills in fields with Exercise's values
        if (exercise.getID() == -1)         //new Exercise, don't populate anything
            return;
        nameField.setText(exercise.getName());
        weightField.setText(String.valueOf(exercise.getWeight()));
        incrementField.setText(String.valueOf(exercise.getIncrement()));
        noteField.setText(exercise.getNote());
    }

    private boolean updateItem() {              //validates data and sets Exercise's values, returns true if successful
        if (nameField.getText() == null || noteField.getText() == null)
            return false;
        String name = nameField.getText().toString().trim();
        String weight = weightField.getText().toString().trim();
        String increment = incrementField.getText().toString().trim();
        String note = noteField.getText().toString().trim();

        if (name.isEmpty() || weight.isEmpty() || increment.isEmpty()) {
            Toast.makeText(activity, "Failed to Save: name, weight, and increment must be filled in.", Toast.LENGTH_LONG).show();
            return false;
        }

        exercise.setName(name);
        exercise.setWeight(Double.parseDouble(weight));
        exercise.setIncrement(Double.parseDouble(increment));
        exercise.setNotes(note);
        return true;
    }
}
