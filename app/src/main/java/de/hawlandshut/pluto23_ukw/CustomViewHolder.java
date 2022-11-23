package de.hawlandshut.pluto23_ukw;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public final TextView mLine1;
    public final TextView mLine2;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        Log.d("xx CustomViewHolder","called Constructor");
        mLine1 = itemView.findViewById( R.id.post_view_line1 );
        mLine2 = itemView.findViewById( R.id.post_view_line2 );
    }
}
