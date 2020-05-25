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
		"sAjaxSource" : "fault/queryTowerTileStateList", 
		"sScrollY":"370px",
		"bLengthChange":false,//取消显示每页条数
		// 服务器回调函数 
		"fnServerData": function retrieveData(sSource, aoData, fnCallback) 
		{
			aoData.push({ "name": "factory",   "value": $("#cronFactory").val()}); 
			aoData.push({ "name": "line",      "value": $("#cronLine").val()});  
			aoData.push({ "name": "device",    "value": $("#cronDevice").val()}); 
			aoData.push({ "name": "deal",      "value": $("#cronDeal").val()});
			aoData.push({ "name": "StartTime", "value": $("#StartTime").val()}); 
			aoData.push({ "name": "EndTime",   "value": $("#EndTime").val()});
			aoData.push({ "name": "userID",    "value": userId});
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
			 "title" : "状态",  
			 "defaultContent" : "", 
			 "data" :"istilt",
			 "width": "10%",
			 "class" : "text-center",       
			 "render": function(data, type, row, meta) {
		            var content ="";
		            if(data == 0){
		            	content = "正常";
		            }
					else if(data == 1){
						content = '<font color=red>告警</font>';
					}
					else{
						content = "未知";
					}
		            return content;
		      }
		 } 	
				,	{	
			 "title" : "设备编号",  
			 "defaultContent" : "", 
			 "data" :"device",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		,	 {	
			 "title" : "设备名称",  
			 "defaultContent" : "", 
			 "data" :"deviceName",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		,	 {	
			 "title" : "杆塔编号",  
			 "defaultContent" : "", 
			 "data" :"tower",
			 "width": "10%",
			 "class" : "text-center"  
		 }   
		,	 {	
			 "title" : "杆塔名称",  
			 "defaultContent" : "", 
			 "data" :"towerName",
			 "width": "10%",
			 "class" : "text-center"  
		 }  
		,	 {	
			 "title" : "上次告警时间",  
			 "defaultContent" : "", 
			 "data" :"last_alarm_time",
			 "width": "10%",
			 "class" : "text-center", 
			 "render": function(data, type, row, meta) {
                 var content ="";
                 if(data == "000000000000"){
                     content = "";
                         }
                 else
                        {
                    content = data;
                         }
                 return content;
           }     
		 }
		/*
		,	 {	
			 "title" : "观测点名称",  
			 "defaultContent" : "", 
			 "data" :"observe_name",
			 "width": "10%",
			 "class" : "text-center", 
		 }  
		*/
		,	 {	
			 "title" : "告警信息",  
			 "defaultContent" : "", 
			 "data" :"content",
			 "width": "10%",
			 "class" : "text-center",    
		 }		,{	
			 "title" : "监测曲线",  
			 "defaultContent" : "", 
			 "data" :null,
			 "width": "10%",
			 "class" : "text-center",
			 "render": function(data, type, row, meta) {
				 var content = "";
				  content = '<button class="btn btn-xs blue" onclick="showEditModal(\''+row.id+'\') " data-toggle="modal" data-target="userModal_add"> 显示 </button>';
		         return content;
		      } 
		 }]
	});
}

// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

//点击编辑按钮
function showEditModal(recordId){
	var data = {"recordId":recordId};
	var dataObj = {
		"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		//url:"sys/queryWaveData",
		url:"sys/queryTowerTiltParamData",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
			console.log(data)
			if (data.status == "success") {
				var dataList = data.dataList;
				console.log("dataList")
				console.log(dataList)
				option = {
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						data:['倾斜度','顺线倾斜度','横向倾斜度','顺线倾斜角','横向倾斜角']
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					toolbox: {
						feature: {
							saveAsImage: {}
						}
					},
					xAxis: {
						type: 'category',
						boundaryGap: false,
						data: dataList[0]
					},
					yAxis: {
						type: 'value'
					},
					series: [
						{
							name:'倾斜度',
							type:'line',
							//stack: '总量',
							data:dataList[1]
						},
						{
							name:'顺线倾斜度',
							type:'line',
							//stack: '总量',
							data:dataList[2]
						},
						{
							name:'横向倾斜度',
							type:'line',
							//stack: '总量',
							data:dataList[3]
						},
						{
							name:'顺线倾斜角',
							type:'line',
							//stack: '总量',
							data:dataList[4]
						},
						{
							name:'横向倾斜角',
							type:'line',
							//stack: '总量',
							data:dataList[5]
						}
					]
				};
					myChart.setOption(option);
					$('#userModal_add').modal('show');
			}
			else if(data.status == "empty")
			{
				showSuccessOrErrorModal("该装置还没有产生监测数据", "warning");
			}
			else {
				showSuccessOrErrorModal(data.msg, "error");
			}
			
		},
		error:function(e) {
		    showSuccessOrErrorModal("请求出错了12","error"); 
		}
		});
}

function queryWave() {//条件查询同步日志
	regulatorTable.ajax.reload();  
}

function initParent(){
	var userMap = $.parseJSON(decrypt($.cookie('userMap'),"abcd1234abcd1234"));
	var userId = userMap.id;
	var data = {"userId":userId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	console.log(userId)
	$.ajax({
		url:"info/queryLineByUser",
		type:"post",
		data:dataObj,
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
		        $("#cronLine").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("请求出错了12121","error"); 
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

function initDevice(){
	var userMap = $.parseJSON(decrypt($.cookie('userMap'),"abcd1234abcd1234"));
	var userId = userMap.id;
	var data = {"userId":userId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	console.log(userId)
	$.ajax({
		url:"info/queryDeviceByUser",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var regulatorList = data.dataList;
		    	var str = "";
				str+='<option value="">-----所有装置-----</option>';
		        for (var int = 0; int < regulatorList.length; int++) {
					str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				}
		        $("#cronDevice").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("请求装置列表出错了","error"); 
		}
	});	
}

function initTower(){
	var userMap = $.parseJSON(decrypt($.cookie('userMap'),"abcd1234abcd1234"));
	var userId = userMap.id;
	var data = {"userId":userId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	console.log(userId)
	$.ajax({
		url:"info/queryTowerByUser",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var regulatorList = data.dataList;
		    	var str = "";
				str+='<option value="">-----所有杆塔-----</option>';
		        for (var int = 0; int < regulatorList.length; int++) {
					str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				}
		        $("#cronTower").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("请求杆塔列表出错了","error"); 
		}
	});	
}

function queryWave1(){
	//$("#main").attr("style","display:none;");
}

function closeModal(){
	//$("#main").attr("style","display:none;");
	//$("#wave_pwd").val("");
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

$("select#cronLine").change(function(){
	var userMap = $.parseJSON(decrypt($.cookie('userMap'),"abcd1234abcd1234"));
	var userId = userMap.id;
	var lineId = $(this).val();
	var factoryId = $("#cronFactory").val();
	var data = {"userId":userId,"lineId":lineId,"factoryId":factoryId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		url:"info/queryDeviceByMultiCondition",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var regulatorList = data.dataList;
		    	var str = "";
				if (0==regulatorList.length)
				{
					str+='<option value="0">---无装置---</option>';
				}
			    else
				{
					str+='<option value="">---所有装置---</option>';
		            for (var int = 0; int < regulatorList.length; int++) 
					{
					     str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				    }
				}

		        $("#cronDevice").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("请求装置列表出错了","error"); 
		}
	});
});

$("select#cronFactory").change(function(){
	var userMap = $.parseJSON(decrypt($.cookie('userMap'),"abcd1234abcd1234"));
	var userId = userMap.id;
	var lineId = $("#cronLine").val();
	var factoryId = $(this).val();
	var data = {"userId":userId,"lineId":lineId,"factoryId":factoryId};
	var dataObj = {
			"paramObj":encrypt(JSON.stringify(data),"abcd1234abcd1234")
	}
	$.ajax({
		url:"info/queryDeviceByMultiCondition",
		type:"post",
		data:dataObj,
		dataType:"text",
		success:function(data) {
			data = $.parseJSON(decrypt(data,"abcd1234abcd1234"));
		    if(data.status=="success") {
		    	var regulatorList = data.dataList;
		    	var str = "";
				if (0==regulatorList.length)
				{
					str+='<option value="0">---无装置---</option>';
				}
			    else
				{
					str+='<option value="">---所有装置---</option>';
		            for (var int = 0; int < regulatorList.length; int++) 
					{
					     str+= '<option value="'+regulatorList[int].id+'">'+regulatorList[int].name+'</option>';
				    }
				}

		        $("#cronDevice").html(str);
		    } else {
		        showSuccessOrErrorModal(data.msg,"error");	
		    }         
		},
		error:function(e) {
		    showSuccessOrErrorModal("请求装置列表出错了","error"); 
		}
	});
});

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
	initParent();
	initFactory();
	initDevice();
    //initTower();//此处暂时不加杆塔为搜索条件，因为监测装置与杆塔存在一一对应关系
	initRegulatorTable();
	$("#regulatorForm").html5Validate(function() {
	   var data = $("#regulatorForm").serialize();
	   data+="&userId="+userId;
	   console.log(data);
	   		$.ajax({
			url:"fault/queryWavePwd",
			type:"post",
			data:data,
			dataType:"json",
			success:function(data) {
			    if(data.status=="success") {
					$("#main").attr("style","display:;");
			    	//showSuccessOrErrorModal(data.msg,"success"); 
			    	//regulatorTable.draw();
			    	//$("#regulatorModal_add").modal("hide");
			    } else {
			        showSuccessOrErrorModal(data.msg,"error");	
			    }         
			},
			error:function(e) {
			    showSuccessOrErrorModal("请求出错了3","error"); 
			}
		});
	});
});


