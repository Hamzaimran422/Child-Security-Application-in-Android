package com.example.childsecuritysystem;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FenceCustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;

    }

    public FenceCustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.fencerow_item, data);
        this.dataSet = data;
        this.mContext=context;

    }
    ////////////////Delete Fence////////////////////////////////
    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        String url = "user/DeleteFence?f_id="+dataModel.F_ID;
        CallResult result = WCFHandler.GetJsonResult(url);
        if(result.StatusCode==200) {
            dataSet.remove(position);
            notifyDataSetChanged();
            Snackbar.make(v, "Delete Fence Successfully " + dataModel.getFeature(), Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        }

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fencerow_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.drawable.up_from_bottom : R.drawable.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.F_Name);
        viewHolder.txtType.setText(dataModel.FenceStatus);
        viewHolder.txtVersion.setText("");
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}


//package com.example.childsecuritysystem;
//
//public class FenceCustomAdapter {
//}
