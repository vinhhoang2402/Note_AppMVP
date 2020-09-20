package com.example.note_appmvp.activity.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.note_appmvp.R;
import com.example.note_appmvp.activity.editor.EditorActivity;
import com.example.note_appmvp.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,MainView {
    private static final int INTENT_EDIT =200 ;
    private static final int INTENT_ADD =100 ;
    FloatingActionButton btn_add;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    List<Note> noteList=new ArrayList<>();
    MainAdapter mainAdapter;
    MainPresenter mainPresenter;
    MainAdapter.ItemClickListener itemClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        mainPresenter = new MainPresenter(this);
        mainPresenter.getData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.getData();
            }
        });;
        itemClickListener=((view,position)->{
            int id=noteList.get(position).getId();
            String title=noteList.get(position).getTitle();
            String note=noteList.get(position).getNote();
            int color=noteList.get(position).getColor();
//            Common.idNote=noteList.get(position);
//            startActivity(new Intent(MainActivity.this, Inte.class));
            Intent intent=new Intent(MainActivity.this,EditorActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("title",title);
            intent.putExtra("note",note);
            intent.putExtra("color",color);
            startActivityForResult(intent,INTENT_EDIT);
        });

    }
    private void anhxa() {
        btn_add=findViewById(R.id.add);
        recyclerView=findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        refreshLayout=findViewById(R.id.refresh);
        btn_add.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==INTENT_EDIT && resultCode==RESULT_OK) {
            mainPresenter.getData();//loaddata
        }else if(requestCode==INTENT_ADD && resultCode==RESULT_OK)
        {
            mainPresenter.getData();//loaddata
        }
    }

    @Override
    public void onClick(View view) {
        startActivityForResult(new Intent(MainActivity.this, EditorActivity.class),
                INTENT_ADD);
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }
    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }
    @Override
    public void onGetResult(List<Note> notes) {
        mainAdapter=new MainAdapter(this,notes,itemClickListener);
        mainAdapter.notifyDataSetChanged();
        if (mainAdapter == null) {
            Log.e("RecyclerView", "No adapter attached; skipping layout");
        } else if (this.recyclerView == null) {
            Log.e("RecyclerView", "No layout manager attached; skipping layout");
        } else {
            recyclerView.setAdapter(mainAdapter);
        }
        noteList=notes;
    }
    @Override
    public void onErrorLoading(String message) {

    }
}