package com.example.note_appmvp.api;

import android.database.Observable;

import androidx.lifecycle.Observer;

import com.example.note_appmvp.model.Note;
import com.example.note_appmvp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("save.php")
    Call<Note> saveNote(
            @Field("title") String title,
            @Field("note") String note,
            @Field("color") int color
    );

    @GET("getNotes.php")
    Call<List<Note>> getNotes();
    @FormUrlEncoded
    @POST("update.php")
    Call<Note> upDateNote(
            @Field("id") int id,
            @Field("title") String title,
            @Field("note") String note,
            @Field("color") int color
    );
    @FormUrlEncoded
    @POST("delete.php")
    Call<Note> deleteNote(
            @Field("id") int id
    );
    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(
            @Field("username") String username,
            @Field("pass") String pass);
}
