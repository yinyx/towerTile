package com.example.demo.service;

import java.util.Map;

import com.example.demo.po.DataTableModel;

public interface FaultService {
//test
    DataTableModel queryWaveInfoList(Map<String, String> dataTableMap);
    
    DataTableModel queryTowerTileParamList(Map<String, String> dataTableMap);
  
    DataTableModel queryTowerTileAlarmList(Map<String, String> dataTableMap); 
    
    DataTableModel queryTowerTileStateList(Map<String, String> dataTableMap); 
    
    DataTableModel queryAlarmList(Map<String, String> dataTableMap);
    
    DataTableModel queryFaultList(Map<String, String> dataTableMap);
    
    DataTableModel queryFaultListInGis(Map<String, String> dataTableMap);
    
    Map<String, Object> getWaveById(String userId);
    
    Map<String, Object> getFaultById(String userId);
    
    void setDealFaultById(String userId);
    
    void setDealTowerTileAlarmById(String userId);
    
}
