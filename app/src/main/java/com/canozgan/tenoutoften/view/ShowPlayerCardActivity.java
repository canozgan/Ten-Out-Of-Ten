package com.canozgan.tenoutoften.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewDebug;
import android.widget.Toast;

import com.canozgan.tenoutoften.databinding.ActivityShowPlayerCardBinding;
import com.canozgan.tenoutoften.model.PlayerModel;
import com.squareup.picasso.Picasso;

import javax.xml.transform.Source;

public class ShowPlayerCardActivity extends AppCompatActivity {
    ActivityShowPlayerCardBinding binding;
    int returnId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowPlayerCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        returnId= getIntent().getIntExtra("id",0);
        getData();
    }
    public void getData(){

        try{
            SQLiteDatabase database=this.openOrCreateDatabase("Football Player Card",MODE_PRIVATE,null);
            Cursor cursor =database.rawQuery("SELECT * FROM players",null);
            int idIx =cursor.getColumnIndex("id");
            int nameIx=cursor.getColumnIndex("name");
            int overallScoreIx=cursor.getColumnIndex("overallScore");
            int offensiveIx=cursor.getColumnIndex("offensive");
            int defensiveIx=cursor.getColumnIndex("defensive");
            int stabilityIx=cursor.getColumnIndex("stability");
            int imageUrlIx=cursor.getColumnIndex("imageUrl");
            while (cursor.moveToNext()){
                int id =cursor.getInt(idIx);
                if(returnId==id){
                    String name=cursor.getString(nameIx);
                    String overallScore=cursor.getString(overallScoreIx);
                    String offensive=cursor.getString(offensiveIx);
                    String defensive=cursor.getString(defensiveIx);
                    String stability=cursor.getString(stabilityIx);
                    String imageUrl=cursor.getString(imageUrlIx);
                    binding.name.setText(name);
                    binding.overallScore.setText(overallScore);
                    binding.offensive.setText(offensive);
                    binding.defensive.setText(defensive);
                    binding.stability.setText(stability);
                    System.out.println(name);
                    Picasso.get().load(imageUrl).into(binding.image);
                }
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
}