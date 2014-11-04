// JavaScript Document 2014/7/14
$(function(){
	$(".Page_option li[class!='disabled']").click(function(){
    	Page_option($(this));
    	return false;
    });
	//数字选择
	if($(".trigger_num").length>0){
		$(".trigger_num").powerFloat({
			eventType: "click",
			target: "",
			targetMode: "ajax",
			showCall: function () {
				var id = $(this).attr("id");
				$(".target_list a").click(function () {
					$("input[id=" + id + "]").val($(this).html());
					$.powerFloat.hide();
				});
			}
		});
	}

	//选择卡
	$("#myTab li").click(function(){
		$(this).parent().find("li").removeClass("cur");
		$(this).addClass("cur");
		$(".tab-pane li").hide();
		$(".tab-pane li").eq($(this).index()).show();
	});

	//下拉菜单赋值
	$(".btn-group a").click(function(){
		var title=$(this).html();
		var id=$(this).attr("rel");
		var inputname=$(this).parent().parent().attr("rel");
		$(this).parent().parent().parent().find(".dropdown-toggle").find("em").html(title);
		$("#"+inputname).val(id);
	});

	//用户权限
	$(document).on('click','.mRole',function(){
		var n="";
		$("input[name='mRolecheckbox']:checkbox:checked").each(function(i){ 
			if(i==0){
				n=$(this).val();
			}else
			{
				n+=","+$(this).val();
			}
		});
		$("#mRole").val(n);
	});
	
	$(document).on('click', 'input[name=mType]', function(){
		   if($(this).val()==0)
			   {
			   $("#quanxian").hide();$("#mRole").val("");
			   }else{
				   $("#quanxian").show();
			   }
	});
	
	//全选操作
	 $(document).on("click",".check",function(){
		 var num=$("[class=check]:checkbox:checked").length;
         if(num!=0){
             tit_ed(true);
         }else{
             tit_ed(false);
         };
	 });
	
	 $(document).on("click",".checkall",function(){
	     $("[class=checkall]").prop("checked",$(this).prop("checked"));
	     $("[class=check]").prop("checked",$(this).prop("checked"));
	     tit_ed($(this).prop("checked"));
	 });
	 
	 $(document).on('click','.tit_ed button',function(){
		 var state=$(this).val();
		 $.layer({
			    shade: [0],
			    area: ['auto','auto'],
			    dialog: {
			        msg: '您确认操作此项？',
			        btns: 2,                    
			        type: 4,
			        btn: ['确定','取消'],
			        yes: function(){
			        	var id="";
			        	$("[class=check]").each(function(){
			        		if($(this).prop("checked")==true){
			        			id+=","+$(this).val();
			        		}
			        	});
			        	$.ajax({
			        		url:"/account/updateLines.htm",
			        		type:"post",
			        		dataType:"json",
			        		data:"id="+id+"&state="+state,		        		
			        		success:function(msg){
			        			alert(msg.code);
			        		}
			        	});
			        }, no: function(){
			            
			        }
			    }
			}); 
	 });
});

//选择操作按钮
function tit_ed(obj){
    if(obj==true){
        $(".tit_ed div").show();
		 $(".checknum").html("已选中"+$("[class=check]:checkbox:checked").length+"条数据");
    }else{
         $(".tit_ed div").hide();
    }
    $("[class=check]").each(function(){
    	if($(this).prop("checked")==true)
    		{
    			$(this).parent().parent().css("background","#f4f4f4");
    			
    		}else
    			{
    			$(this).parent().parent().css("background","");
    			}
    });
    //alert(nid);
}

//用户已选择的权限
function mRoleShow()
{
	var id=$("#mRole").val();
	
	if(id!="")
		{
			id=","+id+",";

			$(".mRole").each(function(){
				
				if(id.indexOf(","+$(this).val()+",")>-1)
					{
						$(this).attr("checked","true");
					}
			});
		}
}


//页面切换//////////////////////////////////////////////////////////////////////////
function Page_option(obj)
{

	if(obj.attr("class")=="disabled"){return false;}
	var id=obj.index();
	var url=obj.find("a").attr("rel");
	obj.parent().find("li").removeClass("current");
	obj.addClass("current");
	if(id==0 || id==1)
	{
		obj.parent().find(".add").remove();
	}
	
	if(url=="")
	{
		$("#Main>div").hide();
		$("#Main>div").eq(1).html("");
		$("#tab1").show();
		return false;
	}
	
	$("#Main>div").eq(0).hide();
	$("#Main>div").eq(1).show();

	Add(0,url);
	return false;
}

//添加选项
function Page_Add(id,url,title)
{
	$(".Page_option li").removeClass("current");
	$(".Page_option ul").append("<li class=\"current add\"><a href=\"#tabl3\" rel=\"\">"+title+"</a></li>");
	$("#Main>div").eq(0).hide();
	$("#Main>div").eq(1).show();
	Add(id,url);
}

//返回指定选项卡
function Page_back(id)
{
	if(id=="0")
		{
			$(".Page_option").find(".add").remove();
			$("#tab1").show();
			$("#tab2").hide();
			$(".Page_option li").removeClass("current");
			$(".Page_option li").eq(id).addClass("current");
			$("#FilterList").filterSearch();
		}
	}

//获取页面数据
function Add(id, act) {
	var loadi = layer.load('加载中…');
	$.ajax({
	type: "get",
	url: act,
	cache: false,
	data:"id="+id,
	success: function (html) {
		$("#tab2").html(html);
		return false;
	},
	complete: function () {
		layer.close(loadi);
	},
	error: function (err) {  }
	});
}
//页面切换end//////////////////////////////////////////////////////////////////////////

//导入模板
function GetTemple(obj){
	var url=$(obj).attr("url");
	if($(obj).attr("id")=="importline"){
		url = $(obj).attr("url")+"?zIds="+$("#AddSelectColumn").val();
	}
	$.layer({
		type: 2,
		title: $(obj).attr("title"),
		maxmin: true,
		area : ['980px' , '460px'],
		offset : ['70px', ''],
		iframe: {src: ''+url+''}
	});
}

function ajaxTemple(obj,id) {
	var loadi = layer.load('加载中…');
	var wh=$(obj).attr("rel");
	$.ajax({
		type: "get",
		url: $(obj).attr("url"),
		dataType:"html",
		data:"id="+id,
		cache: false,
		success: function (html) {
			$.layer({
				type : 1,
				title : $(obj).attr("title"),
				offset:['60px' , ''],
				border : [10, 0.3, '#000'],
				area : [wh.split(",")[0]+'px',wh.split(",")[1]+'px'],
				page : {html :html+"<input type='hidden' value='"+id+"' id='ajaxId'/>"}
			});
		},
		complete: function () {
			layer.close(loadi);
		},
		error: function (err) {  }
	});
}
//act：删除对象
function DelList(url,id,obj,num)
{
	layer.confirm('您确定要删除此数据？',function(index){
		$.ajax({
			type:"post",
			url:url,
			data:"id="+id,
			cache:false,
			datatype:"html",
			ajaxPost: true,
			headers: { 
                Accept : "application/json; charset=utf-8",
                "Content-Type": "application/x-www-form-urlencoded; charset=utf-8"
            },
			success:function(data){
				var msgs=data;
				if(msgs.code==0){
					layer.msg(msgs.message,1,1);
					var a = $(obj); var i = 0; while (i++ < num) { a = a.parent(); } a.remove();
				}else{
					layer.msg(msgs.message,1);
			}
			}
		});
	});
}


function OrgForm(type,obj)
{
	var id=obj.form.id;
	$("#"+id).Validform({
		tiptype:1,
		ajaxPost:true,
		postonce:true,
		callback:function(data)
		{
			if(data.code==0)
			{
				setTimeout(function () {
				$.Hidemsg();
				layer.closeAll();
				location.reload();
				}, 1000);
			}
		}
	});
}

function OrgFormL(obj)
{
	var id=obj.form.id;
	$("#"+id).Validform({
		ajaxPost:true,
		postonce: false,
		tiptype:2,
		showAllError:false,
		callback:function(data)
		{
			if(data.code==0)
			{
				setTimeout(function () {
					$.Hidemsg();
					location.reload();
				}, 1000);
			}
		}
	});
}
function OrgFormH(obj){
	var id=obj.form.id;
	$("#"+id).Validform({
		ignoreHidden:true,
		ajaxPost:true,
		postonce: false,
		tiptype:2,
		showAllError:false,
		callback:function(data)
		{
			if(data.code==0)
			{
				setTimeout(function () {
					$.Hidemsg();
					location.reload();
				}, 1000);
			}
		}
	});
}

function OrgFormR(obj)
{
	var id=obj.form.id;
	$("#"+id).Validform({
		ajaxPost:true,
		postonce: false,
		tiptype:function(msg,o,cssctl){
			if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip=o.obj.siblings(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
			}else{
				var objtip=o.obj.find("#msgdemo");
				cssctl(objtip,o.type);
				objtip.text(msg);
			}
		},
		showAllError:false,
		callback:function(data)
		{
			if(data.code==0)
			{
				setTimeout(function () {
					location.reload();
				}, 1000);
			}
		}
	});
}


//提示
function tooltip() {

    
    xOffset = 10;
    yOffset = 20;

    $("div.ellipsis").hover(function (e) {
    	
        this.t = this.title;
        this.title = "";
        if ($(this).find("span").html() == null) {
            $("body").append("<p id='tooltip' style=\"line-height:20px;text-align:left;\">" + $(this).html() + "</p>");
        }
        else {
            $("body").append("<p id='tooltip' style=\"line-height:20px;text-align:left;\">" + $(this).find("span").html() + "</p>");
        }
        $("#tooltip")
			.css("top", (e.pageY - xOffset) + "px")
			.css("left", (e.pageX + yOffset) + "px")
			.fadeIn("fast");
    },
	function () {
	    this.title = this.t;
	    $("#tooltip").remove();
	});
    $("div.ellipsis").mousemove(function (e) {
        $("#tooltip")
			.css("top", (e.pageY - xOffset) + "px")
			.css("left", (e.pageX + yOffset) + "px");
    });
};


Date.prototype.addDays = function(number)
{
	var adjustDate = new Date(this.getTime() + 24*60*60*1000*30*number);
	return;
};

Date.prototype.format = function(format)
{
	var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(),    //day
		"h+" : this.getHours(),   //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3),
		//quarter
		"S" : this.getMilliseconds() //millisecond
	};
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
		format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
	return format;
};

function decodeURIformat(str)
{
	if($.browser.mozilla)
		{
		 	return decodeURI(str);
		}
	else
		{
		return str;
		}
}

function SiteSelect(id)
{
	var s="";var s1="";
	var c="";var c1="";
	var z="";var z1="";
	var html="";
	url="/currentSite.htm?cat="+id;
	if(id == undefined){
		url="/currentSite.htm";
	}
	$.ajax({
		 url: url,
         dataType: "json",
         success: function (data) {
        	 $.each(data.data, function (index, content) {
	        	
	        		 s="<a href=\"javascript:void(0)\" rel=\""+content.sId+"\">"+content.sProvince+"</a>";
	        	
	        	 s1=s1+s;
	        	 
	        	 $.each(content.chufaList, function (i, con) {
	        		
	                     c="<dd zdianId="+content.sId+"><a hef=\"#\" class=\"chufa"+content.sId+"\" rel=\""+con.cId+"\">"+con.cName+"</a></dd>";
	        		
	        		 c1=c1+c;
	        		 $.each(con.columnList,function(c,com){
	        			 z="<a href=\"#\" rel=\""+com.zId+"\" class=\"zd"+con.cId+"\"  zid=\""+con.cId+"\"><i class=\"icon iHot"+com.zHot+"\"></i>"+com.zName+"</a>";
	        			 z1=z1+z;
	        		 });
	        	 });
        	 });
			html=html+"<div class=\"tit SiteSelect\">"+s1+"</div>";
			html=html+"<div class=\"box\" style=\"display:none\"><div class=\"SiteCf myTab\"><dl>"+c1+"</dl></div></div>";
			html=html+"<ul id=\"ButtonBox\" style=\"display:none\" ><li class=\"travelLines\"><span class=\"tit none\" name=\"zId\" >线 路：</span>"+z1+"</li></ul>";
			$(".SiteList").html(html);
			//默认站点专线
			var sid=$(".SiteSelect a[class='cur']").attr("rel");
			$(".SiteCf dd").find(".chufa"+sid).parent().show();
			 $("#FilterList").filterSearch();
			 $(".SiteSelect a").click(function(){
				 	zSiteSelect($(this));
				 });
				$(".SiteCf a").click(function(){
					 SiteCf($(this));
				 });
         }
   });
}


function SiteSelect2(id)
{
	var html="";
	url="/currentSite.htm?cat="+id;
	if(id == undefined){
		url="/currentSite.htm";
	}
	$.ajax({
		 url: url,
         dataType: "json",
         success: function (data) {
        	 $.each(data.data, function (index, content) {
	        		 html+='<div class="item"><div class="zd">'+content.sProvince+'</div><div class="cfd">';
	        	 $.each(content.chufaList, function (i, con) {
	        		 html+="<dl><dt>"+con.cName+"</dt><dd>";
	        		 $.each(con.columnList,function(c,com){
	        			 html+='<label class="rad"><input type="checkbox" value="'+com.zId+'" name="zId" class="mRole" />'+com.zName+'</label>';
	        		 });
	        		 html+="</dd></dl>";
	        	 });
	        	 html+="</div></div>";
        	 });
        	 $(".comlist").html(html);
        	 mRoleShow();
         }
   });
}



function SiteProvince(id)
{
	var s="";var s1="";
	url="/currentSite.htm?cat="+id;
	if(id == undefined){
		url="/currentSite.htm";
	}
	$.ajax({
		 url: url,
         dataType: "json",
         success: function (data) {
        	 $.each(data.data, function (index, content) {
	        		 s="<a href=\"javascript:void(0)\" rel=\""+content.sId+"\">"+content.sProvince+"</a>";
	        	 s1=s1+s;
        	 });
			$(".title-tab").append(s1);
			$("#FilterList").filterSearch();
         }
   });
}


//切换站点
function zSiteSelect(obj)
{
		 $(obj).parent().find("a").removeClass("cur");
		 $(obj).addClass("cur");
		 var sid=$(obj).attr("rel");
		 var zid=$(".SiteCf").find(".chufa"+sid).eq(0).attr("rel");
		 $(".SiteCf dd").hide();
		 $(".SiteCf dd").find(".chufa"+sid).parent().show();
		 $(".travelLines").find("a").hide();
		 $(".travelLines").find(".zd"+zid).show();
		 $(".SiteCf dd").find(".chufa"+sid).removeClass("on");
		 $(".SiteCf dd").find(".chufa"+sid).eq(0).addClass("on");
		 $(".box").show();
		 $("#ButtonBox").show();
}

//切换专线
function SiteCf(obj)
{
		 var zid=$(obj).attr("rel");
		 $(obj).parent().parent().find("a").removeClass("on");
		 $(obj).addClass("on");
		 $(".travelLines").find("a").hide();
		 $(".travelLines").find(".zd"+zid).show();		
}


function GetR(key, key2) {
    if (key == key2) {
        return "checked";
    }
}

function GetChecked(key) {
    if (key > -1) {
        return "checked";
    }
}

function GetS(key, key2) {
    if (key == key2) {
        return "selected";
    }
}


//选择默认站点
function DefProvince(Province,City,lx)
{
	var opt="";
	var copt="";
	$.ajax({
       url: "/fullSite.htm",
       dataType: "json",
       success: function (data) {
               $.each(data.data, function (index, content) {
			   		if(index==0){de=content.sName;}
			   		var valuelxs=content.sId;
				 	if(lx==1){
				 		valuelxs=content.sName;
				 	}
			   		if(Province==content.sName){
            	   		opt=opt+'<option value="'+valuelxs+'" selected>'+content.sName+'</option>';
						 $.each(content.chufaList, function (i, con){
							 	var valuelx=con.cId;
						 		if(City==con.cName){
								copt=copt+'<option value="'+valuelx+'" selected>'+con.cName+'</option>';
								}else
								{
								copt=copt+'<option value="'+valuelx+'" >'+con.cName+'</option>';
								}
							 });
    				   }else
    				   {
    				   		opt=opt+'<option value="'+valuelxs+'" >'+content.sName+'</option>';
    				   }
               });
			   if(Province==""){
			   		$("#DefProvince").append(opt);
			   }else
			   {
			   		$("#DefProvince").html(opt);
					$("#DefCity").html('<option value="">请选择</option>'+copt);
			   }   
		   }
	});
}

function DefSelect(obj,lx)
{
	DefProvince($(obj).find("option:selected").text(),'',lx);
}

function DefCitySelect(obj)
{
	var city=$(obj).find("option:selected").text();
	var pro=$("#DefProvince").find("option:selected").text();
	$("#siteId").val(pro+"|"+city);
}

//结束


//组团社站点选择
function SelectSite()
{
	var Pro="";
	$(".Select_list dl").each(function(){
		if($(this).find(".cur").length>0)
		{
			Pro=Pro+'{"Province":'+'"'+$(this).find("dd").attr("title")+'", "City":[';
			$(this).find(".cur").each(function(i){
			if(i==0){
			Pro=Pro+'{"CName":"'+$(this).attr("title")+'","CId":"'+$(this).attr("rel")+'"}';
			}else{
			Pro=Pro+',{"CName":"'+$(this).attr("title")+'","CId":"'+$(this).attr("rel")+'"}';
			}
			});
			Pro=Pro+"]},";
			
		}
	});
	Pro=Pro.substring(0,Pro.length-1);
	if(Pro!=""){
		Pro="["+Pro+"]";
	}
	$("#SelectValue").val(Pro);
}

function SiteShow(UserSiteCity)
{
	if($(".Select_list").attr("rel")==0) {
          var h = "";
		 	$.ajax({
             url: "/fullSite.htm",
             dataType: "json",
             success: function (data) {
                 $.each(data.data, function (index, content) {
                     h = h + " <dl><dd title="+content.sName+">" + content.sName + "：</dd>";
                     h = h + "<dt>";
                     $.each(content.chufaList, function (i, con) {
					   		if(UserSiteCity.indexOf(con.cName)>0){
									h = h + "<a title=\"" + con.cName + "\" rel=\"" + con.cId + "\"  class=cur>" + con.cName + "  <i class=\" icon-remove icon-white\"></i></a>";
							   }
							   else
							   {
							  		 h = h + "<a title=\"" + con.cName + "\" rel=\"" + con.cId + "\" >" + con.cName + "  <i class=\" icon-remove icon-white\"></i></a>";
							   }
                     });
                     h = h + "</dt></dl>";
                 });
                 $(".Select_list").html(h);
             }
         });
         $(".Select_list").attr("rel","1");
     }     
}


//图片删除
function delimg(obj,id)
{
	$(obj).parent().parent().parent().remove();
}

//读取用户
function SelectMember(id)
{
	$.ajax({
		type:"post",
		url:"/allMember.htm",
		data:"cId="+id,
        dataType: "json",
		success:function(data){
			var item='<option value="">请选择</option>';
			$.each(data.data, function (i, con) {
				item+='<option value="'+con.mId+'" >'+con.mName+'</option>';
				});
			$("#SelectMember").html(item);
			}
	});
}

function SelecttourMember(id)
{
	$.ajax({
		type:"post",
		url:"/allMember.htm",
		data:"cId="+id,
        dataType: "json",
		success:function(data){
			var item='<option value="">请选择</option>';
			$.each(data.data, function (i, con) {
				item+='<option value="'+con.mId+'|'+con.mName+'|'+con.mUserName+'|'+con.mMobile+'|'+con.mTel+'|'+con.mFax+'">'+con.mName+'</option>';
				});
			$("#SelectMember").html(item);
			}
	});
}
//加入黑名单
function addBlack(id)
{
	$.layer({
	    shade: [0],
	    area: ['auto','auto'],
	    dialog: {
	        msg: '您是否确认把该公司加入黑名单？',
	        btns: 2,                    
	        type: 4,
	        btn: ['确认','取消'],
	        yes: function(){
	        	$.ajax({
	        		type:"post",
	        		url:"/zbmanlogin/blacklistsconfirm.htm",
	        		data:"cId="+id,
	                dataType: "json",
	        		success:function(data){
	        			if(data.code==0){
	        				layer.msg('加入成功', 1, 1);
	        	            location.reload();	
	        			}else
	        				{
	        				alert("操作失败，请重新操作！");
	        				}
	        			}
	        	});
	        }, no: function(){
	            
	        }
	    }
	});
	}


function addintegral(obj)
{
	var pageii = $.layer({
    type: 1,
    title: "添加记录",
    area: ['600px', '300px'],
    page: {
        html: '<div id="integraladd" style="padding:0 20px;"><form id="IntegralForm" method="post" action="/zbmanlogin/addIntegral.htm"><input type="hidden" name="cId" id="companyId" value="'+$(obj).parent().parent().find(".cid").val()+'"/><table width="100%" class="tab2"><tr><th>公司名称：</th><td>'+$(obj).parent().parent().find(".cname").html()+'</td></tr><tr><th>积分：</th><td><input type="text" value="" name="iAddintegral"/></td></tr><tr><th>备注：</th><td><input type="text" value="" name="iRemark"/></td></tr><tr><td></td><td><span><button class="btn btn-primary" type="submit" onclick="OrgFormR(this)">提交</button></span><span id="msgdemo"></span></td></tr></table></form></div>'
    }
	});
}


//星期几
function week(date)
{
	var weekDay = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    var myDate = new Date(Date.parse(date.replace(/-/g, "/"))); 
    return weekDay[myDate.getDay()];
}

function CompanyState(obj,t)
{
	$.layer({
	    shade: [0],
	    area: ['auto','auto'],
	    dialog: {
	        msg: '您确定修改该公司的状态吗？',
	        btns: 2,                    
	        type: 4,
	        btn: ['确定','取消'],
	        yes: function(){
	        	$.ajax({
	        		url:"/companystate.htm",
	        		type:"post",
	        		data:"?t="+t,
	        		dataType:"json",
	        		success:function(msg){
	        			if(msg.code==0){
	        				layer.msg('操作成功', 1, 1);
	        			}else
	        				{
	        				layer.msg("操作失败",1,3);
	        				}
	        		}
	        	});
	            
	        }
	    }
	});
}