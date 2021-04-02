package com.example.callme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {
    Button call_button;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.3:8000");
            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.d("TAG", "Socket Connected!");
                }

            });
        } catch (URISyntaxException e) {
            Log.e("Error", e.toString());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSocket.connect();
        call_button  = findViewById(R.id.buttonCall);
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_button.setEnabled(false);
                call_button.setText("Calling...");
                call();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        call_button.setEnabled(true);
                        call_button.setText("Call Logesh");

                    }
                }, 25000);// set time as per your requirement
            }

        });
    }

    public Socket getSocket() {
        return mSocket;
    }

    public  void  call(){
        mSocket.emit("message","call");
        Log.i("call","sending call");
    }






}