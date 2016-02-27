package com.doz6.autocompletetest;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> datas;//用于存放数据源的集合

    private AutoCompleteTextView mAuto;

    private MyOpenHelper myOpenHelper;

    private SQLiteDatabase mDB;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myOpenHelper=new MyOpenHelper(this,"word.db",null,1);
        mDB=myOpenHelper.getReadableDatabase();
        mAuto=(AutoCompleteTextView)findViewById(R.id.mAuto);
        datas=getData();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        mAuto.setAdapter(adapter);
    }

    public List<String> getData(){
        List<String> contents=new ArrayList<String>();
        Cursor result=mDB.rawQuery("select * from word",null);
        while(result.moveToNext()){
            contents.add(result.getString(result.getColumnIndex("word")));
        }
        return contents;
    }

    public void search(View view){//"搜索"按钮的事件处理
        String input=mAuto.getText().toString();
        if(input==null || "".equals(input.trim())){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("警告提示");
            builder.setMessage("请输入搜索关键字");
            builder.create().show();
        }else{
            if(!datas.contains(input)){
                mDB.execSQL("insert into word(word) values(?)",new String[]{input});//如果关键字中没有该关键字，则将该关键字添加进去
                datas=getData();
                adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
                mAuto.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(mDB!=null){
            mDB.close();
        }
        super.onDestroy();
    }
}
