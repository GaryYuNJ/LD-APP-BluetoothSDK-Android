package com.greenland.btsdk.adapter;

import java.util.List;

import com.greenland.btsdk.R;
import com.greenland.btsdk.model.DeviceInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter {
	
	private List<DeviceInfo> deviceInfos;  
	private Context context;
	
	public MyListViewAdapter(List<DeviceInfo> deviceInfos, Context context) {
		this.deviceInfos = deviceInfos;
		this.context = context;
	}

	@Override
	public int getCount() {
        if (deviceInfos.size() == 0) {  
            return 0;  
        }  
        return deviceInfos.size();  
	}

	@Override
	public Object getItem(int position) {
		return deviceInfos.get(position); 
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListViewHolder holder;
		if (convertView == null) {
			holder = new ListViewHolder();
			convertView = View.inflate(context, R.layout.search_key_list_item, null);  
			holder.deviceName = (TextView) convertView.findViewById(R.id.search_device_name);
			convertView.setTag(holder);
		} else {
			holder = (ListViewHolder)convertView.getTag();
		}
		holder.deviceName.setText(deviceInfos.get(position).getName());
		 
		return convertView;
	}
	class ListViewHolder {
		TextView deviceName;
	}
}
