package de.hawlandshut.pluto23_ukw;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hawlandshut.pluto23_ukw.CustomViewHolder;
import de.hawlandshut.pluto23_ukw.model.Post;

public class CustomAdapter extends  RecyclerView.Adapter<CustomViewHolder>{

    public ArrayList<Post> mPostList;
    int c = 1;
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("xx -------------","-----------------------");
        Log.d("xx CustomAdapter","called onCreateViewHolder ----------->" + (c++));
        View view;
        view = LayoutInflater.from( parent.getContext())
                .inflate(R.layout.post_view, parent, false);
        return new CustomViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Log.d("xx CustomAdapter","called onBindViewHolder pos = "+ holder.getLayoutPosition());

        Post post = mPostList.get( position );
        holder.mLine1.setText( post.email +", " + post.createdAt );
        holder.mLine2.setText( post.body );
    }

    @Override
    public int getItemCount() {
       // Log.d("xx CustomAdapter","called getItemCount >" + mPostList.size());
        return mPostList.size();
    }
}
