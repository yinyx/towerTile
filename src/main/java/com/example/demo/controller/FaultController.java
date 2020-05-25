package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.po.DataTableModel;
import com.example.demo.po.DataTableParam;
import com.example.demo.service.FaultService;
import com.example.demo.service.StateService;

import net.sf.json.JSONObject;
import util.aes.AesUtil;
import util.aes.DatatableUtil;



@RestController
@RequestMapping(value = "/fault")
public class FaultController {
    protected  Logger logger = LogManager.getLogger(getClass());
    
    // 注入用户Service
    @Resource
    private FaultService faultService;
    
    @Resource
    private StateService stateService;
    
    @RequestMapping(value="/queryWaveList")
    public Object  queryRegulatorList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = faultService.queryWaveInfoList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
            logger.info("查询波形列表信息成功!");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询波形列表信息异常!");
            logger.error("查询波形列表信息失败!");
        }
        return resultMap;
    }  
    
    @RequestMapping(value="/queryTowerTileParamList")
    public Object  queryTowerTileParamList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = faultService.queryTowerTileParamList(dataTableMap);
            System.out.println("-----------------------71");
            resultMap.put("status", "success");
            System.out.println("-----------------------72");
            resultMap.put("infoData", dataTableModel);
            System.out.println("-----------------------73");
            logger.info("查询杆塔倾斜参数列表信息成功!");
        }
        catch(Exception e)
        {
            System.out.println("-----------------------74");
            
            resultMap.put("status", "error");
            resultMap.put("msg", "查询杆塔倾斜参数列表信息异常!");
            logger.error("查询杆塔倾斜参数列表信息失败!");
        }
        return resultMap;
    }  
    
    @RequestMapping(value="/queryTowerTileAlarmList")
    public Object  queryTowerTileAlarmList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = faultService.queryTowerTileAlarmList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
            logger.info("查询杆塔倾斜报警列表信息成功!");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询杆塔倾斜报警列表信息异常!");
            logger.error("查询杆塔倾斜报警列表信息失败!");
        }
        return resultMap;
    } 
    
    @RequestMapping(value="/queryTowerTileStateList")
    public Object  queryTowerTileStateList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = faultService.queryTowerTileStateList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
            logger.info("查询杆塔倾斜状态列表信息成功!");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询杆塔倾斜状态列表信息异常!");
            logger.error("查询杆塔倾斜状态列表信息失败!");
        }
        return resultMap;
    } 
    
    @RequestMapping(value="/queryAlarmList")
    public Object  queryAlarmList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = faultService.queryAlarmList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
            logger.info("查询波形列表信息成功!");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询波形列表信息异常!");
            logger.error("查询波形列表信息失败!");
        }
        return resultMap;
    } 
    
	@RequestMapping(value="/queryFaultList")
    public Object  queryFaultList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = faultService.queryFaultList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("infoData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询故障列表信息异常!");
        }
        return resultMap;
    }  
	
	   @RequestMapping(value="/queryFaultListInGis")
	    public Object  queryFaultListInGis(@RequestBody DataTableParam[] dataTableParams){ 
	        DataTableModel dataTableModel = new DataTableModel();
	        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        try {
	            dataTableModel = faultService.queryFaultListInGis(dataTableMap);
	            resultMap.put("status", "success");
	            resultMap.put("infoData", dataTableModel);
	        }
	        catch(Exception e)
	        {
	            resultMap.put("status", "error");
	            resultMap.put("msg", "查询故障列表信息异常!");
	        }
	        return resultMap;
	    }
	
    @RequestMapping(value="/getWaveById",method=RequestMethod.POST)
    @ResponseBody
    public Object getWaveById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> usersData = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            usersData = faultService.getWaveById(recordId);
            resultMap.put("status", "success");
            resultMap.put("usersData", usersData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询波形信息异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    @RequestMapping(value="/getFaultById",method=RequestMethod.POST)
    @ResponseBody
    public Object getFaultById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> usersData = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            usersData = faultService.getFaultById(recordId);
            resultMap.put("status", "success");
            resultMap.put("usersData", usersData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询故障信息异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //setDealFaultById
    @RequestMapping(value="/setDealFaultById",method=RequestMethod.POST)
    @ResponseBody
    public Object setDealFaultById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            faultService.setDealFaultById(recordId);
            resultMap.put("status", "success");
            resultMap.put("msg", "设置故障已处理成功!");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "设置故障已处理失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
    //setDealFaultById
    @RequestMapping(value="/setDealTowerTileAlarmById",method=RequestMethod.POST)
    @ResponseBody
    public Object setDealTowerTileAlarmById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            faultService.setDealTowerTileAlarmById(recordId);
            resultMap.put("status", "success");
            resultMap.put("msg", "设置杆塔倾斜报警已处理成功!");
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "设置杆塔倾斜报警已处理失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
    
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
            resultMap.put("msg", "查询波形列表信息异常!");
        }
        return resultMap;
    } 
    
    @RequestMapping(value="/queryWavePwd")
    public Map<String, Object> queryWavePwd(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("wave_pwd", request.getParameter("wave_pwd"));
        paramMap.put("userId", request.getParameter("userId"));     

        resultMap =stateService.queryWavePwd(paramMap);

        return resultMap;
    } 
}
