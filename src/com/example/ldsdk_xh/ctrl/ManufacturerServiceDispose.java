package com.example.ldsdk_xh.ctrl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.example.ldsdk_xh.callback.BindServiceCallback;
import com.example.ldsdk_xh.factory.DeviceFactory;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import cn.com.reformer.rfBleService.BleService;

/**
 * @ClassName:  ManufacturerServiceDispose   
 * @Description:厂商service绑定
 * @author: wangjy
 * @date:   2017年10月23日 下午10:59:38   
 *
 */
public class ManufacturerServiceDispose {
    private Service service;
    private BindServiceCallback bindServiceCallback;
    //存放各厂商的service
    private Map<String,Service> serviceMap;
    //service类路径和厂商id的map
    private Map<String,String> clazAndMun;
    private InputStream in;
    private Activity activity;
    
    /**
     * @Title:  ManufacturerServiceDispose   
     * @Description:初始化
     * @param:  @param activity  当前XXXActivity.this
     * @throws
     */
	public ManufacturerServiceDispose(Activity activity) {
	    	clazAndMun = new HashMap<String, String>();
	    	serviceMap = new HashMap<String, Service>();
	    	this.activity = activity;
            in = DeviceFactory.class.getClassLoader().getResourceAsStream("resource-service.xml");
	}
	
	/**
	 * 绑定所有厂商服务
	 * @param getServiceCallback
	 */
	public void bindService(BindServiceCallback bindServiceCallback){
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();   
			List elements = root.elements("manufacturer");
			//循环绑定xml读取来的service
			for (Iterator it = root.elementIterator(); it.hasNext();) {
			    Element elm = (Element) it.next();
			    Element serviceEle = (Element) document.selectSingleNode("//manufacturer[@name='"+elm.attributeValue("name")+"']/bean[@id='service']");
			    if (serviceEle == null) {
					continue;
				}
			    String serviceClassName = serviceEle.attribute("class").getText();
			    //判断service有没有在工作，工作了就无需再次绑定
			    boolean worked = isWorked(serviceClassName);
			    if (!worked) {
			    	service = (Service) Class.forName(serviceClassName).newInstance();
			    	clazAndMun.put(serviceClassName,elm.attributeValue("name").toString());
			    	
			    	this.bindServiceCallback = bindServiceCallback;
			    	Intent bindIntent = new Intent(activity.getApplicationContext(), service.getClass());
			    	activity.bindService(bindIntent, this.mServiceConnection, Context.BIND_AUTO_CREATE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void unBindService(){
		activity.unbindService(mServiceConnection);
	}
	/**
	 * 绑定service的回调
	 */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,IBinder rawBinder) {
        	String clazName = className.getClassName();
        	service = ((BleService.LocalBinder) rawBinder).getService();
        	serviceMap.put(clazAndMun.get(clazName), service);
        	bindServiceCallback.onBind(serviceMap);
            
        }
        @Override
        public void onServiceDisconnected(ComponentName classname) {
        	Log.e("111111", classname+"断开了....");
        }
    };
    
    /**
     * @Title: isWorked   
     * @Description: 判断service有没有在运行
     * @param: @param serviceClassName
     * @param: @return      
     * @return: boolean      
     * @throws
     */
	private boolean isWorked(String serviceClassName) {  
		ActivityManager myManager=(ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);  
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);  
		for(int i = 0 ; i<runningService.size();i++) {  
			if(runningService.get(i).service.getClassName().toString().equals(serviceClassName)) {  
				return true;  
			}  
		}  
		return false;  
	}
}
