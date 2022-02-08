package com.example.s528755.smartcashmanager;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Add_Expense extends Fragment {
    TextView hsrc,hamt,htype;
    EditText source,amount,type;
    TextView date,time,balance,expenseimagename;

    Button save,clear,image;
    int bal=0;
    SharedPreferences sp;
    String uid;
    SimpleDateFormat sdfd=new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat sdft=new SimpleDateFormat("HH:mm");
    SimpleDateFormat sdfm=new SimpleDateFormat("MM");
    SimpleDateFormat sdfy=new SimpleDateFormat("yyyy");
    int tdmonth,tdyear;
    Date d;
    DatePickerDialog dated;
    TimePickerDialog timed;
    RelativeLayout ray;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_expense,container,false);
        sp=getActivity().getSharedPreferences("income_expense", Context.MODE_PRIVATE);
        uid=sp.getString("uid","");
        source= (EditText) v.findViewById(R.id.esrc);
        amount= (EditText) v.findViewById(R.id.eamt);
        expenseimagename = (TextView) v.findViewById(R.id.expenseimagename);
        image = (Button) v.findViewById(R.id.expensePhoto);
        type= (EditText) v.findViewById(R.id.etype);
        date= (TextView) v.findViewById(R.id.edate);
        time= (TextView) v.findViewById(R.id.etime);
        balance= (TextView) v.findViewById(R.id.ebal);

        save= (Button) v.findViewById(R.id.esave);
        clear= (Button) v.findViewById(R.id.eclear);
        ray= (RelativeLayout) v.findViewById(R.id.adderay);
        hsrc= (TextView) v.findViewById(R.id.esrc_h);
        hamt= (TextView) v.findViewById(R.id.eamt_h);
        htype= (TextView) v.findViewById(R.id.etype_h);

        d=new Date();
        date.setText(sdfd.format(d.getTime()));
        time.setText(sdft.format(d.getTime()));
        tdmonth=Integer.parseInt(sdfm.format(d.getTime()));
        tdyear=Integer.parseInt(sdfy.format(d.getTime()));
        getbal();

        source.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(source.getText().toString().compareTo("")!=0)
                {
                    hsrc.setVisibility(View.VISIBLE);
                }
                else
                {
                    hsrc.setVisibility(View.INVISIBLE);
                }
            }
        });


        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(amount.getText().toString().compareTo("")!=0)
                {
                    hamt.setVisibility(View.VISIBLE);
                }
                else
                {
                    hamt.setVisibility(View.INVISIBLE);
                }
            }
        });


        type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(type.getText().toString().compareTo("")!=0)
                {
                    htype.setVisibility(View.VISIBLE);
                }
                else
                {
                    htype.setVisibility(View.INVISIBLE);
                }
            }
        });

        clear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action=event.getAction();

                switch (action & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        clear.setBackgroundResource(R.drawable.btn_back);
                        clear.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:
                        clear.setBackgroundResource(R.drawable.edittext_back);
                        clear.setTextColor(getResources().getColor(R.color.darkviolet));
                        break;
                }
                return false;
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });





        image.setOnClickListener(new View.OnClickListener() {

            private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
            private Button btnSelect;
            private Button confirm;
            private Button cancel;
            private ImageView ivImage;
            private String userChoosenTask;
            private int mark = 0;

            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Take Photo", "Choose from Library",
                        "Cancel"};

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        boolean result = Utility.checkPermission(getActivity());

                        if (items[item].equals("Take Photo")) {
                            userChoosenTask = "Take Photo";
                            if (result) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, REQUEST_CAMERA);

                            }

                        } else if (items[item].equals("Choose from Library")) {
                            userChoosenTask = "Choose from Library";
                            if (result) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);//
                                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

                            }

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }


        });









        save.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action=event.getAction();

                switch (action & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        save.setBackgroundResource(R.drawable.btn_back);
                        save.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:
                        save.setBackgroundResource(R.drawable.edittext_back);
                        save.setTextColor(getResources().getColor(R.color.darkviolet));
                        break;
                }
                return false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(source.getText().toString().compareTo("")!=0 || amount.getText().toString().compareTo("")!=0 || type.getText().toString().compareTo("")!=0 || date.getText().toString().compareTo("")!=0 || time.getText().toString().compareTo("")!=0)
                {
                    if(source.getText().toString().compareTo("")!=0)
                    {
                        if(amount.getText().toString().compareTo("")!=0)
                        {
                            if(Integer.parseInt(amount.getText().toString())>0) {
                                if (type.getText().toString().compareTo("") != 0) {
                                    if (date.getText().toString().compareTo("") != 0) {
                                        if (time.getText().toString().compareTo("") != 0) {
                                            if (Integer.parseInt(amount.getText().toString()) > bal) {
                                                amount.setText("");
                                                amount.requestFocus();
                                                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                                                ad.setTitle("Amount Exceeded");
                                                ad.setMessage("The Amount entered is exceeding the Total Balance!");
                                                ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                ad.show();
                                            } else {
                                                try {
                                                    DB db=new DB(getActivity());
                                                    db.open();
                                                    String a=db.carryforward(uid);
                                                    db.close();

                                                    if(a.compareTo("done")==0) {

                                                        DB db1 = new DB(getActivity());
                                                        db1.open();
                                                        String ans = db1.addexpense(uid, source.getText().toString(), amount.getText().toString(), type.getText().toString(), date.getText().toString(), time.getText().toString());
                                                        db1.close();
                                                        if (ans.compareTo("true") == 0) {
                                                            clear();
                                                            Snackbar snack = Snackbar.make(v, "Expense Added", Snackbar.LENGTH_SHORT);
                                                            View vs = snack.getView();
                                                            TextView txt = (TextView) vs.findViewById(android.support.design.R.id.snackbar_text);
                                                            txt.setTextColor(Color.GREEN);
                                                            snack.show();
                                                        }
                                                    }
                                                }catch (Exception e)
                                                {
                                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                        } else {
                                            timed.setTitle("Select a Time");
                                            timed.show();
                                        }
                                    } else {
                                        dated.setTitle("Select a Date");
                                        dated.show();
                                    }
                                }
                                else
                                {
                                    type.setError("Enter Mode of Expense");
                                    type.requestFocus();
                                }
                            }
                            else
                            {
                                amount.setError("Amount should be greater then Zero | 0");
                                amount.requestFocus();
                            }
                        }
                        else
                        {
                            amount.setError("Enter Amount");
                            amount.requestFocus();
                        }
                    }
                    else
                    {
                        source.setError("Enter Source of Expense");
                        source.requestFocus();
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

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dated.show();
            }
        });



        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timed.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        dated = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String dd = sdfd.format(newDate.getTime());

                date.setText(dd);


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        Calendar Calendar_fortime = Calendar.getInstance();
        timed=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.setText(hourOfDay+":"+minute);
            }
        },Calendar_fortime.get(Calendar.HOUR_OF_DAY),Calendar_fortime.get(Calendar.MINUTE),false);

        return v;


    }


    public void clear()
    {
        source.requestFocus();
        source.setText("");
        amount.setText("");
        type.setText("");
        d=new Date();
        date.setText(sdfd.format(d.getTime()));
        time.setText(sdft.format(d.getTime()));
        tdmonth=Integer.parseInt(sdfm.format(d.getTime()));
        tdyear=Integer.parseInt(sdfy.format(d.getTime()));
        getbal();
    }

    public void getbal()
    {
        DB db=new DB(getActivity());
        db.open();
        String ans=db.getbal(uid);
        db.close();
        String s = "Remaining Balance"+"&#36;" + " "+ans;
        bal=Integer.parseInt(ans);
        balance.setText(Html.fromHtml(s));
    }
    @Override
    public  void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        expenseimagename.setVisibility(View.VISIBLE);
        expenseimagename.setText("20171201_1.."+".jpg");

    }
}
