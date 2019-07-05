package msp18034.fooddiary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ProcessedActivity extends AppCompatActivity {

    private TextView foods, calories;
    private ImageView drawnImage;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processed);

        drawnImage = findViewById(R.id.drawnImage);
        foods = findViewById(R.id.return_foods);
        calories = findViewById(R.id.return_calories);

        Intent myIntent = getIntent();
        String msg_f = myIntent.getStringExtra("msg_f");
        String msg_c = myIntent.getStringExtra("msg_c");
        String img = myIntent.getStringExtra("img");
        bitmap = stringToBitmap(img);

        foods.setText(msg_f);
        calories.setText(msg_c);
        drawnImage.setImageBitmap(bitmap);
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
