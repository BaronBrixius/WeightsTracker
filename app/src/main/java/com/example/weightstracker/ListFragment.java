package com.example.weightstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.newExerciseButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercise newExercise = new Exercise();

                ListFragmentDirections.ActionListFragmentToExpandedFragment action =
                        ListFragmentDirections.
                                actionListFragmentToExpandedFragment(newExercise);
                NavHostFragment.findNavController(ListFragment.this).navigate(action);
            }
        });
    }
}
