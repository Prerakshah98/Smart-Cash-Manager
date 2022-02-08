package com.example.s528755.smartcashmanager;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    TextView nameh,conth,passh,cpassh;
    EditText name,cont,pass,cpass;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name= (EditText) findViewById(R.id.name);
        cont= (EditText) findViewById(R.id.cont);
        pass= (EditText) findViewById(R.id.pass);
        cpass= (EditText) findViewById(R.id.cpass);
        submit= (Button) findViewById(R.id.register);
        nameh= (TextView) findViewById(R.id.name_h);
        conth= (TextView) findViewById(R.id.cont_h);
        passh= (TextView) findViewById(R.id.pass_h);
        cpassh= (TextView) findViewById(R.id.cpass_h);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Register");


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(name.getText().toString().compareTo("")!=0)
                {
                    nameh.setVisibility(View.VISIBLE);
                }
                else
                {
                    nameh.setVisibility(View.INVISIBLE);
                }
            }
        });

        cont.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(cont.getText().toString().compareTo("")!=0)
                {
                    conth.setVisibility(View.VISIBLE);
                }
                else
                {
                    conth.setVisibility(View.INVISIBLE);
                }
            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(pass.getText().toString().compareTo("")!=0)
                {
                    passh.setVisibility(View.VISIBLE);
                }
                else
                {
                    passh.setVisibility(View.INVISIBLE);
                }
            }
        });

        cpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(cpass.getText().toString().compareTo("")!=0)
                {
                    cpassh.setVisibility(View.VISIBLE);
                }
                else
                {
                    cpassh.setVisibility(View.INVISIBLE);
                }
            }
        });


        submit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action=event.getAction();

                switch (action & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        submit.setBackgroundResource(R.drawable.btn_back);
                        submit.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:
                        submit.setBackgroundResource(R.drawable.edittext_back);
                        submit.setTextColor(getResources().getColor(R.color.darkviolet));
                        break;
                }
                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().compareTo("")!=0 || cont.getText().toString().compareTo("")!=0 || pass.getText().toString().compareTo("")!=0 || cpass.getText().toString().compareTo("")!=0)
                {
                    if(name.getText().toString().compareTo("")!=0)
                    {
                        if(cont.getText().toString().compareTo("")!=0)
                        {
                            if(pass.getText().toString().compareTo("")!=0)
                            {
                                if(cpass.getText().toString().compareTo("")!=0)
                                {
                                    if(cpass.getText().toString().compareTo(pass.getText().toString())==0)
                                    {
                                        DB db=new DB(Register.this);
                                        db.open();
                                        String ans=db.register(name.getText().toString(),cont.getText().toString(),pass.getText().toString());
                                        db.close();
                                        if(ans.contains("*"))
                                        {
                                            String str[]=ans.split("\\*");
                                            Snackbar snack=Snackbar.make(v,"User Registered!",Snackbar.LENGTH_SHORT);
                                            View vs=snack.getView();
                                            TextView txt= (TextView) vs.findViewById(android.support.design.R.id.snackbar_text);
                                            txt.setTextColor(Color.GREEN);
                                            snack.show();

                                            AlertDialog.Builder ad=new AlertDialog.Builder(Register.this);
                                            ad.setTitle("User ID");
                                            String s="Your Id : <font color='#FF000D'>"+str[0]+"</font>";
                                            ad.setMessage(Html.fromHtml(s));
                                            ad.setCancelable(false);
                                            ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    finish();
                                                }
                                            });
                                            ad.show();
                                        }
                                    }
                                    else
                                    {
                                        pass.setText("");
                                        cpass.setText("");
                                        pass.requestFocus();
                                        Snackbar snack=Snackbar.make(v,"Passwords dont match!",Snackbar.LENGTH_SHORT);
                                        View vs=snack.getView();
                                        TextView txt= (TextView) vs.findViewById(android.support.design.R.id.snackbar_text);
                                        txt.setTextColor(Color.RED);
                                        snack.show();
                                    }
                                }
                                else
                                {
                                    cpass.setError("Confir your Password");
                                    cpass.requestFocus();
                                }
                            }
                            else
                            {
                                pass.setError("Enter Password");
                                pass.requestFocus();
                            }
                        }
                        else
                        {
                            cont.setError("Enter Mobile Number");
                            cont.requestFocus();
                        }
                    }
                    else
                    {
                        name.setError("Enter Name");
                        name.requestFocus();
                    }
                }
                else
                {
                    Snackbar snack=Snackbar.make(v,"Fill all the details!",Snackbar.LENGTH_SHORT);
                    View vs=snack.getView();
                    TextView txt= (TextView) vs.findViewById(android.support.design.R.id.snackbar_text);
                    txt.setTextColor(Color.RED);
                    snack.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
