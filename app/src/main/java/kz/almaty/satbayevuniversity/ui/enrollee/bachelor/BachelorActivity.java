package kz.almaty.satbayevuniversity.ui.enrollee.bachelor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import kz.almaty.satbayevuniversity.R;

public class BachelorActivity extends AppCompatActivity {
    Spinner first_spinner;
    Spinner second_spinner;
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_bachelor);
    first_spinner = findViewById(R.id.first_spinner);
    ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.citizenship_array,android.R.layout.simple_spinner_item);
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    first_spinner.setAdapter(arrayAdapter);

        second_spinner = findViewById(R.id.second_spinner);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this,R.array.citizenship_array,android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        second_spinner.setAdapter(arrayAdapter2);


    }
}
