package com.example.iot.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iot.DeviceControllerMVP.DeviceControllerPresenter;
import com.example.iot.R;
import com.example.iot.model.Switch;

import java.util.ArrayList;

import static com.example.iot.R.drawable.switch_on_drawable;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Switch> switchArrayList;
    private DeviceControllerPresenter presenter;

    public RecyclerAdapter(ArrayList<Switch> switchArrayList, DeviceControllerPresenter presenter) {
        this.switchArrayList = switchArrayList;
        this.presenter=presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_switch_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Switch aSwitch=switchArrayList.get(i);
        viewHolder.switch_name_textview.setText(aSwitch.getSwitch_name());
        viewHolder.pin_no_textview.setText(aSwitch.getPin_number());
        viewHolder.status_textview.setText(""+aSwitch.getId());

        if (aSwitch.getPin_value()==1){

            viewHolder.linearLayout.setBackgroundResource(switch_on_drawable);
            viewHolder.status_ON_OFF.setTextColor(Color.WHITE);
            viewHolder.status_ON_OFF.setText("ON");
        }else{
            viewHolder.status_ON_OFF.setText("OFF");
            viewHolder.status_ON_OFF.setTextColor(Color.BLACK);
            viewHolder.linearLayout.setBackgroundResource(R.drawable.switch_drawable);

        }
    }

    @Override
    public int getItemCount() {
        return switchArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView switch_name_textview;
        TextView pin_no_textview;
        TextView status_textview;
        TextView status_ON_OFF;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            switch_name_textview=itemView.findViewById(R.id.switch_name);
            pin_no_textview=itemView.findViewById(R.id.item_switch_pin_no);
            status_textview=itemView.findViewById(R.id.pin_status);
            status_ON_OFF=itemView.findViewById(R.id.onoffstatus_textview);
            linearLayout=itemView.findViewById(R.id.linearlayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.listviewItemClickListener(switchArrayList.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.listviewItenLongClickListener(switchArrayList.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }
}
