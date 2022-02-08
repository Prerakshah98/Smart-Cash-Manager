package com.example.s528755.smartcashmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DB {

    private static final String DBNAME="incom_expense_tracker";
    private static final String TBUSER="usertb";
    private static final String TBINCOME="incometb";
    private static final String TBEXPENSE="expensetb";
    private static final String TBSEXPENSE="sexpensetb";
    private static final String TBBALANCE="balancetb";
    private static final String TBTEMP="temp_arrange";
    private static final int DBVERSION=1;

    private static final String UID="uid";
    private static final String UNAME="uname";
    private static final String UCONT="ucont";
    private static final String UPASS="upass";

    private static final String IID="iid";
    private static final String ISRC="isrc";
    private static final String IAMT="iamt";
    private static final String ITYPE="itype";
    private static final String IDATE="idate";
    private static final String ITIME="itime";

    private static final String EID="eid";
    private static final String ESRC="esrc";
    private static final String EAMT="eamt";
    private static final String ETYPE="etype";
    private static final String EDATE="edate";
    private static final String ETIME="etime";

    private static final String SID="sid";
    private static final String SSRC="ssrc";
    private static final String SAMT="samt";
    private static final String STYPE="stype";
    private static final String SDATE="sdate";
    private static final String STIME="stime";
    private static final String SSTATUS="sstatus";

    private static final String BAL="bal";
    private static final String BM="bal_month";
    private static final String BY="bal_year";

    private static final String TDATE="tdate";
    private static final String TINCOME="tincome";
    private static final String TEXPENSE="texpense";
    private static final String TSOURCE="tsource";


    SQLiteDatabase sqldb;
    dbhelper dbh;
    Context con;

    SimpleDateFormat sdfm=new SimpleDateFormat("MM");
    SimpleDateFormat sdfy=new SimpleDateFormat("yyyy");
    SimpleDateFormat sdfd=new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat sdft=new SimpleDateFormat("HH:mm");
    Date d;

    public class dbhelper extends SQLiteOpenHelper
    {

        public dbhelper(Context context) {
            super(context, DBNAME, null, DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+TBUSER+" (" + UID + " TEXT NOT NULL , " + UNAME + " TEXT NOT NULL ," +
                    UCONT+" TEXT NOT NULL, " + UPASS + " TEXT NOT NULL);");

            db.execSQL("create table "+TBINCOME+" (" + IID + " TEXT NOT NULL , " + UID + " TEXT NOT NULL ," +
                    ISRC + " TEXT NOT NULL , " + IAMT + " TEXT NOT NULL ," +
                    ITYPE+" TEXT NOT NULL, " + IDATE + " TEXT NOT NULL, " + ITIME + " TEXT NOT NULL);");

            db.execSQL("create table "+TBEXPENSE+" (" + EID + " TEXT NOT NULL , " + UID + " TEXT NOT NULL ," +
                    ESRC + " TEXT NOT NULL , " + EAMT + " TEXT NOT NULL ," +
                    ETYPE+" TEXT NOT NULL, " + EDATE + " TEXT NOT NULL, " + ETIME + " TEXT NOT NULL);");

            db.execSQL("create table "+TBSEXPENSE+" (" + SID + " TEXT NOT NULL , " + UID + " TEXT NOT NULL ," +
                    SSRC + " TEXT NOT NULL , " + SAMT + " TEXT NOT NULL ," +
                    STYPE+" TEXT NOT NULL, " + SDATE + " TEXT NOT NULL, " + STIME + " TEXT NOT NULL, " + SSTATUS + " TEXT NOT NULL);");

            db.execSQL("create table "+TBBALANCE+" (" + UID + " TEXT NOT NULL , " + BAL + " TEXT NOT NULL, " + BM + " TEXT NOT NULL ," +
                    BY+" TEXT NOT NULL);");

            db.execSQL("create table "+TBTEMP+" (" + UID + " TEXT NOT NULL , " + TDATE + " TEXT NOT NULL, " + TINCOME + " TEXT NOT NULL ," +
                    TEXPENSE+" TEXT NOT NULL, " + TSOURCE + " TEXT NOT NULL);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TBUSER + "");
            db.execSQL("DROP TABLE IF EXISTS " + TBINCOME + "");
            db.execSQL("DROP TABLE IF EXISTS " + TBEXPENSE + "");
            db.execSQL("DROP TABLE IF EXISTS " + TBSEXPENSE + "");
            db.execSQL("DROP TABLE IF EXISTS " + TBBALANCE + "");
            db.execSQL("DROP TABLE IF EXISTS " + TBTEMP + "");
        }
    }

    public DB(Context c)
    {
        con=c;
    }

    public DB open()
    {
        dbh=new dbhelper(con);
        sqldb=dbh.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbh.close();
    }

    public String uid()
    {
        String ans="1000";
        Cursor c=sqldb.query(TBUSER,new String[]{UID},null,null,null,null,UID + " DESC");
        if(c.getCount()>0)
        {
            c.moveToFirst();
            int i=Integer.parseInt(c.getString(0))+1;
            ans=""+i;
        }
        return ans;
    }

    public String register(String name,String mobile,String pass)
    {
        String ans="false";
        String id=uid();
        ContentValues cv=new ContentValues();
        cv.put(UID,id);
        cv.put(UNAME,name);
        cv.put(UCONT,mobile);
        cv.put(UPASS,pass);
        sqldb.insert(TBUSER,null,cv);
        setdefbal(id);
        ans=id+"*true";
        return ans;
    }

    public String login(String user,String pass)
    {
        String ans="false";
        Cursor c=sqldb.query(TBUSER,null,UID +" = '" + user + "' AND " + UPASS + " = '" + pass + "'",null,null,null,null);
        if(c.getCount()>0)
        {
            ans="true";
        }
        return ans;
    }

    public void setdefbal(String uid)
    {
        d=new Date();
        String m=sdfm.format(d.getTime());
        String y=sdfy.format(d.getTime());
        ContentValues cv=new ContentValues();
        cv.put(UID,uid);
        cv.put(BAL,"0");
        cv.put(BM,m);
        cv.put(BY,y);
        sqldb.insert(TBBALANCE,null,cv);
    }

    public String Iid()
    {
        String ans="10";
        Cursor c=sqldb.query(TBINCOME,new String[]{IID},null,null,null,null,IID + " DESC");
        if(c.getCount()>0)
        {
            c.moveToFirst();
            int i=Integer.parseInt(c.getString(0))+1;
            ans=""+i;
        }
        return ans;
    }

    public String addincome(String uid,String src, String amt, String type, String date, String time)
    {
        String ans = "false";
        try{
            String a=updatebal(uid,amt,"income");
            if(a.compareTo("done")==0) {
                String id = Iid();
                ContentValues cv = new ContentValues();
                cv.put(IID, id);
                cv.put(UID, uid);
                cv.put(ISRC, src);
                cv.put(IAMT, amt);
                cv.put(ITYPE, type);
                cv.put(IDATE, date);
                cv.put(ITIME, time);
                sqldb.insert(TBINCOME, null, cv);
                ans = "true";
            }
        }
        catch (Exception e)
        {
            Toast.makeText(con,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return ans;
    }

    public String getincomes(String uid,String src)
    {
        d = new Date();
        int m = Integer.parseInt(sdfm.format(d.getTime()));
        int y = Integer.parseInt(sdfy.format(d.getTime()));
        String ans="false";
        Cursor c=sqldb.query(TBINCOME,new String[]{IID,ISRC,IAMT,ITYPE,IDATE,ITIME},UID +" = '" + uid + "'",null,null,null,IDATE +" Desc");
        if(c.getCount()>0)
        {
            c.moveToFirst();
            do
            {
                if(src.compareTo("all")==0)
                {
                    ans += c.getString(0) + "*" + c.getString(1) + "*" + c.getString(2) + "*" + c.getString(3) + "*" + c.getString(4) + "*" + c.getString(5) + "#";
                }
                else
                {
                    String temp[]=c.getString(4).split("/");
                    int by=Integer.parseInt(temp[0]);
                    int bm=Integer.parseInt(temp[1]);

                    if(m==bm && y==by)
                    {
                        ans += c.getString(0) + "*" + c.getString(1) + "*" + c.getString(2) + "*" + c.getString(3) + "*" + c.getString(4) + "*" + c.getString(5) + "#";
                    }

                }
            }
            while (c.moveToNext());
        }
        else
        {
            ans="no";
        }
        return ans;
    }

    public String getbal(String uid)
    {
        String ans="";
        Cursor c=sqldb.query(TBBALANCE,new String[]{BAL},UID +" = '" + uid + "'",null,null,null,null);
        if(c.getCount()>0)
        {
            c.moveToFirst();
            ans=c.getString(0);
        }
        return ans;
    }

    public String updatebal(String uid,String newbal,String src)
    {
        try
        {
            int bal = Integer.parseInt(getbal(uid));
            int tot = 0;
            d = new Date();
            String m = sdfm.format(d.getTime());
            String y = sdfy.format(d.getTime());
            if (src.compareTo("income") == 0) {
                tot = bal + Integer.parseInt(newbal);
            } else if (src.compareTo("expense") == 0) {
                tot = bal - Integer.parseInt(newbal);
            }
            ContentValues cv = new ContentValues();
            cv.put(BAL, "" + tot);
            cv.put(BM, m);
            cv.put(BY, y);
            sqldb.update(TBBALANCE, cv, UID + " = '" + uid + "'", null);
        }
        catch (Exception e)
        {
            Toast.makeText(con,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "done";
    }

    public String Eid()
    {
        String ans="10";
        Cursor c=sqldb.query(TBEXPENSE,new String[]{EID},null,null,null,null,EID + " DESC");
        if(c.getCount()>0)
        {
            c.moveToFirst();
            int i=Integer.parseInt(c.getString(0))+1;
            ans=""+i;
        }
        return ans;
    }

    public String addexpense(String uid,String src, String amt, String type, String date, String time)
    {
        updatebal(uid,amt,"expense");
        String ans="false";
        String id=Eid();
        ContentValues cv=new ContentValues();
        cv.put(EID,id);
        cv.put(UID,uid);
        cv.put(ESRC,src);
        cv.put(EAMT,amt);
        cv.put(ETYPE,type);
        cv.put(EDATE,date);
        cv.put(ETIME,time);
        sqldb.insert(TBEXPENSE,null,cv);
        ans="true";
        return ans;
    }

    public String getexpense(String uid,String src)
    {
        d = new Date();
        int m = Integer.parseInt(sdfm.format(d.getTime()));
        int y = Integer.parseInt(sdfy.format(d.getTime()));
        String ans="";
        Cursor c=null;
        c=sqldb.query(TBEXPENSE,new String[]{EID,ESRC,EAMT,ETYPE,EDATE,ETIME},UID + " = '" + uid + "'",null,null,null,EDATE +" Desc");
        if(c.getCount()>0)
        {
            c.moveToFirst();
            do
            {
                if(src.compareTo("all")==0)
                {
                    ans += c.getString(0) + "*" + c.getString(1) + "*" + c.getString(2) + "*" + c.getString(3) + "*" + c.getString(4) + "*" + c.getString(5) + "#";
                }
                else
                {
                    String temp[]=c.getString(4).split("/");
                    int by=Integer.parseInt(temp[0]);
                    int bm=Integer.parseInt(temp[1]);

                    if(m==bm && y==by)
                    {
                        ans += c.getString(0) + "*" + c.getString(1) + "*" + c.getString(2) + "*" + c.getString(3) + "*" + c.getString(4) + "*" + c.getString(5) + "#";
                    }

                }
            }
            while (c.moveToNext());
        }
        else
        {
            ans="no";
        }
        return ans;
    }

    public String Sid()
    {
        String ans="10";
        Cursor c=sqldb.query(TBSEXPENSE,new String[]{SID},null,null,null,null,SID + " DESC");
        if(c.getCount()>0)
        {
            c.moveToFirst();
            int i=Integer.parseInt(c.getString(0))+1;
            ans=""+i;
        }
        return ans;
    }

//    public String addspecialexpense(String uid,String src, String amt, String type, String date, String time)
//    {
//        String ans="false";
//        String id=Sid();
//        ContentValues cv=new ContentValues();
//        cv.put(SID,id);
//        cv.put(UID,uid);
//        cv.put(SSRC,src);
//        cv.put(SAMT,amt);
//        cv.put(STYPE,type);
//        cv.put(SDATE,date);
//        cv.put(STIME,time);
//        cv.put(SSTATUS,"no");
//        sqldb.insert(TBSEXPENSE,null,cv);
//        ans="true";
//        return ans;
//    }
//
//    public String getspecialexpense(String uid,String src)
//    {
//        String ans = "";
//        Cursor c = null;
//        if (src.compareTo("All") == 0)
//        {
//            c = sqldb.query(TBSEXPENSE, new String[]{SID, SSRC, SAMT, STYPE, SDATE, STIME, SSTATUS}, UID + " = '" + uid + "'", null, null, null, SDATE + " Desc");
//        }
//        else
//        {
//            c = sqldb.query(TBSEXPENSE, new String[]{SID, SSRC, SAMT, STYPE, SDATE, STIME, SSTATUS}, UID + " = '" + uid + "' AND " + SSTATUS + " = '" + src + "'", null, null, null, SDATE + "," + STIME + " Desc");
//        }
//
//        if(c.getCount()>0)
//        {
//            c.moveToFirst();
//            do
//            {
//                ans+=c.getString(0)+"*"+c.getString(1)+"*"+c.getString(2)+"*"+c.getString(3)+"*"+c.getString(4)+"*"+c.getString(5)+"*"+c.getString(6)+"#";
//            }
//            while (c.moveToNext());
//        }
//        else
//        {
//            ans="no";
//        }
//        return ans;
//    }
//
//    public String special_paid(String uid,String sid)
//    {
//        String ans="false";
//        ContentValues cv=new ContentValues();
//        cv.put(SSTATUS,"yes");
//        sqldb.update(TBSEXPENSE,cv,UID +" = '" + uid + "' AND " + SID + " = '" + sid + "'",null);
//        ans="true";
//        return ans;
//    }

    public String getbaldetails(String uid)
    {
        String ans="";
        Cursor c=sqldb.query(TBBALANCE,new String[]{BAL,BM,BY},UID +" = '" + uid + "'",null,null,null,null);
        if(c.getCount()>0)
        {
            c.moveToFirst();
            ans=c.getString(0)+"*"+c.getString(1)+"*"+c.getString(2);
        }
        return ans;
    }

    public String carryforward(String uid)
    {
        try {
            d = new Date();
            int m = Integer.parseInt(sdfm.format(d.getTime()));
            int y = Integer.parseInt(sdfy.format(d.getTime()));

            String s=getbaldetails(uid);
            String temp[]=s.split("\\*");
            int bal = Integer.parseInt(temp[0]);
            int bm=Integer.parseInt(temp[1]);
            int by=Integer.parseInt(temp[2]);

//            Toast.makeText(con, m+"=="+bm+"\n"+y+"=="+by, Toast.LENGTH_SHORT).show();
            if(m!=bm || y!=by)
            {
                d = new Date();
                String dt = sdfd.format(d.getTime());
                String t = sdft.format(d.getTime());
                addexpense(uid, "Prev Month Closing", "" + bal, "Transfer", dt, t);
                addincome(uid, "Prev Month Carry Forward", "" + bal, "Transfer", dt, t);
            }

        }
        catch (Exception e)
        {
            Toast.makeText(con,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "done";
    }

    public String getincome_bal(String uid)
    {
        d=new Date();
        int m=Integer.parseInt(sdfm.format(d.getTime()));
        int y=Integer.parseInt(sdfy.format(d.getTime()));
        String ans="";
        Cursor c=sqldb.query(TBINCOME,new String[]{IAMT,IDATE},UID +" = '" + uid + "'",null,null,null,null);
        if(c.getCount()>0)
        {
            c.moveToFirst();
            do
            {
                String[] temp=c.getString(1).split("/");
                if(Integer.parseInt(temp[0])==y && Integer.parseInt(temp[1])==m)
                {
                    ans+=c.getString(0)+"*";
                }
            }
            while (c.moveToNext());
        }
        else
        {
            ans="no";
        }
        return ans;
    }

    public String getexpense_bal(String uid)
    {
        d=new Date();
        int m=Integer.parseInt(sdfm.format(d.getTime()));
        int y=Integer.parseInt(sdfy.format(d.getTime()));
        String ans="";
        Cursor c=sqldb.query(TBEXPENSE,new String[]{EAMT,EDATE},UID +" = '" + uid + "'",null,null,null,null);
        if(c.getCount()>0)
        {
            c.moveToFirst();
            do
            {
                String[] temp=c.getString(1).split("/");
                if(Integer.parseInt(temp[0])==y && Integer.parseInt(temp[1])==m)
                {
                    ans+=c.getString(0)+"*";
                }
            }
            while (c.moveToNext());
        }
        else
        {
            ans="no";
        }
        return ans;
    }

    public String getgraphdata_income(String uid,String from,String to)
    {
        String ans="";
        try {
            Cursor c=sqldb.query(TBINCOME, new String[]{ISRC,IAMT,IDATE}, UID + " = '" + uid +"'AND "+ IDATE + " BETWEEN '" + from + "' AND '" + to + "'", null, null, null, null);
            if (c.getCount() > 0)
            {
                c.moveToFirst();
                do
                {
                    ans += c.getString(0) + "*" + c.getString(1) + "*" + c.getString(2) + "#";
                }
                while (c.moveToNext());
            } else {
                ans = "no";
            }
        }catch (Exception e)
        {
            ans=e.getMessage();
        }
        return ans;
    }

    public String getgraphdata_expense(String uid,String from,String to)
    {
        String ans="";
        try {
            Cursor c=sqldb.query(TBEXPENSE, new String[]{ESRC,EAMT,EDATE}, UID + " = '" + uid +"'AND "+ EDATE + " BETWEEN '" + from + "' AND '" + to + "'", null, null, null, null);
            if (c.getCount() > 0)
            {
                c.moveToFirst();
                do
                {
                    ans += c.getString(0) + "*" + c.getString(1) + "*" + c.getString(2) + "#";
                }
                while (c.moveToNext());
            } else {
                ans = "no";
            }
        }catch (Exception e)
        {
            ans=e.getMessage();
        }
        return ans;
    }

    public void addtemp(String uid, String date, String income, String expense, String source)
    {
        ContentValues cv=new ContentValues();
        cv.put(UID,uid);
        cv.put(TDATE,date);
        cv.put(TINCOME,income);
        cv.put(TEXPENSE,expense);
        cv.put(TSOURCE,source);
        sqldb.insert(TBTEMP,null,cv);
    }

    public void add_temp(String uid, String date, String income, String expense, String source)
    {
        Cursor c=sqldb.query(TBTEMP,new String[]{TINCOME,TEXPENSE},UID + " = '" + uid + "' AND "+ TDATE + " = '" + date + "'",null,null,null,null);
        if(c.getCount()>0)
        {
            c.moveToFirst();
            int inc=Integer.parseInt(c.getString(0));
            int exp=Integer.parseInt(c.getString(1));

            inc+=Integer.parseInt(income);
            exp+=Integer.parseInt(expense);

            ContentValues cv=new ContentValues();
            cv.put(TEXPENSE,""+exp);
            cv.put(TINCOME,""+inc);
            sqldb.update(TBTEMP,cv,UID + " = '" + uid + "' AND "+ TDATE + " = '" + date + "'",null);
        }
        else
        {
            addtemp(uid, date, income, expense, source);
        }
    }

    public String gettemp(String uid)
    {
        String ans="";
        Cursor c=sqldb.query(TBTEMP,new String[]{TDATE,TINCOME,TEXPENSE},UID + " = '" + uid + "'",null,null,null,TDATE);
        if(c.getCount()>0)
        {
            c.moveToFirst();
            do
            {
                ans+=c.getString(0)+"*"+c.getString(1)+"*"+c.getString(2)+"#";
            }
            while (c.moveToNext());
            delold(uid);
        }
        else
        {
            ans="no";
        }

        return ans;
    }

    public void delold(String uid)
    {
        sqldb.delete(TBTEMP,UID + " = '" + uid + "'",null);
    }

}
