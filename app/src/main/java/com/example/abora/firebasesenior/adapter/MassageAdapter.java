package com.example.abora.firebasesenior.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abora.firebasesenior.Model.Massage;
import com.example.abora.firebasesenior.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MassageAdapter extends RecyclerView.Adapter<MassageAdapter.massageHolder> {
    private Context mContext;
    private List<Massage> list;

    public MassageAdapter(Context mContext, List<Massage> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public massageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myRow = LayoutInflater.from(mContext).inflate(R.layout.row_chat, parent, false);
        return new massageHolder(myRow);
    }

    @Override
    public void onBindViewHolder(@NonNull massageHolder holder, int position) {
        Massage massage=list.get(position);
        holder.nameTv.setText(massage.getName());
        holder.massageTv.setText(massage.getMassage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<Massage> list) {
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }

    public class massageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.massage_tv)
        TextView massageTv;
        @BindView(R.id.name_tv)
        TextView nameTv;

        public massageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
