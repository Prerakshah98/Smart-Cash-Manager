package com.example.s528755.smartcashmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Login extends AppCompatActivity
{
    EditText id,pass;
    Button signin,signup;
    RelativeLayout ray;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences("income_expense", Context.MODE_PRIVATE);
        uid=sp.getString("uid","");
        if(uid.compareTo("")!=0)
        {
            Intent i=new Intent(Login.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            setContentView(R.layout.login);
            id = (EditText) findViewById(R.id.uid);
            pass = (EditText) findViewById(R.id.pass);
            signin = (Button) findViewById(R.id.signin);
            signup = (Button) findViewById(R.id.signup);
            ray = (RelativeLayout) findViewById(R.id.ray);
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id.getText().toString().compareTo("") != 0 || pass.getText().toString().compareTo("") != 0) {
                        if (id.getText().toString().compareTo("") != 0) {
                            if (pass.getText().toString().compareTo("") != 0) {
                                DB db = new DB(Login.this);
                                db.open();
                                String ans = db.login(id.getText().toString(), pass.getText().toString());
                                db.close();
                                if (ans.compareTo("true") == 0) {
                                    editor = sp.edit();
                                    editor.putString("uid", id.getText().toString());
                                    editor.commit();
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (ans.compareTo("false") == 0) {
                                    id.setText("");
                                    pass.setText("");
                                    id.requestFocus();
                                    Snackbar snack = Snackbar.make(v, "Wrong Credentials!", Snackbar.LENGTH_SHORT);
                                    View view = snack.getView();
                                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                                    tv.setTextColor(Color.RED);
                                    snack.show();
                                }
                            } else {
                                pass.setError("Enter Password");
                                pass.requestFocus();
                            }
                        } else {
                            id.setError("Enter UserId");
                            id.requestFocus();
                        }
                    } else {
                        id.requestFocus();
                        Snackbar snack = Snackbar.make(v, "Enter UserId & Password to Proceed!", Snackbar.LENGTH_SHORT);
                        View view = snack.getView();
                        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.RED);
                        snack.show();
                    }
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Login.this, Register.class);
                    startActivity(i);
                }
            });

            signup.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int action=event.getAction();

                    switch (action & MotionEvent.ACTION_MASK)
                    {
                        case MotionEvent.ACTION_UP:
                            signup.setBackgroundResource(R.drawable.btn_back);
                            signup.setTextColor(Color.WHITE);
                            break;
                        case MotionEvent.ACTION_DOWN:
                            signup.setBackgroundResource(R.drawable.edittext_back);
                            signup.setTextColor(getResources().getColor(R.color.darkviolet));
                            break;
                    }
                    return false;
                }
            });

            signin.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int action=event.getAction();

                    switch (action & MotionEvent.ACTION_MASK)
                    {
                        case MotionEvent.ACTION_UP:
                            signin.setBackgroundResource(R.drawable.btn_back);
                            signin.setTextColor(Color.WHITE);
                            break;
                        case MotionEvent.ACTION_DOWN:
                            signin.setBackgroundResource(R.drawable.edittext_back);
                            signin.setTextColor(getResources().getColor(R.color.darkviolet));
                            break;
                    }
                    return false;
                }
            });
        }
    }

}
