package com.example.vamosrachar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText price;
    EditText peopleNumber;
    TextView result;

    ImageButton shareButton;

    ImageButton ttsButton;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        price = (EditText) findViewById(R.id.editTextTextPersonName);
        peopleNumber = (EditText) findViewById(R.id.editTextTextPersonName3);
        result = (TextView) findViewById(R.id.result);

        shareButton = (ImageButton) findViewById(R.id.imageButton);
        ttsButton = (ImageButton) findViewById(R.id.imageButton2);

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String n1Str = price.getText().toString();

                String n2Str = peopleNumber.getText().toString();

                if(n1Str != null && n1Str.trim().length() > 0 && n2Str != null && n2Str.trim().length() > 0) {
                    double peopleNumberD = Double.parseDouble(n2Str);
                    double priceNumber = Double.parseDouble(n1Str);

                    String sendText = "R$ " + (priceNumber / peopleNumberD);
                    result.setText(sendText);
                } else {
                    String defaultText = "R$ 0,00";
                    result.setText(defaultText);
                }
            }
        });

        peopleNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String n1Str = price.getText().toString();

                String n2Str = peopleNumber.getText().toString();

                if(n1Str != null && n1Str.trim().length() > 0 && n2Str != null && n2Str.trim().length() > 0) {
                    double peopleNumberD = Double.parseDouble(n2Str);
                    double priceNumber = Double.parseDouble(n1Str);

                    String sendText = "R$ " + (priceNumber / peopleNumberD);
                    result.setText(sendText);
                } else {
                    String defaultText = "R$ 0,00";
                    result.setText(defaultText);
                }
            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });

        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = result.getText().toString();
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareStr = result.getText().toString();
                i.putExtra(Intent.EXTRA_TEXT, shareStr);
                startActivity(i);
            }
        });
    }

    public void onPause(){
        if(tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}