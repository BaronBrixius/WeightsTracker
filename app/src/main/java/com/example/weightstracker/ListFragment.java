package com.example.weightstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListFragment extends Fragment implements ExerciseListAdapter.ItemClickListener {
    ExerciseListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView exerciseView = view.findViewById(R.id.exerciseList);
        exerciseView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final MainActivity activity = (MainActivity) getActivity();
        if (activity == null)
            return;
        adapter = new ExerciseListAdapter(getActivity(), activity.exerciseList, this);
        exerciseView.setAdapter(adapter);

        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);
        bottomAppBar.getMenu().clear();

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add, activity.getTheme()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExpandedFragment(new Exercise());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        openExpandedFragment(adapter.getItem(position));
    }

    private void openExpandedFragment(Exercise exercise) {      //opens the ExpandedFragment and sends the passed exercise as an argument
        ListFragmentDirections.ActionListFragmentToExpandedFragment action =
                ListFragmentDirections.actionListFragmentToExpandedFragment(exercise);
        NavHostFragment.findNavController(ListFragment.this).navigate(action);
    }
}
