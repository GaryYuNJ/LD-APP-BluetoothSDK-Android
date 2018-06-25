package com.greenland.btsdk.factory;

import java.io.InputStream;
import java.lang.reflect.Constructor;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.greenland.btsdk.interfaces.IBleUtils;
import com.greenland.btsdk.interfaces.IDoor;
import com.greenland.btsdk.interfaces.IElevator;
import com.greenland.btsdk.interfaces.IEscalator;
import com.greenland.btsdk.interfaces.IGroundLock;

import android.app.Activity;
import android.app.Service;
import android.util.Log;

/**
 * @ClassName:  DeviceFactory   
 * @Description:�����豸ʵ�ֵĹ�����
 * @author: wangjy
 * @date:   2017��10��23�� ����11:19:31   
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
				Log.e("resource-service.xml", "�����ļ���ȡ�쳣");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    }
    /**
     * @Title: getDoor   
     * @Description: �õ�doorʵ��
     * @param: @param manufacturerId ����id
     * @param: @param typeId ��Դ����id
     * @param: @param service ���̵�servie
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
     * @Description: �õ�elevatorʵ��
     * @param: @param manufacturerId manufacturerId ����id
     * @param: @param typeId ��Դ����id
     * @param: @param service ���̵�servie
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
     * @Description: �õ�escalatorʵ��
     * @param: @param manufacturerId manufacturerId ����id
     * @param: @param typeId ��Դ����id
     * @param: @param service ���̵�servie
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
     * @Description: �õ����̵��������� 
     * @param: @param manufacturerId ����id
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
	 * @Description: �õ������豸
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
