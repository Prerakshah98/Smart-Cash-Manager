package com.example.s528755.smartcashmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Reports extends Fragment{
    Button selectdate;
    RelativeLayout ray;
    String from="",to="";
    String pointer="";
    TextView fromdate,todate;
    SharedPreferences sp;
    String uid;
    SimpleDateFormat sdfd=new SimpleDateFormat("yyyy/MM/dd");
    Date d;
    DatePickerDialog dated;
    LineChart Linechart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.reports,container,false);
        sp=getActivity().getSharedPreferences("income_expense", Context.MODE_PRIVATE);
        uid=sp.getString("uid","");
        ray= (RelativeLayout) v.findViewById(R.id.cray);
        selectdate= (Button) v.findViewById(R.id.selectdate);
        Linechart = (LineChart) v.findViewById(R.id.linechart);
        Linechart.getDescription().setText("Reports");
        Linechart.getDescription().setTextSize(8);
        d=new Date();
        to=sdfd.format(d.getTime());
        String str[]=to.split("/");
        from=str[0]+"/"+str[1]+"/"+"01";

        datedailog();

        Calendar newCalendar = Calendar.getInstance();
        dated = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String dd = sdfd.format(newDate.getTime());
                if(pointer.compareTo("from")==0)
                {
                    from=dd;
                    fromdate.setText(dd);
                }
                else if(pointer.compareTo("to")==0)
                {
                    to=dd;
                    todate.setText(dd);
                }

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datedailog();
            }
        });

        selectdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action=event.getAction();

                switch (action & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        selectdate.setBackgroundResource(R.drawable.btn_back);
                        selectdate.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:
                        selectdate.setBackgroundResource(R.drawable.edittext_back);
                        selectdate.setTextColor(getResources().getColor(R.color.darkviolet));
                        break;
                }
                return false;
            }
        });

        return v;

    }

    public void datedailog()
    {
        final Dialog d=new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.date_dialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        fromdate= (TextView) d.findViewById(R.id.from);
        todate= (TextView) d.findViewById(R.id.to);
        try {
            from=getArguments().getString("from","");
            to=getArguments().getString("to","");
        }catch (Exception e){}

        fromdate.setText(from);
        todate.setText(to);

        final Button submit= (Button) d.findViewById(R.id.date_submit);

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointer="from";
                dated.setTitle("From Date");
                dated.show();
            }
        });

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointer="to";
                dated.setTitle("To Date");
                dated.show();
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
                try {
                    Date fromd=sdfd.parse(from);
                    Date tod=sdfd.parse(to);
                    if(!fromd.after(tod))
                    {
                        Bundle b=new Bundle();
                        b.putString("from",from);
                        b.putString("to",to);
                        Line_Chart lc=new Line_Chart();
                        lc.setArguments(b);
                        FragmentManager fm=getFragmentManager();
                        fm.beginTransaction().replace(R.id.frameid,lc).commit();
                        d.cancel();
                    }
                    else {

                        Snackbar snack = Snackbar.make(v, "To - date should be greater than From - date", Snackbar.LENGTH_SHORT);
                        View vs = snack.getView();
                        TextView txt = (TextView) vs.findViewById(android.support.design.R.id.snackbar_text);
                        txt.setTextColor(Color.RED);
                        snack.show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        d.show();
    }
}
