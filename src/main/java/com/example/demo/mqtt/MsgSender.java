package com.example.demo.mqtt;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


import com.example.demo.mqtt.ConstStr;
import com.example.demo.mqtt.mqttSender;
import net.sf.json.JSONObject;
@ComponentScan(basePackages={"com.example.coreserver.mqtt.mqttSender"})
@Component
public class MsgSender {
	@Autowired
	private mqttSender sender;
	
	static boolean m_bInit = false;
	//private static MsgSender inst = new MsgSender();
	

	

	
	
	//初始化连接
	public void init() {
		try {

			sender.init();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//复位 版本号 0为旧版本，1为新版本
	public void resetDevice(String uuid,String strUser,String strDevID,int protocolVersion){
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,5);
		obj.put(ConstStr.USER, strUser);
		obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		String strText = obj.toString();
		String strSend = strText;
		
		
		sender.sendToMqtt(strSend);
		
	}
	
	//设置装置参数 版本号 0为旧版本，1为新版本
	public void setDevParameter(String uuid,String User,String strDevID,int[]ValueLst,int protocolVersion) {
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,7);
		obj.put(ConstStr.USER,User);
		obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		JSONObject para = new JSONObject();
		for(int nIndex=0;nIndex<ValueLst.length;nIndex++)
		{
			String strParaName = "Para"+String.valueOf(nIndex+1);
			para.put(strParaName, ValueLst[nIndex]);
		}
		obj.put(ConstStr.PARAMETERS,para);
		
		sender.sendToMqtt(obj.toString());	
	}

	
	
	//读取装置参数 版本号 0为旧版本，1为新版本
	public void readDevParameter(String uuid,String strUser,String strDevID,int protocolVersion) {
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,9);
		obj.put(ConstStr.USER, strUser);
		obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		sender.sendToMqtt(obj.toString());

	}
	
	//发送自检请求 版本号 0为旧版本，1为新版本
	public void readSelfCheckInfo(String uuid,String strUser,String strDevID,int protocolVersion) {
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,21);
		obj.put(ConstStr.USER, strUser);
		obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		sender.sendToMqtt(obj.toString());

	}
	
	//发送log文件下载请求 版本号 0为旧版本，1为新版本
	public void downloadLog(String uuid,String strUser,String strDevID,int protocolVersion) {
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,23);
		obj.put(ConstStr.USER, strUser);
		obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		sender.sendToMqtt(obj.toString());

	}
	
	
	//发送结果给APP 版本号 0为旧版本，1为新版本
	public void SendResult(String id,String factoryid,int faultNum,int nType,int phase,
			String occurr_time,String line,String leftTowerId,String rightTowerId,
			float left_distance,float right_distance,String left_path,String right_path) {
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		obj.put(ConstStr.ID,id);
		obj.put("fSLType", nType);
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.FACTORY,factoryid);

		obj.put("fault_num",faultNum);
		obj.put(ConstStr.PHASE,phase);
		obj.put("occurr_time",occurr_time);
		obj.put("line",line);
		obj.put("left_tower",leftTowerId);
		obj.put("right_tower",rightTowerId);
		obj.put("left_distance", left_distance);
		obj.put("right_distance",right_distance);
		obj.put("left_path",left_path);
		obj.put("right_path",right_path);
		obj.put("isread",0);
		
		
		sender.sendToMqtt(sender.commClientTopic,1,obj.toString());

	}
	
	
	//杆塔倾斜监控装置重置
	public void resetTiltDevice(String uuid,String strUser,String strDevID,int nMode){
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,3);
		obj.put(ConstStr.USER, strUser);
		obj.put(ConstStr.RESET_MODE, nMode);
		//  obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		String strText = obj.toString();
		String strSend = strText;		
		
		sender.sendTiltToMqtt(strSend);
		
	}
	
	//杆塔倾斜监控装置重置
	//requestFlag表示用户选择修改哪个值，0-3位分别代表后面的参数。
	//采样周期(无符号2字节整形，单位分钟)，
	//高速采样点数(无符号2字节整形)，
	//高速采样频率(无符号2字节整形单位hz)，
	//心跳上送周期(1字节单位分钟)
	public void setTiltSample(String uuid,String strUser,String strDevID,
			                  short wMainTime,short wSampleCount,short wSampleFrequency,byte bheartbeat,
			                  byte bRequestFlag){
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_SAMPLE_SET);
		obj.put(ConstStr.USER, strUser);
		//  obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		obj.put(ConstStr.MAIN_TIME,wMainTime);
		obj.put(ConstStr.SAMPLE_COUNT, wSampleCount);
		obj.put(ConstStr.SAMPLE_FREQUENCY,wSampleFrequency);
		obj.put(ConstStr.HEART_TIME, bheartbeat);
		obj.put(ConstStr.REQUEST_FLAG,bRequestFlag);
		
		String strText = obj.toString();
		String strSend = strText;		
		
		sender.sendTiltToMqtt(strSend);
		
	}
		
	public void getTiltSample(String uuid,String strUser,String strDevID){

		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_SAMPLE_QUERY);
		obj.put(ConstStr.USER, strUser);
		//  obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		String strText = obj.toString();
		String strSend = strText;		
		
		sender.sendTiltToMqtt(strSend);

	}
	//设置时间
	public void setTiltTime(String uuid,String strUser,String strDevID,
            long nSecond){
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_TIME_SET);
		obj.put(ConstStr.USER, strUser);
		//  obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		obj.put(ConstStr.CLOCK_STAMP, nSecond);
		
		String strText = obj.toString();
		String strSend = strText;		
		
		sender.sendTiltToMqtt(strSend);
	}
	
	//查询时间
	public void getTiltTime(String uuid,String strUser,String strDevID){

		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_TIME_QUERY);
		obj.put(ConstStr.USER, strUser);
		//  obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		String strText = obj.toString();
		String strSend = strText;		
		
		sender.sendTiltToMqtt(strSend);

	}
	
	//设置杆塔倾斜的阈值参数
	public void setTiltPara(String uuid,String strUser,String strDevID,
			   float fIncliMax,float fIncliMin,
			   float fIncliXMax,float fIncliXMin,
			   float fIncliYMax,float fIncliYMin,
			   float fAngleXMax,float fAngleXMin,
			   float fAngleYMax,float fAngleYMin){
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_THRSHLD_SET);
		obj.put(ConstStr.USER, strUser);
		//  obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		obj.put(ConstStr.INCLICATION_MAX,fIncliMax );
		obj.put(ConstStr.INCLICATION_MIN,fIncliMin );
		obj.put(ConstStr.INCLICATION_X_MAX,fIncliXMax );
		obj.put(ConstStr.INCLICATION_X_MIN,fIncliXMin );
		obj.put(ConstStr.INCLICATION_Y_MAX,fIncliYMax );
		obj.put(ConstStr.INCLICATION_Y_MIN,fIncliYMin );	
		obj.put(ConstStr.ANGLE_X_MAX,fAngleXMax );
		obj.put(ConstStr.ANGLE_X_MIN,fAngleXMin );
		obj.put(ConstStr.ANGLE_Y_MAX,fAngleYMax );
		obj.put(ConstStr.ANGLE_Y_MIN,fAngleYMin );
		
		
		String strText = obj.toString();
		String strSend = strText;		
		
		sender.sendTiltToMqtt(strSend);
	}
	
	//读取杆塔倾斜装置参数命令 下发
	public void readTiltPara(String uuid,String strUser,String strDevID) {
		/*if(!m_bInit)
		{
			init();
			m_bInit = true;
		}*/
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_THRSHLD_QUERY);
		obj.put(ConstStr.USER, strUser);

		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		sender.sendTiltToMqtt(obj.toString());

	}
	
	//设置杆塔倾斜的装置信息
	public void setTiltDevInfo(String uuid,String strUser,String strDevID,int infoType){
		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_DEVINFO_SET);
		obj.put(ConstStr.USER, strUser);
		//  obj.put(ConstStr.PROCOLVERSION, protocolVersion);//新增协议版本号
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		obj.put(ConstStr.INFO_TYPE, infoType);
		
		
		String strText = obj.toString();
		String strSend = strText;		
		
		sender.sendTiltToMqtt(strSend);
	}
	
	//读取杆塔倾斜装置参数命令 下发
	public void readTiltDevInfo(String uuid,String strUser,String strDevID) {

		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_DEVINFO_QUERY);
		obj.put(ConstStr.USER, strUser);

		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		sender.sendTiltToMqtt(obj.toString());

	}
	
	//上召装置倾斜角数据
	public void readTiltData(String uuid,String strUser,String strDevID) {

		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_UPDATA);
		obj.put(ConstStr.USER, strUser);

		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		sender.sendTiltToMqtt(obj.toString());

	}
	
	//校准装置倾角
	public void resetTiltData(String uuid,String strUser,String strDevID) {

		JSONObject obj = new JSONObject();
		//String uuid = UUID.randomUUID().toString().replaceAll("-","");
		obj.put(ConstStr.ID,uuid);
		obj.put(ConstStr.DEVID,strDevID);
		obj.put(ConstStr.TYPE,ConstStr.MQTT_RESET_DATA);
		obj.put(ConstStr.USER, strUser);

		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		String strSDate =df.format(System.currentTimeMillis());   
		obj.put(ConstStr.SENDTIME, strSDate);
		
		sender.sendTiltToMqtt(obj.toString());
	}
}
