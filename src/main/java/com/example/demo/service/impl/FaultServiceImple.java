package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.po.DataTableModel;
import com.example.demo.service.FaultService;
import com.example.demo.mapper.FaultMapper;
import com.example.demo.mapper.InfoMapper;

import util.aes.StringUtils;

@Service
@Transactional
public class FaultServiceImple implements FaultService{
  
    // 注入用户Mapper
    @Autowired
    private FaultMapper faultMapper;
    private InfoMapper infoMapper;
    
    public static String FormatFaultTime(String sDateTime)
    {
        String sAnOther = sDateTime.substring(0, 4)+sDateTime.substring(5, 7)+sDateTime.substring(8, 10)
                         +sDateTime.substring(11, 13)+sDateTime.substring(14, 16);
        return sAnOther;
    }
    
    public static String FormatTime(String sDateTime)
    {
        String sAnOther = sDateTime.substring(0, 4)+sDateTime.substring(5, 7)+sDateTime.substring(8, 10)
                         +sDateTime.substring(11, 13)+sDateTime.substring(14, 16)+"00";
        return sAnOther;
    }
    
    public DataTableModel queryAlarmList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("device", "36572231834010002");
        resultMap.put("line", "横洪I线001杆塔");
        resultMap.put("factory", "201910272336");
        resultMap.put("tower", "201910272330");
        resultMap.put("phase", "横洪I线001观测点");
        resultMap.put("start_time", "杆塔1%倾斜");
        resultMap.put("sample_rate", "未处理");
        
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        resList.add(resultMap);
        Integer count = 1;
        String sEcho = dataTableMap.get("sEcho");
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);
        return dataTableModel;
    }
    
    public DataTableModel queryWaveInfoList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("device", "36572231834010002");
        resultMap.put("line", "横洪I线001杆塔");
        resultMap.put("factory", "201910272336");
        resultMap.put("tower", "201910272330");
        resultMap.put("phase", "10");
        resultMap.put("start_time", "4");
        resultMap.put("sample_rate", "16");
        resultMap.put("wave_length", "21");
        resultMap.put("wave_length", "12");
        
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        resList.add(resultMap);
        Integer count = 1;
        String sEcho = dataTableMap.get("sEcho");
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);
        return dataTableModel;
        /*
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String factory = dataTableMap.get("factory"); 
        String line = dataTableMap.get("line"); 
        String userID = dataTableMap.get("userID");  

        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
    
        if (StringUtils.isEmpty(factory))
        {
            factory = "";
        }
        if (StringUtils.isEmpty(line))
        {
            resList = faultMapper.queryWaveListByUser(start,length,factory,userID);
            count = faultMapper.queryWaveListCountByUser(factory,userID);
        }
        else
        {
            resList = faultMapper.queryWaveList(start,length,factory,line);
            count = faultMapper.queryWaveListCount(factory,line);
        }      
       
        for (int i = 0; i<resList.size(); i++)
        {
        String device1 = (String)resList.get(i).get("device");
        Map<String, Object> deviceMap = faultMapper.getDeviceById(device1);
        String towerID = (String)deviceMap.get("tower_id");
        String factoryID = (String)deviceMap.get("manufacture");
        int phase = (int)deviceMap.get("ied_phase");
        String lineID = (String)deviceMap.get("line_name");
        
        String deviceName = (String)deviceMap.get("name");
        String towerName = faultMapper.getTowerById(towerID);
        String factoryName = faultMapper.getFactoryById(factoryID);
        String sample_rate = faultMapper.getFactorySRById(factoryID);
        String lineName = faultMapper.getLineById(lineID);
        resList.get(i).put("device",deviceName);
        resList.get(i).put("line",lineName);
        resList.get(i).put("factory",factoryName);
        resList.get(i).put("tower",towerName);
        resList.get(i).put("phase",phase);
        resList.get(i).put("sample_rate",sample_rate);
        }
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
        */
    }
    
    public DataTableModel queryFaultListInGis(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        System.out.println(dataTableMap);
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        String userID = dataTableMap.get("userID"); 
        String line = dataTableMap.get("line");
        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");
        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "000000000000";
            EndTime = "999999999999";
        }
        else
        {
            StartTime = FormatFaultTime(StartTime);
            EndTime = FormatFaultTime(EndTime);
        }
        
        if (StringUtils.isEmpty(line))//不用线路搜索条件
        {
            resList = faultMapper.queryFaultListByUserAndInterval(start,length,userID,StartTime,EndTime);
            count = faultMapper.queryFaultListCountByUserAndInterval(userID,StartTime,EndTime);
        }
        else//指定线路
        {
            resList = faultMapper.queryFaultListByLineAndInterval(start,length,line,StartTime,EndTime);
            count = faultMapper.queryFaultListCountByLineAndInterval(line,StartTime,EndTime);
        }
        
        if (null!=resList)
        {
           for (int i = 0; i<resList.size(); i++)
          {
             String lineID = (String)resList.get(i).get("line");       
             String lineName = faultMapper.getLineById(lineID);       
             resList.get(i).put("lineName",lineName);
          }
        }
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }    
    
    //queryTowerTileStateList
    public DataTableModel queryTowerTileStateList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        System.out.println(dataTableMap);
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        String userID = dataTableMap.get("userID"); 
        String factory = dataTableMap.get("factory");
        String line = dataTableMap.get("line");
        String device = dataTableMap.get("device");
        String deal = dataTableMap.get("deal");
        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");
        
        if (StringUtils.isEmpty(factory))
        {
            factory = "";
        }
        if (StringUtils.isEmpty(line))
        {
            line = "";
        }
        if (StringUtils.isEmpty(device))
        {
            device = "";
        }
        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "000000000000";
            EndTime = "999999999999";
        }
        else
        {
            StartTime = FormatFaultTime(StartTime);
            EndTime = FormatFaultTime(EndTime);
        }
        
        resList = faultMapper.queryTowerTileStateList(start,length,userID,factory,line,device,deal,StartTime,EndTime);
        count = faultMapper.queryTowerTileStateListCount(userID,factory,line,device,deal,StartTime,EndTime);
        
        if (null!=resList)
        {
           for (int i = 0; i<resList.size(); i++)
          {
             String deviceID = (String)resList.get(i).get("device");       
             String deviceName = faultMapper.getDeviceNameById(deviceID);       
             resList.get(i).put("deviceName",deviceName);
             String towerID = (String)resList.get(i).get("tower");       
             String towerName = faultMapper.getTowerById(towerID);       
             resList.get(i).put("towerName",towerName);
          }
        }     
        
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }       
    
    //queryTowerTileAlarmList
    public DataTableModel queryTowerTileAlarmList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        System.out.println(dataTableMap);
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        String userID = dataTableMap.get("userID"); 
        String factory = dataTableMap.get("factory");
        String line = dataTableMap.get("line");
        String device = dataTableMap.get("device");
        String deal = dataTableMap.get("deal");
        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");
        
        if (StringUtils.isEmpty(factory))
        {
            factory = "";
        }
        if (StringUtils.isEmpty(line))
        {
            line = "";
        }
        if (StringUtils.isEmpty(device))
        {
            device = "";
        }
        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "000000000000";
            EndTime = "999999999999";
        }
        else
        {
            StartTime = FormatFaultTime(StartTime);
            EndTime = FormatFaultTime(EndTime);
        }
        
        resList = faultMapper.queryTowerTileAlarmList(start,length,userID,factory,line,device,deal,StartTime,EndTime);
        count = faultMapper.queryTowerTileAlarmListCount(userID,factory,line,device,deal,StartTime,EndTime);
        
        if (null!=resList)
        {
           for (int i = 0; i<resList.size(); i++)
          {
             String deviceID = (String)resList.get(i).get("device");       
             String deviceName = faultMapper.getDeviceNameById(deviceID);       
             resList.get(i).put("deviceName",deviceName);
             String towerID = (String)resList.get(i).get("tower");       
             String towerName = faultMapper.getTowerById(towerID);       
             resList.get(i).put("towerName",towerName);
          }
        }     
        
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }       
    
    public DataTableModel queryTowerTileParamList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        //System.out.println(dataTableMap);
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        String userID = dataTableMap.get("userID"); 
        String factory = dataTableMap.get("factory");
        String line = dataTableMap.get("line");
        String device = dataTableMap.get("device");
        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");
        
        if (StringUtils.isEmpty(factory))
        {
            factory = "";
        }
        if (StringUtils.isEmpty(line))
        {
            line = "";
        }
        if (StringUtils.isEmpty(device))
        {
            device = "";
        }
        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "000000000000";
            EndTime = "999999999999";
        }
        else
        {
            StartTime = FormatFaultTime(StartTime);
            EndTime = FormatFaultTime(EndTime);
        }
        System.out.println("-----------------------enter query database");
        resList = faultMapper.queryTowerTileParamList(start,length,userID,factory,line,device,StartTime,EndTime);
        count = faultMapper.queryTowerTileParamListCount(userID,factory,line,device,StartTime,EndTime);
        System.out.println("-----------------------leave query database");
        if (null!=resList)
        {
           for (int i = 0; i<resList.size(); i++)
          {
             System.out.println("-----------------------1");
             String deviceID = (String)resList.get(i).get("device");
             System.out.println("-----------------------2");
             String deviceName = faultMapper.getDeviceNameById(deviceID);
             System.out.println("-----------------------3");
             resList.get(i).put("deviceName",deviceName);
             System.out.println("-----------------------4");
             String towerID = (String)resList.get(i).get("tower"); 
             System.out.println("-----------------------5");
             String towerName = faultMapper.getTowerById(towerID);      
             System.out.println("-----------------------6");
             resList.get(i).put("towerName",towerName);
             System.out.println("-----------------------7");
          }
           System.out.println("-----------------------8");
        }     
        System.out.println("-----------------------9");
        dataTableModel.setiTotalDisplayRecords(count);
        System.out.println("-----------------------10");
        dataTableModel.setiTotalRecords(count);
        System.out.println("-----------------------11");
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        System.out.println("-----------------------12");
        dataTableModel.setAaData(resList);
        System.out.println("-----------------------13");
        return dataTableModel;
    }       
    
    public DataTableModel queryFaultList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        System.out.println(dataTableMap);
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        String userID = dataTableMap.get("userID"); 
        String Regulator = dataTableMap.get("Regulator");
        String VoltageLevel = dataTableMap.get("VoltageLevel");
        String line = dataTableMap.get("line");
        String Kind = dataTableMap.get("Kind");
        String StartTime = dataTableMap.get("StartTime");
        String EndTime = dataTableMap.get("EndTime");
        if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
        {
            StartTime = "000000000000";
            EndTime = "999999999999";
        }
        else
        {
            StartTime = FormatFaultTime(StartTime);
            EndTime = FormatFaultTime(EndTime);
        }
        String deal = dataTableMap.get("deal");
        
        if (StringUtils.isEmpty(line))//不用线路搜索条件
        {
            resList = faultMapper.queryFaultListByMultiLine1(start,length,userID,Regulator,VoltageLevel,Kind,StartTime,EndTime,deal);
            count = faultMapper.queryFaultListCountByMultiLine1(userID,Regulator,VoltageLevel,Kind,StartTime,EndTime,deal);
        }
        //以下两个分支暂时不合并
        else if (line.equals("0"))//无线路
        {
            resList = faultMapper.queryFaultListByUniqueLine(start,length,line,Kind,StartTime,EndTime,deal);
            count = faultMapper.queryFaultListCountByUniqueLine(line,Kind,StartTime,EndTime,deal);
        }
        else//指定线路
        {
            resList = faultMapper.queryFaultListByUniqueLine(start,length,line,Kind,StartTime,EndTime,deal);
            count = faultMapper.queryFaultListCountByUniqueLine(line,Kind,StartTime,EndTime,deal);
        }
        
        if (null!=resList)
        {
           for (int i = 0; i<resList.size(); i++)
          {
             String lineID = (String)resList.get(i).get("line");       
             String lineName = faultMapper.getLineById(lineID);       
             resList.get(i).put("lineName",lineName);
          }
        }
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }    
 
    /*
    public DataTableModel queryFaultList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();

        System.out.println(dataTableMap);
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));
        
        String userID = dataTableMap.get("userID"); 
        String optionID = dataTableMap.get("option"); 
        
       if (optionID !=null)
       {     
        switch(optionID)
        {
           case "1":
               String Regulator = dataTableMap.get("Regulator");
               resList = faultMapper.queryFaultListByRegulator(start,length,userID,Regulator);
               count = faultMapper.queryFaultListCountByRegulator(userID,Regulator);
              break;
           case "2":
               String VoltageLevel = dataTableMap.get("VoltageLevel");
               resList = faultMapper.queryFaultListByVoltageLevel(start,length,userID,VoltageLevel);
               count = faultMapper.queryFaultListCountByVoltageLevel(userID,VoltageLevel);
               break;
           case "3":
               String line = dataTableMap.get("line");
               resList = faultMapper.queryFaultListByLine(start,length,userID,line);
               count = faultMapper.queryFaultListCountByLine(userID,line);
               break;
           case "4":
               String Kind = dataTableMap.get("Kind");
               resList = faultMapper.queryFaultListByKind(start,length,userID,Kind);
               count = faultMapper.queryFaultListCountByKind(userID,Kind);
               break;
           case "5":
               String StartTime = dataTableMap.get("StartTime");
               String EndTime = dataTableMap.get("EndTime");
               if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
               {
                   StartTime = "000000000000";
                   EndTime = "999999999999";
               }
               else
               {
                   StartTime = FormatFaultTime(StartTime);
                   EndTime = FormatFaultTime(EndTime);
               }
               resList = faultMapper.queryFaultListByTime(start,length,userID,StartTime,EndTime);
               count = faultMapper.queryFaultListCountByTime(userID,StartTime,EndTime);
               break;
           case "6":
               String deal = dataTableMap.get("deal");
               resList = faultMapper.queryFaultListByDeal(start,length,userID,deal);
               count = faultMapper.queryFaultListCountByDeal(userID,deal);
               break;
           default:
               resList = faultMapper.queryFaultListByUser(start,length,userID);
               count = faultMapper.queryFaultListCountByUser(userID);
               break;         
        }
       }
       else
       {
           String line = dataTableMap.get("line");          
           String StartTime = dataTableMap.get("StartTime");
           String EndTime = dataTableMap.get("EndTime");

           if (line==null)
           {
               line="";
           }
           
           if ((StringUtils.isEmpty(StartTime))||(StringUtils.isEmpty(EndTime)))
           {
               StartTime = "000000000000";
               EndTime = "999999999999";
           }
           else
           {
               StartTime = FormatTime(StartTime);
               EndTime = FormatTime(EndTime);
           }
           
           resList = faultMapper.queryFaultList1(start,length,line,StartTime,EndTime,userID);
           count = faultMapper.queryFaultListCount1(line,StartTime,EndTime,userID);
       }

       
        for (int i = 0; i<resList.size(); i++)
        {
            String lineID = (String)resList.get(i).get("line");       
            String lineName = faultMapper.getLineById(lineID);       
            resList.get(i).put("lineName",lineName);
        }
               
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }    
    */
    
    public Map<String, Object> getWaveById(String userId) {
        return faultMapper.getWaveById(userId);
    }
    
    public Map<String, Object> getFaultById(String userId) {
        Map<String, Object> FaultMap = faultMapper.getFaultById(userId);
        String leftTowerId = (String)FaultMap.get("left_tower");
        String rightTowerId = (String)FaultMap.get("right_tower");
        String leftTowerName = faultMapper.getFaultNameById(leftTowerId);
        String rightTowerName = faultMapper.getFaultNameById(rightTowerId);
        FaultMap.put("leftTowerName", leftTowerName);
        FaultMap.put("rightTowerName", rightTowerName);
        return FaultMap;
    }
    
    public void setDealFaultById(String userId) {
        faultMapper.setDealFaultById(userId);
    }
    
    public void setDealTowerTileAlarmById(String userId) {
        faultMapper.setDealTowerTileAlarmById(userId);
    }
}
