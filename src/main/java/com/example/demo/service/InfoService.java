package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.po.DataTableModel;

public interface InfoService {

    DataTableModel queryRegulatorInfoList(Map<String, String> dataTableMap);
    
    DataTableModel queryFactoryInfoList(Map<String, String> dataTableMap);
    
    DataTableModel queryTowerInfoList(Map<String, String> dataTableMap);
    
    DataTableModel queryStationList(Map<String, String> dataTableMap);
    
    List<Map<String,Object>> queryRegulator();
    
    List<Map<String,Object>> queryLine();
    
    List<Map<String,Object>> queryLineByUser(String userId);
    
    List<Map<String,Object>> queryDeviceByUser(String userId);
   
    List<Map<String,Object>> queryTowerByUser(String userId);    
    
    List<Map<String,Object>> queryLineByMultiCondition(String userId, String regulatorId, String voltageId);
    
    List<Map<String,Object>> queryDeviceByMultiCondition(String userId, String lineId, String factoryId);
    
    List<Map<String,Object>> queryTower();
    
    List<Map<String,Object>> queryFactory();
    
    List<Map<String,Object>> queryStation();
    
    void saveRegulator(Map<String, Object> paramMap);
    
    void saveFactory(Map<String, Object> paramMap);
    
    void saveStation(Map<String, Object> paramMap);
    
    void saveTower(Map<String, Object> paramMap);
    
    void saveDevice(Map<String, Object> paramMap);
    
    DataTableModel queryLineInfoList(Map<String, String> dataTableMap);
    
    DataTableModel queryDeviceInfoList(Map<String, String> dataTableMap);
    
    void saveLine(Map<String, Object> paramMap);
    
    Map<String, Object> getLineById(String userId);
    
    Map<String, Object> getTowerById(String userId);
    
    Map<String, Object> getDeviceById(String userId);
    
    Map<String, Object> getStationById(String userId);
    
    boolean deleteLine(String userId);
    
    boolean deleteTower(String userId);
    
    boolean deleteDevice(String userId);
    
    boolean deleteFactory(String userId);
    
    boolean deleteStation(String userId);
    
    Map<String, Object> getFactoryById(String regulatorId);
    
    int queryallDeviceNum(String userId);
    
    int queryonlineDeviceNum(String userId);
    
    int queryofflineDeviceNum();
    
    int querynoReadAlarmNumByUser(String userId);
    
    int querynoReadFaultNumByUser(String userId);
    
    int querynoReadTowertileAlarmNumByUser(String userId);
    
    List<Map<String,Object>> queryTowerByLineId(String lineId);
    
}
