package com.example.note_appmvp.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_appmvp.R;
import com.example.note_appmvp.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHoder> {
    Context context;
    List<Note> noteList;
    ItemClickListener itemClickListener;

    public MainAdapter(Context context, List<Note> noteList, ItemClickListener itemClickListener) {
        this.context = context;
        this.noteList = noteList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MainViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_note,parent,false);
        return new MainViewHoder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHoder holder, int position) {
        Note note=noteList.get(position);
        holder.txt_title.setText(note.getTitle());
        holder.txt_note.setText(note.getNote());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date=dateFormat.parse(note.getDate());
            if (date != null) {
                holder.txt_date.setText(dateFormat.format(date));
            }
        }
        catch(Exception e) {
            System.out.println("Excep"+e);
        }

        holder.cardView.setCardBackgroundColor(note.getColor());
    }
    @Override
    public int getItemCount() {
        return noteList.size();
    }
    public class MainViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_title,txt_note,txt_date;
        CardView cardView;
        ItemClickListener itemClickListener;
        public MainViewHoder(@NonNull View itemView,ItemClickListener itemClickListener) {
            super(itemView);
            //tao setter cho bien
            this.itemClickListener=itemClickListener;
            txt_title=itemView.findViewById(R.id.title);
            txt_note=itemView.findViewById(R.id.note);
            txt_date=itemView.findViewById(R.id.date);
            cardView=itemView.findViewById(R.id.card_item);
            cardView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }
    }
    public interface ItemClickListener{
        void onClick(View view,int position);
    }
}
