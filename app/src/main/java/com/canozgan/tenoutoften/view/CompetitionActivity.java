package com.canozgan.tenoutoften.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.canozgan.tenoutoften.R;
import com.canozgan.tenoutoften.databinding.ActivityCompetitionBinding;
import com.canozgan.tenoutoften.model.PlayerModel;
import com.canozgan.tenoutoften.model.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class CompetitionActivity extends AppCompatActivity {
    ActivityCompetitionBinding binding;
    FirebaseFirestore firebaseFirestore;
    ArrayList<QuestionModel> questionModelArrayList;
    int questionCounter;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCompetitionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questionModelArrayList=new ArrayList<>();
        firebaseFirestore=FirebaseFirestore.getInstance();
        questionCounter=0;
        getData();
        setTimer();

    }
    public void goOn(View view){
        questionCounter++;
        showQuestion();
    }
    public void choice1(View view){
        questionModelArrayList.get(questionCounter).userAnswer=binding.textChoice1.getText().toString();
        binding.textChoice1.setBackground(getDrawable(R.drawable.rounded_corner_color));
        binding.textChoice1.setTextColor(Color.WHITE);
        binding.textChoice2.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice2.setTextColor(Color.BLACK);
        binding.textChoice3.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice3.setTextColor(Color.BLACK);
        binding.textChoice4.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice4.setTextColor(Color.BLACK);
    }
    public void choice2(View view){
        questionModelArrayList.get(questionCounter).userAnswer=binding.textChoice2.getText().toString();
        binding.textChoice2.setBackground(getDrawable(R.drawable.rounded_corner_color));
        binding.textChoice2.setTextColor(Color.WHITE);
        binding.textChoice1.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice1.setTextColor(Color.BLACK);
        binding.textChoice3.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice3.setTextColor(Color.BLACK);
        binding.textChoice4.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice4.setTextColor(Color.BLACK);
    }
    public void choice3(View view){
        questionModelArrayList.get(questionCounter).userAnswer=binding.textChoice3.getText().toString();
        binding.textChoice3.setBackground(getDrawable(R.drawable.rounded_corner_color));
        binding.textChoice3.setTextColor(Color.WHITE);
        binding.textChoice1.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice1.setTextColor(Color.BLACK);
        binding.textChoice2.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice2.setTextColor(Color.BLACK);
        binding.textChoice4.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice4.setTextColor(Color.BLACK);
    }
    public void choice4(View view){
        questionModelArrayList.get(questionCounter).userAnswer=binding.textChoice4.getText().toString();
        binding.textChoice4.setBackground(getDrawable(R.drawable.rounded_corner_color));
        binding.textChoice4.setTextColor(Color.WHITE);
        binding.textChoice1.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice1.setTextColor(Color.BLACK);
        binding.textChoice2.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice2.setTextColor(Color.BLACK);
        binding.textChoice3.setBackground(getDrawable(R.drawable.rounded_corner));
        binding.textChoice3.setTextColor(Color.BLACK);
    }
    public void getData(){
        firebaseFirestore.collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isSuccessful()) {
                    if(task.getException()!=null){
                        Toast.makeText(CompetitionActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        QuestionModel questionModel = new QuestionModel();
                        questionModel.question = (String) snapshot.getData().get("question");
                        questionModel.choice1 = (String) snapshot.getData().get("choice1");
                        questionModel.choice2 = (String) snapshot.getData().get("choice2");
                        questionModel.choice3 = (String) snapshot.getData().get("choice3");
                        questionModel.choice4 = (String) snapshot.getData().get("choice4");
                        questionModel.answer = (String) snapshot.getData().get("answer");
                        questionModel.userAnswer = "Non";
                        questionModelArrayList.add(questionModel);
                    }
                    showQuestion();
                }
            }
        });

    }
    public void showQuestion(){
        timer.cancel();
        if(questionCounter<10){
            binding.textQuestion.setText(questionModelArrayList.get(questionCounter).question);
            binding.textChoice1.setText(questionModelArrayList.get(questionCounter).choice1);
            binding.textChoice2.setText(questionModelArrayList.get(questionCounter).choice2);
            binding.textChoice3.setText(questionModelArrayList.get(questionCounter).choice3);
            binding.textChoice4.setText(questionModelArrayList.get(questionCounter).choice4);
            String questionNumberText="Question "+(questionCounter+1);
            binding.textQuestionNumber.setText(questionNumberText);
            binding.button.setEnabled(true);
            binding.textChoice1.setBackground(getDrawable(R.drawable.rounded_corner));
            binding.textChoice1.setTextColor(Color.BLACK);
            binding.textChoice2.setBackground(getDrawable(R.drawable.rounded_corner));
            binding.textChoice2.setTextColor(Color.BLACK);
            binding.textChoice3.setBackground(getDrawable(R.drawable.rounded_corner));
            binding.textChoice3.setTextColor(Color.BLACK);
            binding.textChoice4.setBackground(getDrawable(R.drawable.rounded_corner));
            binding.textChoice4.setTextColor(Color.BLACK);
            timer.start();
        }else{
            evaluateResult();
        }

    }
    public void evaluateResult(){
        binding.button.setEnabled(false);
        binding.textChoice1.setEnabled(false);
        binding.textChoice2.setEnabled(false);
        binding.textChoice3.setEnabled(false);
        binding.textChoice4.setEnabled(false);
        int trueNumber=0;
        for(QuestionModel questionModel:questionModelArrayList){
            if(questionModel.answer.matches(questionModel.userAnswer)){
                trueNumber++;
            }
        }
        if(trueNumber==10){
            firebaseFirestore.collection("Players").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (!task.isSuccessful()) {
                        if(task.getException()!=null){
                            Toast.makeText(CompetitionActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Random random =new Random();
                        int playerIndex=random.nextInt(task.getResult().size());
                        DocumentSnapshot snapshot=task.getResult().getDocuments().get(playerIndex);
                        String name=(String)snapshot.getData().get("name");
                        String overallScore=(String)snapshot.getData().get("overallScore");
                        String offensive=(String)snapshot.getData().get("offensive");
                        String defensive=(String)snapshot.getData().get("defensive");
                        String stability=(String)snapshot.getData().get("stability");
                        String imageUrl=(String)snapshot.getData().get("imageUrl");
                        try{
                            SQLiteDatabase database=CompetitionActivity.this.openOrCreateDatabase("Football Player Card",MODE_PRIVATE,null);
                            database.execSQL("CREATE TABLE IF NOT EXISTS players(id INTEGER PRIMARY KEY, name VARCHAR, overallScore VARCHAR, offensive VARCHAR, defensive VARCHAR, stability VARCHAR, imageUrl VARCHAR)");
                            String  sqlString="INSERT INTO players (name, overallScore, offensive, defensive, stability,imageUrl) VALUES(?, ?, ?, ?, ?, ?)";
                            SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
                            sqLiteStatement.bindString(1,name);
                            sqLiteStatement.bindString(2,overallScore);
                            sqLiteStatement.bindString(3,offensive);
                            sqLiteStatement.bindString(4,defensive);
                            sqLiteStatement.bindString(5,stability);
                            sqLiteStatement.bindString(6,imageUrl);
                            sqLiteStatement.execute();

                            }catch (Exception e){
                            Toast.makeText(CompetitionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                        String resulText="Congratulations, you answered 10/10 correctly.You won a "+name+" Card!";
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CompetitionActivity.this);
                        alertDialog.setTitle("Result");
                        alertDialog.setMessage(resulText);
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentToMainActivity=new Intent(CompetitionActivity.this,MainActivity.class);
                                intentToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentToMainActivity);
                            }
                        });
                        alertDialog.show();
                    }
                }
            });
        }
        else{
            String resulText ="Your success rate "+trueNumber+"/10. Unfortunately, you couldn't win a player card.";
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CompetitionActivity.this);
            alertDialog.setTitle("Result");
            alertDialog.setMessage(resulText);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentToMainActivity=new Intent(CompetitionActivity.this,MainActivity.class);
                    intentToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentToMainActivity);
                }
            });
            alertDialog.show();
        }
    }
    public void setTimer(){
        timer=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeUntilFinished=Long.toString(millisUntilFinished/1000);
                binding.textTime.setText(timeUntilFinished);
            }

            @Override
            public void onFinish() {
                binding.button.setEnabled(false);
                if(questionModelArrayList.get(questionCounter).userAnswer.matches("Non")){
                    Toast.makeText(CompetitionActivity.this, "Unfortunately you did not reply in time.", Toast.LENGTH_LONG).show();
                }
                questionCounter++;
                showQuestion();
            }
        };
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        super.onBackPressed();
    }
}