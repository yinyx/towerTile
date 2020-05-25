package com.example.demo.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.po.ParamAttr;

@Mapper
public interface StateMapper {
    @Select("select * from dev_heartbeat t1, info_device t2 where (t1.heartbeat_time between #{StartTime} and #{EndTime}) and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) ORDER BY t1.heartbeat_time DESC limit #{start},#{length}")
    List<Map<String, Object>> queryHeartBeatList(@Param("start") Integer start, @Param("length") Integer length, @Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select COUNT(1) from  dev_heartbeat t1, info_device t2 where (t1.heartbeat_time between #{StartTime} and #{EndTime}) and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) ")
    int queryHeartBeatListCount(@Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select * from dev_heartbeat where (heartbeat_time between #{StartTime} and #{EndTime}) and device in (select id from info_device where line_name in(select line from line_user where user = #{userID})) ORDER BY heartbeat_time DESC limit #{start},#{length}")
    List<Map<String, Object>> queryHeartBeatListByUser(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select COUNT(1) from dev_heartbeat where (heartbeat_time between #{StartTime} and #{EndTime}) and device in (select id from info_device where line_name in(select line from line_user where user = #{userID}))")
    int queryHeartBeatListCountByUser(@Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    
    @Select("select * from dev_workcondition t1, info_device t2 where (t1.workcondition_time between #{StartTime} and #{EndTime} or t1.workcondition_time IS NULL) and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) ORDER BY t1.workcondition_time DESC limit #{start},#{length}")
    List<Map<String, Object>> queryWorkConditionList(@Param("start") Integer start, @Param("length") Integer length, @Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select COUNT(1) from dev_workcondition t1, info_device t2 where (t1.workcondition_time between #{StartTime} and #{EndTime} or t1.workcondition_time IS NULL) and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) )")
    int queryWorkConditionListCount(@Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select * from dev_workcondition where (workcondition_time between #{StartTime} and #{EndTime} or workcondition_time IS NULL) and device in (select id from info_device where line_name in(select line from line_user where user = #{userID})) ORDER BY workcondition_time DESC limit #{start},#{length}")
    List<Map<String, Object>> queryWorkConditionListByUser(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select COUNT(1) from dev_workcondition where (workcondition_time between #{StartTime} and #{EndTime} or workcondition_time IS NULL) and device in (select id from info_device where line_name in(select line from line_user where user = #{userID}))")
    int queryWorkConditionListCountByUser(@Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    ///OrderStatus
    @Select("select * from dev_orderstatus t1, info_device t2 where (t1.time between #{StartTime} and #{EndTime}) and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) ORDER BY t1.time DESC limit #{start},#{length}")
    List<Map<String, Object>> queryOrderStatusList(@Param("start") Integer start, @Param("length") Integer length, @Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select COUNT(1) from  dev_orderstatus t1, info_device t2 where (t1.time between #{StartTime} and #{EndTime}) and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) ")
    int queryOrderStatusListCount(@Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select * from dev_orderstatus where (time between #{StartTime} and #{EndTime}) and device in (select id from info_device where line_name in(select line from line_user where user = #{userID})) ORDER BY time DESC limit #{start},#{length}")
    List<Map<String, Object>> queryOrderStatusListByUser(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    @Select("select COUNT(1) from dev_orderstatus where (time between #{StartTime} and #{EndTime}) and device in (select id from info_device where line_name in(select line from line_user where user = #{userID}))")
    int queryOrderStatusListCountByUser(@Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime);
    
    //Alarm
    @Select("select * from dev_alarm t1, info_device t2 where (t1.alarm_time between #{StartTime} and #{EndTime}) and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID}))) and (t1.isdealed=#{deal} or #{deal}='') and (t1.alarm_content not like '%传感器故障：未收到传感器数据%')  ORDER BY t1.alarm_time DESC limit #{start},#{length}")
    List<Map<String, Object>> queryAlarmList(@Param("start") Integer start, @Param("length") Integer length, @Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime, @Param("deal") String deal);
    
    @Select("select COUNT(1) from  dev_alarm t1, info_device t2 where (t1.alarm_time between #{StartTime} and #{EndTime}) and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) and (t1.isdealed=#{deal} or #{deal}='')  and (t1.isdealed=#{deal} or #{deal}='') and (t1.alarm_content not like '%传感器故障：未收到传感器数据%') ")
    int queryAlarmListCount(@Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime, @Param("deal") String deal);
    
    @Select("select * from dev_alarm where (alarm_time between #{StartTime} and #{EndTime}) and device in (select id from info_device where line_name in(select line from line_user where user = #{userID})) and (isdealed=#{deal} or #{deal}='') and (alarm_content not like '%传感器故障：未收到传感器数据%')  ORDER BY alarm_time DESC limit #{start},#{length}")
    List<Map<String, Object>> queryAlarmListByUser(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime, @Param("deal") String deal);
    
    @Select("select COUNT(1) from dev_alarm where (alarm_time between #{StartTime} and #{EndTime}) and device in (select id from info_device where line_name in(select line from line_user where user = #{userID})) and (isdealed=#{deal} or #{deal}='') and (alarm_content not like '%传感器故障：未收到传感器数据%')  ")
    int queryAlarmListCountByUser(@Param("userID") String userID, @Param("StartTime") String StartTime, @Param("EndTime") String EndTime, @Param("deal") String deal);
    
    //querydeviceListByUser
    @Select("select * from info_device t1, info_device t2 where (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) limit #{start},#{length}")
    List<Map<String, Object>> querydeviceList(@Param("start") Integer start, @Param("length") Integer length, @Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID);
    
    @Select("select * from info_device t1, info_device t2 where t1.comm_state=0 and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) limit #{start},#{length}")
    List<Map<String, Object>> querydeviceList1(@Param("start") Integer start, @Param("length") Integer length, @Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID);
    
    @Select("select * from info_device t1, info_device t2 where t1.comm_state=1 and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) limit #{start},#{length}")
    List<Map<String, Object>> querydeviceList2(@Param("start") Integer start, @Param("length") Integer length, @Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID);
    
    @Select("select COUNT(1) from  info_device t1, info_device t2 where (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) ")
    int querdeviceListCount(@Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID);
 
    @Select("select COUNT(1) from  info_device t1, info_device t2 where t1.comm_state=0 and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) ")
    int querdeviceListCount1(@Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID);
    
    @Select("select COUNT(1) from  info_device t1, info_device t2 where t2.comm_state=0 and (t2.name=#{device_id}or#{device_id}='') and (t1.device=t2.id)and t1.device in (select id from info_device where ((manufacture = #{QueryType} or #{QueryType} = '') and line_name in(select line from line_user where user = #{userID})) ) ")
    int querdeviceListCount2(@Param("QueryType") String QueryType, @Param("device_id") String device_id, @Param("userID") String userID);
    
    @Select("select * from info_device where line_name in(select line from line_user where user = #{userID}) limit #{start},#{length}")
    List<Map<String, Object>> querydeviceListByUser(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID);
    
    @Select("select * from info_device where comm_state=0 and line_name in(select line from line_user where user = #{userID}) limit #{start},#{length}")
    List<Map<String, Object>> querydeviceListByUser1(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID);
    
    @Select("select * from info_device where comm_state=1 and line_name in(select line from line_user where user = #{userID}) limit #{start},#{length}")
    List<Map<String, Object>> querydeviceListByUser2(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID);
    
    @Select("select * from info_device where (line_name=#{line} or #{line}='') and (name=#{device_id} or #{device_id}='') and line_name in(select line from line_user where user = #{userID}) limit #{start},#{length}")
    List<Map<String, Object>> querydeviceListByUser3(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID, @Param("line") String line, @Param("device_id") String device_id);
    
    @Select("select t1.*,GROUP_CONCAT(t2.`name`) AS \"LineName\" ,GROUP_CONCAT(t2.`length`) AS \"LineLength\" from line_user t1, info_line t2 where t1.user=#{userID} and t1.line=t2.id GROUP BY t1.id limit #{start},#{length}")
    List<Map<String, Object>> queryLineListByUser(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID);
    
    @Select("select count(1) from line_user where user=#{userID}")
    int queryLineListCountByUser(@Param("userID") String userID);
    
    //Fault
    @Select("select * from report_fault where line in(select line from line_user where user = #{userID}) limit #{start},#{length}")
    List<Map<String, Object>> queryFaultListByUser(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID);
    
    //queryAlldeviceListByUser
    @Select("select * from info_device where line_name in(select line from line_user where user = #{userID})")
    List<Map<String, Object>> queryAlldeviceListByUser(@Param("userID") String userID);
    
    //queryLineByLineuser
    @Select("select line from line_user where id=#{LineUserId}")
    String queryLineByLineuser(@Param("LineUserId") String LineUserId);
    
    //queryTowerListByLine
    @Select("select * from info_tower where line=#{LineId} ORDER BY indexno ASC")
    List<Map<String, Object>> queryTowerListByLine(@Param("LineId") String LineId);
    
    @Select("select max(indexno) from info_tower where line=#{LineId}")
    int queryMaxTowerIndexByLine(@Param("LineId") String LineId);
    
    //queryAllFaultListByUser
    @Select("select * from report_fault where line in(select line from line_user where user = #{userID})")
    List<Map<String, Object>> queryAllFaultListByUser(@Param("userID") String userID);
    
    @Select("select COUNT(1) from info_device where line_name in(select line from line_user where user = #{userID})")
    int querydeviceListCountByUser(@Param("userID") String userID);
    
    @Select("select COUNT(1) from info_device where comm_state=0 and line_name in(select line from line_user where user = #{userID})")
    int querydeviceListCountByUser1(@Param("userID") String userID);
    
    @Select("select COUNT(1) from info_device where comm_state=1 and line_name in(select line from line_user where user = #{userID})")
    int querydeviceListCountByUser2(@Param("userID") String userID);
       
    @Select("select COUNT(1) from info_device where (line_name=#{line} or #{line}='') and (name=#{device_id} or #{device_id}='') and line_name in(select line from line_user where user = #{userID})")
    int querydeviceListCountByUser3(@Param("userID") String userID, @Param("line") String line, @Param("device_id") String device_id);
   
    //Fault
    @Select("select COUNT(1) from report_fault where line in(select line from line_user where user = #{userID})")
    int queryFaultListCountByUser(@Param("userID") String userID);
    
    //Param
    @Select("select * from dev_parameter where device in (select id from info_device where (manufacture = #{QueryType} and line_name in(select line from line_user where user = #{userID})) ) limit #{start},#{length}")
    List<Map<String, Object>> queryParameterList(@Param("start") Integer start, @Param("length") Integer length, @Param("QueryType") String QueryType, @Param("userID") String userID);
    
    @Select("select COUNT(1) from dev_parameter where device in (select id from info_device where (manufacture = #{QueryType}) and line_name in(select line from line_user where user = #{userID}))")
    int queryParameterListCount(@Param("QueryType") String QueryType, @Param("userID") String userID);
    
    @Select("select * from dev_parameter where device in (select id from info_device where line_name in(select line from line_user where user = #{userID})) limit #{start},#{length}")
    List<Map<String, Object>> queryParameterListByUser(@Param("start") Integer start, @Param("length") Integer length, @Param("userID") String userID);
    
    @Select("select COUNT(1) from dev_parameter where device in (select id from info_device where line_name in(select line from line_user where user = #{userID}))")
    int queryParameterListCountByUser(@Param("userID") String userID);
    
    //ParamAttr
    @Select("select t1.*, GROUP_CONCAT(t2.`name`) AS \"factory\" from parameter_attr t1, info_factory t2 where t1.manufactureId = #{factoryID} and t1.manufactureId =t2.id GROUP BY t1.id limit #{start},#{length}")
    List<Map<String, Object>> queryParameterAttrListByFactory(@Param("start") Integer start, @Param("length") Integer length, @Param("factoryID") String factoryID);
  
    @Select("select COUNT(1) from parameter_attr where manufactureId = #{factoryID}")    
    int queryParameterAttrListCountByFactory(@Param("factoryID") String factoryID);
    
    @Select("select name,manufacture from info_device where id = #{id}")
    Map<String, Object> getDeviceInfoById(String id);
    
    @Select("select * from report_fault where left_tower = #{towerID}")
    Map<String, Object> getFaultByLefttowerId(String towerID);
    
    //getAlarmByTowerId
    @Select("select * from report_towertilealarm where tower = #{towerID} and isRead=0 and time_stamp=(select max(time_stamp) from report_towertilealarm where tower = #{towerID} and isRead=0)")
    Map<String, Object> getAlarmByTowerId(String towerID);
    
    //getStateByTowerId
    @Select("select * from report_tilt_status where tower = #{towerID} and istilt=1")
    Map<String, Object> getStateByTowerId(String towerID);
    
    //getParamNameListByfactoryId
    @Select("select name from parameter_attr where manufactureId = #{id} order by indexno")
    List<String> getParamNameListByfactoryId(String id);
    
    @Select("select name,type from parameter_attr where manufactureId = #{id}")
    List<Map<String, Object>> getParamInfoListByfactoryId(String id);
    
    @Select("select * from dev_parameter where device = #{id}")
    Map<String, Object> getParamByDeviceId(String id);
    
    @Select("select name from info_factory where id = #{id}")
    String getFactoryNameByFactoryId(String id);

    @Select("select max(heartbeat_time) from dev_heartbeat where device = #{id}")
    String getCommTimeByDeviceId(String id);
    
    @Select("select name from info_line where id = #{id}")
    String getLineNameByLineId(String id);
    
    @Select("select user_name from sys_user where id = #{id}")
    String getUserNameByUserId(String id);
   
    @Select("select * from info_tower where id = #{id}")
    Map<String, Object> getTowerInfoById(String id);
    
    //getFactoryById
    @Select("select * from info_factory where id = #{id}")
    Map<String, Object> getFactoryById(String id);
    
    //ParamAttr
    @Insert("INSERT parameter_attr (id,manufactureId,indexno,name,type,isPrivate) VALUES (#{recordId},#{factoryId},#{indexno},#{name},#{type},#{isPrivate})")
    void addParamAttr(Map<String, Object> paramMap);
    
    @Update("UPDATE parameter_attr SET manufactureId=#{factoryId},indexno=#{indexno},name=#{name},type=#{type},isPrivate=#{isPrivate} WHERE id = #{recordId}")
    void updateParamAttr(Map<String, Object> paramMap);
    
    //getParamAttrById
    @Select("select * from parameter_attr where id = #{id}")
    Map<String, Object> getParamAttrById(String id); 
    
    //getParamById
    @Select("select * from dev_parameter where device = #{id}")
    Map<String, Object> getParamById(String id); 
    
    @Select("select Time_Stamp from dev_parameter where device = #{id}")
    String getTime_StampByDeviceId(String id);
    
    @Select("select InfoType from dev_parameter where device = #{id}")
    int getInfoTypeByDeviceId(String id);
    
    //getThresholdByDeviceId（以下2个接口合用一张表）
    @Select("select * from dev_parameter where device = #{id}")
    Map<String, Object> getThresholdByDeviceId(String id); 

    @Select("select inclination,inclination_x,inclination_y,angle_x,angle_y from dev_parameter where device = #{id}")
    Map<String, Object> getTiltDataByDeviceId(String id);
    
    @Select("select * from dev_parameter where device = #{id}")
    Map<String, Object> getSampleRateByDeviceId(String id); 
    
    //deleteParamAttrById
    @Delete("DELETE FROM parameter_attr WHERE id = #{userId}")
    void deleteParamAttrById(String userId);
        
    @Select("Select * from parameter_attr")
    List<ParamAttr> queryParamAttrList();
    
    @Select("Select * from parameter_attr WHERE id = #{regulatorId}")
    ParamAttr getParamAttrIdById(String regulatorId);
    
    @Select("Select max(serial_num) from dev_orderstatus")
    int getMaxSerialNum();
   
    @Select("Select COUNT(1) from dev_orderstatus")
    int getOrderNum();
    
    @Select("Select COUNT(1) from dev_orderstatus where id =#{id}")
    int getOrderNumByOrderid(String id);
    
    @Select("Select * from dev_orderstatus where id =#{id}")
    Map<String, Object> getStateByOrderid(@Param("id") String id);
    
    @Select("Select * from dev_parameter where device =#{id}")
    Map<String, Object> getSampleRateByDeviceid(@Param("id") String id);
  
    @Select("Select operate_authority from sys_user where id =#{id}")
    int getAuhorityByUser(String id);
    
    @Select("Select access_password from info_device where id =#{id}")
    String getPasswordByDevice(String id);
    
    @Select("Select protocol_version from info_device where id =#{id}")
    int getProtocolVerByDeviceId(String id);
      
    @Update("commit")
    void Commit();
  
    @Insert("INSERT dev_orderstatus (id,serial_num,device,time,type,para_num,content,state,user) VALUES (#{OrderrecordId},#{serial_num},#{recordId},NOW(),0,0,'',0,#{userId})")
    void addReadParamOrder(@Param("OrderrecordId") String OrderrecordId, @Param("serial_num") Integer serial_num, @Param("recordId") String recordId, @Param("userId") String userId);

    @Delete("DELETE FROM dev_orderstatus WHERE id = #{OrderrecordId} and serial_num=1")
    void deleteReadParamOrder(@Param("OrderrecordId") String OrderrecordId);
    
    //addResetOrder
    @Insert("INSERT dev_orderstatus (id,serial_num,manufacture,device,time,type,para_num,content,state,user) VALUES (#{OrderrecordId},#{serial_num},#{factoryId},#{recordId},NOW(),2,0,'',0,#{userId})")
    void addResetOrder(@Param("OrderrecordId") String OrderrecordId, @Param("serial_num") Integer serial_num, @Param("factoryId") String factoryId, @Param("recordId") String recordId, @Param("userId") String userId);
    
    //addSetOrder
    @Insert("INSERT dev_orderstatus (id,serial_num,manufacture,device,time,type,para_num,content,state,user) VALUES (#{OrderrecordId},#{serial_num},#{factoryId},#{recordId},NOW(),1,#{para_num},#{content},0,#{userId})")
    void addSetOrder(@Param("OrderrecordId") String OrderrecordId, @Param("serial_num") Integer serial_num, @Param("factoryId") String factoryId, @Param("recordId") String recordId, @Param("userId") String userId, @Param("content") String content, @Param("para_num") Integer para_num);

    @Update("UPDATE dev_alarm SET isdealed=1 WHERE id = #{userId}")
    void setDealAlarmById(String userId);
    
    @Select("Select log_path from info_device where id =#{userId}")
    String getDeviceLogNameById(String userId);
    
    @Update("update dev_parameter set IncUp=#{Value0},IncLw=#{Value1},IncXUp=#{Value2},IncXLw=#{Value3},IncYUp=#{Value4},IncYLw=#{Value5},AngXUp=#{Value6},AngXLw=#{Value7},AngYUp=#{Value8},AngYLw=#{Value9} where device=#{device}")
    void UpdateShreshold(HashMap<String, Object> result);
}
