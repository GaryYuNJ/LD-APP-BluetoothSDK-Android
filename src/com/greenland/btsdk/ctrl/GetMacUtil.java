package com.greenland.btsdk.ctrl;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.greenland.btsdk.factory.DeviceFactory;
import com.greenland.btsdk.interfaces.IBleUtils;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

/**
 * @ClassName:  GetMacUtil   
 * @Description:蓝牙加密方法
 * @author: wangjy
 * @date:   2017年10月23日 下午10:58:55   
 *
 */
public class GetMacUtil {
	private IBleUtils bleUtils;
	private InputStream in;
	private SAXReader reader;
	private Document document;
	private List<Element> nodes;
	private Activity activity;
	 
	
	@SuppressWarnings("unchecked")
	public GetMacUtil(Activity activity) {
		try {
			in = DeviceFactory.class.getClassLoader().getResourceAsStream("resource-service.xml");
			reader = new SAXReader();
			document = reader.read(in);
			nodes = document.selectNodes("/manufacturers/manufacturer");
			this.activity = activity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getDeviceMAC(final BluetoothDevice device,final byte[] scanRecord) {
		 try {
			for (Element element : nodes) {
				String name = element.attribute("name").getText();
				Element ele = (Element) element.selectSingleNode("/manufacturers/manufacturer[@name='"+name+"']/bean[@name='bleutil']");
				String bleutilClass = ele.attribute("class").getText();
				Class claz = Class.forName(bleutilClass);
	            Constructor c = claz.getConstructor(Activity.class);
	            bleUtils = (IBleUtils) c.newInstance(activity);
				String deviceMAC = bleUtils.getDeviceMAC(device, scanRecord);
				if (deviceMAC != null) {
					return deviceMAC;
				}
			}
		 } catch (Exception e) {
			 return null;
		 }
		 return null;
     }
}
