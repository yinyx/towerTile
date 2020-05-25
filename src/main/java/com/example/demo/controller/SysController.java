package com.example.demo.controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.po.DataTableModel;
import com.example.demo.po.DataTableParam;
import com.example.demo.po.Line;
import com.example.demo.service.FaultService;
import com.example.demo.service.UserService;

import net.sf.json.JSONObject;
import util.aes.AesUtil;
import util.aes.DatatableUtil;
import util.aes.NrUtil;
import util.aes.TestProperties;

@RestController
@RequestMapping(value = "/sys")
public class SysController {
    // 注入用户Service
    @Resource
    private UserService userService;
    private FaultService faultService;
    
    @RequestMapping(value="/syncProcess")
    public String  syncProcess(){ 
        Map<String, Object> resultMap=new HashMap<String,Object>();
        try {
            //String cmd = "F:\\fr\\pubSub\\pubSub.exe";
            
            String cmd = TestProperties.getProperties_1("path.properties","syncpath");
            System.out.println(cmd);
            final Process p = Runtime.getRuntime().exec(cmd);

           // final Process p = Runtime.getRuntime().exec("fg3pf batch C:/tomcat/webapps/FaceGen/img/batch.csv f");
           
            try{
                BufferedInputStream br = new BufferedInputStream(p.getInputStream());
                BufferedOutputStream br1 = new BufferedOutputStream(p.getOutputStream());
                int ch;
                StringBuffer text = new StringBuffer("获得的信息是: \n");
 
                while ((ch = br.read()) != -1) {
                    text.append((char) ch);
                }
                int   retval   =   p.waitFor();
 
                System.out.println(text+br1.toString());
                System.out.println(retval);
                if (1 == retval)
                {
                    resultMap.put("status", "success");
                    resultMap.put("msg", "同步数据成功!");
                }
                else
                {
                    resultMap.put("status", "error");
                    resultMap.put("msg", "同步数据失败!");
                }
           
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                System.out.print(p.exitValue());
            }            
               
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "同步数据失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/syncDeviceProcess")
    public String  syncDeviceProcess(){ 
        Map<String, Object> resultMap=new HashMap<String,Object>();
        try {
            //String cmd = "F:\\fr\\pubSub\\pubSub.exe";
            
            String cmd = TestProperties.getProperties_1("path.properties","jobpath");
            System.out.println(cmd);
            final Process p = Runtime.getRuntime().exec(cmd);

           // final Process p = Runtime.getRuntime().exec("fg3pf batch C:/tomcat/webapps/FaceGen/img/batch.csv f");
           
            try{
                BufferedInputStream br = new BufferedInputStream(p.getInputStream());
                BufferedOutputStream br1 = new BufferedOutputStream(p.getOutputStream());
                int ch;
                StringBuffer text = new StringBuffer("获得的信息是: \n");
 
                while ((ch = br.read()) != -1) {
                    text.append((char) ch);
                }
                int   retval   =   p.waitFor();
 
                System.out.println(text+br1.toString());
                System.out.println(retval);
                if (1 == retval)
                {
                    resultMap.put("status", "success");
                    resultMap.put("msg", "同步异构数据成功!");
                }
                else
                {
                    resultMap.put("status", "error");
                    resultMap.put("msg", "同步异构数据失败!");
                }
           
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                System.out.print(p.exitValue());
            }            
               
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "同步数据失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/syncDeviceProcess1")
    public String  syncDeviceProcess1(){ 
        System.out.println("job run successfully!1");
        Map<String, Object> resultMap=new HashMap<String,Object>();
        System.out.println("job run successfully!2");
        try {
            //String cmd = "F:\\fr\\pubSub\\pubSub.exe";
            
            String jobPath = TestProperties.getProperties_1("path.properties","jobpath");
            System.out.println("job run successfully!3");
            try {
                KettleEnvironment.init();
                System.out.println("job run successfully!4");
                JobMeta jm = new JobMeta(jobPath, null);
                System.out.println("job run successfully!5");
                Job job = new Job(null, jm);
                System.out.println("job run successfully!6");
                job.start();
                System.out.println("job run successfully!7");
                job.waitUntilFinished();
                System.out.println("job run successfully!8");
                if(job.getErrors()>0)
                {
                    System.err.println("job run Failure!");
                }
                else
                {
                    System.out.println("job run successfully!");
                }
            } catch (KettleException e) {
                e.printStackTrace();
            }         
               
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "同步数据失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/queryLine")
    public String  queryLine(){ 
        Map<String, Object> resultMap=new HashMap<String,Object>();
        try {
            List<Line> LineList= this.userService.queryLine();  
            resultMap.put("status", "success");
            resultMap.put("dataList",LineList);
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "获取线路信息异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/queryLineByUser",method=RequestMethod.POST)
    @ResponseBody
    public Object queryLineByUser(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String userId = (String) paramObj.get("userId");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Line> LineList= this.userService.queryLineByUser(userId);
            resultMap.put("status", "success");
            resultMap.put("dataList",LineList);
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "获取线路信息异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/UserList")
    public String  queryUsers(){ 
        Map<String, Object> resultMap=new HashMap<String,Object>();
        try {
            List<Map<String, Object>> UserList= this.userService.queryUserList();  
            System.out.println("UserList");
            System.out.println(UserList);
            resultMap.put("status", "success");
            resultMap.put("dataList",UserList);
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "获取线路信息异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }    
    
    @RequestMapping(value="/queryUsersList")
    public Object  queryUsersList(@RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = userService.queryUsersList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("usersData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询日志列表信息异常!");
        }
                System.out.println("resultMap");
        System.out.println(resultMap);
        return resultMap;
    }

    @RequestMapping(value="/queryLogDList"/*,method=RequestMethod.POST*/)
    public Object  queryLogDList(/*HttpServletRequest request,HttpServletResponse response,*/
            @RequestBody DataTableParam[] dataTableParams){ 
        DataTableModel dataTableModel = new DataTableModel();
        Map<String, String> dataTableMap = DatatableUtil.convertToMap(dataTableParams);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            dataTableModel = userService.queryLogList(dataTableMap);
            resultMap.put("status", "success");
            resultMap.put("stuData", dataTableModel);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询日志列表信息异常!");
        }
                System.out.println("resultMap");
        System.out.println(resultMap);
        return resultMap;
    }    
   

    
    @RequestMapping(value="/queryWaveData",method=RequestMethod.POST)
    @ResponseBody
    public Object queryWaveData(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        Map<String, Object> resultMap=new HashMap<String,Object>();
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> usersData = new HashMap<String, Object>();
       
        usersData = userService.getWaveById(recordId);
        String fileName = (String) usersData.get("path");
        int si =  (int) paramObj.get("si");
        int ei =  (int) paramObj.get("ei");
           
        String cfgDoublePath = "";
        String datDoublePath = "";
        String cfgPath = TestProperties.getProperties_1("path.properties","cfgpath");
        String cfgPath_windows = TestProperties.getProperties_1("path.properties","socketpath");
        if(TestProperties.isOSLinux()){
            cfgDoublePath = cfgPath+fileName+".cfg";
            datDoublePath = cfgPath+fileName+".dat";
        }else{
            cfgDoublePath = cfgPath_windows+fileName+".cfg";
            datDoublePath = cfgPath_windows+fileName+".dat";
        }
        try {
         // 获取cfg content params
            Map<String, String> mapNr = NrUtil.getInstace().ReadCFGAndGetParams(cfgDoublePath);
            // 获取Shex content
            String hexContent = NrUtil.getInstace().readDatFile(datDoublePath);
            if(hexContent==null){
                resultMap.put("status", "error");
                resultMap.put("msg", datDoublePath+"\n该录波文件不存在");
                //logger.error("[查看录波文件失败-ErrorMsg:]", "datDoublePath");
            }else{
                //List<Object> list = NrUtil.getInstace().datH2D(Integer.parseInt(mapNr.get("TT_channelNum")), hexContent,  Float.parseFloat(mapNr.get("a1")), Float.parseFloat(mapNr.get("b1")));
                List<Object> list = NrUtil.getInstace().getDatByI(Integer.parseInt(mapNr.get("TT_channelNum")), hexContent,  Float.parseFloat(mapNr.get("a1")), Float.parseFloat(mapNr.get("b1")),si,ei);
                if(list==null){
                    resultMap.put("status", "error");
                    resultMap.put("msg",datDoublePath+"\n读取录波文件失败\n录播文件为单通道");
                }else{
                    resultMap.put("status", "success");
                    resultMap.put("msg","查看录波文件成功");
                    resultMap.put("data",mapNr);
                    resultMap.put("dataList",list);
                }
                
            }
            
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "查看录波文件失败");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/queryTowerTiltParamData",method=RequestMethod.POST)
    @ResponseBody
    public Object queryTowerTiltParamData(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        Map<String, Object> resultMap=new HashMap<String,Object>();
        String recordId = (String) paramObj.get("recordId");
        String deviceId = userService.getDeviceIdByrecordId(recordId);
        
        try {
            List<Map<String, Object>> towerparamDataList = userService.getTowerParmListByDeviceId(deviceId);
            int sizeofList = towerparamDataList.size();
            if (sizeofList>100)
            {
                towerparamDataList = towerparamDataList.subList(sizeofList-100, sizeofList);
            }
            List<Object> allList = new ArrayList<>();
            List<String> timestampList = new ArrayList<>();
            List<Float> inclinationList = new ArrayList<>();
            List<Float> inclination_xList = new ArrayList<>();
            List<Float> inclination_yList = new ArrayList<>();
            List<Float> angle_xList = new ArrayList<>();
            List<Float> angle_yList = new ArrayList<>();
            if ((towerparamDataList != null)&&(towerparamDataList.size() != 0))
            {
                for (Map<String, Object> towerparamData : towerparamDataList) {
                     String time_stamp = (String) towerparamData.get("time_stamp");
                     Float inclination = (Float) towerparamData.get("inclination");
                     Float inclination_x = (Float) towerparamData.get("inclination_x");
                     Float inclination_y = (Float) towerparamData.get("inclination_y");
                     Float angle_x = (Float) towerparamData.get("angle_x");
                     Float angle_y = (Float) towerparamData.get("angle_y");
                     timestampList.add(time_stamp);
                     inclinationList.add(inclination);
                     inclination_xList.add(inclination_x);
                     inclination_yList.add(inclination_y);
                     angle_xList.add(angle_x);
                     angle_yList.add(angle_y);
                     
                }
                allList.add(timestampList);
                allList.add(inclinationList);
                allList.add(inclination_xList);
                allList.add(inclination_yList);
                allList.add(angle_xList);
                allList.add(angle_yList);
                resultMap.put("status", "success");
                resultMap.put("dataList",allList);
            }
            else {
                resultMap.put("status", "empty");
            }
            
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "获取杆塔监测参数失败");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/queryFaultData",method=RequestMethod.POST)
    @ResponseBody
    public Object queryFaultData(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        Map<String, Object> resultMap=new HashMap<String,Object>();
        String recordId = (String) paramObj.get("recordId");
        Map<String, Object> usersData = new HashMap<String, Object>();
       
        usersData = userService.getFaultById(recordId);
        String fileName = (String) usersData.get("path");
        int si =  (int) paramObj.get("si");
        int ei =  (int) paramObj.get("ei");
           
        String cfgDoublePath = "";
        String datDoublePath = "";
        String cfgPath = TestProperties.getProperties_1("path.properties","cfgpath");
        String cfgPath_windows = TestProperties.getProperties_1("path.properties","socketpath");
        if(TestProperties.isOSLinux()){
            cfgDoublePath = cfgPath+fileName+".cfg";
            datDoublePath = cfgPath+fileName+".dat";
        }else{
            cfgDoublePath = cfgPath_windows+fileName+".cfg";
            datDoublePath = cfgPath_windows+fileName+".dat";
        }
        try {
         // 获取cfg content params
            Map<String, String> mapNr = NrUtil.getInstace().ReadCFGAndGetParams(cfgDoublePath);
            // 获取Shex content
            String hexContent = NrUtil.getInstace().readDatFile(datDoublePath);
            if(hexContent==null){
                resultMap.put("status", "error");
                resultMap.put("msg", datDoublePath+"\n该录波文件不存在");
                //logger.error("[查看录波文件失败-ErrorMsg:]", "datDoublePath");
            }else{
                //List<Object> list = NrUtil.getInstace().datH2D(Integer.parseInt(mapNr.get("TT_channelNum")), hexContent,  Float.parseFloat(mapNr.get("a1")), Float.parseFloat(mapNr.get("b1")));
                List<Object> list = NrUtil.getInstace().getDatByI(Integer.parseInt(mapNr.get("TT_channelNum")), hexContent,  Float.parseFloat(mapNr.get("a1")), Float.parseFloat(mapNr.get("b1")),si,ei);
                if(list==null){
                    resultMap.put("status", "error");
                    resultMap.put("msg",datDoublePath+"\n读取录波文件失败\n录播文件为单通道");
                }else{
                    resultMap.put("status", "success");
                    resultMap.put("msg","查看录波文件成功");
                    resultMap.put("data",mapNr);
                    resultMap.put("dataList",list);
                }
                
            }
            
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "查看录波文件失败");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/queryUserNameIsRepeat",method=RequestMethod.POST)
    public Object  queryUserNameIsRepeat(HttpServletRequest request,HttpServletResponse response){ 
        Map<String, Object> resultMap=new HashMap<String,Object>();
        String userName = request.getParameter("userName");
        boolean flag = false;
        try {
            flag = userService.queryUserNameIsRepeat(userName);
            resultMap.put("status", "success");
            resultMap.put("flag",flag);
        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "获取用户名是否重复失败!");
        }
        return resultMap;
    }
    
    @RequestMapping(value="/saveSchoolUser",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveSchoolUser(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String lines = request.getParameter("line");
        String[] lineArr = lines.split(",");
        List<String> lineslist = java.util.Arrays.asList(lineArr);
        //平台类型
        paramMap.put("userId", request.getParameter("userId"));
        paramMap.put("userName", request.getParameter("realName"));
        paramMap.put("phone", request.getParameter("userName"));     
        paramMap.put("role", request.getParameter("role"));   
        paramMap.put("status", request.getParameter("states")); 
        paramMap.put("authority", request.getParameter("authority")); 
        paramMap.put("lineslist",lineslist);
        paramMap.put("loginUser",request.getParameter("loginUser"));
        try {
            userService.saveSchoolUser(paramMap);
            resultMap.put("status", "success");
            resultMap.put("msg", "用户保存成功!");
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "用户保存失败!");
        }
        return resultMap;
    }
    
    @RequestMapping(value="/getUserById",method=RequestMethod.POST)
    @ResponseBody
    public Object getUserById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String userId = (String) paramObj.get("userId");
        Map<String, Object> usersData = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            usersData = userService.getUserById(userId);
            System.out.println("usersData");
            System.out.println(usersData);
            resultMap.put("status", "success");
            resultMap.put("usersData", usersData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询学校用户列表信息异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }    
    
    @RequestMapping(value="/deleteSchoolUser",method=RequestMethod.POST)
    @ResponseBody
    public Object deleteSchoolUser(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String userId = request.getParameter("userId");
        
        try {
            boolean flag = userService.deleteSchoolUser(userId);
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
    
    @RequestMapping(value="/deleteRegulator",method=RequestMethod.POST)
    @ResponseBody
    public Object deleteRegulator(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String userId = request.getParameter("userId");
        
        try {
            boolean flag = userService.deleteRegulator(userId);
            if(flag){
                resultMap.put("status", "success");
                resultMap.put("msg", "删除成功!");
            }else{
                resultMap.put("status", "error");
                resultMap.put("msg", "删除失败,该监管单位已被使用!");
            }
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "删除失败!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }    
    
    @RequestMapping(value="/saveWaveWord")
    @ResponseBody
    public String saveWaveWord(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", paramObj.get("id"));              
        paramMap.put("password", paramObj.get("password"));
        System.out.println(paramMap);
        try {
            userService.saveWaveWord(paramMap);
            resultMap.put("status", "success");
            resultMap.put("msg", "密码保存成功!");
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "密码保存失败!");
        }
        
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/saveDevicePassword")
    @ResponseBody
    public String saveDevicePassword(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        
        String id = (String) paramObj.get("id");
        String password_old = (String) paramObj.get("password_old");      
        String password_old_db = userService.getPassByDevId(id);
        if (!password_old.equals(password_old_db))
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "您输入的原密码不正确!");
            JSONObject jsonObject = JSONObject.fromObject(resultMap);
            String enResult = AesUtil.enCodeByKey(jsonObject.toString());
            return enResult;
        }
        
        paramMap.put("password", paramObj.get("password"));
        System.out.println(paramMap);
        try {
            userService.saveDevicePass(paramMap);
            resultMap.put("status", "success");
            resultMap.put("msg", "密码保存成功!");
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "密码保存失败!");
        }
        
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/resetPassword")
    @ResponseBody
    public String resetPassword(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", paramObj.get("id"));              
        try {
            userService.resetPassword(paramMap);
            resultMap.put("status", "success");
            resultMap.put("msg", "重置密码成功!");
        } catch(Exception e) {
            resultMap.put("status", "error");
            resultMap.put("msg", "重置密码失败!");
        }
        
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    }
    
    @RequestMapping(value="/getRegulatorById",method=RequestMethod.POST)
    @ResponseBody
    public Object getRegulatorById(@RequestParam Map<String, Object> map){
        JSONObject paramObj=AesUtil.GetParam(map);
        String regulatorId = (String) paramObj.get("regulatorId");
        Map<String, Object> usersData = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            usersData = userService.getRegulatorById(regulatorId);
            resultMap.put("status", "success");
            resultMap.put("usersData", usersData);
        }
        catch(Exception e)
        {
            resultMap.put("status", "error");
            resultMap.put("msg", "查询监管单位列表信息异常!");
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        String enResult = AesUtil.enCodeByKey(jsonObject.toString());
        return enResult;
    } 
}
