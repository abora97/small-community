package com.example.abora.firebasesenior.callback;

import com.example.abora.firebasesenior.Model.Status;

import java.util.List;

public interface OnFirebaseDataListener {
    void onSucess(List<Status> list);
    void onErro(String errorMag);
}
