package com.example.weightstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class ExpandedFragment extends Fragment {
    private Exercise exercise;
    private TextInputEditText nameInput;
    private EditText weightInput;
    private EditText incrementInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expanded, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        exercise = ExpandedFragmentArgs.fromBundle(getArguments()).getExercise();
        nameInput = view.findViewById(R.id.nameField);
        weightInput = view.findViewById(R.id.weightField);
        incrementInput = view.findViewById(R.id.incrementField);

        populateDisplay();

        final MainActivity activity = (MainActivity) getActivity();
        if (activity == null)
            return;
        FloatingActionButton fab = activity.findViewById(R.id.newExerciseButton);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save, activity.getTheme()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise.setName(nameInput.getText());
                exercise.setWeight(weightInput.getText());
                exercise.setIncrement(incrementInput.getText());
                activity.saveExercise(exercise);
            }
        });
    }

    private void populateDisplay() {
        nameInput.setText(exercise.getName());
        weightInput.setText(String.valueOf(exercise.getWeight()));
        incrementInput.setText(String.valueOf(exercise.getIncrement()));
    }
}
