package kz.almaty.satbayevuniversity.ui.enrollee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.ui.enrollee.bachelor.BachelorActivity;

public class EnrolleeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enrollee);
    }
    public void startBachelorActivity(View view){
        Intent i = new Intent(this, BachelorActivity.class);
        startActivity(i);
    }
}
