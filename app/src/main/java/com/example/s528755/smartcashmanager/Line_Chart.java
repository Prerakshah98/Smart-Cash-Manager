package com.example.s528755.smartcashmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Line_Chart extends Fragment {
    CoordinatorLayout ray1;
    Button selectdate;
    RelativeLayout ray;
    String from="",to="";
    SharedPreferences sp;
    String uid;
    LineChart Linechart;
    List<Entry> incomes;
    List<Entry> expenses;
    String[] dates;
    TextView txt;
    ImageView edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.reports,container,false);
        sp=getActivity().getSharedPreferences("income_expense", Context.MODE_PRIVATE);
        uid=sp.getString("uid","");
        ray= (RelativeLayout) v.findViewById(R.id.cray);
        ray1= (CoordinatorLayout) v.findViewById(R.id.chartray);
        ray.setVisibility(View.VISIBLE);
        txt= (TextView) v.findViewById(R.id.htxt);
        edit= (ImageView) v.findViewById(R.id.hedit);
        selectdate= (Button) v.findViewById(R.id.selectdate);
        selectdate.setVisibility(View.GONE);
        Linechart = (LineChart) v.findViewById(R.id.linechart);
        Linechart.getDescription().setText("Income - Expense Chart");
        Linechart.getDescription().setTextSize(8);

        from=getArguments().getString("from","");
        to=getArguments().getString("to","");

        String s=from +"<b> - </b>"+to;
        txt.setText(Html.fromHtml(s));

        getdata();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putString("from",from);
                b.putString("to",to);
                Reports iec=new Reports();
                iec.setArguments(b);
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.frameid,iec).commit();
            }
        });

        edit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "Change Dates", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return v;
    }

    public void getdata()
    {
        try {
            DB db = new DB(getActivity());
            db.open();
            String ans = db.getgraphdata_income(uid,from,to);
////            Toast.makeText(getActivity(), ans, Toast.LENGTH_SHORT).show();
            String ans1 = db.getgraphdata_expense(uid,from,to);
////            Toast.makeText(getActivity(), ans1, Toast.LENGTH_SHORT).show();
            db.close();

            if(ans.compareTo("no")!=0) {
                String temp[] = ans.split("\\#");
                for (int i = 0; i < temp.length; i++) {
                    String temp1[] = temp[i].split("\\*");
                    DB db1 = new DB(getActivity());
                    db1.open();
                    db1.add_temp(uid, temp1[2], temp1[1], "0", temp1[0]);
                    db1.close();
                }
            }
//
            if(ans1.compareTo("no")!=0) {
                String tempp[] = ans1.split("\\#");
                for (int i = 0; i < tempp.length; i++)
                {
                    String tempp1[] = tempp[i].split("\\*");
                    DB db1 = new DB(getActivity());
                    db1.open();
                    db1.add_temp(uid, tempp1[2], "0", tempp1[1], tempp1[0]);
                    db1.close();
                }
            }

            if(ans1.compareTo("no")!=0 || ans.compareTo("no")!=0)
            {
                DB db2 = new DB(getActivity());
                db2.open();
                String res = db2.gettemp(uid);
                db2.close();
                if(res.compareTo("no")!=0)
                {
                    String t[] = res.split("\\#");
                    incomes = new ArrayList<Entry>();
                    expenses = new ArrayList<Entry>();
                    dates = new String[t.length];
                    for (int i = 0; i < t.length; i++)
                    {
                        String s[] = t[i].split("\\*");
                        dates[i]=s[0];
                        incomes.add(new Entry(Float.parseFloat(i + "f"), Float.parseFloat(s[1])));
                        expenses.add(new Entry(Float.parseFloat(i + "f"), Float.parseFloat(s[2])));
////                        Toast.makeText(getActivity(),dates[i]+"\n"+incomes.get(i)+"\n"+expenses.get(i), Toast.LENGTH_SHORT).show();
                    }
                    drawline();
                }
                else
                {
                    Linechart.setData(null);
                    Linechart.invalidate();
                    Snackbar snack=Snackbar.make(ray1,"No Data to Show for the Above Dates!",Snackbar.LENGTH_SHORT);
                    View vs=snack.getView();
                    TextView txt= (TextView) vs.findViewById(android.support.design.R.id.snackbar_text);
                    txt.setTextColor(Color.RED);
                    snack.show();
                }
            }
            else
            {
                Linechart.setData(null);
                Linechart.invalidate();
                Snackbar snack=Snackbar.make(ray1,"No Data to Show for the Above Dates!",Snackbar.LENGTH_SHORT);
                View vs=snack.getView();
                TextView txt= (TextView) vs.findViewById(android.support.design.R.id.snackbar_text);
                txt.setTextColor(Color.RED);
                snack.show();
            }

        }catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void drawline()
    {
        Linechart.clear();
        XAxis x = Linechart.getXAxis();
        x.setValueFormatter(null);
        try {
            LineDataSet ds1 = null;
            LineDataSet ds2 = null;
            if (incomes.size() > 0) {
                ds1 = new LineDataSet(incomes, "Incomes");
                ds1.setCircleColor(getResources().getColor(R.color.incomes));
                ds1.setLineWidth(3);
                ds1.setValueTextSize(10);
                ds1.setCircleRadius(5);
                ds1.setColor(getResources().getColor(R.color.incomes));
            }

            if (expenses.size() > 0) {
                ds2 = new LineDataSet(expenses, "Expenses");
                ds2.setCircleColor(getResources().getColor(R.color.expenses));
                ds2.setLineWidth(3);
                ds2.setValueTextSize(10);
                ds2.setCircleRadius(5);
                ds2.setColor(getResources().getColor(R.color.expenses));
            }

            List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            if (incomes.size() > 0) {
                dataSets.add(ds1);
            }
            if (expenses.size() > 0) {
                dataSets.add(ds2);
            }

            LineData ld = new LineData(dataSets);

            IAxisValueFormatter formatter=null;
            if(incomes.size()>1)
            {
                formatter = new IAxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        int i = (int) value;
                        return dates[i];
                    }
                    @Override
                    public int getDecimalDigits() {
                        return 0;
                    }
                };
            }
            else
            {
                final String temp[]=new String[]{dates[0]," "};
                formatter = new IAxisValueFormatter() {

                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        if(value==-1.0)
                        {
                            return " ";
                        }
                        else
                        {
                            int i = (int) value;
                            return temp[i];
                        }
                    }

                    @Override
                    public int getDecimalDigits() {
                        return 0;
                    }
                };
            }

            XAxis xAxis = Linechart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setTextColor(getResources().getColor(R.color.darkviolet));
            xAxis.setGridColor(Color.TRANSPARENT);
            xAxis.setValueFormatter(formatter);
            xAxis.setAvoidFirstLastClipping(true);

            YAxis left = Linechart.getAxisLeft();
            left.setAxisMinimum(0);
            YAxis right = Linechart.getAxisRight();
            right.setAxisMinimum(0);

            Linechart.getXAxis().mLabelWidth = 1;
            Linechart.setData(ld);
            Linechart.invalidate();
        }
        catch (Exception e)
        {
//            Toast.makeText(getActivity(), "drawline-"+"\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
