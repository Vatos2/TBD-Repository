package com.example.student.charactersheet5e;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import AppModels.CharSheet;

public class Pop_Edit_Money extends AppCompatActivity {

    private CharSheet charSheet;
    private TextView editCopper;
    private TextView editSilver;
    private TextView editElectrum;
    private TextView editGold;
    private TextView editPlatinum;

    private int[] coinAmounts = {0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop__edit__money);

        DisplayMetrics dispMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dispMetrics);

        int width = dispMetrics.widthPixels;
        int height = dispMetrics.heightPixels;

        getWindow().setLayout((int)(width*0.9), (int)(height*0.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 90;

        getWindow().setAttributes(params);

        charSheet = (CharSheet) (getIntent().getSerializableExtra("charSheet"));

        //Connect variables to layout
        editCopper = findViewById(R.id.editCopper_text);
        editSilver = findViewById(R.id.editSilver_text);
        editElectrum = findViewById(R.id.editElectrum_text);
        editGold = findViewById(R.id.editGold_text);
        editPlatinum = findViewById(R.id.editPlatinum_text);

        Button saveButton = findViewById(R.id.Save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Extract data from UI and pass back
                if(editCopper.getText().toString().isEmpty())
                    coinAmounts[0] = 0;
                else
                    coinAmounts[0] = Integer.parseInt(editCopper.getText().toString());
                if(editSilver.getText().toString().isEmpty())
                    coinAmounts[1] = 0;
                else
                    coinAmounts[1] = Integer.parseInt(editSilver.getText().toString());
                if(editElectrum.getText().toString().isEmpty())
                    coinAmounts[2] = 0;
                else
                    coinAmounts[2] = Integer.parseInt(editElectrum.getText().toString());
                if(editGold.getText().toString().isEmpty())
                    coinAmounts[3] = 0;
                else
                    coinAmounts[3] = Integer.parseInt(editGold.getText().toString());
                if(editPlatinum.getText().toString().isEmpty())
                    coinAmounts[4] = 0;
                else
                    coinAmounts[4] = Integer.parseInt(editPlatinum.getText().toString());
                Intent intent = new Intent(getBaseContext(), CharacterSheetPage4.class);
                intent.putExtra("result", coinAmounts);
                setResult(1505, intent);

            }
        });

        Intent intent = new Intent(getBaseContext(), CharacterSheetPage4.class);
        intent.putExtra("result", coinAmounts);
        setResult(1506, intent);

    }


}
