package com.example.note_appmvp.activity.login;

public interface LoginView {
    void showProgress();
    void hideProgress();
    void onRequestSuccess(String message);
    void onRequestError(String message);
}
