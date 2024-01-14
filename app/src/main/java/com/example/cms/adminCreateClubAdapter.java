package com.example.cms;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class adminCreateClubAdapter extends RecyclerView.Adapter <adminCreateClubAdapter.ViewHolder> {

    // variables here
    private final Context context;
    private final ArrayList <adminCreateClubModal> adminCreateClubArrayList;

    public adminCreateClubAdapter(Context context, ArrayList<adminCreateClubModal> adminCreateClubArrayList){
        this.context = context;
        this.adminCreateClubArrayList = adminCreateClubArrayList;
    }

    @NonNull
    @Override
    public adminCreateClubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_create_club_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminCreateClubAdapter.ViewHolder holder, int position) {
        adminCreateClubModal model = adminCreateClubArrayList.get(position);
        holder.club_name.setText(model.getClubName());
        holder.club_description.setText(model.getClubDescription());
        holder.club_created_by.setText(model.getClubCreatedBy());
    }

    @Override
    public int getItemCount() {
        return adminCreateClubArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView club_name;
        private final TextView club_description;
        private final TextView club_created_by;

        public ViewHolder(View itemView){
            super(itemView);
            club_name = itemView.findViewById(R.id.club_name);
            club_description = itemView.findViewById(R.id.club_description);
            club_created_by = itemView.findViewById(R.id.club_created_by);
        }

    }
}
