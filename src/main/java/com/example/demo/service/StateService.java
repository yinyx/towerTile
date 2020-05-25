package com.example.demo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.po.DataTableModel;

public interface StateService {

    DataTableModel queryHeartBeatList(Map<String, String> dataTableMap);
    
    DataTableModel queryWorkConditionList(Map<String, String> dataTableMap); 
    
    DataTableModel queryOrderStatusList(Map<String, String> dataTableMap);
    
    DataTableModel queryAlarmList(Map<String, String> dataTableMap);
      
    DataTableModel queryParameterAttrList(Map<String, String> dataTableMap);
    
    DataTableModel queryParameterList(Map<String, String> dataTableMap);
    
    DataTableModel queryControlDeviceList(Map<String, String> dataTableMap);
    
    DataTableModel querySelfCheckList(Map<String, String> dataTableMap);
    
    DataTableModel queryCommunicateState(Map<String, String> dataTableMap);
    
    Map<String, Object> checkFactoryLogin(String username, String password ,String factoryId);
    
    void saveParamAttr(Map<String, Object> paramMap);
    
    int addReadOrderByDeviceIdanduserId(String recordId, String userId);
    
    int addReadAlarmThresholdOrderByDeviceIdanduserId(String recordId, String userId);
    
    int addReadTiltDataOrderByDeviceIdanduserId(String recordId, String userId);
    
    int addReadDeviceTimeOrderByDeviceIdanduserId(String recordId, String userId);
    
    int addReadInfoTypeOrderByDeviceIdanduserId(String recordId, String userId);
    
    int addReadSampleRateOrderByDeviceIdanduserId(String recordId, String userId);
    
    int addTimingOrderByDeviceIdanduserId(String recordId, String userId);
    
    int readSelfCheckById(String recordId, String userId);
    
    int sendDownloadLogById(String recordId, String userId);
    
    String getDeviceLogNameById(String recordId);
    
    //addResetOrderByDeviceIdanduserId
    int addResetOrderByDeviceIdanduserId(String recordId, String userId, int resetMode);
    
    int addResetTiltDataByIdOrderByDeviceIdanduserId(String recordId, String userId);
    
    int addUpdateOrderByDeviceIdanduserId(String recordId, String userId);
    
    int getAuhorityByUser(String userId);
    
    String getPasswordByDevice(String DeviceId);
    
    //addSetOrderByDeviceIdanduserId
    int addSetOrderByDeviceIdanduserId(String recordId, String userId, String content, int[] ValueLst);
    
    int addSetAlarmthresholdByDeviceIdAnduserId(String recordId, String userId, float Value0, float Value1, float Value2, float Value3, float Value4, float Value5, float Value6, float Value7, float Value8, float Value9);
    
    int addSetSampleRateByDeviceIdanduserId(String recordId, String userId, short val1, short val2, short val3, byte val4);
      
    int addSetInfoTypeByDeviceIdanduserId(String recordId, String userId, int Value);
    
    int addSetDeviceTimeByDeviceIdanduserId(String recordId, String userId, String Time_Stamp);
    
    Map<String, Object> getParamAttrById(String regulatorId);
    
    //getParamById
    Map<String, Object> getParamById(String regulatorId); 
    
    //getThresholdByDeviceId
    Map<String, Object> getThresholdByDeviceId(String regulatorId); 
    
    Map<String, Object> getTiltDataByDeviceId(String regulatorId); 
    
    String getTime_StampByDeviceId(String regulatorId); 
    
    int getInfoTypeByDeviceId(String regulatorId); 
    
    Map<String, Object> getSampleRateByDeviceId(String regulatorId); 
    
    //getcommonParamById
    Map<String, Object> getcommonParamById(String regulatorId); 
    
    //getParamNameById
    List<String> getParamNameById(String regulatorId); 
    
    boolean deleteParamAttrById(String regulatorId);
    
    void setDealAlarmById(String userId);
    
    Map<String, Object> queryWavePwd(Map<String, Object> paramMap);
}
