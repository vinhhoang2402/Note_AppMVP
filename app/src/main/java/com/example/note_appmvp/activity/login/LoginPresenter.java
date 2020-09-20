package com.example.note_appmvp.activity.login;

import android.content.Intent;

import com.example.note_appmvp.activity.main.MainActivity;
import com.example.note_appmvp.api.ApiClient;
import com.example.note_appmvp.api.ApiInterface;
import com.example.note_appmvp.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    void login(String username,String pass) {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<User> call=apiInterface.login(username,pass);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                loginView.showProgress();
                if(response.isSuccessful() && response.body()!=null){
                    Boolean success=response.body().getSuccess();
                    if(success){
                        loginView.hideProgress();
                        loginView.onRequestSuccess(response.body().getMessage());
                    }else{
                        loginView.hideProgress();
                        loginView.onRequestError(response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                loginView.hideProgress();
                loginView.onRequestError(t.getMessage());
            }
        });

    }
}
