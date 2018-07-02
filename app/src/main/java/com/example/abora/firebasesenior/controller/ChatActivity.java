package com.example.abora.firebasesenior.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abora.firebasesenior.Model.Massage;
import com.example.abora.firebasesenior.R;
import com.example.abora.firebasesenior.adapter.MassageAdapter;
import com.example.abora.firebasesenior.api.FirebaseHelper;
import com.example.abora.firebasesenior.api.Networking;
import com.example.abora.firebasesenior.callback.OnFirebaseMassageListener;
import com.example.abora.firebasesenior.callback.OnMassageListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.my_recycler)
    RecyclerView myRecycler;
    @BindView(R.id.massage_tv)
    EditText massageTv;
    @BindView(R.id.send_fab_bu)
    FloatingActionButton sendFabBu;

    private RecyclerView.LayoutManager layoutManager;
    private MassageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        sendFabBu.setOnClickListener(this);
        init();

    }

    private void init() {
        layoutManager = new LinearLayoutManager(this);
        //myRecycler.setLayoutManager(layoutManager);
        //adapter = new MassageAdapter(ChatActivity.this, new ArrayList<Massage>());
        //myRecycler.setAdapter(adapter);
        getAllMassage();

    }

    private void getAllMassage() {

        Networking.getMassage(new OnFirebaseMassageListener() {
            @Override
            public void onSucess(List<Massage> list) {
                myRecycler.setLayoutManager(layoutManager);

                adapter = new MassageAdapter(ChatActivity.this, list);
                myRecycler.setAdapter(adapter);
            }

            @Override
            public void onErro(String errorMag) {
                Toast.makeText(ChatActivity.this, errorMag, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_fab_bu:
                String name = FirebaseHelper.getAuth().getCurrentUser().getEmail();
                String massageSend = massageTv.getText().toString();
                Massage massage = new Massage()
                        .setName(name)
                        .setMassage(massageSend);
                Networking.sendMassage(massage, new OnMassageListener() {
                    @Override
                    public void onSucess() {
                        massageTv.setText("");
                        init();
                    }

                    @Override
                    public void onErro(String errorMag) {
                        Toast.makeText(ChatActivity.this, errorMag, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
