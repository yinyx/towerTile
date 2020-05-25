var regulatorTable = null;
var userMap = {};
var userId = 0;

function closeModalForDeviceTime(){
	$("#forDeviceTimeaccess_m").val("");
}

function initTimeSelect(){
    $("#Time_Stamp_m").datetimepicker({
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
		"sAjaxSource" : "state/queryControlDeviceList", 
		"bLengthChange":false,//取消显示每页条数
		// 服务器回调函数 
		"fnServerData": function retrieveData(sSource, aoData, fnCallback) 
		{
			aoData.push({ "name": "factory", "value": $("#cronFactory").val()}); 
			aoData.push({ "name": "device_id", "value": $("#device_id").val()});
			aoData.push({ "name": "CommState", "value": $("#cronCommState").val()}); 
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
					showSuccessOrErrorModal("获取参数数据失败","error");
				}
			});
		},
		// 列属性
		"columns" : [{	
			 "title" : "通信状态",  
			 "defaultContent" : "", 
			 "data" :"comm_state",
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
		            var content ="";
		            if(data == 0){
		            	content = "在线";
		            }
					else if(data == 1){
						content = '<font color=red>离线</font>';
					}
					else {
						content = "未知";
					}
		            return content;
		      }   
		 } 
		 ,	 {	
			 "title" : "所属厂家",  
			 "defaultContent" : "", 
			 "data" :"factory",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		 ,	 {	
			 "title" : "装置ID编号",  
			 "defaultContent" : "", 
			 "data" :"id",
			 "width": "10%",
			 "class" : "text-center"  
		 } 
		,	 {	
			 "title" : "装置标识",  
			 "defaultContent" : "", 
			 "data" :"device",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		,{	
			 "title" : "告警阈值",  
			 "defaultContent" : "", 
			 "data" :null,
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
				 var content = "";
				  content = '<button class="btn btn-primary" onclick="readAlarmThreshold(\''+row.id+'\')"> 查询 </button>'+
				 '<button class="btn btn-success" onclick="setAlarmThreshold(\''+row.id+'\')"> 设置 </button>';
		         return content;
		      } 
		 }	
		 ,{	
			 "title" : "采样率",  
			 "defaultContent" : "", 
			 "data" :null,
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
				 var content = "";
				  content = '<button class="btn btn-primary" onclick="readSampleRate(\''+row.id+'\')"> 查询 </button>'+
				 '<button class="btn btn-success" onclick="setSampleRate(\''+row.id+'\')"> 设置 </button>';
		         return content;
		      } 
		 }
		 ,{	
			 "title" : "装置时间",  
			 "defaultContent" : "", 
			 "data" :null,
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
				 var content = "";
				  content = '<button class="btn btn-primary" onclick="readDeviceTime(\''+row.id+'\')"> 查询 </button>'+
				 '<button class="btn btn-success" onclick="setDeviceTime(\''+row.id+'\')"> 设置 </button>';
		         return content;
		      } 
		 }
		 ,{	
			 "title" : "倾角数据",  
			 "defaultContent" : "", 
			 "data" :null,
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
				 var content = "";
				  content = '<button class="btn btn-primary" onclick="readTiltData(\''+row.id+'\')"> 上召 </button>'+
				 '<button class="btn btn-success" onclick="setTiltData(\''+row.id+'\')"> 校准 </button>';
		         return content;
		      } 
		 } 
/* 		 ,{	
			 "title" : "信息类型",  
			 "defaultContent" : "", 
			 "data" :null,
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
				 var content = "";
				  content = '<button class="btn btn-primary" onclick="readInfoType(\''+row.id+'\')"> 查询 </button>'+
				 '<button class="btn btn-success" onclick="setInfoType(\''+row.id+'\')"> 设置 </button>';
		         return content;
		      } 
		 } */		 
		 ]
	});
}

function readAlarmThreshold(recordId){
	showConfirmModal("该操作会查询装置告警阈值，是否确定继续！",function(){
			   $("#device_access_m1").val("");
			   $("#regulatorId").val(recordId);
			   $('#accessDeviceModal').modal('show');
	});
}

function readSampleRate(recordId){
	showConfirmModal("该操作会查询装置采样率，是否确定继续！",function(){
			   $("#device_access_mForSampleRate").val("");
			   $("#regulatorIdForSampleRate").val(recordId);
			   $('#accessDeviceModalForSampleRate').modal('show');
	});
}

function readDeviceTime(recordId){
	showConfirmModal("该操作会查询装置时间，是否确定继续！",function(){
			   $("#device_access_mForDeviceTime").val("");
			   $("#regulatorIdForDeviceTime").val(recordId);
			   $('#accessDeviceModalForDeviceTime').modal('show');
	});
}

function readInfoType(recordId){
	showConfirmModal("该操作会查询装置上送信息类型，是否确定继续！",function(){
			   $("#device_access_mForInfoType").val("");
			   $("#regulatorIdForInfoType").val(recordId);
			   $('#accessDeviceModalForInfoType').modal('show');
	});
}

function readTiltData(recordId){
	showConfirmModal("该操作会查询杆塔倾角数据，是否确定继续！",function(){
			   $("#device_access_mForTiltData").val("");
			   $("#regulatorIdForTiltData").val(recordId);
			   $('#accessDeviceModalForTiltData').modal('show');
	});
}

function setAlarmThreshold(recordId){
	showConfirmModal("该操作会设置装置告警阈值，是否确定继续！",function(){
	startPageLoading();
	var data = {"recordId":recordId, "userId":userId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		url:"state/getAlarmThresholdById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
				  var Threshold = data.ThresholdData;
                  $("#recordIdofExtrabody1").val(recordId);
				  $("#IncUp_m").attr("disabled", false);
				  $("#IncLw_m").attr("disabled", false);
				  $("#IncXUp_m").attr("disabled", false);
				  $("#IncXLw_m").attr("disabled", false);
				  $("#IncYUp_m").attr("disabled", false);
				  $("#IncYLw_m").attr("disabled", false);
				  $("#AngXUp_m").attr("disabled", false);
				  $("#AngXLw_m").attr("disabled", false);
				  $("#AngYUp_m").attr("disabled", false);
				  $("#AngYLw_m").attr("disabled", false);
				  
				  $("#IncUp_m").val(Threshold.IncUp);
				  $("#IncLw_m").val(Threshold.IncLw);
				  $("#IncXUp_m").val(Threshold.IncXUp);
				  $("#IncXLw_m").val(Threshold.IncXLw);
				  $("#IncYUp_m").val(Threshold.IncYUp);
				  $("#IncYLw_m").val(Threshold.IncYLw);
				  $("#AngXUp_m").val(Threshold.AngXUp);
				  $("#AngXLw_m").val(Threshold.AngXLw);
				  $("#AngYUp_m").val(Threshold.AngYUp);
				  $("#AngYLw_m").val(Threshold.AngYLw);

				  $("#alarmthresholdFooter").attr("style","display:;");
				  $("#extraheader1").attr("style","display:;");
				  $("#extrabody1").attr("style","display:;");	
				  $("#extrabody1access_m").val("");
				  $('#alarmthresholdModal').modal('show');			 
			      stopPageLoading()
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		    showSuccessOrErrorModal("请求出错了1","error"); 
		}
	});
	});
}

function setSampleRate(recordId){
	showConfirmModal("该操作会设置装置采样率，是否确定继续！",function(){
	startPageLoading();
	var data = {"recordId":recordId, "userId":userId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		//url:"state/getAlarmThresholdById",
		url:"state/getSampleRateById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {  
				  var SampleRate = data.SampleRateData;
				  $("#SampleRateId").val(recordId);
				  $("#Main_Time_m").attr("disabled", false);
				  $("#Sample_Count_m").attr("disabled", false);
				  $("#Sample_Frequency_m").attr("disabled", false);
				  $("#Heartbeat_Time_m").attr("disabled", false);
				  
				  $("#Main_Time_m").val(SampleRate.Main_Time);
				  $("#Sample_Count_m").val(SampleRate.Sample_Count);
				  $("#Sample_Frequency_m").val(SampleRate.Sample_Frequency);
				  $("#Heartbeat_Time_m").val(SampleRate.Heartbeat_Time);
				  console.log(data.SampleRateData)
				  $("#extrabodyforsampleFooter").attr("style","display:;");
				  $("#extraheaderforsamplerate").attr("style","display:;");
				  $("#extrabodyforsamplerate").attr("style","display:;");		
                  $("#forsamplerateaccess_m").val("");				  
				  $('#SampleRateModal').modal('show');
				  
			      stopPageLoading()
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		    showSuccessOrErrorModal("请求出错了1","error"); 
		}
	});
	});
}

function setDeviceTime(recordId){
	showConfirmModal("该操作会设置装置时间，是否确定继续！",function(){
	startPageLoading();
	var data = {"recordId":recordId, "userId":userId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		//url:"state/getAlarmThresholdById",
		url:"state/getDeviceTimeById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {  
				  var DeviceTime = data.Time_Stamp;
				  $("#recordIdforDeviceTime").val(recordId);
				  $("#Time_Stamp_m").attr("disabled", false);
				  var d = new Date();
				  var month = d.getMonth()+1;
				  if (13==month)
				  {
					  month = 1;
				  }
				var day = d.getDate();
				var year = d.getFullYear();
				var hour =d.getHours();
				var minute=d.getMinutes();          
				var second=d.getSeconds(); 
				var TimeStamp = year+'-'+month+'-'+day+' '+hour+':'+minute+':'+second;

				  $("#Time_Stamp_m").val(TimeStamp);
				  $("#forDeviceTimeaccess_m").val("");
				  console.log(DeviceTime)
				  $("#extrafooterforDeviceTime").attr("style","display:;");
				  $("#extraheaderforDeviceTime").attr("style","display:;");
				  $("#extrabodyforDeviceTime").attr("style","display:;");		
                  $("#device_access_mForDeviceTime").val("");				  
				  $('#DeviceTimeModal').modal('show');
				  
			      stopPageLoading()
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		    showSuccessOrErrorModal("请求出错了1","error"); 
		}
	});
	});
}

//setInfoType
function setInfoType(recordId){
	
	showConfirmModal("该操作会设置装置上送信息类型，是否确定继续！",function(){
	startPageLoading();
	var data = {"recordId":recordId, "userId":userId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		//url:"state/getAlarmThresholdById",
		url:"state/getInfoTypeById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {  
				  var InfoType = data.InfoType;
				  $("#InfoTypeId").val(recordId);
				  $("#cronInfoType").attr("disabled", false);
				var s2 = document.getElementById("cronInfoType");
				   
				  var ops2 = s2.options;
				  for(var i=0;i<ops2.length; i++){
				    var tempValue2 = ops2[i].value;
				    if(tempValue2 == InfoType) //这里是你要选的值
				    {
				       ops2[i].selected = true;
				       break;
				  }
				  }
				  $("#extrafooterforInfoType").attr("style","display:;");
				  $("#extraheaderforInfoType").attr("style","display:;");
				  $("#extrabodyforInfoType").attr("style","display:;");		
                  $("#forInfoTypeaccess_m").val("");				  
				  $('#InfoTypeModal').modal('show');
				  
			      stopPageLoading()
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		    showSuccessOrErrorModal("请求出错了1","error"); 
		}
	});
	});
	
}

//setTiltData
function setTiltData(recordId){
	
	showConfirmModal("该操作会校准装置倾角数据，是否确定继续！",function(){
			   $("#device_access_mForResetTiltData").val("");
			   $("#regulatorIdForResetTiltData").val(recordId);
			   $('#accessDeviceModalForResetTiltData').modal('show');
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

function queryLog() {//条件查询同步日志
	regulatorTable.ajax.reload();  
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

function closeModal(){
	$("#device_access_m").val("");
}

function closeModal1(){
	$("#device_access_m1").val("");
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
	initFactory();
	initRegulatorTable();
	initTimeSelect();
	
	//告警阈值
	$("#accessDeviceForm").html5Validate(function() {
	   $("#accessDeviceModal").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorId").val();
	   var devicePassword = $("#device_access_m1").val();
	   var data = {"recordId":recordId, "userId":userId, "devicePassword":devicePassword};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		url:"state/readAlarmThreshold",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
			   stopPageLoading()
			   if (data.result=="0")
			   {
				  $("#IncUp_m").attr("disabled", true);
				  $("#IncLw_m").attr("disabled", true);
				  $("#IncXUp_m").attr("disabled", true);
				  $("#IncXLw_m").attr("disabled", true);
				  $("#IncYUp_m").attr("disabled", true);
				  $("#IncYLw_m").attr("disabled", true);
				  $("#AngXUp_m").attr("disabled", true);
				  $("#AngXLw_m").attr("disabled", true);
				  $("#AngYUp_m").attr("disabled", true);
				  $("#AngYLw_m").attr("disabled", true);
				  
				  var Threshold = data.ThresholdData;
				  $("#IncUp_m").val(Threshold.IncUp);
				  $("#IncLw_m").val(Threshold.IncLw);
				  $("#IncXUp_m").val(Threshold.IncXUp);
				  $("#IncXLw_m").val(Threshold.IncXLw);
				  $("#IncYUp_m").val(Threshold.IncYUp);
				  $("#IncYLw_m").val(Threshold.IncYLw);
				  $("#AngXUp_m").val(Threshold.AngXUp);
				  $("#AngXLw_m").val(Threshold.AngXLw);
				  $("#AngYUp_m").val(Threshold.AngYUp);
				  $("#AngYLw_m").val(Threshold.AngYLw);
				  console.log(data.ThresholdData)
				  $("#alarmthresholdFooter").attr("style","display:none;");
				  $("#extraheader1").attr("style","display:none;");
				  $("#extrabody1").attr("style","display:none;");				  
				  $('#alarmthresholdModal').modal('show');
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("查询告警阈值失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("查询告警阈值超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("查询告警阈值重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("查询告警阈值状态未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		   showSuccessOrErrorModal("查询告警阈值请求出错了","error"); 
		}
	});
	});
	
	$("#alarmthresholdForm").html5Validate(function() {
	   $("#alarmthresholdModal").modal("hide");
	   startPageLoading()
	   var data = $("#alarmthresholdForm").serialize();
	   data+="&userId="+userId;
	   console.log(data);
	   		$.ajax({
			//url:"state/setParamById",
			url:"state/setAlarmthresholdById",
			type:"post",
			data:data,
			dataType:"json",
			success:function(data) {
			  if(data.status=="success") {
			   stopPageLoading()	  
			   if (data.result=="0")
			   {
				  showSuccessOrErrorModal("设置装置告警阈值成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("设置装置告警阈值失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("设置装置告警阈值超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("设置装置告警阈值重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("设置装置告警阈值状态未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
			    	
			    } else {
					stopPageLoading()
			        showSuccessOrErrorModal(data.msg,"error");	
			    }         
			},
			error:function(e) {
				stopPageLoading()
			    showSuccessOrErrorModal("设置装置告警阈值请求出错了","error"); 
			}
		});
	});
	
	//采样率
	$("#accessDeviceFormForSampleRate").html5Validate(function() {
	   $("#accessDeviceModalForSampleRate").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorIdForSampleRate").val();
	   var devicePassword = $("#device_access_mForSampleRate").val();
	   var data = {"recordId":recordId, "userId":userId, "devicePassword":devicePassword};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		//url:"state/readAlarmThreshold",
		url:"state/readSampleRate",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
			   stopPageLoading()
			   if (data.result=="0")
			   {
				  $("#Main_Time_m").attr("disabled", true);
				  $("#Sample_Count_m").attr("disabled", true);
				  $("#Sample_Frequency_m").attr("disabled", true);
				  $("#Heartbeat_Time_m").attr("disabled", true);
				  
				  var SampleRate = data.SampleRateData;
				  $("#Main_Time_m").val(SampleRate.Main_Time);
				  $("#Sample_Count_m").val(SampleRate.Sample_Count);
				  $("#Sample_Frequency_m").val(SampleRate.Sample_Frequency);
				  $("#Heartbeat_Time_m").val(SampleRate.Heartbeat_Time);
				  console.log(data.SampleRateData)
				  $("#extrabodyforsampleFooter").attr("style","display:none;");
				  $("#extraheaderforsamplerate").attr("style","display:none;");
				  $("#extrabodyforsamplerate").attr("style","display:none;");				  
				  $('#SampleRateModal').modal('show');
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("查询采样率失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("查询采样率超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("查询采样率重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("查询采样率状态未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		   showSuccessOrErrorModal("查询采样率请求出错了","error"); 
		}
	});
	});
	
	$("#SampleRateForm").html5Validate(function() {
	   $("#SampleRateModal").modal("hide");
	   startPageLoading()
	   var data = $("#SampleRateForm").serialize();
	   data+="&userId="+userId;
	   console.log(data);
	   		$.ajax({
			url:"state/setSampleRateById",
			type:"post",
			data:data,
			dataType:"json",
			success:function(data) {
			  if(data.status=="success") {
			   stopPageLoading()	  
			   if (data.result=="0")
			   {
				  showSuccessOrErrorModal("设置装置采样率成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("设置装置采样率失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("设置装置采样率超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("设置装置采样率重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("设置装置采样率状态未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
			    	
			    } else {
					stopPageLoading()
			        showSuccessOrErrorModal(data.msg,"error");	
			    }         
			},
			error:function(e) {
				stopPageLoading()
			    showSuccessOrErrorModal("设置装置采样率请求出错了","error"); 
			}
		});
	});
	
	
	//装置时间DeviceTime
	$("#accessDeviceFormForDeviceTime").html5Validate(function() {
	   $("#accessDeviceModalForDeviceTime").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorIdForDeviceTime").val();
	   var devicePassword = $("#device_access_mForDeviceTime").val();
	   var data = {"recordId":recordId, "userId":userId, "devicePassword":devicePassword};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		url:"state/readDeviceTime",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
			   stopPageLoading()
			   if (data.result=="0")
			   {
				  $("#Time_Stamp_m").attr("disabled", true);
				  
				  var DeviceTime = data.Time_Stamp;
				  $("#Time_Stamp_m").val(DeviceTime);
				  console.log(DeviceTime)
				  $("#extrafooterforDeviceTime").attr("style","display:none;");
				  $("#extraheaderforDeviceTime").attr("style","display:none;");
				  $("#extrabodyforDeviceTime").attr("style","display:none;");				  
				  $('#DeviceTimeModal').modal('show');
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("查询装置时间失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("查询装置时间超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("查询装置时间重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("查询装置时间未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		   showSuccessOrErrorModal("查询装置时间请求出错了","error"); 
		}
	});
	});
	
	$("#DeviceTimeForm").html5Validate(function() {
	   $("#DeviceTimeModal").modal("hide");
	   startPageLoading()
	   var data = $("#DeviceTimeForm").serialize();
	   data+="&userId="+userId;
	   //data+="&userId="+$("#regulatorId").val();;
	   console.log(data);
	   		$.ajax({
			//url:"state/setSampleRateById",
			url:"state/setDeviceTimeById",
			type:"post",
			data:data,
			dataType:"json",
			success:function(data) {
			  if(data.status=="success") {
			   stopPageLoading()	  
			   if (data.result=="0")
			   {
				  showSuccessOrErrorModal("设置装置时间成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("设置装置时间失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("设置装置时间超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("设置装置时间重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("设置装置时间状态未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
			    	
			    } else {
					stopPageLoading()
			        showSuccessOrErrorModal(data.msg,"error");	
			    }         
			},
			error:function(e) {
				stopPageLoading()
			    showSuccessOrErrorModal("设置装置时间请求出错了","error"); 
			}
		});
	});
	
	//信息类型InfoType
	$("#accessDeviceFormForInfoType").html5Validate(function() {
	   $("#accessDeviceModalForInfoType").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorIdForInfoType").val();
	   var devicePassword = $("#device_access_mForInfoType").val();
	   var data = {"recordId":recordId, "userId":userId, "devicePassword":devicePassword};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		//url:"state/readDeviceTime",
		url:"state/readInfoType",
		//InfoType
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
			   stopPageLoading()
			   if (data.result=="0")
			   {
				  $("#cronInfoType").attr("disabled", true);
				  
				  var InfoType = data.InfoType;
				  var s2 = document.getElementById("cronInfoType");
				   
				  var ops2 = s2.options;
				  for(var i=0;i<ops2.length; i++){
				    var tempValue2 = ops2[i].value;
				    if(tempValue2 == InfoType) //这里是你要选的值
				    {
				       ops2[i].selected = true;
				       break;
				    }
				} 
				  console.log(InfoType)
				  $("#extrafooterforInfoType").attr("style","display:none;");
				  $("#extraheaderforInfoType").attr("style","display:none;");
				  $("#extrabodyforInfoType").attr("style","display:none;");				  
				  $('#InfoTypeModal').modal('show');
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("查询装置信息类型失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("查询装置信息类型超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("查询装置信息类型重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("查询装置信息类型未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		   showSuccessOrErrorModal("查询装置信息类型请求出错了","error"); 
		}
	});
	});
	
	$("#InfoTypeForm").html5Validate(function() {
	   $("#InfoTypeModal").modal("hide");
	   startPageLoading()
	   var data = $("#InfoTypeForm").serialize();
	     data+="&InfoType="+$("#cronInfoType").val();
	     data+="&userId="+userId;
	   console.log(data);
	   		$.ajax({
			//url:"state/setSampleRateById",
			url:"state/setInfoTypeById",
			type:"post",
			data:data,
			dataType:"json",
			success:function(data) {
			  if(data.status=="success") {
			   stopPageLoading()	  
			   if (data.result=="0")
			   {
				  showSuccessOrErrorModal("设置装置信息类型成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("设置装置信息类型失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("设置装置信息类型超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("设置装置信息类型重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("设置装置信息类型状态未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
			    	
			    } else {
					stopPageLoading()
			        showSuccessOrErrorModal(data.msg,"error");	
			    }         
			},
			error:function(e) {
				stopPageLoading()
			    showSuccessOrErrorModal("设置装置信息类型请求出错了","error"); 
			}
		});
	});
	
	//倾角数据TiltData
	$("#accessDeviceFormForTiltData").html5Validate(function() {
	   $("#accessDeviceModalForTiltData").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorIdForTiltData").val();
	   var devicePassword = $("#device_access_mForTiltData").val();
	   var data = {"recordId":recordId, "userId":userId, "devicePassword":devicePassword};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		url:"state/readTiltData",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
			   stopPageLoading()
			   if (data.result=="0")
			   {
					$("#inclination_m").attr("disabled", true);
					$("#inclination_x_m").attr("disabled", true);
					$("#inclination_y_m").attr("disabled", true);
					$("#angle_x_m").attr("disabled", true);
					$("#angle_y_m").attr("disabled", true);
					
					var TiltData = data.TiltData;
					var inclination = TiltData.inclination;
					var inclination_x = TiltData.inclination_x;
					var inclination_y = TiltData.inclination_y;
					var angle_x = TiltData.angle_x;
					var angle_y = TiltData.angle_y;
					$("#inclination_m").val(inclination);
					$("#inclination_x_m").val(inclination_x);
					$("#inclination_y_m").val(inclination_y);
					$("#angle_x_m").val(angle_x);
					$("#angle_y_m").val(angle_y);
				
				  $("#extrafooterforTiltData").attr("style","display:none;");
				  $("#extraheaderforTiltData").attr("style","display:none;");
				  $("#extrabodyforTiltData").attr("style","display:none;");				  
				  $('#TiltDataModal').modal('show');
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("查询杆塔倾角数据失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("查询杆塔倾角数据超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("查询杆塔倾角数据重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("查询杆塔倾角数据未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		   showSuccessOrErrorModal("查询杆塔倾角数据请求出错了","error"); 
		}
	});
	});
	
	//复位倾角数据
	$("#accessDeviceModalForResetTiltData").html5Validate(function() {
	   $("#accessDeviceModalForResetTiltData").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorIdForResetTiltData").val();
	   var devicePassword = $("#device_access_mForResetTiltData").val();
	   var data = {"recordId":recordId, "userId":userId, "devicePassword":devicePassword};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		//url:"state/resetDeviceById",   
		url:"state/resetTiltDataById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
			   stopPageLoading()
			   if (data.result=="0")
			   {
				  //regulatorTable.ajax.reload();
				  showSuccessOrErrorModal("复位倾角数据成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("复位倾角数据失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("复位倾角数据超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("复位倾角数据重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("复位倾角数据状态未知","error");
			   }
			   else if (data.result=="-2")
			   {
				  showSuccessOrErrorModal("访问设备密码错误","error");
			   }
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal(data.msg,"error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		   showSuccessOrErrorModal("复位倾角数据请求出错了","error"); 
		}
	});
	});
	
});


