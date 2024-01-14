package com.example.cms;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class adminCreateSportsTeamAdapter extends RecyclerView.Adapter <adminCreateSportsTeamAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<adminCreateSportsTeamModal> arrayList;

    public adminCreateSportsTeamAdapter (Context context, ArrayList<adminCreateSportsTeamModal> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_create_sports_team_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminCreateSportsTeamAdapter.ViewHolder holder, int position) {
        adminCreateSportsTeamModal modal = arrayList.get(position);
        holder.team_name.setText(modal.getTeam_name());
        holder.coach.setText(modal.getCoach());
    }

    @Override
    public int getItemCount() {
        return  arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView team_name;
        private final TextView coach;

        public ViewHolder(View itemView){
            super(itemView);
            team_name = itemView.findViewById(R.id.sports_team_name);
            coach = itemView.findViewById(R.id.sports_team_coach);
        }

    }
}
