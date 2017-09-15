package com.example.gursimransingh.contentproviderdemo;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllUsers extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ArrayList<User> userArrayList;
    User user;
    int pos;
    @BindView(R.id.listView)
    ListView listView;
    UserAdapter userAdapter;
    ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        ButterKnife.bind(this);
        resolver = getContentResolver();
        retrieveUsers();
    }

    void retrieveUsers(){
        userArrayList = new ArrayList<>();

        String[] projection = {Util.COL_ID,Util.COL_NAME,Util.COL_EMAIL,Util.COL_PASSWORD,Util.COL_GENDER,Util.COL_CITY};
        Cursor cursor = resolver.query(Util.USER_URI,projection,null,null,null);

        if (cursor!=null){
            int i = 0;
            String n="", e="",p="",g="",c="";
            while (cursor.moveToNext()){
                i = cursor.getInt(cursor.getColumnIndex(Util.COL_ID));
                n = cursor.getString(cursor.getColumnIndex(Util.COL_NAME));
                e = cursor.getString(cursor.getColumnIndex(Util.COL_EMAIL));
                p = cursor.getString(cursor.getColumnIndex(Util.COL_PASSWORD));
                g = cursor.getString(cursor.getColumnIndex(Util.COL_GENDER));
                c = cursor.getString(cursor.getColumnIndex(Util.COL_CITY));

                User user =  new User(i,n,e,p,g,c);
                userArrayList.add(user);
            }
            userAdapter = new UserAdapter(this,R.layout.list_item,userArrayList);
            listView.setAdapter(userAdapter);
            listView.setOnItemClickListener(this);
        }


    }


    void showOptions(){
        String[] items = {"View User", "Delete User", "Update User"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0: showUser();
                        break;
                    case  1:
                            askForDeletion();
                        break;
                    case 2:
                        Intent intent = new Intent(AllUsers.this,UserSignUpActivity.class);
                        intent.putExtra(Util.KEY_USER,user);
                        startActivity(intent);
                        //updateUSer();
                        break;
                }
            }
        });
        builder.create().show();
    }

    void showUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(user.getName());
        builder.setMessage(user.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos =i;
        user = userArrayList.get(i);
        showOptions();
    }

    void askForDeletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete"+user.getName());
        builder.setMessage("Are you SUre");
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteUser();
            }
        });
        builder.create().show();
    }
    void deleteUser(){
        String where = Util.COL_ID+" = "+user.getId();
       int i = resolver.delete(Util.USER_URI,where,null);
        if (i>0){
            userArrayList.remove(pos);
            //CHANGE IN USERADAPTER
            userAdapter.notifyDataSetChanged();
            Toast.makeText(this, user.getName()+" deleted... ",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, user.getName()+" not deleted... ",Toast.LENGTH_LONG).show();

        }

    }

}
