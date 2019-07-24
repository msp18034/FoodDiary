package msp18034.fooddiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.don.pieviewlibrary.LinePieView;

public class NutritionActivity extends AppCompatActivity {

    TextView carbo_t,carbo_e,pro_t,pro_e,fat_t,fat_e,fiber_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        carbo_t = findViewById(R.id.carbo);
        carbo_e = findViewById(R.id.carbo_e);
        pro_t = findViewById(R.id.protein);
        pro_e = findViewById(R.id.protein_e);
        fat_t = findViewById(R.id.fat);
        fat_e = findViewById(R.id.fat_e);
        fiber_t = findViewById(R.id.fiber);

        Intent myIntent = getIntent();
        double carbo = myIntent.getDoubleExtra("carbo",1);
        double protein = myIntent.getDoubleExtra("protein",1);
        double fat = myIntent.getDoubleExtra("fat",1);
        double fiber = myIntent.getDoubleExtra("fiber",1);
//        double carbo=15.34;
//        double protein=14.23;
//        double fat=23.45;
//        double fiber=22.343;

        int[] data = new int[]{(int)fat, (int)protein, (int)carbo};
        String[] name = new String[]{"Fat", "Protein", "Carbohydrate"};
        int[] color = new int[]{
                getResources().getColor(R.color.fat),
                getResources().getColor(R.color.protein),
                getResources().getColor(R.color.carbo)};
        LinePieView linePieView = (LinePieView) findViewById(R.id.pieView);
        linePieView.setData(data, name, color);

        double sum = carbo + protein + fat;
        carbo /= sum;
        protein /= sum;
        fat /= sum;

        carbo_t.setText(String.format ("%.2f", carbo*100)+"%");
        pro_t.setText(String.format ("%.2f", protein*100)+"%");
        fat_t.setText(String.format ("%.2f", fat*100)+"%");
        fiber_t.setText(String.format ("%.2f", fiber)+"g");

        //蛋白质 20％，脂肪 25%，碳水化合物 55% 上下浮动5%均可
        if(carbo > 0.6){
            carbo_e.setTextColor(getResources().getColor(R.color.red));
            carbo_e.setText("↑");
        }
        else if(carbo < 0.5){
            carbo_e.setTextColor(getResources().getColor(R.color.yellow));
            carbo_e.setText("↓");
        }
        else{
            carbo_e.setTextColor(getResources().getColor(R.color.green));
            carbo_e.setText("√");
        }

        if(fat > 0.3){
            fat_e.setTextColor(getResources().getColor(R.color.red));
            fat_e.setText("↑");
        }
        else if(carbo < 0.2){
            fat_e.setTextColor(getResources().getColor(R.color.yellow));
            fat_e.setText("↓");
        }
        else{
            fat_e.setTextColor(getResources().getColor(R.color.green));
            fat_e.setText("√");
        }

        if(protein > 0.25){
            pro_e.setTextColor(getResources().getColor(R.color.red));
            pro_e.setText("↑");
        }
        else if(protein < 0.15){
            pro_e.setTextColor(getResources().getColor(R.color.yellow));
            pro_e.setText("↓");
        }
        else{
            pro_e.setTextColor(getResources().getColor(R.color.green));
            pro_e.setText("√");
        }

    }

    public void confirm(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
