package msp18034.fooddiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import msp18034.fooddiary.R;
import msp18034.fooddiary.utils.CaptureImagePresenter;

public class CaptureImageActivity extends AppCompatActivity {

    private CaptureImagePresenter captureImagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        Intent myIntent = getIntent();
        String medium = myIntent.getStringExtra("medium");
        captureImagePresenter = new CaptureImagePresenter(this, medium);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
            captureImagePresenter.onActivityResult(requestCode, resultCode, data);
        else if(resultCode == RESULT_CANCELED)
            captureImagePresenter.cancel();
    }

    public void cancelButton(View view) {
        captureImagePresenter.cancel();
    }

    public void sendImage(View view) {
        ImageView img = findViewById(R.id.imageView);
        Button b1 = findViewById(R.id.button3);
        Button b2 = findViewById(R.id.button4);
        img.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);

        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        Thread t = new Thread(() -> captureImagePresenter.sendImage());
        t.start();
    }


}
