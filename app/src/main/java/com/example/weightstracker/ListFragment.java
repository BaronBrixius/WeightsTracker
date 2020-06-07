package com.example.weightstracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

public class ListFragment extends Fragment implements ExerciseListAdapter.ItemClickListener {
    ExerciseListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MainActivity activity = (MainActivity) getActivity();
        if (activity == null) {
            return;
        }

        //setup recyclerview
        RecyclerView exerciseView = view.findViewById(R.id.exerciseList);
        exerciseView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new ExerciseListAdapter(activity, activity.exerciseList, this);
        exerciseView.setAdapter(adapter);

        //drag and drop reordering for recyclerview, and swipe to open
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Collections.swap(activity.exerciseList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                openExpandedFragment(adapter.getItem(viewHolder.getAdapterPosition()));
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {  //lowers opacity while dragging
                super.onSelectedChanged(viewHolder, actionState);

                if (viewHolder != null && actionState == ItemTouchHelper.ACTION_STATE_DRAG)
                    viewHolder.itemView.setAlpha(0.5f);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {    //restores opacity
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1.0f);
            }
        });
        touchHelper.attachToRecyclerView(exerciseView);

        //remove menu buttons from bottom bar
        BottomAppBar bottomAppBar = activity.findViewById(R.id.bottomAppBar);
        bottomAppBar.getMenu().clear();

        //setup New Exercise button
        FloatingActionButton fab = activity.findViewById(R.id.fab);
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
