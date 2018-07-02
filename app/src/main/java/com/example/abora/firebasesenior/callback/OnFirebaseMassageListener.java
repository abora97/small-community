package com.example.abora.firebasesenior.callback;

import com.example.abora.firebasesenior.Model.Massage;

import java.util.List;

public interface OnFirebaseMassageListener {
    void onSucess(List<Massage> list);
    void onErro(String errorMag);
}
