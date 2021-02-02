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

import java.text.DecimalFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    EditText price;
    EditText peopleNumber;
    TextView result;

    String sendText = "R$ 0,00";

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

                if (n1Str != null && n1Str.trim().length() > 0 && n2Str != null && n2Str.trim().length() > 0) {
                    double peopleNumberD = Double.parseDouble(n2Str);
                    double priceNumber = Double.parseDouble(n1Str);
                    DecimalFormat df = new DecimalFormat("#.00");

                    sendText = "R$ " + df.format(priceNumber / peopleNumberD);
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

                if (n1Str != null && n1Str.trim().length() > 0 && n2Str != null && n2Str.trim().length() > 0) {
                    double peopleNumberD = Double.parseDouble(n2Str);
                    double priceNumber = Double.parseDouble(n1Str);
                    DecimalFormat df = new DecimalFormat("#.00");

                    sendText = "R$ " + df.format(priceNumber / peopleNumberD);
                    result.setText(sendText);
                } else {
                    String defaultText = "R$ 0,00";
                    result.setText(defaultText);
                }
            }
        });

        // Text to Speech checagem para inicializar TTS
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, 1122);

        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tts != null) {
                    String speakText = "O valor que deve ser pago por cada pessoa é de "
                            + sendText.replaceAll("[R$]","").trim()
                            + " reais";
                    tts.speak(speakText, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        // Ação do botão de compartilhamento
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareStr = result.getText().toString();
                i.putExtra(Intent.EXTRA_TEXT, shareStr);
                Intent openInChooser = Intent.createChooser(i, "Compartilhar");
                startActivity(openInChooser);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1122) {
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                tts = new TextToSpeech(this, this);
            } else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    @Override
    public void onInit(int status) {
        // Checar a inicialização do TTS
        if(status == TextToSpeech.SUCCESS) {
            Toast.makeText(this, "TTS Ativado", Toast.LENGTH_LONG).show();
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "TSS não foi habilitado com sucesso", Toast.LENGTH_LONG).show();
        }
    }
}