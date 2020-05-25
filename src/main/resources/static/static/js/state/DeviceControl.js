var regulatorTable = null;
var userMap = {};
var userId = 0;
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
			 "title" : "操作",  
			 "defaultContent" : "", 
			 "data" :null,
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
				 var content = "";
				 content = '<button class="btn btn-xs blue" onclick="resetDevice(\''+row.id+'\')"> 复位 </button>';
				  //content = '<button class="btn btn-xs blue" onclick="timingDevice(\''+row.id+'\')"> 校时 </button>'+
                 //'<button class="btn btn-xs yellow" onclick="updateDevice(\''+row.id+'\')"> 升级 </button>'+
				 //'<button class="btn btn-xs purple" onclick="resetDevice(\''+row.id+'\')"> 复位 </button>';
		         return content;
		      } 
		 }	
		 ]
	});
}

//点击编辑按钮
function moreParam(recordId){
	startPageLoading();
	var data = {"recordId":recordId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		url:"state/getParamById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
               var usersData = data.usersData;
			   var count = data.paracount;
			   var paraname = data.paraNameList;
			   var str = "";
               for(var key in usersData){    
                 str+='<div class="col-sm-12"><div class="col-sm-4"style="text-align:right;"><label class="control-label" for="userName">'+key+'</label></div><div class="col-sm-8"><input type="text" id="userName_m" name="userName" class="form-control " value="'+usersData[key]+'" /></div></div>';
               }  

               $("#current").html(str);
			   $('#waveModal').modal('show');
			   stopPageLoading()
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal("获取私有参数信息失败","error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		   showSuccessOrErrorModal("请求出错了1","error"); 
		}
	});
}

function timingDevice(recordId){
	showConfirmModal("该操作会校时装置，是否确定继续！",function(){
			   $("#device_access_m1").val("");
			   $("#regulatorId").val(recordId);
			   $('#accessDeviceModal').modal('show');
	});
}

function resetDevice(recordId){
	showConfirmModal("该操作会复位装置，是否确定继续！",function(){
			   $("#device_access_m2").val("");
			   $("#regulatorId").val(recordId);
			   $('#accessDeviceForResetModal').modal('show');
	});
}

function updateDevice(recordId){
	showConfirmModal("该操作会升级装置，是否确定继续！",function(){
			   $("#device_access_m3").val("");
			   $("#regulatorId").val(recordId);
			   $('#accessDeviceForUpdateModal').modal('show');
	});
}

function alertAuthority()
{
	   showSuccessOrErrorModal("复位装置失败111","error");
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
	$("#setParamForm").html5Validate(function() {
	   $("#setParamModal").modal("hide");
	   //$("#device_access_m").val("");
	   startPageLoading()
	   var data = $("#setParamForm").serialize();
	   data+="&userId="+userId;
	   console.log(data);
	   //showSuccessOrErrorModal("设置参数命令已发出，请等待设备响应！","info");
	   		$.ajax({
			url:"state/setParamById",
			type:"post",
			data:data,
			dataType:"json",
			success:function(data) {
			  if(data.status=="success") {
			   stopPageLoading()	  
			   if (data.result=="0")
			   {
				  showSuccessOrErrorModal("设置装置参数信息成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("设置装置参数信息失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("设置装置参数信息超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("设置装置参数信息重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("设置装置参数信息状态未知","error");
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
			    showSuccessOrErrorModal("请求出错了3","error"); 
			}
		});
	});
	
	$("#accessDeviceModal").html5Validate(function() {
	   $("#accessDeviceModal").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorId").val();
	   var devicePassword = $("#device_access_m1").val();
	   var data = {"recordId":recordId, "userId":userId, "devicePassword":devicePassword};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		url:"state/timingDeviceById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
			   stopPageLoading()
			   if (data.result=="0")
			   {
				  regulatorTable.ajax.reload();
				  showSuccessOrErrorModal("校时装置成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("校时装置失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("校时装置超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("校时装置重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("校时装置状态未知","error");
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
		   showSuccessOrErrorModal("校时请求出错了","error"); 
		}
	});
	});
	
	$("#accessDeviceForResetModal").html5Validate(function() {
	   $("#accessDeviceForResetModal").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorId").val();
	   var devicePassword = $("#device_access_m2").val();
	   var resetMode = $("#cronResetType").val();
	   var data = {"recordId":recordId, 
	                   "userId":userId, 
					   "devicePassword":devicePassword,
					   "resetMode":resetMode};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		url:"state/resetDeviceById",
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
				  showSuccessOrErrorModal("复位装置成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("复位装置失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("复位装置超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("复位装置重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("复位装置状态未知","error");
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
		   showSuccessOrErrorModal("请求出错了1","error"); 
		}
	});
	});
	
	$("#accessDeviceForUpdateModal").html5Validate(function() {
	   $("#accessDeviceForUpdateModal").modal("hide");
	   startPageLoading();
	   var recordId = $("#regulatorId").val();
	   var devicePassword = $("#device_access_m3").val();
	   var data = {"recordId":recordId, "userId":userId, "devicePassword":devicePassword};
	   var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	   }
	   $.ajax({
		url:"state/updateDeviceById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
			   stopPageLoading()
			   if (data.result=="0")
			   {
				  regulatorTable.ajax.reload();
				  showSuccessOrErrorModal("升级装置参数信息成功","success");
			   }
			   else if (data.result=="1")
			   {
				  showSuccessOrErrorModal("升级装置参数信息失败","error");
			   }
			   else if (data.result=="2")
			   {
				  showSuccessOrErrorModal("升级装置参数信息超时","error");
			   }
			   else if (data.result=="3")
			   {
				  showSuccessOrErrorModal("升级装置参数信息重复","error");
			   }
			   else if (data.result=="-1")
			   {
				  showSuccessOrErrorModal("升级装置参数信息状态未知","error");
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
		   showSuccessOrErrorModal("升级装置请求出错了","error"); 
		}
	});
	});
});


