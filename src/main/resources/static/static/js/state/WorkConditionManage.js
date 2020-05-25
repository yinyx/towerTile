var regulatorTable = null;
var userMap = {};
var userId = 0;

function initTimeSelect(){
    $("#StartTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: true,//今日按钮
        format: "yyyy-mm-dd hh:ii"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
    });
    $("#EndTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: true,//今日按钮
        format: "yyyy-mm-dd hh:ii"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
    });
}

function initRegulatorTable() {
	regulatorTable = $('#mainTable').DataTable({
		// url
		"sAjaxSource" : "state/queryWorkConditionList", 
		"bLengthChange":false,//取消显示每页条数
		// 服务器回调函数 
		"fnServerData": function retrieveData(sSource, aoData, fnCallback) 
		{
			aoData.push({ "name": "factory", "value": $("#cronFactory").val()}); 
			aoData.push({ "name": "device_id", "value": $("#device_id").val()}); 
			aoData.push({ "name": "StartTime", "value": $("#StartTime").val()}); 
			aoData.push({ "name": "EndTime", "value": $("#EndTime").val()});
			aoData.push({ "name": "userID",  "value": userId});
			$.ajax({
				type: "POST",
				url: sSource,
				contentType: "application/json; charset=utf-8",
			    data: JSON.stringify(aoData),
				success: function(data) 
				{	
					if(data.status == "success")
					{
						fnCallback(data.infoData);
					}else{
						showSuccessOrErrorModal(data.msg,"error");
					}
				},
				error:function(e){
					console.log("LogData fail")
					showSuccessOrErrorModal("获取波形数据失败","error");
				}
			});
		},
		// 列属性
		"columns" : [		 
		 {	
			 "title" : "所属厂家",  
			 "defaultContent" : "", 
			 "data" :"factory",
			 "width": "10%",
			 "class" : "text-center"  
		 } 
		 ,	 {	
			 "title" : "装置ID编号",  
			 "defaultContent" : "", 
			 "data" :"device",
			 "width": "10%",
			 "class" : "text-center"  
		 } 
		,	 {	
			 "title" : "装置标识",  
			 "defaultContent" : "", 
			 "data" :"deviceName",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		,	 {	
			 "title" : "工况时间",  
			 "defaultContent" : "", 
			 "data" :"workcondition_time",
			 "width": "10%",
			 "class" : "text-center"  
		 } 
		/*
		 ,	 {	
			 "title" : "通信状态",  
			 "defaultContent" : "", 
			 "data" :"comm_state",
			 "width": "10%",
			 "class" : "text-center"  
		 } 	*/
		,	 {	
			 "title" : "电源电压(V)",  
			 "defaultContent" : "", 
			 "data" :"battery_voltage",
			 "width": "10%",
			 "class" : "text-center",
		 } 
		,	 {	
			 "title" : "工作温度(℃)",  
			 "defaultContent" : "", 
			 "data" :"operation_temperature",
			 "width": "10%",
			 "class" : "text-center"  
		 } 
		,	 {	
			 "title" : "电池电量(Ah)",  
			 "defaultContent" : "", 
			 "data" :"battery_capacity",
			 "width": "10%",
			 "class" : "text-center"  
		 } 
		,	 {	
			 "title" : "浮充状态",  
			 "defaultContent" : "", 
			 "data" :"floating_charge",
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
		            var content ="";
		            if(data == 0){
		            	content = "充电";
		            }
					else if(data == 1){
						content = "放电";
					}
					else{
						content = "未知";
					}
		            return content;
		      }  
		 } 	
		,	 {	
			 "title" : "工作总时间(小时)",  
			 "defaultContent" : "", 
			 "data" :"total_working_time",
			 "width": "10%",
			 "class" : "text-center"  
		 } 	
		,	 {	
			 "title" : "本次连续工作时间(小时)",  
			 "defaultContent" : "", 
			 "data" :"working_time",
			 "width": "10%",
			 "class" : "text-center"  
		 }  
		,	 {	
			 "title" : "网络连接状态",  
			 "defaultContent" : "", 
			 "data" :"connection_state",
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
		            var content ="";
		            if(data == 0){
		            	content = "正常";
		            }
					else if(data == 1){
						content = "断开";
					}
					else{
						content = "未知";
					}
		            return content;
		      }    
		 } 
		 ]
	});
}

function initFactory(){
	$.ajax({
		url:"info/queryFactory",
		type:"post",
		data:{},
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var regulatorList = data.dataList;
		    	var str = "";
				str+='<option value="">---所有厂家---</option>';
		        for (var int = 0; int < regulatorList.length; int++) {
					str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				}
		        $("#cronFactory").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("初始化厂家列表下拉框请求出错了","error"); 
		}
	});	
}

function showTime(){
	var newDateObj = new Date(); 
	var year = newDateObj.getFullYear();
	var month = newDateObj.getMonth()+1;
	if(month==13)
		{
		month =1;
		}
	var day = newDateObj.getDate();
	var week = newDateObj.getDay();
	var arr = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
	var hour = newDateObj.getHours();
	var minute = newDateObj.getMinutes();
	var second = newDateObj.getSeconds();
	var showTime = year+"/"+month+"/"+day+" "+arr[week]+" "+hour+((minute<10)?":0":":")
	               +minute+((second<10)?":0":":")+second+((hour>12)?" 下午":" 上午");
	showTime = '<font color=red size=4>'+showTime+'</font>';
	
	var data = {"userId":userId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	
	$.ajax({
		url:"info/queryMarqueeInfo",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var allDeviceNum = data.allDeviceNum;
				var onlineDeviceNum = data.onlineDeviceNum;
				var offlineDeviceNum = data.offlineDeviceNum;
				var noReadAlarmNum = data.noReadAlarmNum;
				var noReadFaultNum = data.noReadFaultNum;
	            var showDevice ="系统接入设备："+allDeviceNum+"台     系统正常设备："
				           +onlineDeviceNum+"台     系统异常设备："+offlineDeviceNum+"台 ";
	            showDevice = '<font color=blue size=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+showDevice+'</font>';
	            var showFault = "    未读杆塔倾斜报警信息："+noReadFaultNum+"条";
	            showFault = '<font color=green size=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+showFault+'</font>';
	            var showAlarm = "    未读设备告警信息："+noReadAlarmNum+"条";
	            showAlarm = '<font color=red size=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+showAlarm+'</font>';
	            var str=/*showTime + */showDevice/* + showFault + showAlarm*/;
	            $("#marqueeTitle").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    //showSuccessOrErrorModal("滚动栏请求出错了","error"); 
		}
	});		
}

$(document).ready(function(){
		//判断是否登录
	userMap = isLogined();
	if(userMap){//成功登录
		userId = userMap.id;
	}else{
		//parent.location.href = jQuery.getBasePath() + "/login.html";
	}
	clearInterval(timer);
	showTime();
	timer = setInterval("showTime()",10000);
	initTimeSelect();
	initFactory();
	initRegulatorTable();
});


