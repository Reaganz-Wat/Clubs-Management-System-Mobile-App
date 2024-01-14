package com.example.cms;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Practice> practiceArrayList;

    // Constructor
    public PracticeAdapter(Context context, ArrayList<Practice> practiceArrayList) {
        this.context = context;
        this.practiceArrayList = practiceArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Practice practice = practiceArrayList.get(position);
        holder.student_username.setText(practice.getStudent_name());
        holder.student_email.setText(practice.getStudent_email());
        holder.imageView.setImageResource(practice.getStudent_image());
    }

    @Override
    public int getItemCount() {
        return practiceArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView student_username;
        private final TextView student_email;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagep);
            student_username = itemView.findViewById(R.id.us_name);
            student_email = itemView.findViewById(R.id.emailp);
        }
    }
}