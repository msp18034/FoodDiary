package msp18034.fooddiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import msp18034.fooddiary.utils.LogPresenter;
import msp18034.fooddiary.utils.foodrecord;
import msp18034.fooddiary.utils.foodrecordAdaptor;

public class FoodLogsActivity extends AppCompatActivity {

    private ListView lvfr;
    private List<foodrecord> recordList = new ArrayList<foodrecord>();
    private LogPresenter logPresenter;
    int calorie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_logs);

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("id", null);
        calorie = sharedPreferences.getInt("calorie",0);
        logPresenter = new LogPresenter(this, userid);


        Toolbar tb = findViewById(R.id.toolbar_fl);
        TextView at = findViewById(R.id.analysis_today);
        Button na = findViewById(R.id.na_button);
        LinearLayout ll = findViewById(R.id.linearLayout);

        tb.setVisibility(View.INVISIBLE);
        at.setVisibility(View.INVISIBLE);
        na.setVisibility(View.INVISIBLE);
        ll.setVisibility(View.INVISIBLE);

        ProgressBar progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);

        Thread t = new Thread(() -> logPresenter.sendQuery());
        t.start();

    }


    public void onResponse(String[] images, String[] foods, String[] calories, int total_cal,
                           double total_carbo, double total_fat, double total_fiber,double total_protein) {
        ProgressBar progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);

        Toolbar tb = findViewById(R.id.toolbar_fl);
        TextView at = findViewById(R.id.analysis_today);
        Button na = findViewById(R.id.na_button);
        LinearLayout ll = findViewById(R.id.linearLayout);

        //alert part
        int max = (int)(calorie * 1.1);
        int min = (int)(calorie * 0.9);

        if(total_cal>max){
            at.setText("Eating too much! Do more practice ^.^");
            at.setTextColor(getResources().getColor(R.color.fat));
            at.setVisibility(View.VISIBLE);
        }
        else if(total_cal<max){
            at.setText("Insufficient intake~ eat some more");
            at.setTextColor(getResources().getColor(R.color.nutrition));
            at.setVisibility(View.VISIBLE);
        }
        else{
            at.setText("Enough food today ^.^");
            at.setTextColor(getResources().getColor(R.color.nutrition));
            at.setVisibility(View.VISIBLE);
        }



        tb.setVisibility(View.VISIBLE);
        na.setVisibility(View.VISIBLE);
        ll.setVisibility(View.VISIBLE);

        // for listView
        lvfr = (ListView) findViewById(R.id.listview);  //获得子布局
        for (int i=0; i<images.length; i++) {
            recordList.add(new foodrecord(images[i],foods[i],calories[i]));
        }
        foodrecordAdaptor frAdapter = new foodrecordAdaptor(this,
                R.layout.foodrecord, recordList);     //关联数据和子布局
        lvfr.setAdapter(frAdapter);          //绑定数据和适配器

        na.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),
                        NutritionActivity.class);
                myIntent.putExtra("protein", total_protein);
                myIntent.putExtra("carbo", total_carbo);
                myIntent.putExtra("fat", total_fat);
                myIntent.putExtra("fiber", total_fiber);
                startActivity(myIntent);
            }

        });


    }


}
