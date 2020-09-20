package com.example.note_appmvp.activity.editor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note_appmvp.model.Note;
import com.example.note_appmvp.R;
import com.example.note_appmvp.api.ApiClient;
import com.example.note_appmvp.api.ApiInterface;
import com.thebluealliance.spectrum.SpectrumPalette;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity implements EditorView {
    EditText edt_title,edt_note;
    ProgressDialog progressDialog;
    SpectrumPalette spectrumPalette;
    EditorPresenter presenter;
    private static int mcolor,id;
    String title,note;
    Menu actionMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        anhxa();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        //KHoi tao Prisenter
        presenter=new EditorPresenter(this);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
        title=intent.getStringExtra("title");
        note=intent.getStringExtra("note");
        mcolor=intent.getIntExtra("color",0);
        setFormData();
    }
    private void setFormData() {
        if(id!=0){
            edt_title.setText(title);
            edt_note.setText(note);
            spectrumPalette.setSelectedColor(mcolor);
            getSupportActionBar().setTitle("Update Note");
            readMode();
        }else{
            spectrumPalette.setSelectedColor(getResources().getColor(R.color.white));
            mcolor=getResources().getColor(R.color.white);
            editMode();
        }
    }
    private void editMode() {
        edt_title.setFocusableInTouchMode(true);
        edt_note.setFocusableInTouchMode(true);
        spectrumPalette.setFocusable(true);
    }
    private void readMode() {
        edt_title.setFocusableInTouchMode(false);
        edt_note.setFocusableInTouchMode(false);
        spectrumPalette.setFocusable(false);
        edt_title.setFocusable(false);
        edt_note.setFocusable(false);
    }
    private void anhxa() {
        edt_title=findViewById(R.id.title);
        edt_note=findViewById(R.id.note);
        spectrumPalette=findViewById(R.id.palette);
        spectrumPalette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                mcolor=color;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu=menu;
        if(id!=0){
            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(true);
        }else{
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                title=edt_title.getText().toString().trim();
                note=edt_note.getText().toString().trim();
                if(title.isEmpty()){
                    edt_title.setError("Plese enter a Title");
                }else{
                    if(note.isEmpty()){
                        edt_note.setError("Plese enter a Note");
                    }else
                    {
                       presenter.saveNote(title,note,mcolor);
                    }
            }
                return true;
            case R.id.edit:
                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);
                return true;
            case R.id.update:
                title=edt_title.getText().toString().trim();
                note=edt_note.getText().toString().trim();
                if(title.isEmpty()){
                    edt_title.setError("Plese enter a Title");
                }else {
                    if (note.isEmpty()) {
                        edt_note.setError("Plese enter a Note");
                    } else {
                        presenter.updateNote(id,title,note,mcolor);
                    }
                }
                return true;
            case R.id.delete:
                AlertDialog.Builder alertDialog= new AlertDialog.Builder(this);
                alertDialog.setTitle("ConFirm !");
                alertDialog.setMessage("Bạn có muốn xóa "+title+" không ?");
                alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.deleteNote(id);
                    }
                });
                alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        Toast.makeText(this, message,
                Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}