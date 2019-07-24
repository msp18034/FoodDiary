package msp18034.fooddiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        //如果本地没有用户信息，跳转到用户界面
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("id", null);
        if (userid == null){
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
        }


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

        Button usersetting = findViewById(R.id.usersetting);
        usersetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),
                        LoginActivity.class);
                startActivity(myIntent);
            }

        });



    }


}
