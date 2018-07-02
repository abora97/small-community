package com.example.abora.firebasesenior.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.abora.firebasesenior.R;
import com.example.abora.firebasesenior.api.FirebaseHelper;
import com.example.abora.firebasesenior.api.Networking;
import com.example.abora.firebasesenior.callback.OnFireBaseOperationListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.email_ed)
    EditText emailEd;
    @BindView(R.id.pass_ed)
    EditText passEd;
    @BindView(R.id.buLog)
    Button buLog;
    @BindView(R.id.buSing)
    Button buSing;
    @BindView(R.id.tvForg)
    TextView tvForg;
    @BindView(R.id.sign_in_google)
    SignInButton signInGoogle;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.root)
    LinearLayout root;

    GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseHelper.getAuth().getCurrentUser()!=null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        buLog.setOnClickListener(this);
        buSing.setOnClickListener(this);
        tvForg.setOnClickListener(this);
        signInGoogle.setOnClickListener(this);
    //    authGoogle();

    }

    private void authGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Toast.makeText(this, "Welcome "+account, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "Welcome "+FirebaseHelper.getAuth().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buLog:
                login();
                break;
            case R.id.buSing:
                startActivity(new Intent(this, SingupActivity.class));
                break;
            case R.id.tvForg:
                startActivity(new Intent(this, ForgetPassActivity.class));
                break;
            case R.id.sign_in_google:
                signIn();
                break;
        }
    }


    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailEd.getText().toString().trim();
        String pass = passEd.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            Networking.login(email, pass, new OnFireBaseOperationListener() {
                @Override
                public void onSuccess() {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailed(String errorMassege) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, errorMassege, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            progressBar.setVisibility(View.GONE);

            emailEd.setError("Required !");
            emailEd.requestFocus();
            passEd.setError("Required !");
            passEd.requestFocus();
            YoYo.with(Techniques.DropOut)
                    .duration(800)
                    .playOn(root);
            Toast.makeText(this, "please enter the data", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
}
