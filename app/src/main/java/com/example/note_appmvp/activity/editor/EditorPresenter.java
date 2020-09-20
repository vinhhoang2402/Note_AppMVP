package com.example.note_appmvp.activity.editor;

import android.util.Log;
import android.widget.Toast;

import com.example.note_appmvp.api.ApiClient;
import com.example.note_appmvp.api.ApiInterface;
import com.example.note_appmvp.model.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class EditorPresenter{
    private EditorView view;
    public EditorPresenter(EditorView view) {
        this.view = view;
    }
    void saveNote(final String title,final String note,final int color) {
        view.hideProgress();
        //thuc hien request Api thong qua interface
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<Note> call=apiInterface.saveNote(title,note,color);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body()!=null){
                    Boolean success=response.body().getSuccess();
                    if(success){
                        view.showProgress();
                        view.onRequestSuccess(response.body().getMessage());
                    }else{
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getMessage());
            }
        });
    }
     void updateNote(int id,String title, String note, int mcolor) {
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<Note> call=apiInterface.upDateNote(id,title,note,mcolor);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body()!=null){
                    Boolean success=response.body().getSuccess();
                    if(success){
                        view.showProgress();
                        view.onRequestSuccess(response.body().getMessage());
                    }else{
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getMessage());
            }
        });
    }
    void deleteNote(int id) {
        view.hideProgress();
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<Note> call=apiInterface.deleteNote(id);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Boolean success=response.body().getSuccess();
                    if(success){
                        view.showProgress();
                        view.onRequestSuccess(response.body().getMessage());
                    }else{
                        view.showProgress();
                        view.onRequestError(response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                view.hideProgress();
                view.onRequestError(t.getMessage());
            }
        });
    }
}
