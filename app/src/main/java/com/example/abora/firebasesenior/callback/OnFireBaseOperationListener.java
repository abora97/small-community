package com.example.abora.firebasesenior.callback;

public interface OnFireBaseOperationListener {
    void onSuccess();

    void onFailed(String errorMassege);
}
