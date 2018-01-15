package com.example.ldsdk_xh.factory;

import java.io.InputStream;
import java.lang.reflect.Constructor;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.example.ldsdk_xh.interfaces.IBleUtils;
import com.example.ldsdk_xh.interfaces.IDoor;
import com.example.ldsdk_xh.interfaces.IElevator;
import com.example.ldsdk_xh.interfaces.IEscalator;
import com.example.ldsdk_xh.interfaces.IGroundLock;

import android.app.Activity;
import android.app.Service;
import android.util.Log;

/**
 * @ClassName:  DeviceFactory   
 * @Description:制造设备实现的工厂类
 * @author: wangjy
 * @date:   2017年10月23日 下午11:19:31   
 *
 */
public class DeviceFactory {

    private static IDoor door;
    private static IElevator elevator; 
    private static IEscalator escalator;
    private static IBleUtils bleUtils;
    private static IGroundLock groundLock;
    
    private static Document document = null;

    static {
        InputStream in = DeviceFactory.class.getClassLoader().getResourceAsStream("resource-service.xml");
        SAXReader reader = new SAXReader();
        try {
			document = reader.read(in);
			if (document == null) {
				Log.e("resource-service.xml", "配置文件读取异常");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    }
    /**
     * @Title: getDoor   
     * @Description: 得到door实现
     * @param: @param manufacturerId 厂商id
     * @param: @param typeId 资源类型id
     * @param: @param service 厂商的servie
     * @param: @return      
     * @return: IDoor      
     * @throws
     */
    public static IDoor getDoor(Integer manufacturerId,Integer typeId,Service service,Activity activity){
        try{
            Element manufacturerEle = (Element) document.selectSingleNode("//manufacturer[@name='"+manufacturerId+"']");
            Element doorImplEle = (Element) manufacturerEle.selectSingleNode("/manufacturers/manufacturer[@name='"+manufacturerId+"']/bean[@type-id='"+typeId+"']");
            String doorImplClass = doorImplEle.attribute("class").getText();
            Class claz = Class.forName(doorImplClass);
            Constructor c = null;
            if (service != null) {
            	c = claz.getConstructor(Service.class,Activity.class);
            	door = (IDoor) c.newInstance(service,activity);
			} else {
				c = claz.getConstructor(Activity.class);
            	door = (IDoor) c.newInstance(activity);
			}

        } catch (Exception e) {
            e.printStackTrace();
        }
        return door;
    }
    
	/**
     * @Title: getElevator   
     * @Description: 得到elevator实现
     * @param: @param manufacturerId manufacturerId 厂商id
     * @param: @param typeId 资源类型id
     * @param: @param service 厂商的servie
     * @param: @return      
     * @return: IElevator      
     * @throws
     */
    public static IElevator getEscalator(Integer manufacturerId,Integer typeId,Service service,Activity activity){
        try{
            Element manufacturerEle = (Element) document.selectSingleNode("//manufacturer[@name='"+manufacturerId+"']");
            Element elevatorImplEle = (Element) manufacturerEle.selectSingleNode("//bean[@type-id='"+typeId+"']");
            String elevatorImplClass = elevatorImplEle.attribute("class").getText();
			Class claz = Class.forName(elevatorImplClass);
            Constructor c = null;
            if (service != null) {
            	c = claz.getConstructor(Service.class,Activity.class);
            	elevator = (IElevator) c.newInstance(service,activity);
            } else {
            	c = claz.getConstructor(Activity.class);
            	elevator = (IElevator) c.newInstance(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elevator;
    }
    
    /**
     * @Title: getEscalator   
     * @Description: 得到escalator实现
     * @param: @param manufacturerId manufacturerId 厂商id
     * @param: @param typeId 资源类型id
     * @param: @param service 厂商的servie
     * @param: @return      
     * @return: IEscalator      
     * @throws
     */
    public static IEscalator getElevator(Integer manufacturerId,Integer typeId,Service service,Activity activity){
        try{
            Element manufacturerEle = (Element) document.selectSingleNode("/manufacturers/manufacturer[@name='"+manufacturerId+"']");
            Element escalatorImplEle = (Element) manufacturerEle.selectSingleNode("//bean[@type-id='"+typeId+"']");
            String escalatorImplClass = escalatorImplEle.attribute("class").getText();
            Class claz = Class.forName(escalatorImplClass);
            Constructor c = null;
            if (service != null) {
            	c = claz.getConstructor(Service.class,Activity.class);
                escalator = (IEscalator) c.newInstance(service,activity);
            } else {
            	c = claz.getConstructor(Activity.class);
                escalator = (IEscalator) c.newInstance(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return escalator;
    }
    
    /**
     * @Title: getBleUtils   
     * @Description: 得到厂商的蓝牙工具 
     * @param: @param manufacturerId 厂商id
     * @param: @return      
     * @return: IBleUtils      
     * @throws
     */
    public static IBleUtils getBleUtils(Integer manufacturerId){
        try {
			Element manufacturerEle = (Element) document.selectSingleNode("//manufacturer[@name='"+manufacturerId+"']");
			Element bleUtilElement = (Element) manufacturerEle.selectSingleNode("//bean[@id='bleutil']");
			String bleUtilClaz = bleUtilElement.attribute("class").getText();
			bleUtils = (IBleUtils) Class.forName(bleUtilClaz).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return bleUtils;
    }

	/**   
	 * @Title: getGroundLock   
	 * @Description: 得到地锁设备
	 * @param: @param manufacturerId
	 * @param: @param typeId
	 * @param: @param service
	 * @param: @param activity
	 * @param: @return      
	 * @return: IEscalator      
	 * @throws   
	 */
	public static IGroundLock getGroundLock(Integer manufacturerId, Integer typeId, Service service, Activity activity) {
        try{
            Element manufacturerEle = (Element) document.selectSingleNode("/manufacturers/manufacturer[@name='"+manufacturerId+"']");
            Element escalatorImplEle = (Element) manufacturerEle.selectSingleNode("//bean[@type-id='"+typeId+"']");
            String escalatorImplClass = escalatorImplEle.attribute("class").getText();
            Class claz = Class.forName(escalatorImplClass);
            Constructor c = null;
            if (service != null) {
            	c = claz.getConstructor(Service.class,Activity.class);
            	groundLock = (IGroundLock) c.newInstance(service,activity);
            } else {
            	c = claz.getConstructor(Activity.class);
            	groundLock = (IGroundLock) c.newInstance(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return groundLock;
	}
    
}
