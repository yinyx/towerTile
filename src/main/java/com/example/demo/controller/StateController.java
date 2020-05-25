package com.example.demo.controller;

import util.aes.TestProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.po.DataTableModel;
import com.example.demo.po.DataTableParam;
import com.example.demo.service.FaultService;
import com.example.demo.service.StateService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import util.aes.AesUtil;
import util.aes.DatatableUtil;

@RestController
@RequestMapping(value = "/state")
public class StateController {

    // 注入用户Service
    @Resource
    private StateService stateService;
   
    @RequestMapping(value="/queryHeartBeatList")
    public Object  queryHeartBeatList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.queryHeartBeatList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询心跳列表信息异常!");
        }
        return resultMap;
    }  
    
    @RequestMapping(value="/queryWorkConditionList")
    public Object  queryWorkConditionList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.queryWorkConditionList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询工况列表信息异常!");
        }
        return resultMap;
    } 
    
    @RequestMapping(value="/queryOrderStatusList")
    public Object  queryOrderStatusList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.queryOrderStatusList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询命令列表信息异常!");
        }
        return resultMap;
    }  
    
    @RequestMapping(value="/queryAlarmList")
    public Object  queryAlarmList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.queryAlarmList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询告警列表信息异常!");
        }
        return resultMap;
    }  
    
    @RequestMapping(value="/queryParameterAttrList")
    public Object  queryParameterAttrList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.queryParameterAttrList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询参数属性列表信息异常!");
        }
        return resultMap;
    }  
    
    @RequestMapping(value="/queryParameterList")
    public Object  queryParameterList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.queryParameterList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询参数列表信息异常!");
        }
        return resultMap;
    }  
    
    @RequestMapping(value="/queryControlDeviceList")
    public Object  queryControlDeviceList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.queryControlDeviceList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询操作设备列表信息异常!");
        }
        return resultMap;
    }  
    
    @RequestMapping(value="/querySelfCheckList")
    public Object  querySelfCheckList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.querySelfCheckList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询自检列表信息异常!");
        }
        return resultMap;
    } 
    
    @RequestMapping(value="/queryCommunicateState")
    public Object  queryCommunicateState(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = stateService.queryCommunicateState(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询通信状态信息异常!");
        }
        return resultMap;
    }
    
    @RequestMapping(value="/checkFactoryLogin")
    public String checkFactoryLogin(@RequestParam Map<String, Object> map,HttpSession session) {
        JSONObject paramObj=AesUtil.GetParam(map);
        String username = (String) paramObj.get("username");
        String password = (String) paramObj.get("password");
        String factoryId = (String) paramObj.get("factoryId");
        Map<String, Object> userMap = stateService.checkFactoryLogin(username, password, factoryId);
        JSONObject jsonObject = JSONObject.fromObject(userMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/addParamAttr")
    public String addParamAttr(@RequestParam Map<String, Object> map,HttpSession session) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        JSONObject paramObj=AesUtil.GetParam(map);
        paramMap.put("recordId", (String) paramObj.get("recordId"));
        paramMap.put("indexno", (String) paramObj.get("indexno"));
        paramMap.put("name", (String) paramObj.get("name"));
        paramMap.put("type", (Integer) paramObj.get("type"));
        paramMap.put("isPrivate", (Integer) paramObj.get("isPrivate"));
        paramMap.put("factoryId", (String) paramObj.get("factoryId"));

        try {
            stateService.saveParamAttr(paramMap);
            resultMap.put("status", "success");
            resultMap.put("msg", "参数属性保存成功!");
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "参数属性保存失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //getParamAttrById
    @RequestMapping(value="/getParamAttrById",method=RequestMethod.POST)
    @ResponseBody
    public Object getParamAttrById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> usersData = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            usersData = stateService.getParamAttrById(recordId);
            resultMap.put("status", "success");
            resultMap.put("usersData", usersData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询参数属性异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //getParamById
    @RequestMapping(value="/getParamById",method=RequestMethod.POST)
    @ResponseBody
    public Object getParamById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> usersData = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<String> paraNameList = new LinkedList<>();

        try {
            usersData = stateService.getParamById(recordId);
            paraNameList = stateService.getParamNameById(recordId);
            System.out.print("paraNameList");
            System.out.print(paraNameList);
            int paracount = usersData.size();
            resultMap.put("status", "success");
            resultMap.put("usersData", usersData);
            resultMap.put("paracount", paracount);
            resultMap.put("paraNameList", paraNameList);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询参数值异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //readParamById
    @RequestMapping(value="/readParamById",method=RequestMethod.POST)
    @ResponseBody
    public Object readParamById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "读取装置参数异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addReadOrderByDeviceIdanduserId(recordId, userId);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "增加读取参数记录异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //getAlarmThresholdById
    @RequestMapping(value="/getAlarmThresholdById",method=RequestMethod.POST)
    @ResponseBody
    public Object getAlarmThresholdById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> ThresholdData = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询告警阈值异常!");
         }
     }
     else 
     {
        try {
            ThresholdData = stateService.getThresholdByDeviceId(recordId);
            resultMap.put("status", "success");
            resultMap.put("ThresholdData", ThresholdData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询告警阈值异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //getInfoTypeById
    @RequestMapping(value="/getInfoTypeById",method=RequestMethod.POST)
    @ResponseBody
    public Object getInfoTypeById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int InfoType =1;
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询装置上送信息类型异常!");
         }
     }
     else 
     {
        try {
            InfoType = stateService.getInfoTypeByDeviceId(recordId);
            resultMap.put("status", "success");
            resultMap.put("InfoType", InfoType);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询装置上送信息类型异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //getDeviceTimeById
    @RequestMapping(value="/getDeviceTimeById",method=RequestMethod.POST)
    @ResponseBody
    public Object getDeviceTimeById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String Time_Stamp = "";
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询装置时间异常!");
         }
     }
     else 
     {
        try {
            Time_Stamp = stateService.getTime_StampByDeviceId(recordId);
            resultMap.put("status", "success");
            resultMap.put("Time_Stamp", Time_Stamp);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询装置时间异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //getSampleRateById
    @RequestMapping(value="/getSampleRateById",method=RequestMethod.POST)
    @ResponseBody
    public Object getSampleRateById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> SampleRateData = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询采样率异常!");
         }
     }
     else 
     {
        try {
            SampleRateData = stateService.getSampleRateByDeviceId(recordId);
            resultMap.put("status", "success");
            resultMap.put("SampleRateData", SampleRateData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询采样率异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //readAlarmThreshold
    @RequestMapping(value="/readAlarmThreshold",method=RequestMethod.POST)
    @ResponseBody
    public Object readAlarmThreshold(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> ThresholdData = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询告警阈值异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addReadAlarmThresholdOrderByDeviceIdanduserId(recordId, userId);
            //如果设备响应成功
            if (0==result)
            {
                ThresholdData = stateService.getThresholdByDeviceId(recordId);
            }
            resultMap.put("status", "success");
            resultMap.put("result", result);
            resultMap.put("ThresholdData", ThresholdData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询告警阈值异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //readTiltData
    @RequestMapping(value="/readTiltData",method=RequestMethod.POST)
    @ResponseBody
    public Object readTiltData(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> TiltData = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询倾角数据异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addReadTiltDataOrderByDeviceIdanduserId(recordId, userId);
            //如果设备响应成功
            if (0==result)
            {
                TiltData = stateService.getTiltDataByDeviceId(recordId);
            }
            resultMap.put("status", "success");
            resultMap.put("result", result);
            resultMap.put("TiltData", TiltData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询倾角数据异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //readDeviceTime
    @RequestMapping(value="/readDeviceTime",method=RequestMethod.POST)
    @ResponseBody
    public Object readDeviceTime(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String Time_Stamp="";
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询装置时间异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addReadDeviceTimeOrderByDeviceIdanduserId(recordId, userId);
            //如果设备响应成功
            if (0==result)
            {
                Time_Stamp = stateService.getTime_StampByDeviceId(recordId);
            }
            resultMap.put("status", "success");
            resultMap.put("result", result);
            resultMap.put("Time_Stamp", Time_Stamp);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询装置时间异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //readInfoType
    @RequestMapping(value="/readInfoType",method=RequestMethod.POST)
    @ResponseBody
    public Object readInfoType(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int InfoType= 1;
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询装置信息类型异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addReadInfoTypeOrderByDeviceIdanduserId(recordId, userId);
            //如果设备响应成功
            if (0==result)
            {
                InfoType = stateService.getInfoTypeByDeviceId(recordId);
            }
            resultMap.put("status", "success");
            resultMap.put("result", result);
            resultMap.put("InfoType", InfoType);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询信息类型异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //readSampleRate
    @RequestMapping(value="/readSampleRate",method=RequestMethod.POST)
    @ResponseBody
    public Object readSampleRate(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> SampleRateData = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "查询采样率异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addReadSampleRateOrderByDeviceIdanduserId(recordId, userId);
            //如果设备响应成功
            if (0==result)
            {
                SampleRateData = stateService.getSampleRateByDeviceId(recordId);
            }
            resultMap.put("status", "success");
            resultMap.put("result", result);
            resultMap.put("SampleRateData", SampleRateData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询采样率异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //timingDeviceById
    @RequestMapping(value="/timingDeviceById",method=RequestMethod.POST)
    @ResponseBody
    public Object timingDeviceById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "校时装置异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addTimingOrderByDeviceIdanduserId(recordId, userId);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "校时装置异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    //downloadLogById
    @RequestMapping(value="/downloadLogById")
    @ResponseBody
    public Object downloadLogById(String file_name, String pathname
            ,HttpServletRequest request, HttpServletResponse response){
        
        //String fileName = "aim_test.txt";// 设置文件名，根据业务需要替换成要下载的文件名
        String fileName = file_name;
        if (fileName != null) {
          //设置文件路径
          //String realPath = "D://aim//";
            String realPath = pathname;
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
   
    
    //sendDownloadLogById
    @RequestMapping(value="/sendDownloadLogById",method=RequestMethod.POST)
    @ResponseBody
    public Object sendDownloadLogById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(3000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "增加复位装置记录异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.sendDownloadLogById(recordId, userId);
            String FileName = stateService.getDeviceLogNameById(recordId);
            //System.out.println(result);
            String cfgPath_windows = TestProperties.getProperties_1("path.properties","devicelogpath_w");
            String cfgPath_linux = TestProperties.getProperties_1("path.properties","devicelogpath_l");
            cfgPath_linux += recordId+"/";
            if(TestProperties.isOSLinux()){
                resultMap.put("PathName", cfgPath_linux);
            }else{
                resultMap.put("PathName", cfgPath_windows);
            }
            resultMap.put("status", "success");
            resultMap.put("result", result);
            resultMap.put("FileName", "log.txt");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "下载装置自检日志异常!");
        }
     }  
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //readSelfCheckById
    @RequestMapping(value="/readSelfCheckById",method=RequestMethod.POST)
    @ResponseBody
    public Object readSelfCheckById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(3000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "增加复位装置记录异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
         
        try {
            int result = stateService.readSelfCheckById(recordId, userId);
            //System.out.println(result);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "读取装置自检记录异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //getAllParamById
    @RequestMapping(value="/getAllParamById",method=RequestMethod.POST)
    @ResponseBody
    public Object getAllParamById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> commonData = new HashMap<String, Object>();
        Map<String, Object> privateData = new HashMap<String, Object>();

        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "增加复位装置记录异常!");
         }
     }
     else 
     {
        
        try {
            commonData = stateService.getcommonParamById(recordId);
            privateData = stateService.getParamById(recordId);
            int paracount = privateData.size();
            resultMap.put("commonData", commonData);
            resultMap.put("privateData", privateData);
            resultMap.put("paracount", paracount);
            resultMap.put("status", "success");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "读取全部参数记录异常!");
        }
     }   
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //resetDeviceById
    @RequestMapping(value="/resetDeviceById",method=RequestMethod.POST)
    @ResponseBody
    public Object resetDeviceById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        String sResetMode = (String) paramObj.get("resetMode");
        int iResetMode = Integer.parseInt(sResetMode);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "增加复位装置记录异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addResetOrderByDeviceIdanduserId(recordId, userId, iResetMode);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "增加复位装置记录异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //resetTiltDataById
    @RequestMapping(value="/resetTiltDataById",method=RequestMethod.POST)
    @ResponseBody
    public Object resetTiltDataById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "增加复位倾角数据记录异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addResetTiltDataByIdOrderByDeviceIdanduserId(recordId, userId);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "增加复位倾角数据记录异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //updateDeviceById
    @RequestMapping(value="/updateDeviceById",method=RequestMethod.POST)
    @ResponseBody
    public Object updateDeviceById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        int authority = stateService.getAuhorityByUser(userId);
     if (1 == authority) {   
         try {
         Thread.sleep(1000);
         resultMap.put("status", "error");
         resultMap.put("msg", "当前登录用户没有操作设备的权限，请与系统管理员联系!");
         }
         catch(Exception e)
         {
             resultMap.put("status", "error");
             resultMap.put("msg", "升级装置异常!");
         }
     }
     else 
     {
         //装置访问控制
         String device_access = (String) paramObj.get("devicePassword");
         //String device_password = "123";//此处后期要实现接口，从数据库中获取
         String device_password = stateService.getPasswordByDevice(recordId);
         if (!device_access.equals(device_password))
         {
             resultMap.put("status", "success");
             int result = -2;
             resultMap.put("result", result);
             JSONObject jsonObject = JSONObject.fromObject(resultMap);
             String enResult = AesUtil.enCodeByKey(jsonObject.toString());
             return enResult;
         }
        try {
            int result = stateService.addUpdateOrderByDeviceIdanduserId(recordId, userId);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "升级装置异常!");
        }
     }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    @RequestMapping(value="/deleteParamAttrById",method=RequestMethod.POST)
    @ResponseBody
    public Object deleteParamAttrById(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String userId = request.getParameter("recordId");
        
        try {
            boolean flag = stateService.deleteParamAttrById(userId);
            if(flag){
                resultMap.put("status", "success");
                resultMap.put("msg", "删除成功!");
            }else{
                resultMap.put("status", "error");
                resultMap.put("msg", "删除失败,该用户已被使用!");
            }
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "删除失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/setAlarmthresholdById")
    public Map<String, Object> setAlarmthresholdById(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        
        //装置访问控制
        String device_access = request.getParameter("extrabody1access");
        String recordId = request.getParameter("recordIdofExtrabody1");

        //String device_password = "123";//此处后期要实现接口，从数据库中获取
        String device_password = stateService.getPasswordByDevice(recordId);
        if (!device_access.equals(device_password))
        {
            resultMap.put("status", "success");
            int result = -2;
            resultMap.put("result", result);
            return resultMap;
        }
                
        String userId = request.getParameter("userId");
        
        String IncUp = request.getParameter("IncUp");
        String IncLw = request.getParameter("IncLw");
        String IncXUp = request.getParameter("IncXUp");
        String IncXLw = request.getParameter("IncXLw");
        String IncYUp = request.getParameter("IncYUp");
        String IncYLw = request.getParameter("IncYLw");
        String AngXUp = request.getParameter("AngXUp");
        String AngXLw = request.getParameter("AngXLw");
        String AngYUp = request.getParameter("AngYUp");
        String AngYLw = request.getParameter("AngYLw");

        
        int nSize = 10;
        float[] ValueLst = new float[nSize];
     
        float fIncUp = Float.valueOf(IncUp).floatValue();
        ValueLst[0] = fIncUp;
        float fIncLw = Float.valueOf(IncLw).floatValue();
        ValueLst[1] = fIncLw;
        float fIncXUp = Float.valueOf(IncXUp).floatValue();
        ValueLst[2] = fIncXUp;
        float fIncXLw = Float.valueOf(IncXLw).floatValue();
        ValueLst[3] = fIncXLw;
        float fIncYUp = Float.valueOf(IncYUp).floatValue();
        ValueLst[4] = fIncYUp;
        float fIncYLw = Float.valueOf(IncYLw).floatValue();
        ValueLst[5] = fIncYLw;
        float fAngXUp = Float.valueOf(AngXUp).floatValue();
        ValueLst[6] = fAngXUp;
        float fAngXLw = Float.valueOf(AngXLw).floatValue();
        ValueLst[7] = fAngXLw;
        float fAngYUp = Float.valueOf(AngYUp).floatValue();
        ValueLst[8] = fAngYUp;
        float fAngYLw = Float.valueOf(AngYLw).floatValue();
        ValueLst[9] = fAngYLw;

        try {
            int result = stateService.addSetAlarmthresholdByDeviceIdAnduserId(recordId, userId,ValueLst[0]
                                        ,ValueLst[1],ValueLst[2],ValueLst[3],ValueLst[4],ValueLst[5],ValueLst[6],ValueLst[7],ValueLst[8],ValueLst[9]);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "增加设置装置告警阈值操作失败!");
        }
        return resultMap;
    }
    
    @RequestMapping(value="/setSampleRateById")
    public Map<String, Object> setSampleRateById(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        
        //装置访问控制
        String device_access = request.getParameter("forsamplerateaccess");
        String recordId = request.getParameter("SampleRateId");

        //String device_password = "123";//此处后期要实现接口，从数据库中获取
        String device_password = stateService.getPasswordByDevice(recordId);
        if (!device_access.equals(device_password))
        {
            resultMap.put("status", "success");
            int result = -2;
            resultMap.put("result", result);
            return resultMap;
        }
                
        String userId = request.getParameter("userId");
        String Main_Time = request.getParameter("Main_Time");
        String Sample_Count = request.getParameter("Sample_Count");
        String Sample_Frequency = request.getParameter("Sample_Frequency");
        String Heartbeat_Time = request.getParameter("Heartbeat_Time");
     
        short iMain_Time = Short.parseShort(Main_Time);
        short iSample_Count = Short.parseShort(Sample_Count);
        short iSample_Frequency = Short.parseShort(Sample_Frequency);
        byte iHeartbeat_Time = Byte.parseByte(Heartbeat_Time);


        try {
            int result = stateService.addSetSampleRateByDeviceIdanduserId(recordId, userId,iMain_Time,iSample_Count,iSample_Frequency,iHeartbeat_Time);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "增加设置采样率记录失败!");
        }
        return resultMap;
    }
    
    @RequestMapping(value="/setInfoTypeById")
    public Map<String, Object> setInfoTypeById(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        
        //装置访问控制
        String device_access = request.getParameter("forInfoTypeaccess");
        String recordId = request.getParameter("InfoTypeId");

        //String device_password = "123";//此处后期要实现接口，从数据库中获取
        String device_password = stateService.getPasswordByDevice(recordId);
        if (!device_access.equals(device_password))
        {
            resultMap.put("status", "success");
            int result = -2;
            resultMap.put("result", result);
            return resultMap;
        }
                
        String userId = request.getParameter("userId");
        String sInfoType = request.getParameter("InfoType");
        int iInfoType = Integer.valueOf(sInfoType).intValue();

        try {
            int result = stateService.addSetInfoTypeByDeviceIdanduserId(recordId, userId,iInfoType);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "增加设置装置上送信息类型记录失败!");
        }
        return resultMap;
    }
    
    @RequestMapping(value="/setDeviceTimeById")
    public Map<String, Object> setDeviceTimeById(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        
        //装置访问控制
        String device_access = request.getParameter("forDeviceTimeaccess");
        String recordId = request.getParameter("recordIdforDeviceTime");

        //String device_password = "123";//此处后期要实现接口，从数据库中获取
        String device_password = stateService.getPasswordByDevice(recordId);
        if (!device_access.equals(device_password))
        {
            resultMap.put("status", "success");
            int result = -2;
            resultMap.put("result", result);
            return resultMap;
        }
                
        String userId = request.getParameter("userId");
        String Time_Stamp = request.getParameter("Time_Stamp");

        try {
            int result = stateService.addSetDeviceTimeByDeviceIdanduserId(recordId, userId,Time_Stamp);
            resultMap.put("status", "success");
            resultMap.put("result", result);
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "增加设置装置时间记录失败!");
        }
        return resultMap;
    }
    
    @RequestMapping(value="/setParamById")
    public Map<String, Object> setParamById(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        
        //装置访问控制
        String device_access = request.getParameter("device_access");
        String recordId = request.getParameter("recordId");

        //String device_password = "123";//此处后期要实现接口，从数据库中获取
        String device_password = stateService.getPasswordByDevice(recordId);
        if (!device_access.equals(device_password))
        {
            resultMap.put("status", "success");
            int result = -2;
            resultMap.put("result", result);
            return resultMap;
        }
                
        String userId = request.getParameter("userId");
        String wave_current_time = request.getParameter("wave_current_time");
        String wave_current_threshold = request.getParameter("wave_current_threshold");
        String wave_current_time_collection = request.getParameter("wave_current_time_collection");
        String wave_current_freq_collection = request.getParameter("wave_current_freq_collection");
        String pf_current_time = request.getParameter("pf_current_time");
        String pf_current_threshold = request.getParameter("pf_current_threshold");
        String pf_current_time_collection = request.getParameter("pf_current_time_collection");
        String pf_current_freq_collection = request.getParameter("pf_current_freq_collection");
        String work_status_time = request.getParameter("work_status_time");
        String work_data_collection_interval = request.getParameter("work_data_collection_interval");
        String content = "";
        content+="wave_current_time: ";
        content+=wave_current_time;
        content+=" wave_current_threshold: ";
        content+=wave_current_threshold;
        content+=" wave_current_time_collection: ";
        content+=wave_current_time_collection;
        content+=" wave_current_freq_collection: ";
        content+=wave_current_freq_collection;
        content+=" pf_current_time: ";
        content+=pf_current_time;
        content+=" pf_current_threshold: ";
        content+=pf_current_threshold;
        content+=" pf_current_time_collection: ";
        content+=pf_current_time_collection;
        content+=" pf_current_freq_collection: ";
        content+=pf_current_freq_collection;
        content+=" work_status_time: ";
        content+=work_status_time;
        content+=" work_data_collection_interval: ";
        content+=work_data_collection_interval;
        
        int nSize = 10;
        int[] ValueLst = new int[nSize];
     
        int Iwave_current_time = Integer.valueOf(wave_current_time).intValue();
        ValueLst[0] = Iwave_current_time;
        int Iwave_current_threshold = Integer.valueOf(wave_current_threshold).intValue();
        ValueLst[1] = Iwave_current_threshold;
        int Iwave_current_time_collection = Integer.valueOf(wave_current_time_collection).intValue();
        ValueLst[2] = Iwave_current_time_collection;
        int Iwave_current_freq_collection = Integer.valueOf(wave_current_freq_collection).intValue();
        ValueLst[3] = Iwave_current_freq_collection;
        int Ipf_current_time = Integer.valueOf(pf_current_time).intValue();
        ValueLst[4] = Ipf_current_time;
        int Ipf_current_threshold = Integer.valueOf(pf_current_threshold).intValue();
        ValueLst[5] = Ipf_current_threshold;
        int Iwork_status_time = Integer.valueOf(work_status_time).intValue();
        ValueLst[8] = Iwork_status_time;
        int Ipf_current_freq_collection = Integer.valueOf(pf_current_freq_collection).intValue();
        ValueLst[7] = Ipf_current_freq_collection;
        int Ipf_current_time_collection = Integer.valueOf(pf_current_time_collection).intValue();
        ValueLst[6] = Ipf_current_time_collection;
        int Iwork_data_collection_interval = Integer.valueOf(work_data_collection_interval).intValue();
        ValueLst[9] = Iwork_data_collection_interval;
        
        /*获取私有参数
        String paraName = "南瑞私有参数7";
        String userId = request.getParameter(paraName);
        System.out.println(userId);
        */

        try {
            int result = stateService.addSetOrderByDeviceIdanduserId(recordId, userId, content,ValueLst);
            resultMap.put("status", "success");
            //resultMap.put("msg", "增加设置参数记录成功!");
            resultMap.put("result", result);
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "增加设置参数记录失败!");
        }
        return resultMap;
    }
    
    //setDealAlarmById
    @RequestMapping(value="/setDealAlarmById",method=RequestMethod.POST)
    @ResponseBody
    public Object setDealAlarmById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            stateService.setDealAlarmById(recordId);
            resultMap.put("status", "success");
            resultMap.put("msg", "设置故障报警已处理成功!");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "设置报警已处理失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
}