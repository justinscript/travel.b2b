//接送地选择
$(function () {
	$(".trigger_isjs").powerFloat({
		eventType: "click",
		target: $("#targetBox"),
		showCall: function () {
			//var id = $(this).attr("id");
			//var src = $(this).attr("src");
			$('#targetBox a').unbind("click");
			$("#targetBox a").click(function () {
				var Jid = $(this).attr("alt"); var Jname = $(this).html(); var js_input = $("#L_JsRud").val();
				if ($(this).attr("class") == "hover") {
					$(".zd_selected").find("a[alt=" + Jid + "]").remove();
					var js_input2 = js_input.replace("," + Jid, "");
					$("#L_JsRud").val(js_input2);
					$(this).attr("class", "");
				} else {
					$(".zd_selected").append("<a alt=" + Jid + ">" + Jname + "</a>");
					$("#L_JsRud").val(js_input + "," + Jid);
					$(this).attr("class", "hover");
				}
				if ($("#L_JsRud").val() != "") {
					$("#EditR").show();
				} else {
					$("#EditR").hide();
				}
			});
		}
	});
	//接送地开关
	$("#js_switch span").click(function () {
		var id = $(this).attr("id");
		if (id == "js_on") {
			$("#L_Isjs").val("1");
			$("#js_switch").attr("class", "on");
			$(this).html("<input type=\"hidden\" value=\"\" name=\"L_JsRud\" id=\"L_JsRud\" datatype=\"*\" nullmsg=\"请选择接送地\" />");
		} else {
			$("#L_Isjs").val("0");
			$("#js_switch").attr("class", "off");
			$("#targetBox a").removeClass("hover");
			$(".zd_selected").html("");
			$("#EditR").hide();
			$("#L_JsRud").remove();
		}
		if ($("#L_JsRud").length != "") {
			$("#EditR").show();
		} else {
			$("#EditR").hide();
		}
	});
	
	//积分
	$("#integralno").click(function(){
		$(":input[name='lAdultIntegral']").val("");
		$(":input[name='lChildrenIntegral']").val("");                   
	});
	
	
	//设置封面图片
	$("#PicList").click(function(){
		var str='';
		$("#RoteList").find("input[type='hidden']").each(function(){
			var picsplit=$(this).val().split(',');
			if(picsplit.length>-1)
			{
				for(var i=0;i<picsplit.length-1;i++)
				{
					str+='<a href="javascript:void(0)"><img src="'+picsplit[i]+'" class="img-polaroid"  /></a> ';
				}
			}
		})
		if(str!='')
		{
			var Coverimg = $.layer({
                type : 1,
                title : false,
                offset:['150px' , ''],
                area : ['800px','400px'],
                page: {
        			html: '<div class="coverimg">'+str+'</div>'
   				}				
   		 	});
		$(document).on('click','.coverimg a',function(){
			$("#defPic").html('<img src="'+$(this).find("img").attr("src")+'" style="width:100px;height:100px"/>');
			$("#lPhotoCover").val($(this).find("img").attr("src"));
    		layer.close(Coverimg);
		});
		 }else{
			 var loadi2 = layer.load('加载中…');
				$.ajax({
					type: "get",
					url: "resourcelist.htm",
					dataType:"html",
					data:"id=0",
					cache: false,
					success: function (html) {
						$.layer({
							type : 1,
							title : "导入图片",
							offset:['60px' , ''],
							border : [10, 0.3, '#000'],
							area : ['980px','540px'],
							page : {html :html}
						});
					},
					complete: function () {
						layer.close(loadi2);
					},
					error: function (err) {  }
				});
		 }
	});
	
	
});





function JF(obj) {
	var ztime = $("#GoGroupTimeString").val().split(",").length - 1;
	//var zjf = parseInt($("#zjf").html()); //总积分
	var RenCount = parseInt($("#L_RenCount").val());
	if ($("#L_RenCount").val() == "") {
		alert("请先填写人数");
		$("#L_RenCount").focus();
		return false;
	}

	if (isNaN(parseInt($(obj).val()) * RenCount)) {
		$("#djf").html(0);
		$("#djfs").val(0);
	} else {
		$("#djf").html(parseInt(($(obj).val()) * RenCount)*ztime);
		$("#djfs").val(parseInt(($(obj).val()) * RenCount));
		$("#onejf").html(parseInt(($(obj).val()) * RenCount));
	}
	$("#timecount").html(ztime);

}

//是否使用积分
function IsIntegral(obj) {
	if ($(obj).val() == "1") {
		$("#isIntegral").show();
	}
	else {
		$("#isIntegral").hide();
	}
}


//添加行程
function AddXC() {
	var zCount = $("#RoteList table").length; //总数
	var txtCount = parseInt($("#L_DayCount").val());
	if ($("#L_Travelday").val() == "") {
		txtCount = 0;
	}
	if (parseInt($("#L_DayCount").val()) > 0) {
		$("#RoteList").show();
		$("#xcButton").show();
	}
	else {
		$("#RoteList").hide();
		$("#xcButton").hide();
	}
	if (txtCount < zCount) {
		for (var i = 1; i <= zCount - txtCount; i++) {
			$("#RoteList table").eq(zCount - i).remove();
		}
		return false;
	}

	var str = "";
	var Idstr = "";
	var zCounts = txtCount - zCount;

	for (i = 1; i <= zCounts; i++) {
		Idstr = zCount + i;
		str = str + '<table width="100%" cellspacing="0" cellpadding="0" ><tr><td width="80" class="day">第'+Idstr+'天</td><td><input type="text" class="form-control" value="" name="routelist['+(Idstr-1)+'].rCar"  placeholder="交通" style="width:250px;margin-bottom:6px;"><br/><textarea	class="form-control w500" value="" name="routelist['+(Idstr-1)+'].rContent" placeholder="行程"></textarea></td><td width="200" class="c" valign="top"><button type="button" class="btn btn-default btn-sm" onclick="ajaxTemple(this,'+(Idstr-1)+')" title="导入图片" url="resourcelist.htm" rel="980,540">导入图片</button><input type="hidden" value="" class="linephoto" name="routelist['+(Idstr-1)+'].rPicpath"/><div class="xc-photo"><ul class="xc-photo-list"></ul></div></td><td width="200" class="c"><label><input value="早" type="checkbox" name="routelist['+(Idstr-1)+'].rCan"/>早</label><label><input type="checkbox" value="中" name="routelist['+(Idstr-1)+'].rCan"/>中</label><label><input type="checkbox" value="晚" name="routelist['+(Idstr-1)+'].rCan"/>晚</label><br/><textarea name="routelist['+(Idstr-1)+'].rZhu"class="form-control" placeholder="酒店"></textarea></td></tr></table>';
	}
	if(zCount=="0"){
		$("#RoteList").append(str);
	}else
	{
		$("#RoteList table").eq($("#RoteList table").length-1).after(str);
	}


	//  $('.info').autoTextarea();
}
//添加接送区域
function RoadAdd(obj)
{
	var city=$("#c2").val();
	var R_Quyu=$("#R_Quyu").val();
	if(city==""){
		alert("请先选择城市");
		return false;
	}else
	{
		if($("#road_list").find("a:contains("+city+")").length<1){
			$("#R_Quyu").val(R_Quyu+","+city);
			$("#road_list").append("<a href=\"javascript:void\" onclick='RoadDel(this)'>"+city+"</a>");
		}
	}
}

//删除接送地
function RoadDel(obj){
	var city=$(obj).html();
	var R_Quyu=$("#R_Quyu").val();
	$(obj).remove();
	$("#R_Quyu").val(R_Quyu.replace(","+city,""));
}

//取消订单
function ClearOrder(obj,id) {
	var loadi = layer.load('加载中…');
	$.ajax({
		type: "get",
		url: "/account/clearorder.htm?id="+id,
		cache: false,
		success: function (html) {
			$.layer({
				type : 1,
				title :"取消订单",
				offset:['100px' , ''],
				border : [10, 0.3, '#000'],
				area : ['500px','300px'],
				page : {html :html}
			});
		},
		complete: function () {
			layer.close(loadi);
		},
		error: function (err) {  }
	});
}


//导入线路
function InsertTemplate(id)
{
	$.ajax({
		url: "/account/getTemplate.htm",
        type: "post",
        dataType: "json",
        data: "id=" + id,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function (msg) {
        	$("#L_Title").val(msg.data.lTile);
        	$("#L_Type option[value='"+msg.data.lType+"']").attr("selected","selected");
			$("#L_DayCount").val(msg.data.lDay);
			$("#lIco").val(msg.data.lIco);
			$("#lJhd").val(msg.data.lJhd);
			$("#lDipei").val(msg.data.lDipei);
			$("#lGroupTel").val(msg.data.lGroupTel);
			$("#lJhdTime").val(msg.data.lJhdTime);
			$("#lMode").val(msg.data.lMode);
			$("#lNMode").val(msg.data.lNMode);
			$("#lYesItem").val(msg.data.lYesItem);
			$("#lNoItem").val(msg.data.lNoItem);
			$("#lChildren").val(msg.data.lChildren);
			$("#lShop").val(msg.data.lShop);
			$("#lExpenseItem").val(msg.data.lExpenseItem);
			$("#lPreseItem").val(msg.data.lPreseItem);
			$("#lAttention").val(msg.data.lAttention);
			$("#lOther").val(msg.data.lOther);
			parent.GetP('Province', 'City', 'County', ["" + msg.data.lProvince + "", "" + msg.data.lCity + "", ""+msg.data.lArea+""]);
            parent.GetP('Province2', 'City2', 'County2', ["" + msg.data.lArrivalProvince + "", "" + msg.data.lArrivalCity + "", ""+msg.data.lArrivalArea+""]);
			$("#lReminder").val(msg.data.lReminder);
			$("#L_TourContent").val(msg.data.lTourContent);
            var routelist="";
            $.each(msg.data.routelist, function (index, con) {
            	var imglist="";
            	var img=con.rPicpath;
            	var rCan=con.rCan;
            	if(rCan==null){rCan="1";}
            	if(img!=null){
            	var imgs= new Array(); 
            	imgs=img.split(","); 
            	for (var i=0;i<imgs.length-1 ;i++ ) 
            	{ 
            				imglist=imglist+'<li><img width="50" height="50" src="'+imgs[i]+'"></li>';
            		}
            	}
            	routelist = routelist + '<table width="100%" cellspacing="0" cellpadding="0" ><tr><td width="80" class="day">第'+(parseInt(index)+1)+'天</td><td><input type="text" class="form-control" name="routelist['+index+'].rCar" value="'+con.rCar+'" placeholder="交通" style="width:250px;margin-bottom:6px;"><br/><textarea	class="form-control w500" name="routelist['+index+'].rContent" placeholder="行程">'+con.rContent+'</textarea></td><td width="200" class="c" valign="top"><button type="button" class="btn btn-default btn-sm" onclick="ajaxTemple(this,'+index+')" title="导入图片" url="resourcelist.htm" rel="980,500">导入图片</button><input type="hidden" value="'+img+'" class="linephoto" name="routelist['+index+'].rPicpath"/><div class="xc-photo"><ul class="xc-photo-list">'+imglist+'</ul></div></td><td width="200" class="c"><label><input value="早" type="checkbox" name="routelist['+index+'].rCan"  '+GetChecked(rCan.indexOf('早'))+'/> 早</label><label><input type="checkbox" value="中" name="routelist['+index+'].rCan"  '+GetChecked(rCan.indexOf('中'))+'/>中</label><label><input type="checkbox" value="晚" name="routelist['+index+'].rCan" '+GetChecked(rCan.indexOf('晚'))+'/>晚</label><br/><textarea name="routelist['+index+'].rZhu"class="form-control" placeholder="酒店">'+con.rZhu+'</textarea></td></tr></table>';
            });
            $("#RoteList").find("table").show();
            $("#RoteList").html(routelist);
        	}
		});
		parent.layer.closeAll();
	}

//导入交通

///交通选择
function tabCar(s) {
	var n=$(s).attr("id");
	tabCarS($(s).val(),n);
}

function tabCarI(m,n){
	$("#"+n+" option[value='"+m+"']").attr("selected","selected");
	tabCarS(m,n);
}

function tabCarS(m,n){
	switch (m) {
	case "0":
		$("."+n).find(".flight_fieji").show();
		$("."+n).find(".flight_other").hide();
		$("."+n).find(".flight_temple").show();
		break;
	case "1":
		$("."+n).find(".flight_fieji").hide();
		$("."+n).find(".flight_other").show().find("input").val(''); 
		$("."+n).find(".flight_temple").show();
		break;		
	case "2":
		$("."+n).find(".flight_fieji").hide();
		$("."+n).find(".flight_other").show().find("input").val(''); 
		$("."+n).find(".flight_temple").show();
		break;		
	case "3":
		$("."+n).find(".flight_fieji").hide();
		$("."+n).find(".flight_other").show().find("input").val(''); 
		$("."+n).find(".flight_temple").show();
		break;
	case "4":
		$("."+n).find(".flight_fieji").hide();
		$("."+n).find(".flight_fieji").find("input").val(''); 
		$("."+n).find(".flight_other").show().find("input").val('待定');
		$("."+n).find(".flight_temple").hide();
		break;
	}
	var url=$("."+n).find(".TrafficUrl");
	var url_v=url.attr("url");	
	url_v=url_v.substring(0,url_v.length-1);
	url.attr("url",url_v+m);
	$("input[name='"+n+"']").val("");
}
function InsertTraffic(id)
{
	$.ajax({
		url: "/account/getTraffic.htm",
        type: "post",
        dataType: "json",
        data: "id=" + id,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function (msg) {
            var GoKey = msg.data.tTraffic.split(",");
        	 if (msg.data.tCat == 0) {
        	        if (msg.data.tType == 0) {
        	            $("input[name='lGoTraffic']").eq(0).val(GoKey[0]);
        	            $("input[name='lGoTraffic']").eq(1).val(GoKey[1]);
        	            $("input[name='lGoTraffic']").eq(2).val(GoKey[2]);
        	            $("input[name='lGoTraffic']").eq(3).val(GoKey[3]);
        	            $("input[name='lGoTraffic']").eq(4).val(GoKey[4]);
        	            $("input[name='lGoTraffic']").eq(5).val(GoKey[5]);
        	            $("input[name='lGoTraffic']").eq(6).val(GoKey[6]);
        	            $("select[name='lGoTraffic']").find("option:contains('" + GoKey[7] + "')").attr("selected", "selected");
        	        }
        	        else {
        	            $("#lGoTraffico").val(msg.data.tTraffic);
        	        }
        	    }
        	    else {
        	        if (msg.data.tType == 0) {
        	            $("input[name='lBackTraffice']").eq(0).val(GoKey[0]);
        	            $("input[name='lBackTraffice']").eq(1).val(GoKey[1]);
        	            $("input[name='lBackTraffice']").eq(2).val(GoKey[2]);
        	            $("input[name='lBackTraffice']").eq(3).val(GoKey[3]);
        	            $("input[name='lBackTraffice']").eq(4).val(GoKey[4]);
        	            $("input[name='lBackTraffice']").eq(5).val(GoKey[5]);
        	            $("input[name='lBackTraffice']").eq(6).val(GoKey[6]);
        	            $("select[name='lBackTraffice']").find("option:contains('" + GoKey[7] + "')").attr("selected", "selected");
        	        }
        	        else {
        	            $("#lBackTrafficeo").val(msg.data.tTraffic);
        	        }
        	    }
	
        }
	
	});
	parent.layer.closeAll();
       
}
//end

