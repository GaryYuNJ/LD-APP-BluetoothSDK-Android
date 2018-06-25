/**  
 * @Title:  MyBaseExpandableListAdapter.java   
 * @Package com.example.ldsdk_xh.adapter   
 * @Description:    TODO(��һ�仰�������ļ���ʲô)   
 * @author: wangjy  
 * @date:   2017��10��24�� ����9:09:26   
 * @version V1.0 
 */  
package com.greenland.btsdk.adapter;

import java.util.List;
import java.util.Map;

import com.greenland.btsdk.R;
import com.greenland.btsdk.model.DeviceInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**   
 * @ClassName:  MyBaseExpandableListAdapter   
 * @Description:ExpandListView�����������̳���BaseExpandableListAdapter
 * @author: wangjy
 * @date:   2017��10��24�� ����9:09:26   
 *      
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter implements OnClickListener {
	
	private Context mContext;
	private List<String> buildingNameList;
	private  Map<Integer, List<DeviceInfo>> blueNameMap;
	
	public MyBaseExpandableListAdapter(Context mContext, List<String> buildingNameList,Map<Integer, List<DeviceInfo>> blueNameMap) {
		this.mContext = mContext;
		this.buildingNameList = buildingNameList;
		this.blueNameMap = blueNameMap;
	}

	/**   
	 * <p>Title: getGroupCount</p>   
	 * <p>Description: �õ�������</p>   
	 * @return   
	 * @see android.widget.ExpandableListAdapter#getGroupCount()   
	 */
	@Override
	public int getGroupCount() {
		return buildingNameList.size();
	}

	/**   
	 * <p>Title: getChildrenCount</p>   
	 * <p>Description: ȡ��ָ���������Ԫ����</p>   
	 * @param groupPosition
	 * @return   
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)   
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return blueNameMap.get(groupPosition).size();
	}

	/**   
	 * <p>Title: getGroup</p>   
	 * <p>Description: ȡ��������������������</p>   
	 * @param groupPosition
	 * @return   
	 * @see android.widget.ExpandableListAdapter#getGroup(int)   
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return buildingNameList.get(groupPosition);
	}

	/**   
	 * <p>Title: getChild</p>   
	 * <p>Description: �õ�ָ���ӷ���</p>   
	 * @param groupPosition
	 * @param childPosition
	 * @return   
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)   
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return blueNameMap.get(groupPosition).get(childPosition);
	}

	/**   
	 * <p>Title: getGroupId</p>   
	 * <p>Description: �õ�����id</p>   
	 * @param groupPosition
	 * @return   
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)   
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**   
	 * <p>Title: getChildId</p>   
	 * <p>Description: �õ��ӷ���id</p>   
	 * @param groupPosition
	 * @param childPosition
	 * @return   
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)   
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**   
	 * <p>Title: hasStableIds</p>   
	 * <p>Description: ��ʾ�����Ƿ����ID�ǿ�������ݵĸ����ȶ�</p>   
	 * @return   
	 * @see android.widget.ExpandableListAdapter#hasStableIds()   
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**   
	 * <p>Title: getGroupView</p>   
	 * <p>Description: </p>   
	 * @param groupPosition
	 * @param isExpanded
	 * @param convertView
	 * @param parent
	 * @return   
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)   
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupHolder groupHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.building_item_list, null);
			groupHolder = new GroupHolder();
			groupHolder.textView = (TextView) convertView.findViewById(R.id.buildingname);
			groupHolder.imageView = (ImageView) convertView.findViewById(R.id.btn_direct);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		if (isExpanded) {
			groupHolder.imageView.setImageResource(R.drawable.arrow_down);
		} else {
			groupHolder.imageView.setImageResource(R.drawable.arrow_right);
		}
		groupHolder.textView.setText(buildingNameList.get(groupPosition));
		return convertView;
	}

	/**   
	 * <p>Title: getChildView</p>   
	 * <p>Description: </p>   
	 * @param groupPosition
	 * @param childPosition
	 * @param isLastChild
	 * @param convertView
	 * @param parent
	 * @return   
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)   
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		 ChildHolder childHolder = null;
		 if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.blue_item_list, null);
			childHolder = new ChildHolder();
			childHolder.textView = (TextView) convertView.findViewById(R.id.device_name);
			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		childHolder.textView.setText(blueNameMap.get(groupPosition).get(childPosition).getName());
		return convertView;
	}

	/**   
	 * <p>Title: isChildSelectable</p>   
	 * <p>Description: </p>   
	 * @param groupPosition
	 * @param childPosition
	 * @return   
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)   
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/**   
	 * <p>Title: onClick</p>   
	 * <p>Description: </p>   
	 * @param v   
	 * @see android.view.View.OnClickListener#onClick(android.view.View)   
	 */
	@Override
	public void onClick(View v) {

	}
	
	/**
	 * �Ӻ����ϵ���ʾ
	 */
	private class GroupHolder{
		TextView textView;
		ImageView imageView;
	}
	
	private class ChildHolder{
		TextView textView;
	}
	
}
