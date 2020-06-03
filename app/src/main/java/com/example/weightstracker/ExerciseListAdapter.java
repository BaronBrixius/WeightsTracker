package com.example.weightstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Exercise;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {

    private List<Exercise> data;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    ExerciseListAdapter(Context context, List<Exercise> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.exercise_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListAdapter.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.exerciseName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    Exercise getItem(int id) {
        return data.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
