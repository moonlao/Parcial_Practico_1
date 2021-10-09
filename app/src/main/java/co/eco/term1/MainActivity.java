package co.eco.term1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    //private ConstraintLayout main;
    private Button greenBtn, yellowBtn, redBtn, viewBtn, okBtn;
    private EditText posX, posY, remTxt;

    private boolean relevance;
    private String ok, tone;

    private BufferedWriter bw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        relevance = false;

        greenBtn = findViewById(R.id.greenBtn);
        yellowBtn = findViewById(R.id.yellowBtn);
        redBtn = findViewById(R.id.redBtn);
        viewBtn = findViewById(R.id.viewBtn);
        okBtn = findViewById(R.id.okBtn);
        posX = findViewById(R.id.posX);
        posY = findViewById(R.id.posY);
        remTxt = findViewById(R.id.remTxt);




        new Thread(

                () -> {

                    try {
                        Socket socket = new Socket("127.0.0.1", 5000);
                        Toast.makeText(this,"se conectó",Toast.LENGTH_SHORT).show();
                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bw = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

        ).start();

        low();
        medium();
        high();

        view();
        next();
    }


    private void low(){
        greenBtn.setOnClickListener(
                view -> {
                    relevance = true;
                    tone = "green"; //posible error en tag
                    Toast.makeText(this,"green",Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void medium(){
        yellowBtn.setOnClickListener(
                view -> {
                    relevance = true;
                    tone = "yellow"; //posible error en tag
                    Toast.makeText(this,"yellow",Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void high(){
        redBtn.setOnClickListener(
                view -> {
                    relevance = true;
                    tone = "red"; //posible error en tag
                    Toast.makeText(this,"red",Toast.LENGTH_SHORT).show();
                }
        );
    }


    private void view() {
        viewBtn.setOnClickListener(
                v -> {

                    ok = "no";
                    String posx, posy, r, i;
                    posx = posX.getText().toString();
                    posy = posY.getText().toString();
                    r = remTxt.getText().toString();

                    if (posx.isEmpty() || posy.isEmpty() || r.isEmpty()) {

                        Toast.makeText(this, "Digite todos los datos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (relevance == false) {
                        Toast.makeText(this, "Escoja un color", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    i = tone + "," + posx + "," + posy + "," + r + "," + ok;

                    Log.e("Recordatorio= ", i);
                    Gson gson = new Gson();


                    String reJson = gson.toJson(i);

                    new Thread(

                            () -> {
                                try {
                                    bw.write(reJson + "\n");
                                    bw.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    ).start();
                }
        );
    }


    private void next() {

        okBtn.setOnClickListener(
                v -> {

                    ok = "si";
                    String posx, posy, r, i;
                    posx = posX.getText().toString();
                    posy = posY.getText().toString();
                    r = remTxt.getText().toString();

                    if (posx.isEmpty() || posy.isEmpty() || r.isEmpty()) {
                        Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (relevance == false) {
                        Toast.makeText(this, "Seleccione relevancia", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    i = tone + "," + posx + "," + posy + "," + r + "," + ok;

                    Log.e("Recordatorio = ", i);
                    Gson gson = new Gson();

                    String reJson = gson.toJson(i);


                    new Thread(

                            () -> {
                                try {
                                    bw.write(reJson + "\n");
                                    bw.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(
                                        () -> {

                                            posX.setText("");
                                            posY.setText("");
                                            remTxt.setText("");
                                            tone = null;
                                        }
                                );

                            }
                    ).start();
                }
        );

    }

}
