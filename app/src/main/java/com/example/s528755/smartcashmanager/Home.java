package com.example.s528755.smartcashmanager;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Home extends Fragment {

    TextView savings,incomes,expenses;
    SharedPreferences sp;
    String uid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.home,container,false);
        sp=getActivity().getSharedPreferences("income_expense", Context.MODE_PRIVATE);
        uid=sp.getString("uid","");
        savings= (TextView) v.findViewById(R.id.savings);
        incomes= (TextView) v.findViewById(R.id.incomes);
        expenses= (TextView) v.findViewById(R.id.expenses);
        getdata();
        return v;
    }

    public void getdata()
    {

        DB db=new DB(getActivity());
        db.open();
        String a=db.carryforward(uid);
        db.close();

        if(a.compareTo("done")==0)
        {
            DB db1=new DB(getActivity());
            db1.open();
            String s1 = "&#36;" + " "+db1.getbal(uid);
            savings.setText(Html.fromHtml(s1));
            String inc=db1.getincome_bal(uid);
            String exp=db1.getexpense_bal(uid);
            db1.close();
            int totincome=0,totexpense=0;

            if(inc.compareTo("no")!=0 && inc.contains("*"))
            {
                String temp[] = inc.split("\\*");
                for (int i = 0; i < temp.length; i++) {
                    totincome += Integer.parseInt(temp[i]);
                }
            }
            String s2 = "&#36;" + " "+totincome;
            incomes.setText(Html.fromHtml(s2));

            if(exp.compareTo("no")!=0 && inc.contains("*"))
            {
                String temp1[] = exp.split("\\*");
                for (int i = 0; i < temp1.length; i++) {
                    totexpense += Integer.parseInt(temp1[i]);
                }
            }
            String s3 = "&#36;" + " "+totexpense;
            expenses.setText(Html.fromHtml(s3));
        }
    }
}
