package com.example.weightstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {
    private final List<Exercise> data;
    private final LayoutInflater inflater;
    private final ItemClickListener clickListener;

    ExerciseListAdapter(Context context, List<Exercise> data, ItemClickListener itemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.exercise_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListAdapter.ViewHolder holder, final int position) {
        final Exercise thisExercise = data.get(position);
        holder.exerciseName.setText(thisExercise.getName());
        holder.weightSpinner.setText(String.valueOf(thisExercise.getWeight()));
        holder.incrementButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Hold to increment weight.", Toast.LENGTH_SHORT).show();
            }
        });
        holder.incrementButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                thisExercise.increment();
                notifyItemChanged(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView exerciseName;
        final TextView weightSpinner;
        final ImageButton incrementButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            weightSpinner = itemView.findViewById(R.id.weightSpinner);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }

    Exercise getItem(int id) {
        return data.get(id);
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
