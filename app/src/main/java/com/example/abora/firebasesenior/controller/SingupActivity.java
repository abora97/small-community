package com.example.abora.firebasesenior.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.abora.firebasesenior.R;
import com.example.abora.firebasesenior.api.Networking;
import com.example.abora.firebasesenior.callback.OnFireBaseOperationListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingupActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.email_ed)
    EditText emailEd;
    @BindView(R.id.pass_ed)
    EditText passEd;
    @BindView(R.id.buSing)
    Button buSing;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.root)
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        buSing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buSing:
                singUp();
                break;
        }
    }

    private void singUp() {
        progressBar.setVisibility(View.VISIBLE);
        final String emial = emailEd.getText().toString().trim();
        final String pass = passEd.getText().toString();

        if (!TextUtils.isEmpty(emial) && !TextUtils.isEmpty(pass)) {
            Networking.signUp(emial, pass, new OnFireBaseOperationListener() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SingupActivity.this,MainActivity.class));
                    // to finish all activity in queue
                    finishAffinity();
                }

                @Override
                public void onFailed(String errorMassege) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {

            progressBar.setVisibility(View.GONE);

            emailEd.setError("Required !");
            emailEd.requestFocus();
            passEd.setError("Required !");
            passEd.requestFocus();
            YoYo.with(Techniques.Bounce)
                    .duration(800)
                    .playOn(root);
        }
    }
}
