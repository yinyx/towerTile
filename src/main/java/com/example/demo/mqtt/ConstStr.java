package com.example.demo.mqtt;

public class ConstStr {

	static public String ID = "id";
	static public String DEVID = "DevId";
	static public String PARAMETERS = "Parameters";	
	static public String TYPE = "type";
	static public String IDX="idx";
	static public String CRC = "crc";	
	static public String DATA = "data";
	static public String DATALEN="data_len";
	static public String FLAG = "flag";
	static public String RESULT = "result";
	static public String CMD = "cmd";
	static public String TIME = "time";
	static public String NUMBER = "number";
	static public String PRIVATEDATA = "private_data";
	static public String BATTERY = "battery";
	static public String PRESS = "press";
	static public String TEMPERATURE = "temperature";
	static public String CURRENT = "current";	
	static public String ERRORRPTTIME = "errorrpttime";
	static public String INFORMATION = "information";
	static public String USER = "user";
	static public String FACTORY = "factory";
	static public String PROCOLVERSION = "protocol_version";
	
	static public String STATE = "state";
	static public String DEVICE = "device";
	static public String NAME = "name";
	static public String RECVTIME="recv_time";
	static public String WAVESTART = "wave_start";
	static public String PARANUM = "para_num";
	static public String CONTENT = "content";
	static public String FRAMETYPE="frame_type";
	static public String PACKGETTYPE="packet_type";
	static public String DTUSTATE="dtu_state";
	static public String DBTIME="db_time";
	static public String SENDTIME="send_time";
	
	static public String MANUFACTURE = "manufacture";
	static public String MANUFACTUREID = "manufactureid";
	static public String VERSION = "version";
	static public String LINE = "line";
	static public String TOWER = "tower";
	static public String PHASE = "phase";
	static public String COMPANY = "company";
	static public String ALARMTIME = "alarm_time";
	static public String ALARMCONTENT = "alarm_content";
	static public String ISDEALED = "isdealed";
	static public String BAK = "bak";
	static public String REMARK = "remark";
	static public String HEARTBEATTIME = "heartbeat_time";
	static public String WAVECURRENTTIME = "wave_current_time";
	static public String WAVECURRENTTHRESHOLD = "wave_current_threshold";
	static public String WAVECURRENTTIMECOLLECTION =  "wave_current_time_collection";
	static public String WAVECURRENTFREQCOLLECTION = "wave_current_freq_collection";
	static public String STARTTIME = "start_time";
	static public String WAVELENGTH = "wave_length";
	static public String WAVETYPE = "wave_type";
	static public String PATH = "path";

	static public String CREATETIME = "create_time";
	
	//采样参数
	static public final String MAIN_TIME = "main_time";//采样间隔 ,以分钟为单位
	//高速采样点
	static public final String SAMPLE_COUNT = "sample_count";
	//高速采样频率
	static public final String SAMPLE_FREQUENCY = "sample_frequency";
	//心跳上送周期
	static public final String HEART_TIME = "heart_time";
	
	//请求flag 按位设置
	static public final String REQUEST_FLAG = "request_flag";
	
	//mqtt协议
	static public final int MQTT_TILT = 1;//倾斜度上送
	static private final int MQTT_TILT_ACK = 2;//倾斜度上送回复（不用，暂留，这个由前端直接回复了）
	static public final int MQTT_RESET = 3;//复位命令
	static public final int MQTT_RESET_ACK = 4;//复位命令	
	static public final int MQTT_SAMPLE_SET = 7;//采样设置
	static public final int MQTT_SAMPLE_SET_ACK = 8;//采样设置回复
	static public final int MQTT_SAMPLE_QUERY = 9;//采样查询
	static public final int MQTT_SAMPLE_QUERY_ACK = 10;//采样查询回复	
	static public final int MQTT_TIME_SET = 11;//时间设置
	static public final int MQTT_TIME_SET_ACK = 12;	//时间设置回复
	static public final int MQTT_TIME_QUERY = 13;//时间查询
	static public final int MQTT_TIME_QUERY_ACK = 14;	//时间查询回复	
	static public final int MQTT_THRSHLD_SET = 15;//阈值设置
	static public final int MQTT_THRSHLD_SET_ACK = 16;	//阈值设置回复
	static public final int MQTT_THRSHLD_QUERY = 17;//阈值查询
	static public final int MQTT_THRSHLD_QUERY_ACK = 18;	//阈值查询回复
	static public final int MQTT_DEVINFO_SET = 19;//装置基本信息设置
	static public final int MQTT_DEVINFO_SET_ACK = 20;	//装置基本信息回复
	static public final int MQTT_DEVINFO_QUERY = 21;//装置基本信息设置
	static public final int MQTT_DEVINFO_QUERY_ACK = 22;	//装置基本信息回复
	static public final int MQTT_WORKSTATION = 23;//装置工况
	static public final int MQTT_WORKSTATION_ACK = 24;//装置工况回复	
	static public final int MQTT_ERROR = 25;//装置工况
	static private final int MQTT_ERROR_ACK = 26;//装置工况（不用，前端回复了）
	static public final int MQTT_HEART = 27;
	static public final int MQTT_DEVINFO = 28;//基本信息
	static public final int MQTT_UPDATA = 29;//上召数据
	static public final int MQTT_UPDATA_ACK = 30;//上召数据回复
	static public final int MQTT_RESET_DATA = 31;//校准
	static public final int MQTT_RESET_DATA_ACK = 32;//校准回复
	
	//时间
	static public final String CLOCK_STAMP = "clock_stamp";

	//参数设置
	static public final String INCLICATION_MAX = "IncUp";
	static public final String INCLICATION_MIN = "IncLw";

	static public final String INCLICATION_X_MAX = "IncXUp";
	static public final String INCLICATION_X_MIN = "IncXLw";
	
	static public final String INCLICATION_Y_MAX = "IncYUp";
	static public final String INCLICATION_Y_MIN = "IncYLw";

	static public final String ANGLE_X_MAX = "AngXUp";
	static public final String ANGLE_X_MIN = "AngXLw";
	
	static public final String ANGLE_Y_MAX = "AngYUp";
	static public final String ANGLE_Y_MIN = "AngYLw";
	
	//基本信息查询/设置
	//信息类型
	static public final String INFO_TYPE = "info_type";
	static public final String INFO_VERSION = "info_version";
	static public final int BASIC_INFO = 0x01;
	static public final int STATUS_INFO = 0x02;
	
	static public final String RESET_MODE = "reset_mode";
	
}

