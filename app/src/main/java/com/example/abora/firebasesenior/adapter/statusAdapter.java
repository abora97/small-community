package com.example.abora.firebasesenior.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abora.firebasesenior.Model.Status;
import com.example.abora.firebasesenior.R;
import com.example.abora.firebasesenior.callback.OnItemLongClickListner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class statusAdapter extends RecyclerView.Adapter<statusAdapter.statusHolder> {

    private final OnItemLongClickListner listner;
    private Context mContext;
    private List<Status> list;

    public statusAdapter(Context mContext, List<Status> list, OnItemLongClickListner listner){
        this.mContext = mContext;
        this.list = list;
        this.listner=listner;
    }

    @NonNull
    @Override
    public statusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myRow = LayoutInflater.from(mContext).inflate(R.layout.row_status, parent, false);
        return new statusHolder(myRow);
    }

    @Override
    public void onBindViewHolder(@NonNull statusHolder holder, int position) {
        final Status status = list.get(position);
        holder.rowName.setText(status.getName());
        holder.rowPost.setText(status.getStatus());
        holder.root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               listner.onLongClisck(status);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class statusHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_name)
        TextView rowName;
        @BindView(R.id.row_post)
        TextView rowPost;
        @BindView(R.id.root)
        LinearLayout root;

        public statusHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void update(List<Status> lists) {
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }


}
