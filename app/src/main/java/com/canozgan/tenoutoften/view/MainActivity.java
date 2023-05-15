package com.canozgan.tenoutoften.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.canozgan.tenoutoften.R;
import com.canozgan.tenoutoften.adapter.PlayerAdapter;
import com.canozgan.tenoutoften.databinding.ActivityMainBinding;
import com.canozgan.tenoutoften.model.PlayerModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<PlayerModel> playerModelArrayList;
    PlayerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        playerModelArrayList=new ArrayList<>();
        adapter =new PlayerAdapter(playerModelArrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        getData();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.start_competition){
            Intent intent=new Intent(MainActivity.this,StartCompetitionActivity.class);
            MainActivity.this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
                String name=cursor.getString(nameIx);
                String overallScore=cursor.getString(overallScoreIx);
                String offensive=cursor.getString(offensiveIx);
                String defensive=cursor.getString(defensiveIx);
                String stability=cursor.getString(stabilityIx);
                String imageUrl=cursor.getString(imageUrlIx);
                PlayerModel playerModel =new PlayerModel(name,overallScore,offensive,defensive,stability,imageUrl);
                playerModel.id=id;
                playerModelArrayList.add(playerModel);

            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
}