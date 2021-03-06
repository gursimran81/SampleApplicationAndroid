package com.example.gursimransingh.contentproviderdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gursimransingh on 20/07/17.
 */

public class UserAdapter extends ArrayAdapter<User> {
    Context context;
    int resource;
    ArrayList<User> userArrayList;
    public UserAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource =resource;
        userArrayList =objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        //it wiil be called position times

        view = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtEmail = (TextView)view.findViewById(R.id.textViewEmail);

        User user = userArrayList.get(position);
        txtName.setText(user.getId()+" "+user.getName());
        txtEmail.setText(user.getEmail());
        return view;
    }

    public void filter(String s){

    }
}
