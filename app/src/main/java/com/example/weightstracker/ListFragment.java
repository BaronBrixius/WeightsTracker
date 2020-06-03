package com.example.weightstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListFragment extends Fragment implements ExerciseListAdapter.ItemClickListener {
    ExerciseListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView exerciseView = view.findViewById(R.id.exerciseList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        exerciseView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(exerciseView.getContext(), layoutManager.getOrientation());
        exerciseView.addItemDecoration(divider);

        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Lift", 50, 5));      //TODO replace with actual data
        exercises.add(new Exercise("Push", 70, 10));
        exercises.add(new Exercise("Pull", 60, 5));
        exercises.add(new Exercise("Curl", 12, 2));

        adapter = new ExerciseListAdapter(getActivity(), exercises);
        adapter.setClickListener(this);
        exerciseView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.newExerciseButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExpandedFragment(new Exercise());
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        openExpandedFragment(adapter.getItem(position));
    }

    private void openExpandedFragment(Exercise exercise) {
        ListFragmentDirections.ActionListFragmentToExpandedFragment action =
                ListFragmentDirections.
                        actionListFragmentToExpandedFragment(exercise);
        NavHostFragment.findNavController(ListFragment.this).navigate(action);
    }
}
