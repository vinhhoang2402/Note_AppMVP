package com.example.note_appmvp.activity.main;

import android.util.Log;

import com.example.note_appmvp.api.ApiClient;
import com.example.note_appmvp.api.ApiInterface;
import com.example.note_appmvp.model.Note;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainView view;
    public MainPresenter(MainView view) {
        this.view = view;
    }
    void getData(){
        view.hideLoading();
        //Request to server
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<Note>> call=apiInterface.getNotes();
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                view.hideLoading();
                if(response.isSuccessful() && response.body()!=null){
                    view.onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                view.showLoading();
                Log.d("vinhhoag",t.getMessage());
            }
        });
    }
}
