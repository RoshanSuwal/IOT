package com.example.iot.Adapter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iot.R;
import com.example.iot.model.Project;
import com.example.iot.model.Switch;

import java.util.ArrayList;

import static com.example.iot.R.drawable.switch_on_drawable;

public class ListAdapter extends ArrayAdapter<Project> {

    private ArrayList<Project> projectArrayList;
    Context context;


    public ListAdapter(Context applicationContext, ArrayList<Project> projectArrayList) {
        super(applicationContext,R.layout.project_item_list,projectArrayList);
        this.projectArrayList=projectArrayList;
        this.context=context;
    }

    private static class ViewHolder{
         TextView project_name_field;
         TextView token_field;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        ViewHolder viewHolder ;
        Project project=getItem(position);

        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.project_item_list,parent,false);
            viewHolder.project_name_field=convertView.findViewById(R.id.project_name);
            viewHolder.token_field=convertView.findViewById(R.id.token_view);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.project_name_field.setText(project.getProjectName());
        viewHolder.token_field.setText(project.getToken());

        return convertView;
    }
}
