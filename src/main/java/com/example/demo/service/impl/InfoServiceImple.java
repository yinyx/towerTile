package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.service.InfoService;
import com.example.demo.mapper.InfoMapper;
import com.example.demo.po.DataTableModel;

import util.aes.StringUtils;

@Service
@Transactional
public class InfoServiceImple implements InfoService{

    @Autowired
    private InfoMapper infoMapper;
    
    public DataTableModel queryRegulatorInfoList(Map<String, String> dataTableMap)
    {
        DataTableModel dataTableModel = new DataTableModel();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<Map<String, Object>> resList;
        Integer count;        
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("QueryType");
        
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));

        paramMap.put("stuCode_s", dataTableMap.get("stuCode_s"));
        paramMap.put("stuName_s", dataTableMap.get("stuName_s"));
        paramMap.put("stuStates_s", dataTableMap.get("stuStates_s"));

        if(QueryType.isEmpty())
        {
            resList = infoMapper.queryRegulatorList(start,length);
            count = infoMapper.queryRegulatorListCount()-1;
        }
        else
        {
            resList = infoMapper.queryRegulatorListByCondition(start,length,QueryType);
            count = infoMapper.queryRegulatorListCountByCondition(QueryType)-1;
        }
        
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);
        return dataTableModel;
    }
    
    public DataTableModel queryFactoryInfoList(Map<String, String> dataTableMap)
    {
        DataTableModel dataTableModel = new DataTableModel();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<Map<String, Object>> resList;
        Integer count;        
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("QueryType");
        
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));

        paramMap.put("stuCode_s", dataTableMap.get("stuCode_s"));
        paramMap.put("stuName_s", dataTableMap.get("stuName_s"));
        paramMap.put("stuStates_s", dataTableMap.get("stuStates_s"));

        resList = infoMapper.queryFactoryList(start,length,QueryType);
        count = infoMapper.queryFactoryListCount(QueryType);
        
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);
        return dataTableModel;
    }
    
    //queryStationList
    public DataTableModel queryStationList(Map<String, String> dataTableMap)
    {
        DataTableModel dataTableModel = new DataTableModel();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<Map<String, Object>> resList;
        Integer count;        
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("QueryType");
        
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));

        paramMap.put("stuCode_s", dataTableMap.get("stuCode_s"));
        paramMap.put("stuName_s", dataTableMap.get("stuName_s"));
        paramMap.put("stuStates_s", dataTableMap.get("stuStates_s"));

        resList = infoMapper.queryStationList(start,length,QueryType);
        count = infoMapper.queryStationListCount(QueryType);
       
        
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);
        return dataTableModel;
    }
    
    public DataTableModel queryTowerInfoList(Map<String, String> dataTableMap)
    {
        DataTableModel dataTableModel = new DataTableModel();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<Map<String, Object>> resList;
        Integer count;        
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("QueryType");

        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));

        paramMap.put("stuCode_s", dataTableMap.get("stuCode_s"));
        paramMap.put("stuName_s", dataTableMap.get("stuName_s"));
        paramMap.put("stuStates_s", dataTableMap.get("stuStates_s"));

        if(StringUtils.isEmpty(QueryType)||QueryType.equals("0"))
        {
            resList = infoMapper.queryTowerList(start,length);
            count = infoMapper.queryTowerListCount();
        }
        else
        {
            resList = infoMapper.queryTowerListByCondition(start,length,QueryType);
            count = infoMapper.queryTowerListCountByCondition(QueryType);
        }
        
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);
        return dataTableModel;
    }
    
    public List<Map<String,Object>> queryRegulator()
    {
        List<Map<String, Object>> allCollege = infoMapper.queryRegulator();
        return allCollege;
    }
   
    public List<Map<String,Object>> queryLineByUser(String userId)
    {
        List<Map<String, Object>> resList = new ArrayList<>();
        
        List<String> allCollege = infoMapper.queryLineListByUser(userId);
        String lineId = "";
        String lineName = "";
        for (int i=0; i<allCollege.size(); i++)
        {
            Map<String,Object> LineMap = new HashMap<String,Object>();
            lineId = allCollege.get(i);
            lineName = (String)infoMapper.getLineById(lineId).get("name");
            LineMap.put("id", lineId);
            LineMap.put("name", lineName);
            resList.add(LineMap);
        }
        return resList;
    }
    
    public List<Map<String,Object>> queryDeviceByUser(String userId)
    {
        List<Map<String, Object>> resList = new ArrayList<>();
        
        List<String> allCollege = infoMapper.queryDeviceListByUser(userId);
        String DeviceId = "";
        String DeviceName = "";
        for (int i=0; i<allCollege.size(); i++)
        {
            Map<String,Object> DeviceMap = new HashMap<String,Object>();
            DeviceId = allCollege.get(i);
            DeviceName = (String)infoMapper.getDeviceById(DeviceId).get("name");
            DeviceMap.put("id", DeviceId);
            DeviceMap.put("name", DeviceName);
            resList.add(DeviceMap);
        }
        return resList;
    }
    
    public List<Map<String,Object>> queryTowerByUser(String userId)
    {
        List<Map<String, Object>> resList = new ArrayList<>();
        
        List<String> allCollege = infoMapper.queryTowerListByUser(userId);
        String TowerId = "";
        String TowerName = "";
        for (int i=0; i<allCollege.size(); i++)
        {
            Map<String,Object> TowerMap = new HashMap<String,Object>();
            TowerId = allCollege.get(i);
            TowerName = (String)infoMapper.getTowerById(TowerId).get("name");
            TowerMap.put("id", TowerId);
            TowerMap.put("name", TowerName);
            resList.add(TowerMap);
        }
        return resList;
    }
    
    public List<Map<String,Object>> queryLineByMultiCondition(String userId, String regulatorId, String voltageId)
    {
        List<Map<String, Object>> resList = new ArrayList<>();
        
        List<String> allCollege = infoMapper.queryLineByMultiCondition(userId, regulatorId, voltageId);
        String lineId = "";
        String lineName = "";
        for (int i=0; i<allCollege.size(); i++)
        {
            Map<String,Object> LineMap = new HashMap<String,Object>();
            lineId = allCollege.get(i);
            lineName = (String)infoMapper.getLineById(lineId).get("name");
            LineMap.put("id", lineId);
            LineMap.put("name", lineName);
            resList.add(LineMap);
        }
        return resList;
    }
    
    public List<Map<String,Object>> queryDeviceByMultiCondition(String userId, String lineId, String factoryId)
    {
        List<Map<String, Object>> resList = new ArrayList<>();
        
        List<String> allCollege = infoMapper.queryDeviceByMultiCondition(userId, lineId, factoryId);
        String DeviceId = "";
        String DeviceName = "";
        for (int i=0; i<allCollege.size(); i++)
        {
            Map<String,Object> DeviceMap = new HashMap<String,Object>();
            DeviceId = allCollege.get(i);
            DeviceName = (String)infoMapper.getDeviceById(DeviceId).get("name");
            DeviceMap.put("id", DeviceId);
            DeviceMap.put("name", DeviceName);
            resList.add(DeviceMap);
        }
        return resList;
    }
    
    public List<Map<String,Object>> queryLine()
    {
        List<Map<String, Object>> allCollege = infoMapper.queryLine();
        return allCollege;
    }
    
    public List<Map<String,Object>> queryTower()
    {
        List<Map<String, Object>> allCollege = infoMapper.queryTower();
        return allCollege;
    }
    
    public List<Map<String,Object>> queryFactory()
    {
        List<Map<String, Object>> allCollege = infoMapper.queryFactory();
        return allCollege;
    }
    
    public List<Map<String,Object>> queryStation()
    {
        List<Map<String, Object>> allCollege = infoMapper.queryStation();
        return allCollege;
    }
    
    public void saveRegulator(Map<String, Object> paramMap) {
        String userId = (String) paramMap.get("regulatorId");
        if (StringUtils.isEmpty(userId)) {
            paramMap.put("regulatorId", StringUtils.getUUId());
            infoMapper.addRegulator(paramMap);
        } else {
            infoMapper.updateRegulator(paramMap);
        }
    }
    
    public void saveFactory(Map<String, Object> paramMap) {
        String regulatorId = (String) paramMap.get("regulatorId");
        String num = (String) paramMap.get("num");
        String name = (String) paramMap.get("name");
        String contact = (String) paramMap.get("contact");
        String call = (String) paramMap.get("call");
        String login_name = (String) paramMap.get("login_name");
        String login_password = (String) paramMap.get("login_password");
        //String uuid = (String) paramMap.get("uuid");
        String sample_Rate = (String) paramMap.get("sample_Rate");

        if (StringUtils.isEmpty(regulatorId)) {
            paramMap.put("regulatorId", StringUtils.getUUId());
            regulatorId = (String) paramMap.get("regulatorId");
            infoMapper.addFactory(regulatorId, num, name, contact, call, login_name,
                                  login_password, /*uuid,*/ sample_Rate);
        } else {
            infoMapper.updateFactory(regulatorId, num, name, contact, call, login_name,
                    login_password, /*uuid,*/ sample_Rate);
        }
    }
    
    public void saveStation(Map<String, Object> paramMap) {
        String towerId = (String) paramMap.get("regulatorId");
        if (StringUtils.isEmpty(towerId)) {
            paramMap.put("regulatorId", StringUtils.getUUId());
            infoMapper.addStation(paramMap);
        } else {
            infoMapper.updateStation(paramMap);
        }
    }
    
    public void saveTower(Map<String, Object> paramMap) {
        String towerId = (String) paramMap.get("recordId");
        if (StringUtils.isEmpty(towerId)) {
            paramMap.put("recordId", StringUtils.getUUId());
            infoMapper.addTower(paramMap);
        } else {
            infoMapper.updateTower(paramMap);
        }
    }
    
    public void saveDevice(Map<String, Object> paramMap) {
        String towerId = (String) paramMap.get("recordId");
        String deviceId = (String) paramMap.get("device");
        String deviceIP = (String) paramMap.get("IP");
        String name = (String) paramMap.get("name");
        String factory = (String) paramMap.get("factory");
        int InstallIndex = Integer.parseInt((String) paramMap.get("InstallIndex"));
        int phase = Integer.parseInt((String) paramMap.get("phase"));
        int ProtocalType = Integer.parseInt((String) paramMap.get("ProtocalType"));
        String IedType = (String) paramMap.get("IedType");
        String version = (String) paramMap.get("version");
        String ManuDate = (String) paramMap.get("ManuDate");
        String InstallTime = (String) paramMap.get("InstallTime");
        String longitude = (String) paramMap.get("longitude");
        String latitude = (String) paramMap.get("latitude");
        String altitude = (String) paramMap.get("altitude");
        String line = (String) paramMap.get("line");
        String tower = (String) paramMap.get("tower");

        if (StringUtils.isEmpty(towerId)) {
            paramMap.put("recordId", StringUtils.getUUId());
            towerId = (String) paramMap.get("recordId");
            infoMapper.addDevice(deviceId, deviceIP,InstallIndex, name, line, factory, ManuDate,
                    IedType, version, tower, InstallTime, phase, longitude, latitude,
                    altitude,ProtocalType);
        } else {
            infoMapper.updateDevice(towerId,deviceIP,InstallIndex, name, line, factory, ManuDate,
                    IedType, version, tower, InstallTime, phase, longitude, latitude,
                    altitude,ProtocalType);
        }
    }
    
    public DataTableModel queryLineInfoList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("QueryType");
        
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));

        paramMap.put("stuCode_s", dataTableMap.get("stuCode_s"));
        paramMap.put("stuName_s", dataTableMap.get("stuName_s"));
        paramMap.put("stuStates_s", dataTableMap.get("stuStates_s"));

        if(QueryType.isEmpty())
        {
            resList = infoMapper.queryLineList(start,length);
            count = infoMapper.queryLineListCount();
        }
        else
        {
            resList = infoMapper.queryLineListByCondition(start,length,QueryType);
            count = infoMapper.queryLineListCountByCondition(QueryType);
        }
        
        System.out.println(resList);
        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }
    
    public DataTableModel queryDeviceInfoList(Map<String, String> dataTableMap)
    {       
        DataTableModel dataTableModel = new DataTableModel();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<Map<String, Object>> resList;
        Integer count;
        String sEcho = dataTableMap.get("sEcho");
        String QueryType = dataTableMap.get("QueryType");
        String QueryType1 = dataTableMap.get("QueryType1");       
        
        int start = Integer.parseInt(dataTableMap.get("iDisplayStart"));
        int length = Integer.parseInt(dataTableMap.get("iDisplayLength"));

        paramMap.put("stuCode_s", dataTableMap.get("stuCode_s"));
        paramMap.put("stuName_s", dataTableMap.get("stuName_s"));
        paramMap.put("stuStates_s", dataTableMap.get("stuStates_s"));
    
        if (StringUtils.isEmpty(QueryType))
        {
            QueryType = "";
        }
        if (StringUtils.isEmpty(QueryType1))
        {
            QueryType1 = "";
        }
        resList = infoMapper.queryDeviceList(start,length,QueryType,QueryType1);
        count = infoMapper.queryDeviceListCount(QueryType,QueryType1);

        dataTableModel.setiTotalDisplayRecords(count);
        dataTableModel.setiTotalRecords(count);
        dataTableModel.setsEcho(Integer.valueOf(sEcho));
        dataTableModel.setAaData(resList);

        return dataTableModel;
    }
    
    public void saveLine(Map<String, Object> paramMap) {
        String lineId = (String) paramMap.get("recordId");
        if (StringUtils.isEmpty(lineId)) {
            paramMap.put("recordId", StringUtils.getUUId());
            infoMapper.addLine(paramMap);
            System.out.println("----------add");
        } else {
            infoMapper.updateLine(paramMap);
            System.out.println("----------update");
        }
    }
    
    public Map<String, Object> getLineById(String userId) {
        return infoMapper.getLineById(userId);
    }
    
    public Map<String, Object> getTowerById(String userId) {
        return infoMapper.getTowerById(userId);
    }
    
    public Map<String, Object> getDeviceById(String userId) {
        return infoMapper.getDeviceById(userId);
    }    
    
    public Map<String, Object> getStationById(String userId) {
        return infoMapper.getStationById(userId);
    }  
    
    public boolean deleteLine(String userId) {
        infoMapper.deleteLine(userId);
        infoMapper.deleteUser_LineByLine(userId);
        return true;
    }
    
    public boolean deleteTower(String userId) {
        infoMapper.deleteTower(userId);
        return true;
    }
    
    public boolean deleteDevice(String userId) {
        infoMapper.deleteDevice(userId);
        return true;
    }
    
    public boolean deleteFactory(String userId) {
        infoMapper.deleteFactory(userId);
        return true;
    }
    
    //deleteStation
    public boolean deleteStation(String userId) {
        infoMapper.deleteStation(userId);
        return true;
    }
    
    public Map<String, Object> getFactoryById(String regulatorId) {
        return infoMapper.getFactoryById(regulatorId);
    }
    
    public int queryallDeviceNum(String userId) {
        return infoMapper.queryallDeviceNum(userId);
    }
    
    public int queryonlineDeviceNum(String userId) {
        return infoMapper.queryonlineDeviceNum(userId);
    }
    
    public int queryofflineDeviceNum() {
        return infoMapper.queryofflineDeviceNum();
    }
    
    public int querynoReadAlarmNumByUser(String userId) {
        return infoMapper.querynoReadAlarmNumByUser(userId);
    }
    
    public int querynoReadFaultNumByUser(String userId) {
        return infoMapper.querynoReadFaultNumByUser(userId);
    }
    
    public int querynoReadTowertileAlarmNumByUser(String userId) {
        return infoMapper.querynoReadTowertileAlarmNumByUser(userId);
    }
    
    public List<Map<String,Object>> queryTowerByLineId(String lineId)
    {
        List<Map<String, Object>> allCollege = infoMapper.queryTowerByLineId(lineId);
        return allCollege;
    }
}
