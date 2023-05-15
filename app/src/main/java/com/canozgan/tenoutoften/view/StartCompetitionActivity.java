package com.canozgan.tenoutoften.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.canozgan.tenoutoften.databinding.ActivityStartCompetitionBinding;

public class StartCompetitionActivity extends AppCompatActivity {
    ActivityStartCompetitionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStartCompetitionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    public void startCompetition(View view){
        Intent intentToCompetition = new Intent(StartCompetitionActivity.this, CompetitionActivity.class);
        intentToCompetition.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToCompetition);
    }
}