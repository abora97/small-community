package com.example.abora.firebasesenior.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abora.firebasesenior.Model.Status;
import com.example.abora.firebasesenior.R;
import com.example.abora.firebasesenior.api.FirebaseHelper;
import com.example.abora.firebasesenior.api.Networking;
import com.example.abora.firebasesenior.callback.OnFireBaseOperationListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActitity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.description_et)
    EditText descriptionEt;
    @BindView(R.id.add_but)
    Button addBut;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        addBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_but:
                addStatus();
                break;
        }
    }

    private void addStatus() {
        progressBar.setVisibility(View.VISIBLE);
        String name = FirebaseHelper.getAuth().getCurrentUser().getEmail();
        String post = descriptionEt.getText().toString();
        Status status = new Status()
                .setName(name)
                .setStatus(post);
        Networking.addStatus(status, new OnFireBaseOperationListener() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailed(String errorMassege) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailActitity.this, errorMassege, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
