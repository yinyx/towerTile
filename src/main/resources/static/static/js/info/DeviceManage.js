var regulatorTable = null;
var userMap = {};
var userId = 0;
function initRegulatorTable() {
	regulatorTable = $('#mainTable').DataTable({
		// url
		"sAjaxSource" : "info/queryDeviceList", 
		"bLengthChange":false,//取消显示每页条数
		// 服务器回调函数 
		"fnServerData": function retrieveData(sSource, aoData, fnCallback) 
		{
			aoData.push({ "name": "QueryType", "value": $("#cronID").val()}); 
			aoData.push({ "name": "QueryType1", "value": $("#cronFactory1").val()}); 
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
					showSuccessOrErrorModal("获取装置信息失败","error");
				}
			});
		},
		// 列属性
		"columns" : [	 {	
			 "title" : "所属厂家",  
			 "defaultContent" : "", 
			 "data" :"factoryName",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		,	 {	
			 "title" : "所属线路",  
			 "defaultContent" : "", 
			 "data" :"lineName",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		,	 {	
			 "title" : "安装序号",  
			 "defaultContent" : "", 
			 "data" :"indexno",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		,	 {	
			 "title" : "装置标识",  
			 "defaultContent" : "", 
			 "data" :"name",
			 "width": "10%",
			 "class" : "text-center"  
		 }  
		,	 {	
			 "title" : "杆塔名称",  
			 "defaultContent" : "", 
			 "data" :"towerName",
			 "width": "10%",
			 "class" : "text-center",    
		 } 	
		,	 {	
			 "title" : "相别",  
			 "defaultContent" : "", 
			 "data" :"ied_phase",
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
		            var content ="";
		            if(data == 1){
		            	content = "A";
		            }
					else if(data == 2){
						content = "B";
					}
					else if(data == 3){
						content = "C";
					}
					else if(data == 4){
						content = "AB";
					}
					else if(data == 5){
						content = "BC";
					}
					else if(data == 6){
						content = "AC";
					}
					else{
						content = "异常";
					}
		            return content;
		      }    
		 } 

		,	 {	
			 "title" : "协议类型",  
			 "defaultContent" : "", 
			 "data" :"protocol_version",
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
		            var content ="2019版";
		            if(data == 1){
		            	content = "2018版";
		            }
		            return content;
		      }   
		 }   
		,{	
			 "title" : "操作",  
			 "defaultContent" : "", 
			 "data" :null,
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
				 var content = "";
				  content = '<button class="btn btn-xs blue" onclick="showEditModal(\''+row.id+'\') " data-toggle="modal" data-target="#"> 编辑 </button>' +
                 '<button class="btn btn-xs red" onclick="deleteSchoolUser(\''+row.id+'\')"> 删除 </button>';
		         return content;
		      } 
		 }]
	});
}

//点击编辑按钮
function showEditModal(recordId){
	startPageLoading();
	var data = {"recordId":recordId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		url:"info/getDeviceById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
		   data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		   if(data.status=="success") {
               var usersData = data.usersData;
               console.log(usersData)
			   $("#device_m").attr("disabled", true);
			   $("#device_m").val(usersData.device);
			   $("#IP_m").val(usersData.remark);
               $("#recordId").val(recordId);
			   $("#name_m").val(usersData.name);
				var s2 = document.getElementById("cronFactory");
				var ops2 = s2.options;
				for(var i=0;i<ops2.length; i++){
				    var tempValue2 = ops2[i].value;
				    if(tempValue2 == usersData.manufacture) //这里是你要选的值
				    {
				       ops2[i].selected = true;
				       break;
				    }
				} 
				var s = document.getElementById("cronLine");
				var ops = s.options;
				for(var i=0;i<ops.length; i++){
				    var tempValue = ops[i].value;
				    if(tempValue == usersData.line_name) //这里是你要选的值
				    {
				       ops[i].selected = true;
				       break;
				    }
				} 
				initTower();
				var s1 = document.getElementById("cronTower");
				var ops1 = s1.options;
				for(var i=0;i<ops1.length; i++){
				    var tempValue1 = ops1[i].value;
				    if(tempValue1 == usersData.tower_id) //这里是你要选的值
				    {
				       ops1[i].selected = true;
				       break;
				    }
				}
				var s3 = document.getElementById("cronProtocalType");
				var ops3 = s3.options;
				for(var i=0;i<ops3.length; i++){
				    var tempValue3 = ops3[i].value;
				    if(tempValue3 == usersData.protocol_version) //这里是你要选的值
				    {
				       ops3[i].selected = true;
				       break;
				    }
				} 				
                       $("#InstallIndex_m").val(usersData.indexno);
			   $("#phase_m").val(usersData.ied_phase);	
               $("#IedType_m").val(usersData.ied_type);
			   $("#version_m").val(usersData.version);	
               $("#ManuDate_m").val(usersData.manu_date);
			   $("#InstallTime_m").val(usersData.install_time);	
               $("#longitude_m").val(usersData.longitude);
			   $("#latitude_m").val(usersData.latitude);		
               $("#altitude_m").val(usersData.altitude);

			   $("#zero_drift_comps_small_m").val(usersData.zero_drift_comps_small);
               $('#regulatorModal_add').modal('show');
               stopPageLoading()
		   } else {
			   stopPageLoading()
			   showSuccessOrErrorModal("获取监管单位信息失败","error");
		   }
		   
		},
		error:function(e) {
			stopPageLoading()
		   showSuccessOrErrorModal("请求出错了1","error"); 
		}
	});
}

function queryLog() {//条件查询同步日志
	regulatorTable.ajax.reload();  
}

//新增监管单位按钮
function addRegulator(){
	$("#regulatorForm")[0].reset();
	$("#recordId").val("");
	$("#device_m").attr("disabled", false);
	initParent1();
	initTower();
}

function initParent(){
	$.ajax({
		url:"info/queryLine",
		type:"post",
		data:{},
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var regulatorList = data.dataList;
		    	var str = "";
				str+='<option value="">---所有线路---</option>';
		        for (var int = 0; int < regulatorList.length; int++) {
					str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				}
		        $("#cronID").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("查询线路列表请求出错了","error"); 
		}
	});	
}

function initParent1(){
	$.ajax({
		url:"info/queryLine",
		type:"post",
		data:{},
		dataType:"text",
		async:false,
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var regulatorList = data.dataList;
		    	var str = "";
		        for (var int = 0; int < regulatorList.length; int++) {
					str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				}
		        $("#cronLine").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("初始化线路下拉框请求出错了","error"); 
		}
	});	
}

$("select#cronLine").change(function(){
	initTower();
});

function initTower(){
	var lineId = $("#cronLine").val();
	var data = {"lineId":lineId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		url:"info/queryTowerByLine",
		//url:"info/getDeviceById",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var regulatorList = data.dataList;
		    	var str = "";
		        for (var int = 0; int < regulatorList.length; int++) {
					str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				}
		        $("#cronTower").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("初始化杆塔下拉框请求出错了","error"); 
		}
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
		        for (var int = 0; int < regulatorList.length; int++) {
					str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				}
		        $("#cronFactory").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("初始化厂家下拉框请求出错了","error"); 
		}
	});	
}

function initFactory1(){
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
		        $("#cronFactory1").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("初始化厂家下拉框请求出错了","error"); 
		}
	});	
}

//删除用户
function deleteSchoolUser(userId){
	showConfirmModal("是否确定删除！",function(){
		$.ajax({
			url:"info/deleteDevice",
			type:"post",
			data:{"userId":userId},
			dataType:"text",
			success:function(data) {
				data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
			    if(data.status=="success") {
			        showSuccessOrErrorModal(data.msg,"success"); 
			        regulatorTable.draw(); //刷新表格
			    } else {
			        showSuccessOrErrorModal(data.msg,"error");	
			    }         
			},
			error:function(e) {
				console.error(e)
			    showSuccessOrErrorModal("请求出错了2","error"); 
			}
		});
	});
	
}

function sync()
{
	$.ajax({
		url:"sys/syncDeviceProcess",
		type:"post",
		data:{},
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	showSuccessOrErrorModal(data.msg,"success"); 
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("请求出错了","error"); 
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
	            var showFault = "    未读故障信息："+noReadFaultNum+"条";
	            showFault = '<font color=green size=4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+showFault+'</font>';
	            var showAlarm = "    未读告警信息："+noReadAlarmNum+"条";
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
	initParent();
	initParent1();
	//initTower();
	initFactory();
	initFactory1();
	initRegulatorTable();
	$("#regulatorForm").html5Validate(function() {
	   var data = $("#regulatorForm").serialize();
	   data+="&line="+$("#cronLine").val();
	   data+="&tower="+$("#cronTower").val();
	   data+="&factory="+$("#cronFactory").val();
	   data+="&ProtocalType="+$("#cronProtocalType").val();
	   console.log(data);
	   		$.ajax({
			url:"info/saveDevice",
			type:"post",
			data:data,
			dataType:"json",
			success:function(data) {
			    if(data.status=="success") {
			    	showSuccessOrErrorModal(data.msg,"success"); 
			    	regulatorTable.draw();
			    	$("#regulatorModal_add").modal("hide");
			    } else {
			        showSuccessOrErrorModal(data.msg,"error");	
			    }         
			},
			error:function(e) {
			    showSuccessOrErrorModal("请求出错了3","error"); 
			}
		});
	}, {
		validate : function() {
			var self = $("#InstallIndex_m").val();
			if (isNaN(self)||(self.indexOf('.')!=-1)||(parseInt(self)<=0)) {
				$("#InstallIndex_m").testRemind("此处应填写正整数!");
				return false;
			} 
			return true;
		}
	});
	
	$("#InstallIndex_m").on('change blur',function(e){
				var self = this;
				if(isNaN(self.value)||(self.value.indexOf('.')!=-1)||(parseInt(self.value)<=0))
	            {
					     $(self).testRemind("此处应填写正整数!"); 
		 		         $(self).focus();
				}
	});
	
	$("#editWavePwd").html5Validate(function() {
		var password_old = $("#password_wave").val();
		var password_new = $("#password_wave_new").val();
		var data = {
				"id":userMap.id,
				"password_old":password_old,
				"password" : password_new
			};
		var dataObj = {
				"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
		}
		$.ajax({
			url : "sys/saveDevicePassword",
			type : "post",
			data :dataObj,
			dataType : "text",
			success : function(data) {
				data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
				if (data.status == "success") {
					showSuccessOrErrorModal(data.msg, "success");
					$("#editWavePwd")[0].reset();
					$("#waveModal").modal("hide");
				} else {
					showSuccessOrErrorModal(data.msg, "error");
				}
			},
			error : function(e) {
				showSuccessOrErrorModal("保存密码请求出错了", "error");
			}
		})
	}, {
		validate : function() {
			if ($("#password_wave_new").val() != $("#password_wave_new_c").val()) {
				$("#password_wave_new_c").testRemind("您2次输入的密码不相同!");
				return false;
			}
			return true;
		}
	});
	
});


