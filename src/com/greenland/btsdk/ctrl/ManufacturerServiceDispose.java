package com.greenland.btsdk.ctrl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.greenland.btsdk.callback.BindServiceCallback;
import com.greenland.btsdk.factory.DeviceFactory;

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
 * @Description:����service��
 * @author: wangjy
 * @date:   2017��10��23�� ����10:59:38   
 *
 */
public class ManufacturerServiceDispose {
    private Service service;
    private BindServiceCallback bindServiceCallback;
    //��Ÿ����̵�service
    private Map<String,Service> serviceMap;
    //service��·���ͳ���id��map
    private Map<String,String> clazAndMun;
    private InputStream in;
    private Activity activity;
    
    /**
     * @Title:  ManufacturerServiceDispose   
     * @Description:��ʼ��
     * @param:  @param activity  ��ǰXXXActivity.this
     * @throws
     */
	public ManufacturerServiceDispose(Activity activity) {
	    	clazAndMun = new HashMap<String, String>();
	    	serviceMap = new HashMap<String, Service>();
	    	this.activity = activity;
            in = DeviceFactory.class.getClassLoader().getResourceAsStream("resource-service.xml");
	}
	
	/**
	 * �����г��̷���
	 * @param getServiceCallback
	 */
	public void bindService(BindServiceCallback bindServiceCallback){
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Element root = document.getRootElement();   
			List elements = root.elements("manufacturer");
			//ѭ����xml��ȡ����service
			for (Iterator it = root.elementIterator(); it.hasNext();) {
			    Element elm = (Element) it.next();
			    Element serviceEle = (Element) document.selectSingleNode("//manufacturer[@name='"+elm.attributeValue("name")+"']/bean[@id='service']");
			    if (serviceEle == null) {
					continue;
				}
			    String serviceClassName = serviceEle.attribute("class").getText();
			    //�ж�service��û���ڹ����������˾������ٴΰ�
			    boolean worked = isWorked(serviceClassName);
			    if (!worked) {
			    	service = (Service) Class.forName(serviceClassName).newInstance();
			    	clazAndMun.put(serviceClassName,elm.attributeValue("name").toString());
			    	
			    	this.bindServiceCallback = bindServiceCallback;
			    	Intent bindIntent = new Intent(activity.getApplicationContext(), service.getClass());
			    	boolean result = activity.bindService(bindIntent, this.mServiceConnection, Context.BIND_AUTO_CREATE);
			    	System.out.println(result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void unBindService(){
		try {
			
			activity.unbindService(mServiceConnection);
		} catch (Exception e) {
			Log.i("11111", "sdk����ر�ʧ�ܻ����޷���");
		}
	}
	/**
	 * ��service�Ļص�
	 */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
    	public String classNameStr;
        @Override
        public void onServiceConnected(ComponentName className,IBinder rawBinder) {
        	String clazName = className.getClassName();
        	service = ((BleService.LocalBinder) rawBinder).getService();
        	serviceMap.put(clazAndMun.get(clazName), service);
        	bindServiceCallback.onBind(serviceMap);
            
        }
        @Override
        public void onServiceDisconnected(ComponentName classname) {
        	Log.e("111111", classname+"�Ͽ���....");
        }
    };
    
    /**
     * @Title: isWorked   
     * @Description: �ж�service��û��������
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
