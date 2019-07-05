package msp18034.fooddiary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takephoto = findViewById(R.id.takephoto);
        takephoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),
                        CaptureImageActivity.class);
                myIntent.putExtra("medium", "camera");
                startActivity(myIntent);
            }

        });

        Button gallery = findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),
                        CaptureImageActivity.class);
                myIntent.putExtra("medium", "gallery");
                startActivity(myIntent);
            }

        });

        Button showlogs = (Button) findViewById(R.id.showlogs);
        showlogs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),
                        FoodLogsActivity.class);
                startActivity(myIntent);
            }

        });



    }


}
