package com.example.note_appmvp.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note_appmvp.R;
import com.example.note_appmvp.activity.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {
    ProgressDialog progressDialog;
    LoginPresenter loginPresenter;
    Button btn_login;
    EditText edt_user,edt_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        loginPresenter=new LoginPresenter(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
    }

    private void anhxa() {
        edt_user=findViewById(R.id.userName);
        edt_pass=findViewById(R.id.passWord);
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=edt_user.getText().toString().trim();
                String pass=edt_pass.getText().toString().trim();
                loginPresenter.login(username,pass);
            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onRequestSuccess(String message) {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}