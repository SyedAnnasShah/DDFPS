package com.example.papersdashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AddQuestionAdapter extends RecyclerView.Adapter<AddQuestionAdapter.MyViewHolder>   {
    private final Context context;
    private List<Questions> items;
    public AddQuestionAdapter(Context context, List<Questions> item) {
        this.context = context;
        this.items=item;
    }
    @NonNull
    @Override
    public AddQuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionadded_layout, parent, false);
        return new AddQuestionAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AddQuestionAdapter.MyViewHolder holder, int position) {

        holder.data.setText(items.get(position).questionno+items.get(position).getQuestiondata());
        String diff= " ( "+items.get(position).difficulty+" )";
        holder.difficulty.setText(diff);
        holder.marks.setText(String.valueOf(items.get(position).marks));
        if(items.get(position).getImage()!=null){

            byte[] decodedString2 = Base64.decode(items.get(position).image, Base64.DEFAULT);
            Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            holder.imageIV.setImageBitmap(decodedByte2);
        }

    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView difficulty, marks , data;
        ImageView imageIV ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data =itemView.findViewById(R.id.tv_questionaddeddetail);
            difficulty =itemView.findViewById(R.id.tv_questionaddeddificulty);
            marks =itemView.findViewById(R.id.tv_questionaddedmarks);
            imageIV =itemView.findViewById(R.id.iv_questionaddedimage);
        }
    }
}
