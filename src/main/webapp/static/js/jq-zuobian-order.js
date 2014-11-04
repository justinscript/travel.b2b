//2014/7/15:23:24 wangzujun
$(function(){

	//返回顶部
	$("#top_btn").zbscrollTop();
	
	//日期选择
	$(".itemvalue").click(function(event){
		event.stopPropagation();
		$('.itemlist').show();
	});
	$(document).click(function (event) { $('.itemlist').hide(); });

	$(document).on('click','.itemlist li',function(){
		var val=$(this).html();
		$("#lineId").val($(this).attr("rel"));
		$(this).parent().prev().html(val);
		$(this).parent().hide();
	});
	//提交产品
	$("#submitorder").click(function(){
		if($("#lineId").val()==""){
			alert('请选择日期!');return false;
		}else{
			$("#submitor").submit();
		}
	});

	//加减人数
	$(".minus,.plus").click(function() {
		if ($(this).attr("class") == 'minus') {
			var inp = $(this).next();
			var val = parseInt(inp.val());
			if (inp.val() == 0) {
				return false;
			} else {
				inp.val(val - 1);
			}
		}else
		{
			var inp=$(this).prev();
			var val=parseInt(inp.val());
			var xcount=0;
			$(this).parent().parent().find(".number").each(function(){
				xcount=xcount+parseInt($(this).val());
			});
			if(xcount<parseInt($("#syren").val())){
				inp.val(val+1);
			}else
			{
				alert("人数已满；");
			};
		}
		var crcount=parseInt($("#crcount").val())*parseInt($("#crprice").val());
		var xhcount=parseInt($("#xhcount").val())*parseInt($("#xhprice").val());
		var yrcount=parseInt($("#yrcount").val())*parseInt($("#yrprice").val());
		$("#countprice").html(crcount+xhcount+yrcount);
	});

	
	var str=''
	$(".listpic").each(function(){
		
		str+='<li><a href="'+$(this).attr("src")+'" target="_blank"><img src="'+$(this).attr("src")+'" /></a></li>';
		
		
	})
	$("#listPic").html(str);
	
	//点击小图切换大图
	$("#thumbnail li a").click(function(){
		$(".zoompic img").attr({ "src": $(this).attr("href"), "title": $("> img", this).attr("title") });
		$("#thumbnail li.currents").removeClass("currents");
		$(this).parents("li").addClass("currents");
		return false;
	});
	//小图片左右滚动
	var $slider = $('.slider ul');
	var $slider_child_l = $('.slider ul li').length;
	var $slider_width = $('.slider ul li').width();
	$slider.width($slider_child_l * $slider_width);

	var slider_count = 0;

	if ($slider_child_l < 5) {
		$('#btn-right').css({cursor: 'auto'});
		$('#btn-right').removeClass("dasabled");
	}

	$('#btn-right').click(function() {
		if ($slider_child_l < 5 || slider_count >= $slider_child_l - 5) {
			return false;
		}

		slider_count++;
		$slider.animate({left: '-=' + $slider_width + 'px'}, 'fast');
		slider_pic();
	});

	$('#btn-left').click(function() {
		if (slider_count <= 0) {
			return false;
		}
		slider_count--;
		$slider.animate({left: '+=' + $slider_width + 'px'}, 'fast');
		slider_pic();
	});

	function slider_pic() {
		if (slider_count >= $slider_child_l - 5) {
			$('#btn-right').css({cursor: 'auto'});
			$('#btn-right').addClass("dasabled");
		}
		else if (slider_count > 0 && slider_count <= $slider_child_l - 5) {
			$('#btn-left').css({cursor: 'pointer'});
			$('#btn-left').removeClass("dasabled");
			$('#btn-right').css({cursor: 'pointer'});
			$('#btn-right').removeClass("dasabled");
		}
		else if (slider_count <= 0) {
			$('#btn-left').css({cursor: 'auto'});
			$('#btn-left').addClass("dasabled");
		}
	}
});



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

//添加游客
function AddColl(lid) {
	$.ajax({
		url: "/order/queryCount.htm",
		type: "post",
		dataType: "json",
		data: "lId="+lid,
		success: function (msg) {
			if(msg.code==0){
				var addnum=parseInt($(".order_roll").find(".info").length);
				if(msg.data>addnum)
					{
					var td_input = '<tr class="info"><td><input type="hidden" value="0" name="guestVOs.gId" /><input type="text" name="guestVOs.gName" value="" class="w50"/> </td><td><select name="guestVOs.gSex" class="w50"><option value="0">男</option><option value="1">女</option></select></td><td><select name="guestVOs.gType" class="w70"><option value="0">成人</option><option value="1">儿童</option><option value="2">婴儿</option></select></td><td><input type="text" name="guestVOs.gMobile" value="" class="w80"/></td><td><input type="text" name="guestVOs.gCard" value=""class="w120"/></td><td><select name="guestVOs.gDangFang" class="w60"><option value="0">是</option><option value="1">否</option></select></td><td><input type="text" name="guestVOs.gYouHui" value="" class="w50"/></td><td><input type="text" name="guestVOs.gMode" value=""/></td><td><div class="w60" style="line-height: 26px;"><a class="btn btn-mini" onclick="DelCollId(this,0)"><i class="icon-remove"></i> 取消</a></div></td></tr>';
					$(".order_roll").append(td_input);
					}else
						{
						alert("人数已超出！");
						}
			}else {
				{
					alert("读取数据异常，请重新登录再试！");
				}
			}
		}
	});
}

//删除单个名单
function DelCollId(obj, id) {
	if (id == "0") {
		$(obj).parent().parent().parent().remove();
	} else {
		layer.confirm('您确认要删除这个名单,此操作不可恢复?',function(index){
			$.ajax({
				url: "/order/deleteOrder.htm",
				type: "post",
				data:"id="+id,
				dataType: "json",
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success: function (dtRow) {
					if (dtRow.code == "0") {
						$(obj).parent().parent().parent().remove();
						layer.closeAll();
					}
				}
			});
		});

	}
}

//选择游客数量
function AddCount(obj) {
	AddCountTd($(obj).val(),$(obj).attr("id"));
}
//生成游客数量
function AddCounts(num,id) {
	AddCountTd(num,id);
}

//生成游客名单列表
function AddCountTd(SelectCount,id)
{
	var SR = parseInt($("#ShenRen").val()); //剩余人数
	var CR = parseInt($("#CrCount").val()); //成人数
	var XH = parseInt($("#XhCount").val()); //小孩数
	var YR = parseInt($("#YingErCount").val());
	var sCR = parseInt($("#sCrCount").val()); //剩余成人
	var sXH = parseInt($("#sXhCount").val()); //剩余儿童
	var sYr = parseInt($("#sYingErCount").val()); //剩余婴儿
	var Select_Count = parseInt(SelectCount);
	var Ren_Type = '';
	var Ren_Tr = "";
	var Ren_Num = 0;
	var Ren_Count = 0;
	var Ren_TypeId=0;
	
	switch (id) {
		case "CrCount":
			Ren_TypeId=0;
			Ren_Type = '成人';
			Ren_Tr = "success";
			Ren_Num = sCR;
			Ren_Count = Select_Count + XH + YR;
			$("#sCrCount").val(CR);
			break;
		case "XhCount":
			Ren_TypeId=1;
			Ren_Type = '儿童';
			Ren_Tr = "error";
			Ren_Num = sXH;
			Ren_Count = Select_Count + CR + YR;
			$("#sXhCount").val(XH);
			break;
		case "YingErCount":
			Ren_TypeId=2;
			Ren_Type = '婴儿';
			Ren_Tr = "warning";
			Ren_Num = sYr;
			Ren_Count = Select_Count + XH + CR;
			$("#sYingErCount").val(YR);
			break;
	}
	
	if (Ren_Count > SR) {
		alert("温馨提示：\n\n超出总人数");
		if (id == "XhCount") {
			id[0].selectedIndex = sXH;
		} else if (id == "CrCount") {
			id[0].selectedIndex = sCR;
		} else if (id == "YingErCount") {
			id[0].selectedIndex = sYr;
		}
		return false;
	}
	var str = "";

	
	$("#Ren_Count").val(Ren_Count);	
	if (Select_Count > Ren_Num) {
		for (var i = 0; i < parseInt(Select_Count - Ren_Num); i++) {
			str += '<tr class="' + Ren_Tr + '">\
                    <td align="center"><input type="text" t="text" name="guestVOs.gName" value="" style="width: 60px" /></td>\
                    <td align="center"><select name="guestVOs.gSex" class="w100"><option value="0" selected>无</option><option value="0">男</option><option value="1">女</option></select></td>\
                    <td align="center"><input type="hidden" value="'+Ren_TypeId+'" name="guestVOs.gType">'+Ren_Type+'</td>\
                    <td align="center"><input type="text" name="guestVOs.gMobile" value="" style="width: 90px" /></td>\
                    <td align="center"><input type="text" name="guestVOs.gCard" t="text" value="" style="width: 130px" /></td>\
                    <td><select name="guestVOs.gDangFang" class="w60"><option value="0" selected>否</option><option value="1">是</option></select></td>\
                    <td align="center"><input type="hidden" name="guestVOs.gYouHui" value="0" /><input type="text" style="width:98%" name="guestVOs.gMode" value="" /></td>\
                    </tr>';
		}
		
		$("#MingDangTable").append(str);
		
	} else {
		for (var i = Ren_Num; i > Select_Count-1; i--) {
			$("#MingDangTable tr[class='" + Ren_Tr + "']").eq(i).remove();
		}
	}
	
	
	
}



///预留查询公司
function GetCompany() {
	$.ajax({
		url: url,
		type: "post",
		dataType: "text",
		data: "act=getcompanylist&c_name=" + escape($("#CompanyName").val()),
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success: function (msg) {
			msg = eval(msg);
			var str = "";
			for (var i = 0; i < msg.length; i++) {
				str += "<option value=" + msg[i].C_Id + ">" + msg[i].C_Name + "</option>";
			}
			$("#CustomCompany_Id").html(str);
		}
	});
}

function GetMember(obj) {
	$("#Custom_Id").attr("disabled", "disabled");
	$.ajax({
		url: url,
		type: "post",
		dataType: "text",
		data: "act=getmemberlist&c_id=" + escape($(obj).val()),
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		success: function (msg) {
			msg = eval(msg);
			var str = "<option value=\"\">请选择计调</option>";
			for (var i = 0; i < msg.length; i++) {
				str += "<option value=" + msg[i].M_Id + "," + msg[i].M_Name + "," + msg[i].M_Mobile + "," + msg[i].M_Tel + "," + msg[i].M_Fax + ">" + msg[i].M_Name + "(" + msg[i].M_UserName + ")</option>";
			}
			$("#Custom_Id").removeAttr("disabled").html(str);
			$("#Custom_Id").change(function () {
				var Custom = $(this).val();
				var strs = new Array();
				strs = Custom.split(",");
				$("#or_name").val(strs[1]);
				$("#or_mobile").val(strs[2]);
				$("#or_tel").val(strs[3]);
				$("#or_fax").val(strs[4]);
			});
		}
	});
}

//确认订单
function confirmorder(id)
{
	$.layer({
	    shade: [0],
	    area: ['auto','auto'],
	    dialog: {
	        msg: '您是否确定执行此操作？',
	        btns: 2,                    
	        type: 4,
	        btn: ['确认','取消'],
	        yes: function(){
	        	$.ajax({
	        		url: "/account/affirmOrder.htm",
	        		type: "post",
	        		dataType: "json",
	        		data: "id="+id,
	        		contentType: "application/x-www-form-urlencoded; charset=utf-8",
	        		success: function (msg) {
	        			msg = eval(msg);
	        			if(msg.code==0)
	        				{
		        				layer.msg('确认成功', 1, 1);
		        				location.reload();
	        				}else
	        					{
	        					layer.msg('操作失败', 1, 0);	
	        			}
	        		}
	        	});
	            
	        }
	    }
	});
}

