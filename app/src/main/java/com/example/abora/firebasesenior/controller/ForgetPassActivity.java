package com.example.abora.firebasesenior.controller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.abora.firebasesenior.R;
import com.example.abora.firebasesenior.api.Networking;
import com.example.abora.firebasesenior.callback.OnFireBaseOperationListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.email_ed)
    EditText emailEd;
    @BindView(R.id.reset_password)
    Button resetPassword;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        resetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_password:
                reset();
                break;
        }
    }

    private void reset() {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailEd.getText().toString().trim();

        if (!TextUtils.isEmpty(email)) {
            Networking.resetPassword(email, new OnFireBaseOperationListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(ForgetPassActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailed(String errorMassege) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgetPassActivity.this, errorMassege, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            emailEd.setError("Required !");
            emailEd.requestFocus();

            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(emailEd);
        }
    }
}
