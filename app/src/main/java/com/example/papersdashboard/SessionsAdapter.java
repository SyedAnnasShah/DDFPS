package com.example.papersdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.MyViewHolder>   {
    private List<Papers> items;
    private Context context;
    String session;

    public SessionsAdapter(Context context, List<Papers> item) {
        this.context = context;
        this.items=item;
    }
    @NonNull
    @Override
    public SessionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sessions_layout, parent, false);
        return new SessionsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.session.setText(items.get(position).getSemester());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView session;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            session =itemView.findViewById(R.id.tv_session);
        }
    }

}
