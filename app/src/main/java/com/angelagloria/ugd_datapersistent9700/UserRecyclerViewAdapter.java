package com.angelagloria.ugd_datapersistent9700;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;
    private List<User> userListFiltered;

    public UserRecyclerViewAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textView1.setText(user.getNumber());
        holder.textView.setText(user.getFullName());
        holder.textView2.setText(user.getAge() + " years old");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView, textView1, textView2;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.number_text);
            textView = itemView.findViewById(R.id.full_name_text);
            textView2 = itemView.findViewById(R.id.age_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            User user = userList.get(getAdapterPosition());
            Bundle data = new Bundle();
            data.putSerializable("user", user);
            UpdateFragment updateFragment = new UpdateFragment();
            updateFragment.setArguments(data);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, updateFragment)
                    .commit();
        }
    }

    public void setUser(List<User> user){
        userList = user;
        userListFiltered = user;
        notifyDataSetChanged();
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = userListFiltered.size();
                    filterResults.values = userListFiltered;
                }else{
                    String searchChr = charSequence.toString().toUpperCase();

                    List<User> resultData = new ArrayList<>();

                    for (User user : userListFiltered){
                        if(user.getFullName().toUpperCase().contains(searchChr)){
                            resultData.add(user);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userList.addAll((List<User>) filterResults.values);
            }
        };
    }
}




