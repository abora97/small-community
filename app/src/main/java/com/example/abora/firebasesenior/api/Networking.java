package com.example.abora.firebasesenior.api;

import android.support.annotation.NonNull;

import com.example.abora.firebasesenior.Model.Massage;
import com.example.abora.firebasesenior.Model.Status;
import com.example.abora.firebasesenior.Util.Constant;
import com.example.abora.firebasesenior.callback.OnFireBaseOperationListener;
import com.example.abora.firebasesenior.callback.OnFirebaseDataListener;
import com.example.abora.firebasesenior.callback.OnFirebaseMassageListener;
import com.example.abora.firebasesenior.callback.OnMassageListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Networking {
    //login operation
    public static void login(String email, String pass, final OnFireBaseOperationListener onFireBaseOperationListener) {

        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onFireBaseOperationListener.onSuccess();
                        } else {
                            onFireBaseOperationListener.onFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    //sing up operation
    public static void signUp(String email, String pass, final OnFireBaseOperationListener onFireBaseOperationListener) {

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onFireBaseOperationListener.onSuccess();
                        } else {
                            onFireBaseOperationListener.onFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    //forget password operation
    public static void resetPassword(String email, final OnFireBaseOperationListener onFireBaseOperationListener) {

        FirebaseHelper.getAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onFireBaseOperationListener.onSuccess();
                        } else {
                            onFireBaseOperationListener.onFailed(task.getException().getMessage());
                        }
                    }

                });
    }

    //add new post operation
    public static void addStatus(Status status, final OnFireBaseOperationListener listener) {
        String key = FirebaseHelper.getDatabase()
                .getReference().child(Constant.Firebase.STATUS_NODE)
                .push().getKey();

        status.setKey(key);

        FirebaseHelper.getDatabase()
                .getReference().child(Constant.Firebase.STATUS_NODE)
                .child(key).setValue(status)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    // get all posts from database
    public static void getAllStatus(final OnFirebaseDataListener listener) {
        FirebaseHelper.getDatabase()
                .getReference()
                .child(Constant.Firebase.STATUS_NODE)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Status> statuses = new ArrayList<>();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Status status = child.getValue(Status.class);
                            statuses.add(status);
                        }

                        listener.onSucess(statuses);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onErro(databaseError.getMessage());
                    }
                });
    }

    // Delete post
    public static void deletePost(Status status, final OnFireBaseOperationListener listener) {
        FirebaseHelper.getDatabase()
                .getReference()
                .child(Constant.Firebase.STATUS_NODE)
                .child(status.getKey())
                .setValue(null)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    // refresh and get all massage
    public static void getMassage(final OnFirebaseMassageListener listener) {
        FirebaseHelper.getDatabase()
                .getReference()
                .child(Constant.Firebase.MASSAGE_NODE)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Massage> massages = new ArrayList<>();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Massage massagess = child.getValue(Massage.class);
                            massages.add(massagess);
                        }

                        listener.onSucess(massages);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listener.onErro(databaseError.getMessage());
                    }
                });

    }

    // send massage "enter massage to firebase
    public static void sendMassage(Massage massage, final OnMassageListener listener){
        String key = FirebaseHelper.getDatabase()
                .getReference().child(Constant.Firebase.MASSAGE_NODE)
                .push().getKey();

        massage.setMassageKey(key);

        FirebaseHelper.getDatabase()
                .getReference().child(Constant.Firebase.MASSAGE_NODE)
                .child(key).setValue(massage)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onSucess();
                        } else {
                            listener.onErro(task.getException().getMessage());
                        }
                    }
                });

    }

}
