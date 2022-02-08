package com.example.s528755.smartcashmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Expenses extends Fragment {

    ListView list;
    ArrayList<String> data;
    SharedPreferences sp;
    String uid;
    RelativeLayout ray;
    Spinner spin;
    String[] spindata=new String[]{"All","This month"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.expenses,container,false);
        sp=getActivity().getSharedPreferences("income_expense", Context.MODE_PRIVATE);
        uid=sp.getString("uid","");
        list= (ListView) v.findViewById(R.id.elist);
        ray= (RelativeLayout) v.findViewById(R.id.incomeray);
        spin = (Spinner) v.findViewById(R.id.expense_spin);
        ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity(), R.layout.spin_item, R.id.spin_txt, spindata);
        spin.setAdapter(a);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    getdata("all");
                }
                else if(position==1)
                {
                    getdata("month");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    public void getdata(String src)
    {
        DB db=new DB(getActivity());
        db.open();
        String ans=db.getexpense(uid,src);
        db.close();
        if(ans.compareTo("no")==0)
        {
            list.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No Expenses have Added!", Toast.LENGTH_SHORT).show();
//            Snackbar snack=Snackbar.make(ray,"No Incomes have Added!",Snackbar.LENGTH_SHORT);
//            View vs=snack.getView();
//            TextView txt= (TextView) vs.findViewById(android.support.design.R.id.snackbar_text);
//            txt.setTextColor(Color.GREEN);
//            snack.show();
        }
        else
        {
            if(ans.contains("*"))
            {
                data=new ArrayList<String>();
                String temp[]=ans.split("\\#");
                for(int i=0;i<temp.length;i++)
                {
                    data.add(temp[i]);
                }
                list.setVisibility(View.VISIBLE);
                Adpater adapt=new Adpater(getActivity(),data);
                list.setAdapter(adapt);
            }
        }

    }

    public class Adpater extends ArrayAdapter<String>
    {
        Context con;
        ArrayList<String> dataset;
        public Adpater(Context context,ArrayList<String> data) {
            super(context, R.layout.income_listitem,data);
            con=context;
            dataset=data;
        }

        @NonNull
        @Override
        public View getView(int position, View v, ViewGroup parent) {

            //Method one
//            LayoutInflater li=LayoutInflater.from(con);
//            View v=li.inflate(R.layout.income_listitem,null,true);

            //method two
            if (v == null) {
                LayoutInflater mInflater = (LayoutInflater) con.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                v = mInflater.inflate(R.layout.income_listitem, null);
            }
            String temp[]=dataset.get(position).split("\\*");
            TextView name,amt,type,datetime;
            name= (TextView) v.findViewById(R.id.il_name);
            amt= (TextView) v.findViewById(R.id.il_amt);
            type= (TextView) v.findViewById(R.id.il_type);
            datetime= (TextView) v.findViewById(R.id.il_dt);

            name.setText(temp[1]);
            String s1 = "&#36;" + " "+temp[2];
            amt.setText(Html.fromHtml(s1));
            String s2 = "<b>Expense Mode: </b>"+temp[3];
            type.setText(Html.fromHtml(s2));
            datetime.setText(temp[4] +"  "+temp[5]);
            return v;


        }
    }
}
