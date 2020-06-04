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
    Exercise exercise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expanded, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exercise = ExpandedFragmentArgs.fromBundle(getArguments()).getExercise();
        initializeViews(view);

        FloatingActionButton fab = getActivity().findViewById(R.id.newExerciseButton);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save, getContext().getTheme()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).saveExercise(exercise);
            }
        });
    }

    private void initializeViews(View view) {
        ((TextInputEditText) view.findViewById(R.id.nameField)).setText(exercise.getName());
        ((EditText) view.findViewById(R.id.weightField)).setText(String.valueOf(exercise.getWeight()));
        ((EditText) view.findViewById(R.id.incrementField)).setText(String.valueOf(exercise.getIncrement()));
    }
}
