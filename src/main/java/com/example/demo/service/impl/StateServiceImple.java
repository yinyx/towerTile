package com.example.demo.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.po.DataTableModel;
import com.example.demo.service.StateService;
import com.example.demo.mqtt.MsgSender;
import com.example.demo.mapper.StateMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.po.ParamAttrManage;
import com.example.demo.po.ParamAttr;

import util.aes.StringUtils;
import util.aes.TestProperties; 

@Service
@Transactional
public class StateServiceImple  implements StateService{
    
    // 注入用户Mapper
    @Autowired
    private StateMapper stateMapper;
    
    @Autowired
    private ParamAttrManage paramAttrManage;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    MsgSender sender;
    
    //文件下载相关代码
    public static String downloadFile(HttpServletRequest request, HttpServletResponse response) {
      String fileName = "aim_test.txt";// 设置文件名，根据业务需要替换成要下载的文件名
      if (fileName != null) {
        //设置文件路径
        String realPath = "D://aim//";
        File file = new File(realPath , fileName);
        if (file.exists()) {
          response.setContentType("application/force-download");// 设置强制下载不打开
          response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
          byte[] buffer = new byte[1024];
          FileInputStream fis = null;
          BufferedInputStream bis = null;
          try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
              os.write(buffer, 0, i);
              i = bis.read(buffer);
            }
            System.out.println("success");
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            if (bis != null) {
              try {
                bis.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
            if (fis != null) {
              try {
                fis.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        }
      }
      return null;
    }
    
    public static byte[] int2Bytes(int integer)
    {
            byte[] bytes=new byte[4];

            bytes[3]=(byte)(integer>>24);
            bytes[2]=(byte)(integer>>16);
            bytes[1]=(byte)(integer>>8);
            bytes[0]=(byte)integer;

            return bytes;
    }
    
    public static int bytes2Int(byte[] bytes )
    {
        int int1=bytes[0]&0xff;
        int int2=(bytes[1]&0xff)<<8;
        int int3=(bytes[2]&0xff)<<16;
        int int4=(bytes[3]&0xff)<<24;

        return int1|int2|int3|int4;
    }
    
    public static String FormatTime(String sDateTime)
    {
        String sAnOther = sDateTime.substring(0, 4)+"."+sDateTime.substring(5, 7)+"."+sDateTime.substring(8, 10)+" "
                         +sDateTime.substring(11, 16)+"00.000000000";
        return sAnOther;
    }
    
    public static String FormatTime1(String sDateTime)
    {
        String sAnOther = sDateTime.substring(0, 4)+sDateTime.substring(5, 7)+sDateTime.substring(8, 10)
                         +sDateTime.substring(11, 13)+sDateTime.substring(14, 16)+"00";
        return sAnOther;
    }
    
    
    public DataTableModel queryHeartBeatList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String device_id = dataTableMap.get("device_id");
        String userID = dataTableMap.get("userID");  
        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");


        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "0000-00-00 00:00:00";
            EndTime = "9999-99-99 99:99:99";
        }
        /*
        else
        {
            StartTime = FormatTime(StartTime);
            EndTime = FormatTime(EndTime);
        }
        
        Long iStartTime = Long.parseLong(StartTime);
        Long iEndTime   = Long.parseLong(EndTime);
        */
        
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
    
        if ((StringUtils.isEmpty(QueryType))&&(StringUtils.isEmpty(device_id)))
        {
            resList = stateMapper.queryHeartBeatListByUser(start,length,userID,StartTime,EndTime);
            count = stateMapper.queryHeartBeatListCountByUser(userID,StartTime,EndTime);
        }
        else
        {
            resList = stateMapper.queryHeartBeatList(start,length,QueryType,device_id,userID,StartTime,EndTime);
            count = stateMapper.queryHeartBeatListCount(QueryType,device_id,userID,StartTime,EndTime);
        }      
       
        for (int i = 0; i<resList.size(); i++)
        {
            String deviceID = (String)resList.get(i).get("device");       
            Map<String, Object> deviceMap = stateMapper.getDeviceInfoById(deviceID); 
            String deviceName = (String)deviceMap.get("name");
            String factoryId = (String)deviceMap.get("manufacture");
            String factoyName = stateMapper.getFactoryNameByFactoryId(factoryId);  
            resList.get(i).put("deviceName",deviceName);
            resList.get(i).put("factory",factoyName);
        }
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        //paramAttrManage.print();
        return dataTableModel;
    } 
    
    public DataTableModel queryWorkConditionList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String device_id = dataTableMap.get("device_id");
        String userID = dataTableMap.get("userID");  

        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");

        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "000000000000";
            EndTime = "999999999999";
        }      
        else
        {
            StartTime = FormatTime1(StartTime);
            EndTime = FormatTime1(EndTime);
        }
        
        
        //Long iStartTime = Long.parseLong(StartTime);
        //Long iEndTime   = Long.parseLong(EndTime);
        
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
    
        if ((StringUtils.isEmpty(QueryType))&&(StringUtils.isEmpty(device_id)))
        {
            resList = stateMapper.queryWorkConditionListByUser(start,length,userID,StartTime,EndTime);
            count = stateMapper.queryWorkConditionListCountByUser(userID,StartTime,EndTime);
        }
        else
        {
            resList = stateMapper.queryWorkConditionList(start,length,QueryType,device_id,userID,StartTime,EndTime);
            count = stateMapper.queryWorkConditionListCount(QueryType,device_id,userID,StartTime,EndTime);
        }      
       
        for (int i = 0; i<resList.size(); i++)
        {
            String deviceID = (String)resList.get(i).get("device");       
            Map<String, Object> deviceMap = stateMapper.getDeviceInfoById(deviceID); 
            String deviceName = (String)deviceMap.get("name");
            String factoryId = (String)deviceMap.get("manufacture");
            String factoyName = stateMapper.getFactoryNameByFactoryId(factoryId);  
            resList.get(i).put("deviceName",deviceName);
            resList.get(i).put("factory",factoyName);
        }
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    } 
    
    public DataTableModel queryOrderStatusList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String device_id = dataTableMap.get("device_id");
        String userID = dataTableMap.get("userID"); 
        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");

        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "0000-00-00 00:00:00";
            EndTime = "9999-99-99 99:99:99";
        }
        /*
        else
        {
            StartTime = FormatTime(StartTime);
            EndTime = FormatTime(EndTime);
        }
        
        Long iStartTime = Long.parseLong(StartTime);
        Long iEndTime   = Long.parseLong(EndTime);
        */

        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
    
        if ((StringUtils.isEmpty(QueryType))&&(StringUtils.isEmpty(device_id)))
        {
            resList = stateMapper.queryOrderStatusListByUser(start,length,userID,StartTime,EndTime);
            count = stateMapper.queryOrderStatusListCountByUser(userID,StartTime,EndTime);
        }
        else
        {
            resList = stateMapper.queryOrderStatusList(start,length,QueryType,device_id,userID,StartTime,EndTime);
            count = stateMapper.queryOrderStatusListCount(QueryType,device_id,userID,StartTime,EndTime);
        }      
       
        for (int i = 0; i<resList.size(); i++)
        {
            String deviceID = (String)resList.get(i).get("device");       
            String userIDofRecord = (String)resList.get(i).get("user");  
            /*
            Date  timeofRecord = (Date )resList.get(i).get("time"); 
            System.out.println("time");
            System.out.println(timeofRecord);
            String value = null;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            value = dateFormat.format(timeofRecord);
            System.out.println(value);
            */
            
            Map<String, Object> deviceMap = stateMapper.getDeviceInfoById(deviceID); 
            String deviceName = (String)deviceMap.get("name");
            String factoryId = (String)deviceMap.get("manufacture");
            String factoyName = stateMapper.getFactoryNameByFactoryId(factoryId);  
            String userName = stateMapper.getUserNameByUserId(userIDofRecord);  
            resList.get(i).put("deviceName",deviceName);
            resList.get(i).put("factory",factoyName);
            resList.get(i).put("user_name",userName);
            //resList.get(i).put("time_sring",value);
        }
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }     
    
    public DataTableModel queryAlarmList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String device_id = dataTableMap.get("device_id");
        String userID = dataTableMap.get("userID");  
        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");
        String deal = dataTableMap.get("deal");

        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "0000-00-00 00:00:00";
            EndTime =   "9999-11-30 23:59:59";
        }      
        else
        {
            StartTime = StartTime+":00";
            EndTime = EndTime+":00";
        }
        
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
    
        if ((StringUtils.isEmpty(QueryType))&&(StringUtils.isEmpty(device_id)))
        {
            resList = stateMapper.queryAlarmListByUser(start,length,userID,StartTime,EndTime,deal);
            count = stateMapper.queryAlarmListCountByUser(userID,StartTime,EndTime,deal);
        }
        else
        {
            resList = stateMapper.queryAlarmList(start,length,QueryType,device_id,userID,StartTime,EndTime,deal);
            count = stateMapper.queryAlarmListCount(QueryType,device_id,userID,StartTime,EndTime,deal);
        }      
       
        for (int i = 0; i<resList.size(); i++)
        {
            String deviceID = (String)resList.get(i).get("device");       
            Map<String, Object> deviceMap = stateMapper.getDeviceInfoById(deviceID); 
            String deviceName = (String)deviceMap.get("name");
            String factoryId = (String)deviceMap.get("manufacture");
            String factoyName = stateMapper.getFactoryNameByFactoryId(factoryId);  
            resList.get(i).put("deviceName",deviceName);
            resList.get(i).put("factory",factoyName);
        }
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }  

    public DataTableModel queryParameterAttrList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String userID = dataTableMap.get("userID");  
        System.out.println("QueryType");
        System.out.println(QueryType);
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));

        if ((QueryType == null)||(QueryType.equals("0")))
        {
            count = 0;
            resList= new ArrayList();
        }
        else
        {
            resList = stateMapper.queryParameterAttrListByFactory(start,length,QueryType);
            count = stateMapper.queryParameterAttrListCountByFactory(QueryType);
        }
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
        
        
    }  
    
    public DataTableModel queryCommunicateState(Map<String, String> dataTableMap)
    {
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> resList;
        List<Map<String, Object>> deviceList;
        Map<String, Object> deviceMap;
        Map<String, Object> resMap;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String device_id = dataTableMap.get("device_id");
        String CommState = dataTableMap.get("CommState");
        Integer iCommState = Integer.parseInt(CommState);
        String userID = dataTableMap.get("userID");  
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        //首先获取设备列表
        if ((StringUtils.isEmpty(QueryType))&&(StringUtils.isEmpty(device_id)))
        {
            if (0 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser(start,length,userID);
                count = stateMapper.querydeviceListCountByUser(userID);
            }
            else if(1 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser1(start,length,userID);
                count = stateMapper.querydeviceListCountByUser1(userID);
            }
            else //if(2 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser2(start,length,userID);
                count = stateMapper.querydeviceListCountByUser2(userID);
            }
        }
        else
        {
            if (0 == iCommState)
            {
                deviceList = stateMapper.querydeviceList(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount(QueryType,device_id,userID);
            }
            else if(1 == iCommState)
            {
                deviceList = stateMapper.querydeviceList1(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount1(QueryType,device_id,userID);
            }
            else //if(2 == iCommState)
            {
                deviceList = stateMapper.querydeviceList2(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount2(QueryType,device_id,userID);
            }
        } 
        
        for (int i=0; i<deviceList.size();i++)
        {
            deviceMap = deviceList.get(i);
            String factoryid = (String)deviceMap.get("manufacture");
            String factoryName = stateMapper.getFactoryNameByFactoryId(factoryid);
            deviceMap.put("factory", factoryName);
            String deviceid = (String)deviceMap.get("id");
            String commTime = stateMapper.getCommTimeByDeviceId(deviceid);
            deviceMap.put("commTime", commTime);
        }
        
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(deviceList);

        return dataTableModel;
    }  
    
    public DataTableModel querySelfCheckList(Map<String, String> dataTableMap)
    {
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> resList;
        List<Map<String, Object>> deviceList;
        Map<String, Object> deviceMap;
        Map<String, Object> resMap;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String device_id = dataTableMap.get("device_id");
        String CommState = dataTableMap.get("CommState");
        Integer iCommState = Integer.parseInt(CommState);
        String userID = dataTableMap.get("userID");  
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        //首先获取设备列表
        if ((StringUtils.isEmpty(QueryType))&&(StringUtils.isEmpty(device_id)))
        {
            if (0 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser(start,length,userID);
                count = stateMapper.querydeviceListCountByUser(userID);
            }
            else if(1 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser1(start,length,userID);
                count = stateMapper.querydeviceListCountByUser1(userID);
            }
            else //if(2 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser2(start,length,userID);
                count = stateMapper.querydeviceListCountByUser2(userID);
            }
        }
        else
        {
            if (0 == iCommState)
            {
                deviceList = stateMapper.querydeviceList(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount(QueryType,device_id,userID);
            }
            else if(1 == iCommState)
            {
                deviceList = stateMapper.querydeviceList1(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount1(QueryType,device_id,userID);
            }
            else //if(2 == iCommState)
            {
                deviceList = stateMapper.querydeviceList2(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount2(QueryType,device_id,userID);
            }
        } 
        
        for (int i=0; i<deviceList.size();i++)
        {
            deviceMap = deviceList.get(i);
            String factoryid = (String)deviceMap.get("manufacture");
            String factoryName = stateMapper.getFactoryNameByFactoryId(factoryid);
            deviceMap.put("factory", factoryName);
        }
        
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(deviceList);

        return dataTableModel;
    }
    
    //queryParameterList
    //此处以设备为标准，不以参数为标准，因为第一次有可能参数还未获取
    public DataTableModel queryParameterList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> resList;
        List<Map<String, Object>> deviceList;
        Map<String, Object> deviceMap;
        Map<String, Object> resMap;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String device_id = dataTableMap.get("device_id");
        String CommState = dataTableMap.get("CommState");
        Integer iCommState = Integer.parseInt(CommState);
        String userID = dataTableMap.get("userID");  
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        //首先获取设备列表
        if ((StringUtils.isEmpty(QueryType))&&(StringUtils.isEmpty(device_id)))
        {
            if (0 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser(start,length,userID);
                count = stateMapper.querydeviceListCountByUser(userID);
            }
            else if(1 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser1(start,length,userID);
                count = stateMapper.querydeviceListCountByUser1(userID);
            }
            else //if(2 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser2(start,length,userID);
                count = stateMapper.querydeviceListCountByUser2(userID);
            }
        }
        else
        {
            if (0 == iCommState)
            {
                deviceList = stateMapper.querydeviceList(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount(QueryType,device_id,userID);
            }
            else if(1 == iCommState)
            {
                deviceList = stateMapper.querydeviceList1(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount1(QueryType,device_id,userID);
            }
            else //if(2 == iCommState)
            {
                deviceList = stateMapper.querydeviceList2(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount2(QueryType,device_id,userID);
            }
        } 
        
        resList = new LinkedList<>();
        //遍历设备列表，获取对应的参数
        for (int i=0; i<deviceList.size();i++)
        {
            deviceMap = deviceList.get(i);
            String deviceId = (String)deviceMap.get("id");
            Integer comm_state = (Integer)deviceMap.get("comm_state");
            String deviceName = (String)deviceMap.get("name");
            String factoryid = (String)deviceMap.get("manufacture");
            String factoryName = stateMapper.getFactoryNameByFactoryId(factoryid);
            //resList.get(i).put("device", deviceName);
            resMap= new HashMap<String, Object>();
            resMap.put("device", deviceName);
            resMap.put("factory", factoryName);
            Map<String, Object> paramMap = stateMapper.getParamByDeviceId(deviceId);
            if (paramMap!=null)
            {
                resMap.put("work_status_time", (int)paramMap.get("work_status_time"));
                resMap.put("work_data_collection_interval", (int)paramMap.get("work_data_collection_interval"));
                resMap.put("wave_current_time", (int)paramMap.get("wave_current_time"));
                resMap.put("wave_current_threshold", (int)paramMap.get("wave_current_threshold"));
                resMap.put("wave_current_time_collection", (int)paramMap.get("wave_current_time_collection"));
                resMap.put("wave_current_freq_collection", (int)paramMap.get("wave_current_freq_collection"));
                resMap.put("pf_current_time", (int)paramMap.get("pf_current_time"));
                resMap.put("pf_current_threshold", (int)paramMap.get("pf_current_threshold"));
                resMap.put("pf_current_time_collection", (int)paramMap.get("pf_current_time_collection"));
                resMap.put("pf_current_freq_collection", (int)paramMap.get("pf_current_freq_collection"));
                resMap.put("id", deviceId);
                resMap.put("comm_state", comm_state);
            }
            else
            {
                resMap.put("work_status_time", "0");
                resMap.put("work_data_collection_interval", "0");
                resMap.put("wave_current_time", "0");
                resMap.put("wave_current_threshold","0");
                resMap.put("wave_current_time_collection", "0");
                resMap.put("wave_current_freq_collection", "0");
                resMap.put("pf_current_time","0");
                resMap.put("pf_current_threshold", "0");
                resMap.put("pf_current_time_collection","0");
                resMap.put("pf_current_freq_collection","0");
                resMap.put("id",deviceId);
                resMap.put("comm_state", comm_state);
            }

            resList.add(resMap);
        }
    /*
        if (StringUtils.isEmpty(QueryType))
        {
            resList = stateMapper.queryParameterListByUser(start,length,userID);
            count = stateMapper.queryParameterListCountByUser(userID);
        }
        else
        {
            resList = stateMapper.queryParameterList(start,length,QueryType,userID);
            count = stateMapper.queryParameterListCount(QueryType,userID);
        }      
       
        for (int i = 0; i<resList.size(); i++)
        {            
            String deviceID = (String)resList.get(i).get("device");       
            Map<String, Object> deviceMap = stateMapper.getDeviceInfoById(deviceID); 
            String deviceName = (String)deviceMap.get("name");
            String factoryId = (String)deviceMap.get("manufacture");
            String factoyName = stateMapper.getFactoryNameByFactoryId(factoryId);  
            resList.get(i).put("device",deviceName);
            resList.get(i).put("factory",factoyName);
            
            
            //以下是要迁移到更多参数里面单独处理
            //---------------------------------------------------------------
            //blob
            byte[] privateData = (byte[])resList.get(i).get("private_data"); 
            int paramNum = (int)(resList.get(i).get("number"));
            Map<String, Object> privateparaMap = new HashMap<String, Object>();
            
            for (int j = 0;j<paramNum; j++)
            {
                ParamAttr paramAttr = paramAttrManage.getParamAttr(factoryId, j+11);
                if(paramAttr.getType()==1)//此处先假设参数类型为int型
                {
                    byte[] ParamUnit = {privateData[4*j],privateData[4*j+1],privateData[4*j+2],privateData[4*j+3]};
                    int intpara = bytes2Int(ParamUnit);
                    String paraName = paramAttr.getName();
                    privateparaMap.put(paraName, intpara);
                }
            }
            
            System.out.println(privateparaMap);
        }
           */
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }  
    
    //queryControlDeviceList
    public DataTableModel queryControlDeviceList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        List<Map<String, Object>> deviceList;
        Map<String, Object> deviceMap;
        Map<String, Object> resMap;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("factory");
        String device_id = dataTableMap.get("device_id");
        String CommState = dataTableMap.get("CommState");
        Integer iCommState = Integer.parseInt(CommState);
        String userID = dataTableMap.get("userID");  
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        //首先获取设备列表
        if ((StringUtils.isEmpty(QueryType))&&(StringUtils.isEmpty(device_id)))
        {
            if (0 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser(start,length,userID);
                count = stateMapper.querydeviceListCountByUser(userID);
            }
            else if(1 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser1(start,length,userID);
                count = stateMapper.querydeviceListCountByUser1(userID);
            }
            else //if(2 == iCommState)
            {
                deviceList = stateMapper.querydeviceListByUser2(start,length,userID);
                count = stateMapper.querydeviceListCountByUser2(userID);
            }
        }
        else
        {
            if (0 == iCommState)
            {
                deviceList = stateMapper.querydeviceList(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount(QueryType,device_id,userID);
            }
            else if(1 == iCommState)
            {
                deviceList = stateMapper.querydeviceList1(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount1(QueryType,device_id,userID);
            }
            else //if(2 == iCommState)
            {
                deviceList = stateMapper.querydeviceList2(start,length,QueryType,device_id,userID);
                count = stateMapper.querdeviceListCount2(QueryType,device_id,userID);
            }
        } 
        
        for (int i=0; i<deviceList.size();i++)
        {
            deviceMap = deviceList.get(i);
            String deviceId = (String)deviceMap.get("id");
            Integer comm_state = (Integer)deviceMap.get("comm_state");
            String deviceName = (String)deviceMap.get("name");
            String factoryid = (String)deviceMap.get("manufacture");
            String factoryName = stateMapper.getFactoryNameByFactoryId(factoryid);

            deviceMap.put("device", deviceName);
            deviceMap.put("factory", factoryName);
        }
                       
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(deviceList);

        return dataTableModel;
    }  
    
    public Map<String, Object> checkFactoryLogin(String username, String password ,String factoryId)
    {
        Map<String, Object> rstMap = new HashMap<String, Object>();
        Map<String, Object> factoryMap = new HashMap<String, Object>();
        factoryMap = stateMapper.getFactoryById(factoryId);
        if (factoryMap != null) {
            if ((factoryMap.get("login_name").equals(username))&&(factoryMap.get("login_password").equals(password))) {
                rstMap.put("status", "success");
                rstMap.put("msg", "验证成功");
            } else {
                rstMap.put("status", "fail");
                rstMap.put("msg", "验证失败");
            }
        } else {
            rstMap.put("status", "fail");
            rstMap.put("msg", "厂家不存在");
        }
        return rstMap;       
    }
    
    public void saveParamAttr(Map<String, Object> paramMap) {
        String towerId = (String) paramMap.get("recordId");
        if (StringUtils.isEmpty(towerId)) {
            String recordId = StringUtils.getUUId();
            paramMap.put("recordId", recordId);
            stateMapper.addParamAttr(paramMap);
            ParamAttr paramAttr = stateMapper.getParamAttrIdById(recordId);
            System.out.println("-----------------beforeadd");
            paramAttrManage.print();
            paramAttrManage.addParamAttr(paramAttr);
            System.out.println("-----------------afteradd");
            paramAttrManage.print();
        } else {
            stateMapper.updateParamAttr(paramMap);
            ParamAttr paramAttr = stateMapper.getParamAttrIdById(towerId);
            System.out.println("-----------------beforeupdate");
            paramAttrManage.print();
            paramAttrManage.updateParamAttr(paramAttr);
            System.out.println("-----------------afterupdate");
            paramAttrManage.print();
        }
    }
    
    public String getDeviceLogNameById(String recordId)
    {
        return stateMapper.getDeviceLogNameById(recordId);
    }
    
    //sendDownloadLogById
    public int sendDownloadLogById(String recordId, String userId)
    {
        //return 0;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
        sender.downloadLog(uuid,userId,recordId,protocol_version);
        int loopCount = 0; 
        while(loopCount<100)
        {
            try {
                Thread.sleep(5000);
                System.out.println("-----------------loopCount");
                System.out.println(loopCount);
               }catch(InterruptedException e){
                e.printStackTrace();
               }
            
            String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
            stateMapper.addReadParamOrder(tempuuid,1,"2","2");
            stateMapper.Commit();
            stateMapper.deleteReadParamOrder(tempuuid);
            stateMapper.Commit();

            Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
            if (stateMap != null)
            {
                return (int)stateMap.get("state");   
            }
            loopCount++;
        }    
        return -1;
    }
    
    public int readSelfCheckById(String recordId, String userId)
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
        sender.readSelfCheckInfo(uuid,userId,recordId,protocol_version);
        int loopCount = 0; 
        while(loopCount<13)
        {
            try {
                Thread.sleep(3000);
                System.out.println("-----------------loopCount");
                System.out.println(loopCount);
               }catch(InterruptedException e){
                e.printStackTrace();
               }
            
            String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
            stateMapper.addReadParamOrder(tempuuid,1,"2","2");
            stateMapper.Commit();
            stateMapper.deleteReadParamOrder(tempuuid);
            stateMapper.Commit();

            Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
            if (stateMap != null)
            {
                return (int)stateMap.get("state");   
            }
            loopCount++;
        }    
        return -1;
    }
    
    //addReadInfoTypeOrderByDeviceIdanduserId
    public int addReadInfoTypeOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            //sender.readDevParameter(uuid,userId,recordId,protocol_version);
            sender.readTiltPara(uuid, userId, recordId);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;
        }
        else {
            return -1;
        }
    }    
    
    
    public int addReadDeviceTimeOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //System.out.println(uuid);
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            //sender.readDevParameter(uuid,userId,recordId,protocol_version);
            sender.getTiltTime(uuid, userId, recordId);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                //int temp = (int)(Math.random()*1000);
                //System.out.println(temp);
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1; 
        }
        else {
            return -1;
        }
    }    
    
    public int addReadTiltDataOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            //sender.readDevParameter(uuid,userId,recordId,protocol_version);
            sender.readTiltData(uuid, userId, recordId);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                //int temp = (int)(Math.random()*1000);
                //System.out.println(temp);
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;
        }
        else {
            return -1;
        }       
    }        
    
    //addReadAlarmThresholdOrderByDeviceIdanduserId
    public int addReadAlarmThresholdOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String workmode = "default";
        //String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            sender.readTiltPara(uuid, userId, recordId);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;
        }
        else {
            return -1;
        }
    }    
      
    public int addReadSampleRateOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            //sender.readDevParameter(uuid,userId,recordId,protocol_version);
            sender.getTiltSample(uuid, userId, recordId);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                //int temp = (int)(Math.random()*1000);
                //System.out.println(temp);
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;
        }
        else {
            return -1;
        }       
    }    
    
    public int addReadOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //System.out.println(uuid);
        int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
        sender.readDevParameter(uuid,userId,recordId,protocol_version);
        int loopCount = 0; 
        while(loopCount<13)
        {
            try {
                Thread.sleep(3000);
                System.out.println("-----------------loopCount");
                System.out.println(loopCount);
               }catch(InterruptedException e){
                e.printStackTrace();
               }
            
            String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
            stateMapper.addReadParamOrder(tempuuid,1,"2","2");
            stateMapper.Commit();
            stateMapper.deleteReadParamOrder(tempuuid);
            stateMapper.Commit();
            //int temp = (int)(Math.random()*1000);
            //System.out.println(temp);
            Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
            if (stateMap != null)
            {
                return (int)stateMap.get("state");   
            }
            loopCount++;
        }    
        return -1;
    }
    
    //addTimingOrderByDeviceIdanduserId
    public int addTimingOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //System.out.println(uuid);
        int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
        //此处要改为校时装置命令发送接口
        sender.readDevParameter(uuid,userId,recordId,protocol_version);
        int loopCount = 0; 
        while(loopCount<13)
        {
            try {
                Thread.sleep(3000);
                System.out.println("-----------------loopCount");
                System.out.println(loopCount);
               }catch(InterruptedException e){
                e.printStackTrace();
               }
            
            String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
            stateMapper.addReadParamOrder(tempuuid,1,"2","2");
            stateMapper.Commit();
            stateMapper.deleteReadParamOrder(tempuuid);
            stateMapper.Commit();
            Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
            if (stateMap != null)
            {
                return (int)stateMap.get("state");   
            }
            loopCount++;
        }    
        return -1;
    }
    
    //addUpdateOrderByDeviceIdanduserId
    public int addUpdateOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
        //此处要改为升级命令发送接口
        sender.resetDevice(uuid,userId,recordId,protocol_version);
        int loopCount = 0; 
        while(loopCount<20)
        {
            try {
                Thread.sleep(3000);
                System.out.println("-----------------loopCount");
                System.out.println(loopCount);
               }catch(InterruptedException e){
                e.printStackTrace();
               }
            String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
            stateMapper.addReadParamOrder(tempuuid,1,"2","2");
            stateMapper.Commit();
            stateMapper.deleteReadParamOrder(tempuuid);
            stateMapper.Commit();
            Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
            if (stateMap != null)
            {
                return (int)stateMap.get("state");   
            }
            loopCount++;
        }    
        return -1;
    }
    
    public int addResetTiltDataByIdOrderByDeviceIdanduserId(String recordId, String userId)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            sender.resetTiltData(uuid,userId,recordId);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;
        }
        else {
            return -1;
        }
    }    
    
    //addResetOrderByDeviceIdanduserId
    public int addResetOrderByDeviceIdanduserId(String recordId, String userId, int resetMode)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            sender.resetTiltDevice(uuid,userId,recordId,resetMode);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;
        }
        else {
            return -1;
        }
    }
    
    public int getAuhorityByUser(String userId)
    {
        return stateMapper.getAuhorityByUser(userId);
    }
    
    public String getPasswordByDevice(String DeviceId)
    {
        return stateMapper.getPasswordByDevice(DeviceId);
    }
    
    public int addSetDeviceTimeByDeviceIdanduserId(String recordId, String userId, String Time_Stamp)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                int timeStrLen = Time_Stamp.length();
                if (16 == timeStrLen)
                {
                    Time_Stamp = Time_Stamp+":00";
                }
                
                //System.out.println(timeStrLen);
                Date date = format.parse(Time_Stamp); 
                int deviceTime = (int)(date.getTime()/1000);
                //System.out.println("---------Time_Stamp----------");
                //System.out.println(Time_Stamp);
                //System.out.println(deviceTime);
                //sender.setDevParameter(uuid, userId,recordId, ValueLst,protocol_version);
                sender.setTiltTime(uuid, userId, recordId,deviceTime);
               }catch(Exception e){
                e.printStackTrace();
               }
            
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;    
        }
        else {
            return -1;
        }       
    }
    
    public int addSetInfoTypeByDeviceIdanduserId(String recordId, String userId, int Value){
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            System.out.println("-------------------");
            System.out.println(Value);
            //sender.setDevParameter(uuid, userId,recordId, ValueLst,protocol_version);
            sender.setTiltDevInfo(uuid, userId, recordId,Value);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;  
        }
        else {
            return -1;
        }
    }
    
    public int addSetSampleRateByDeviceIdanduserId(String recordId, String userId, short val1, short val2, short val3, byte val4)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
        byte iChangeFlag = 0;
        Map<String, Object> SampleRateMap = stateMapper.getSampleRateByDeviceid(recordId);
        if (null!=SampleRateMap)
        {
            int Main_Time = (int)SampleRateMap.get("Main_Time");
            int Sample_Count = (int)SampleRateMap.get("Sample_Count");
            int Sample_Frequency = (int)SampleRateMap.get("Sample_Frequency");
            int Heartbeat_Time = (int)SampleRateMap.get("Heartbeat_Time");
            if (val1 != Main_Time)
            {
                iChangeFlag +=1;
            }
            if (val2 != Sample_Count)
            {
                iChangeFlag +=2;
            }
            if (val3 != Sample_Frequency)
            {
                iChangeFlag +=4;
            }
            if (val4 != Heartbeat_Time)
            {
                iChangeFlag +=8;
            }
        }
        System.out.println("-------------------");
        System.out.println(iChangeFlag);
        //sender.setDevParameter(uuid, userId,recordId, ValueLst,protocol_version);
        sender.setTiltSample(uuid, userId, recordId, val1, val2, val3, val4, iChangeFlag);
        int loopCount = 0; 
        while(loopCount<13)
        {
            try {
                Thread.sleep(3000);
                System.out.println("-----------------loopCount");
                System.out.println(loopCount);
               }catch(InterruptedException e){
                e.printStackTrace();
               }
            String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
            stateMapper.addReadParamOrder(tempuuid,1,"2","2");
            stateMapper.Commit();
            stateMapper.deleteReadParamOrder(tempuuid);
            stateMapper.Commit();
            Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
            if (stateMap != null)
            {
                return (int)stateMap.get("state");   
            }
            loopCount++;
        }    
        return -1;    
        }
        else {
            return -1;
        }     
    }
    
    public int addSetAlarmthresholdByDeviceIdAnduserId(String recordId, String userId, float Value0, float Value1, float Value2, float Value3, float Value4, float Value5, float Value6, float Value7, float Value8, float Value9)
    {
        String workmode = TestProperties.getProperties_1("path.properties","workmode");
        if (workmode.equals("default"))
        {
            return 0;
        }
        else if (workmode.equals("connect")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            sender.setTiltPara(uuid, userId, recordId,
                    Value0,Value1,
                    Value2,Value3,
                    Value4,Value5,
                    Value6,Value7,
                    Value8,Value9);
            HashMap<String, Object> result = new HashMap<>();
            result.put("device", recordId);
            result.put("Value0", Value0);
            result.put("Value1", Value1);
            result.put("Value2", Value2);
            result.put("Value3", Value3);
            result.put("Value4", Value4);
            result.put("Value5", Value5);
            result.put("Value6", Value6);
            result.put("Value7", Value7);
            result.put("Value8", Value8);
            result.put("Value9", Value9);
            stateMapper.UpdateShreshold(result);
            return 0;
            /*
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
            sender.setTiltPara(uuid, userId, recordId,
                    Value0,Value1,
                    Value2,Value3,
                    Value4,Value5,
                    Value6,Value7,
                    Value8,Value9);
            int loopCount = 0; 
            while(loopCount<13)
            {
                try {
                    Thread.sleep(3000);
                    System.out.println("-----------------loopCount");
                    System.out.println(loopCount);
                   }catch(InterruptedException e){
                    e.printStackTrace();
                   }
                String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
                stateMapper.addReadParamOrder(tempuuid,1,"2","2");
                stateMapper.Commit();
                stateMapper.deleteReadParamOrder(tempuuid);
                stateMapper.Commit();
                Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
                if (stateMap != null)
                {
                    return (int)stateMap.get("state");   
                }
                loopCount++;
            }    
            return -1;  
            */
        }
        else {
            return -1;
        }      
    }
    
    //addSetOrderByDeviceIdanduserId
    public int addSetOrderByDeviceIdanduserId(String recordId, String userId, String content,int[] ValueLst)
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int protocol_version = stateMapper.getProtocolVerByDeviceId(recordId);
        sender.setDevParameter(uuid, userId,recordId, ValueLst,protocol_version);
        int loopCount = 0; 
        while(loopCount<10)
        {
            try {
                Thread.sleep(3000);
                System.out.println("-----------------loopCount");
                System.out.println(loopCount);
               }catch(InterruptedException e){
                e.printStackTrace();
               }
            String tempuuid = UUID.randomUUID().toString().replaceAll("-", "");
            stateMapper.addReadParamOrder(tempuuid,1,"2","2");
            stateMapper.Commit();
            stateMapper.deleteReadParamOrder(tempuuid);
            stateMapper.Commit();
            Map<String, Object> stateMap = stateMapper.getStateByOrderid(uuid);
            if (stateMap != null)
            {
                return (int)stateMap.get("state");   
            }
            loopCount++;
        }    
        return -1;
    }
    
    public Map<String, Object> getParamAttrById(String userId) {
        return stateMapper.getParamAttrById(userId);
    }
    
    //getParamById
    public Map<String, Object> getParamById(String userId) {
        Map<String, Object> ParamMap = stateMapper.getParamById(userId);//此处可能为空，需处理
        Map<String, Object> privateparaMap = new HashMap<String, Object>();
        if ((ParamMap != null)&&(ParamMap.get("private_data")!=null)&&((((byte[])ParamMap.get("private_data")).length/4)==((int)(ParamMap.get("number"))-10)))
        {
            byte[] privateData = (byte[])ParamMap.get("private_data"); 
            int paramNum = (int)(ParamMap.get("number"));
            String deviceID = (String)ParamMap.get("device");       
            Map<String, Object> deviceMap = stateMapper.getDeviceInfoById(deviceID); 
            String factoryId = (String)deviceMap.get("manufacture");

            
            for (int j = 0;j<paramNum; j++)
            {
                ParamAttr paramAttr = paramAttrManage.getParamAttr(factoryId, j+11);
                if(paramAttr.getType()==1)//此处先假设参数类型为int型
                {
                    byte[] ParamUnit = {privateData[4*j],privateData[4*j+1],privateData[4*j+2],privateData[4*j+3]};
                    int intpara = bytes2Int(ParamUnit);
                    String paraName = paramAttr.getName();
                    privateparaMap.put(paraName, intpara);
                }
                else if (paramAttr.getType()==0)
                {
                    float floatpara = 0.1F;
                    String paraName = paramAttr.getName();
                    privateparaMap.put(paraName, floatpara);
                }
            }
            
            return privateparaMap;
        }
        else
        {
            Map<String, Object> deviceMap = stateMapper.getDeviceInfoById(userId);
            String factoryId1 = (String)deviceMap.get("manufacture");
            List<Map<String, Object>> ParamMapList = stateMapper.getParamInfoListByfactoryId(factoryId1);
            for (int k = 0; k<ParamMapList.size();k++)
            {
                Map<String, Object> defaultParamMap = ParamMapList.get(k);
                if ((boolean)defaultParamMap.get("type"))
                {
                    String paraName = (String)(defaultParamMap.get("name"));
                    int intpara = 0;
                    privateparaMap.put(paraName, intpara);
                }
                else
                {
                    String paraName = (String)(defaultParamMap.get("name"));
                    float floatpara = 0.1F;
                    privateparaMap.put(paraName, floatpara);
                }
            }
            return privateparaMap;
        }
    }
    
    //getcommonParamById
    public Map<String, Object> getcommonParamById(String userId) {
        Map<String, Object> ParamMap = stateMapper.getParamById(userId);//此处可能为空，需处理
        if (ParamMap == null)
        {
            ParamMap = new HashMap<String, Object>();
            ParamMap.put("wave_current_time",0);
            ParamMap.put("wave_current_threshold",0);
            ParamMap.put("wave_current_time_collection",0);
            ParamMap.put("wave_current_freq_collection",0);
            ParamMap.put("pf_current_time",0);
            ParamMap.put("pf_current_threshold",0);
            ParamMap.put("pf_current_time_collection",0);
            ParamMap.put("pf_current_freq_collection",0);
            ParamMap.put("work_status_time",0);
            ParamMap.put("work_data_collection_interval",0);
        }
        
        return ParamMap;
    }
    
    public String getTime_StampByDeviceId(String regulatorId) {
        String Time_Stamp = stateMapper.getTime_StampByDeviceId(regulatorId);//此处可能为空，需处理
        if (Time_Stamp == null)
        {
            Time_Stamp = "";
        }
        
        return Time_Stamp;
    }    
    
    public int getInfoTypeByDeviceId(String regulatorId)
    {
        int InfoType = 0;
        try {
        
            InfoType = stateMapper.getInfoTypeByDeviceId(regulatorId);//此处可能为空，需处理

        } catch (Exception e) {
            // TODO: handle exception
        }
        return InfoType;
    }
    
    public Map<String, Object> getTiltDataByDeviceId(String regulatorId){
        Map<String, Object> ParamMap = stateMapper.getTiltDataByDeviceId(regulatorId);//此处可能为空，需处理
        if (ParamMap == null)
        {
            ParamMap = new HashMap<String, Object>();
            ParamMap.put("inclination",0.0);
            ParamMap.put("inclination_x",0.0);
            ParamMap.put("inclination_y",0.0);
            ParamMap.put("angle_x",0.0);
            ParamMap.put("angle_y",0.0);
        }
        
        return ParamMap;
    }  
    
    //getThresholdByDeviceId
    public Map<String, Object> getThresholdByDeviceId(String userId) {
        Map<String, Object> ParamMap = stateMapper.getThresholdByDeviceId(userId);//此处可能为空，需处理
        if (ParamMap == null)
        {
            ParamMap = new HashMap<String, Object>();
            ParamMap.put("IncUp",0.0);
            ParamMap.put("IncLw",0.0);
            ParamMap.put("IncXUp",0.0);
            ParamMap.put("IncXLw",0.0);
            ParamMap.put("IncYUp",0.0);
            ParamMap.put("IncYLw",0.0);
            ParamMap.put("AngXUp",0.0);
            ParamMap.put("AngXLw",0.0);
            ParamMap.put("AngYUp",0.0);
            ParamMap.put("AngYLw",0.0);
        }
        
        return ParamMap;
    }    
    
    public Map<String, Object> getSampleRateByDeviceId(String regulatorId){
        Map<String, Object> ParamMap = stateMapper.getSampleRateByDeviceId(regulatorId);//此处可能为空，需处理
        if (ParamMap == null)
        {
            ParamMap = new HashMap<String, Object>();
            ParamMap.put("Main_Time",0);
            ParamMap.put("Sample_Count",0);
            ParamMap.put("Sample_Frequency",0);
            ParamMap.put("Heartbeat_Time",0);
        }
        
        return ParamMap;
    }  
    
    //getParamNameById
    public List<String> getParamNameById(String regulatorId)
    {
        Map<String, Object> deviceMap = stateMapper.getDeviceInfoById(regulatorId);
        String factoryId = (String)deviceMap.get("manufacture");
        List<String> ParamNameList = stateMapper.getParamNameListByfactoryId(factoryId);
        return ParamNameList;
    }
    
    public boolean deleteParamAttrById(String regulatorId)
    {
        System.out.println("-----------------before");
        paramAttrManage.print();
        //相应的map内存删除操作
        ParamAttr paramAttr = stateMapper.getParamAttrIdById(regulatorId);
        System.out.println(paramAttr);
        paramAttrManage.deleteParamAttr(paramAttr);
        System.out.println("-----------------after");
        paramAttrManage.print();
        stateMapper.deleteParamAttrById(regulatorId);
        return true;
    }
  
    public void setDealAlarmById(String userId) {
        stateMapper.setDealAlarmById(userId);
    }
    
    public Map<String, Object> queryWavePwd(Map<String, Object> paramMap)
    {
        String userId = (String)paramMap.get("userId");
        String wave_pwd = (String)paramMap.get("wave_pwd");
        Map<String, Object> rstMap = new HashMap<String, Object>();
        String  wave_pwdofdb = userMapper.findUserWavePwdById(userId);

            if (wave_pwd.equals(wave_pwdofdb)) {
                rstMap.put("status", "success");
                rstMap.put("msg", "查询录波文件成功");
            } else {
                rstMap.put("status", "fail");
                rstMap.put("msg", "查询录波文件失败");
            }
        return rstMap;
    }
}
