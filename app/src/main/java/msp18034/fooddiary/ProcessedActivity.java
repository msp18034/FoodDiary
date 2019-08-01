package msp18034.fooddiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProcessedActivity extends AppCompatActivity {

    private TextView foods, calories;
    private ImageView drawnImage;
    private Bitmap bitmap;
    private Button alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processed);

        drawnImage = findViewById(R.id.drawnImage);
        foods = findViewById(R.id.return_foods);
        calories = findViewById(R.id.return_calories);
        alert = findViewById(R.id.alerttext);

        Intent myIntent = getIntent();
        int total = myIntent.getIntExtra("total",0);
        String msg_f = myIntent.getStringExtra("msg_f");
        String msg_c = myIntent.getStringExtra("msg_c");
        String img = myIntent.getStringExtra("img");
        bitmap = stringToBitmap(img);

        foods.setText(msg_f);
        calories.setText(msg_c);
        drawnImage.setImageBitmap(bitmap);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        int calorie = sharedPreferences.getInt("calorie",1500);
        if ( (double)total / calorie > 0.4){
            alert.setVisibility(View.VISIBLE);
        }
//        foods.setText("食物\nChicken pot\nPork Ribs Soup\nShredded cabbage\nRice\n合计");
//        calories.setText("热量(cal)\n262cal\n5cal\n47cal\n232cal\n546cal");
//        drawnImage.setImageResource(R.drawable.detection_result);

        //TODO:能不能直接显示到页面底端呀
    }

    public void confirm(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
