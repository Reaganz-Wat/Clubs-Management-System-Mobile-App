package com.example.cms;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class student_join_clubs_adapter extends RecyclerView.Adapter<student_join_clubs_adapter.ViewHolder> {

    private final Context context;
    private final ArrayList<student_join_clubs_modal> arrayList;

    public student_join_clubs_adapter(Context context, ArrayList<student_join_clubs_modal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public student_join_clubs_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_join_clubs_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull student_join_clubs_adapter.ViewHolder holder, int position) {
        student_join_clubs_modal modal = arrayList.get(position);
        holder.clubName.setText(modal.getClubName());
        holder.clubDescription.setText(modal.getClubDescription());
        holder.clubCreatedBy.setText(modal.getClubCreatedBy());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, select_club_to_join.class);
                intent.putExtra("club_name", modal.getClubName());
                intent.putExtra("description", modal.getClubDescription());
                intent.putExtra("created_by", modal.getClubCreatedBy());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView clubName;
        private final TextView clubDescription;
        private final TextView clubCreatedBy;
        private final CardView cardView;
        public ViewHolder(View view){
            super(view);
            clubName = view.findViewById(R.id.s_club_name);
            clubDescription = view.findViewById(R.id.s_club_description);
            clubCreatedBy = view.findViewById(R.id.s_club_creator);
            cardView = view.findViewById(R.id.student_join_Club);
        }
    }



}
