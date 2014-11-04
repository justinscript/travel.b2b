(function ($) {
	// ZB Menu
	$.fn.initMenu = function (options) {
		var defaults = {
			activeClass: 'active'
		};
		options = $.extend(defaults, options);

		var obj = $(this),
			$SubMenu = $('li:has("div")', obj);

		$('a:eq(0)', $SubMenu).click(function () {
			var $this = $(this),
				$curMenu = $this.parent();

			if (!$curMenu.hasClass(options.activeClass)) {
				$('div', obj).slideUp(300);

				$('div', $curMenu).slideDown(300);

				$SubMenu.removeClass(options.activeClass);
				$curMenu.addClass(options.activeClass);
			}

			return false;
		});

		$('a.active', $SubMenu).first().parent().siblings('a').click();
	};

	//标题停留 2014-7-12 wangzujun
	$.fn.TitleFixed = function (options) {
		var settings = $.extend({
			Height:100,
			cla:"fixed"
		}, options);
		var ots = $(this);
		var sctop = (document.documentElement.scrollTop || document.body.scrollTop);
		var obtop = ots.offset().top + settings.Height;
		if ($.support.leadingWhitespace) {
			$(window).scroll(function(){
				var sctop = (document.documentElement.scrollTop || document.body.scrollTop);
				
				if(sctop>obtop-110){
					ots.addClass(settings.cla);
				}else{
					ots.removeClass(settings.cla);
				}
			});
		}
	}
	
	//
	//搜索框提示
	$.fn.autoTip = function(G){
	var D;
	var tip=$(this).attr("tip");
	D = {
		tip:"tip",             //默认提示信息样式名class
		tipnone:"tipnone"      //在指定的input执行click时替换的样式名class
	};
	$.extend(D,G);
	if ($(this).val()==""){
		$(this).val(tip)
			   .addClass(D.tip)
		       .click(function(){
				if($(this).val()==tip){
						 $(this).val("");
						 $(this).removeClass(D.tip);
						 $(this).addClass(D.tipnone);
				}
				})
				.blur(function(){
				if($(this).val()==""){
				  $(this).removeClass(D.tipnone);
				  $(this).addClass(D.tip);
				  $(this).val(tip);
				}
				});
	};
	}
	
	//返回顶端
	$.fn.zbscrollTop=function(options){
		var ots=$(this);
		$(this).click(function(){if(scroll=="off") return;$("html,body").animate({scrollTop: 0}, 600);});
	}

	// fixed menu
	$.fn.fixedMenu = function (opt) {
		var defaults = {
			scrollHeight: 0
		};
		opt = $.extend(defaults, opt);

		var obj = $(this);

		$(obj).scroll(function () {
			if ($(obj).scrollTop() > opt.scrollHeight) {
				$("#Menu,#LeftNav").addClass('fixed');
			}
			else {
				$("#Menu,#LeftNav").removeClass('fixed');
			}
		});
	};

	// filter search
	$.fn.filterSearch = function (opt) {
		var defaults = {
			tarId: '#FilterSearch',
			callblack:function(){}
		};
		opt = $.extend(defaults, opt);

		var obj = $(this);
		var where=""
		$('li', obj).each(function (index) {
			var $this = $(this);

			$('a', $this).click(function () {
				var $this = $(this),
					thisP = $this.parent(),
					txt =  $this.text(),
					value=$this.attr("rel"),
					hide=$this.siblings('.tit').attr("hide"),
					tit = $this.siblings('.tit').text(),
					cla = $this.siblings('.tit').attr("name");
				//区分是否选中
				if($("#"+cla).length>0)
				{
					$("#FilterSearch",obj).find("#"+cla).remove();
				}
				
				//往选中条件里赋值
				$(opt.tarId).append('<label id="'+cla+'" name="'+cla+'" val="'+value+'"><b class="tit">' + tit + '</b>' + txt + '<b>X</b></label>');
				$("."+cla).val(value);
				
				//将选中项标志
				thisP.find("a").removeClass("hover");
				$this.addClass("hover")
				if(hide=="true")
				{
					$(thisP).slideUp();
				}
				
		        getPage(1,'')
				return false;
			});
		});

		$(opt.tarId).on('click', 'label', function () {
			var $this = $(this),
				listId = $this.attr('id'),
				claId=$this.attr('name');
			
				
			$this.remove();
			
			$('span[name='+claId+']', obj).parent().slideDown();
			$('span[name='+claId+']', obj).parent().find("a").removeClass("hover")
			
	        getPage(1,'')
			
		});
		
		$(".search-list",obj).find("button").click(function(){
			getPage(1,'')
		});
		
		var defaultUrl=location.href.split("#");

		//url分析重获条件
		if(typeof(defaultUrl[1])!="undefined")
		{
			
			where = defaultUrl[1];
			var SetHtmlDefaut = defaultUrl[1].split("&");
			for(var i=0;i<SetHtmlDefaut.length;i++)
			{
				var Key = SetHtmlDefaut[i].split("=");
				$('li span[name="'+Key[0]+'"]',obj).each(function(){
						if($(this).parent().find("span").length)
						{
							if($(this).attr("hide")=="true")
							{
								$(this).parent().slideUp();
							}
							$("span[name="+$(this).attr("name")+"]",obj).parent().find("a[rel="+Key[1]+"]").addClass("hover");
							
							$(opt.tarId).append('<label id="'+$(this).attr("name")+'" name="'+$(this).attr("name")+'" val="'+Key[1]+'"><b class="tit">' + $(this).text() + '</b>' + $(this).parent().find("a[rel="+Key[1]+"]").text()+ '<b>X</b></label>');
					}
				});
				
				
						if(Key[0]=='zId')
						{
							var cgd=$("span[name='"+Key[0]+"']",obj).parent().find("a[rel='"+Key[1]+"']").attr("zid");
							var zdian=$(".SiteCf",obj).find("a[rel="+cgd+"]").parent().attr("zdianId");
							//alert(zdian+"---"+cgd)
							$(".SiteSelect",obj).find("a[rel="+zdian+"]").addClass("cur");
							$(".SiteCf",obj).find("a[rel="+cgd+"]").addClass("on");
							$(".SiteCf",obj).find("dd[zdianId="+zdian+"]").show();
							
							$("span[name='"+Key[0]+"']",obj).parent().find("a").hide();
							$("span[name='"+Key[0]+"']",obj).parent().find("a[zid="+cgd+"]").show()
							$(".box",obj).show()
							$("#ButtonBox",obj).show()
						}
				
				
				$(".search-list",obj).find("input[type='text'][name="+Key[0]+"]").each(function(){
					$(this).val(decodeURIformat(Key[1]))
				})
				
				$(".search-list",obj).find('input[type="radio"]').each(function(){
					if($(this).attr("name")==Key[0] && $(this).val()==Key[1])
					{
						$(this).attr("checked","checked")
					}
				})
				
				$(".search-list",obj).find('select[name='+Key[0]+']').each(function(){
					$("option[value="+Key[1]+"]",this).attr("selected", true)
				})
				
			}
		}
		
		var getPage=function(page,where){
				var loadi = layer.load('数据正在读加载中', 0); //需关闭加载层时，执行layer.close(loadi)即可
				if(where=="")
				{
					
					where="page="+page
				 	obj.find("label").each(function () {
			            where += "&" + $(this).attr("name") + "=" + $(this).attr("val")
			        })
			        
			        //Form表单
			        //text
			        $(".search-list",obj).find("input[type='text']").each(function(){
			        	if($(this).val()!="")
			        		where+="&"+$(this).attr("name")+"="+$(this).val();
			        })
			        
			        //radio
			        $(".search-list",obj).find('input[type="radio"]:checked').each(function(){
			        		where+="&"+$(this).attr("name")+"="+$(this).val();
			        })
			        
			        //select
			        $(".search-list",obj).find('select').each(function(){
			        		if($(this).val()!=="")
		        			{
			        			where+="&"+$(this).attr("name")+"="+$(this).val();
		        			}
			        });
			    }
				
				location.href='#'+where;
	            $.ajax({
	                type: "post",//使用get方法访问后台
	                dataType: "html",//返回json格式的数据
	                data:where,
	                url: obj.attr("url"),//要访问的后台地址
	                complete :function(){layer.close(loadi);},//AJAX请求完成时隐藏loading提示
	                success: function(msg){//msg为返回的数据，在这里做数据绑定
	                	var msgObj=$(msg)
	                	if(msgObj.find("tr").length<=1)
                		{
	                		$(obj).parent().find(".tableContent").html(msg).find("table tbody").append('<tr><td colspan="'+msgObj.find("th").length+'" style="font-size:14px;height:50px;font-weight:700;text-align:center">暂无您查询的数据，请重新查询！</td></tr>');
                		}
	                	else
                		{
	                		$(obj).parent().find(".tableContent").html(msg);
                		}
	                	
	                	$(".pager").find("a").click(function(){
						getPage($(this).attr("p"),'');						
						return false;	
						});
	                	
						defaults.callblack()
						
	                }
	            })
	     }
		 getPage(1,where);
		 
	};

	//日期
	$.fn.calendar=function(opt)
	{
		var defaults={
			toId:"test1",
			multiple:true,
			ajax:"",
			click:"",
			title:"",
			callblank:"",
			blankJson:"",
			defDate:new Date(),
			dateFormat:"d",
			weeks: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
			month: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
		}
		opt=$.extend(defaults,opt);
		// alert(defaults.toId)
		var obj=$(this);

		var geStyle="calendar_d_box_no_hover";
		if(defaults.multiple)
		{
			geStyle="calendar_d_box";
		}

		var get_first_date= function (year, month) {
			return new Date(year, month, 1);
		}

		var get_last_date= function (year, month) {
			return new Date(year, month + 1, 0);
		}

		var get_ren=function(rencount)
		{
			if(rencount>9)
				return '充足'
			else
				return rencount
		}
		
		
		var getCalendar=function(year,month)
		{

			var now = defaults.defDate;
		
			year = year ? year : now.getFullYear();
			month = month ? month - 1 : now.getMonth()-1;

			var firstday = get_first_date(year, month).getDay();

			var lastday = get_last_date(year, month).getDay();
			var lastdate = get_last_date(year, month).getDate();
			
			var html = '<div class="calendar_box" style="display: block;" onselectstart="return false"> \
                    <div class="calendar_m">       \
                    <div class="search_pp_cal_nevm">        \
                    <span class="cal_nevm_icon"><<'+new Date(year,month-1,1).format("M")+'月<b style="display:none">'+new Date(year,month-1,1).format("yyyy-MM-dd")+'</b></span>       \
                    </div>                \
					<div class="calendar_tit">'+new Date(year,month,1).format("yyyy-MM")+''+defaults.title+'</div>\
            <div class="search_pp_cal_nextm">         \
            <span class="cal_nextm_icon">'+new Date(year,month+1,1).format("M")+'月<b style="display:none">'+new Date(year,month+1,1).format("yyyy-MM-dd")+'</b>>></span>    \
                    </div></div>';

			html += '<ul class="calendar_t">'

			for (var i in defaults.weeks) {
				html += '<li>' + defaults.weeks[i] + '</li>';
			}
			html += '</ul>';

			html += '<ul class="calendar_d">';
			var d = 0;
			var last_month_lastdate=0;
			if (firstday != 0) {//如果第一天不是星期天，补上上个月日期
				last_month_lastdate = get_last_date(year, month - 1).getDate();
				var last_month_last_sunday = last_month_lastdate - firstday + 1;

				for (var j = last_month_last_sunday; j <= last_month_lastdate; j++) {
					html += '<li> ';


					html+='<div class="calendar_d_box_no_hover month_1"> ';
					html+='<span class="calendar_day"></span>       \
                            \<span class="calendar_price1"></span>\
                    <span class="calendar_price"></span>     \
                            </div>         \
                            </li>';
					d++;
				}
			}
			var yd=parseInt(d);

			for (var k = 1; k <= lastdate; k++) {
				html += '<li>';
				
				
					if(defaults.multiple && ($("#"+defaults.toId).val()+',').indexOf(','+new Date(year,month,k).format("yyyy-MM-dd")+',')>-1)
					{
						html+='<div class="'+geStyle+' month_2 cursor">';
					} else{
						html+='<div class="'+geStyle+' month_2"> ';
					}
				
				html+='<span class="calendar_day">' + new Date(year,month,k).format(defaults.dateFormat) + '</span>       ';

				html += '<span class="calendar_price1"></span>'
				html += '<span class="calendar_price2"><b><img src="/static/img/ico_01.jpg"></b><b>&nbsp;<img src="/static/img/ico_02.jpg"></b></span>'
				html += '<span class="calendar_price"></span>'

				html += '<span style="display:none;" class="calendar_day_date" id="'+new Date(year,month,k).format("yyyy-MM-dd")+'">'+new Date(year,month,k).format("yyyy-MM-dd")+'</span>\
                            </div>         \
                            </li>';
				d++;
				if (d == 7) {
					d = 0;
					html += '</ul><ul class="calendar_d">';
					if (lastday != 6) {
						html += '</ul><ul class="calendar_d">';
					}
				}
			}

			yd=parseInt(yd+lastdate)
			if (lastday <42) {//如果最后一天不是周六，补上下个月日期
				var last_month_saturday = 6 - lastday;
				for (var l = 1; l <= parseInt(42-yd); l++) {
					html += '<li>';
					if(defaults.multiple && ($("#"+defaults.toId).val()+',').indexOf(','+new Date(year,month+1,l).format("yyyy-MM-dd")+',')>-1)
					{
						html+='<div class="'+geStyle+' month_1 cursor"> ';
					} else{
						html+='<div class="'+geStyle+' month_1"> ';
					}
					html+='<span class="calendar_day">' + new Date(year,month+1,l).format(defaults.dateFormat) + '</span>       ';

					html += '<span class="calendar_price1"></span>'
					html += '<span class="calendar_price2"><b><img src="/static/img/ico_01.jpg"></b><b>&nbsp;<img src="/static/img/ico_02.jpg"></b></span>'
					html += '<span class="calendar_price"></span>'

					html += '<span style="display:none;" class="calendar_day_date" id="'+new Date(year,month+1,l).format("yyyy-MM-dd")+'">'+new Date(year,month+1,l).format("yyyy-MM-dd")+'</span>\
                            </div>         \
                            </li>';
				}
				html += '</ul><ul class="calendar_d">';
			}
			html += '</ul></div>';

			obj.html(html);

			//---- ajax数据加载------//
			if(defaults.ajax!="")
			{
				$.ajax({
					type: "get",//使用get方法访问后台
					dataType: "json",//返回json格式的数据
					url: defaults.ajax,//要访问的后台地址
					complete :function(){},//AJAX请求完成时隐藏loading提示
					success: function(msg){//msg为返回的数据，在这里做数据绑定
						json=msg.data//({"2014-06-01":{"price":"856","roomNum":"4","GoTime":"2014-06-01"},"2014-06-29":{"price":"1945","roomNum":"4","GoTime":"2014-06-29"},"2014-08-29":{"price":"1945","roomNum":"4","GoTime":"2014-08-29"}})
						defaults.blankJson(json)

						var maxDate=new Date(json[json.length-1].lGoGroupTime).format("yyyy-MM-dd");
						var minDate=new Date(json[0].lGoGroupTime).format("yyyy-MM-dd");
						$.each(json,function(i){
							$("#"+new Date(json[i].lGoGroupTime).format("yyyy-MM-dd")).parent().removeClass("calendar_d_box_no_hover").addClass("calendar_d_box").each(function(){
								$(this).find(".calendar_price1").html(get_ren(json[0].lSurplusCount))
								$(this).find(".calendar_price").html(json[i].lCrPrice+"元") ;
								$(this).attr('title','<b>门市价：</b><font color=red>￥'+json[i].lCrPrice+'</font>元/成人 <font color=red>￥'+json[i].lXhPrice+'</font>元/儿童');
								$(this).attr("lid",json[i].lId);
								$(this).attr("lGroupNumber",json[i].lGroupNumber);
								$(this).attr('IsIntegral',1)
								//if(json[i].IsIntegral==1)
								//{
								//	$(this).find(".calendar_price2 b:eq(0)").show()
								//	$(this).attr('title',$(this).attr('title')+'<br/><b>积　分：</b><font color=red>'+json[i].lChildrenIntegral+'</font>/成人 <font color=red>'+json[i].lAdultIntegral+'</font>/儿童');
								//}

								//if(json[i].IsVouchers==1)
								//{
								//	$(this).find(".calendar_price2 b:eq(1)").show()
								//	$(this).attr('title',$(this).attr('title')+'<br/><b>抵用卷：</b><font color=red>'+json[i].IntegralCren+'</font>元/成人 <font color=red>'+json[i].IntegralCren+'</font>元/儿童<br/><b style="color:red">(注：婴儿不能使用抵用卷)</b>');
								//}
							})
						})
						var upTime=$(obj).find(".cal_nevm_icon b").html();
						var downTime=$(obj).find(".cal_nextm_icon b").html()
						if(new Date(upTime.split("-")[0],upTime.split("-")[1],1)<new Date(minDate.split("-")[0],minDate.split("-")[1],1))
						{     $(obj).find(".cal_nevm_icon").hide()
						}
					
						if(new Date(downTime.split("-")[0],downTime.split("-")[1],1)>new Date(maxDate.split("-")[0], maxDate.split("-")[1],1))
						{
								$(obj).find(".cal_nextm_icon").hide()
						}
						if(defaults.click!="")
						{
							$(obj).find(".calendar_d_box").click(function(){
								defaults.click({"lGroupNumber":$(this).attr("lGroupNumber")});
							})
						}
						defaults.callblank(obj)
					}
				})
			}


			//-------多选日期-------//
			if(defaults.multiple)
			{
				$(obj).find(".calendar_d_box").click(function(){
					if($(this).hasClass("cursor"))
					{
						$("#"+defaults.toId).val($("#"+defaults.toId).val().replace(','+$(this).find(".calendar_day_date").html(),''))
						$(this).removeClass("cursor");
					}
					else{
						$("#"+defaults.toId).val($("#"+defaults.toId).val()+','+$(this).find(".calendar_day_date").html())
						$(this).addClass("cursor")
					}
				})
			}
			obj.find(".cal_nextm_icon").click(function(){
				//alert($(this).parent().find("b:eq(0)").html().toString().split('-')[0])
				getCalendar($(this).parent().find("b:eq(0)").html().toString().split('-')[0],$(this).parent().find("b:eq(0)").html().toString().split('-')[1])

			})

			obj.find(".cal_nevm_icon").click(function(){
				getCalendar($(this).parent().find("b:eq(0)").html().toString().split('-')[0],$(this).parent().find("b:eq(0)").html().toString().split('-')[1])

			})


		}
		getCalendar();
	}



	//列表结构遍历生成
    $.fn.SysMessage=function(opt)
    {
        var defaults = {
            html:"",
            key:"",
            blackcall:function(o){

            }
        };
        opt = $.extend(defaults, opt);

        var obj = $(this);

        var host = "wap.zuobian.com"//location.hostname;
	        
            var heartbeat_timer = 0;//缓冲时间
            var last_health = -1; 
            var health_timeout = 3000*10;
            var push_url="ws://" + host + "/push/msg.htm";
            var status_code = "$zuobian$";
            var J = $;
            
            var dataParam = {
            	  
            }
            var request = {   
                  action: '6',  
                  type: 'client_heartbeat', 
                  data:status_code  //data可以是对象dataParam
            };  
            var request_encoded = J.toJSON(request);

            $(function(){
                ws = ws_conn(push_url); 
                $("#send_btn").click(function(){
                    var msg = $("#mysendbox").val();
                    console.log(msg);
                    var msg_request = {
                   		action: '5',  
                        type: 'send_data', 
                        data:msg  //data可以是对象dataParam
                    }
                    ws.send(J.toJSON(msg_request));
                    $("#mysendbox").val("");
                });
            });

            function keepalive(ws){
                var time = new Date();
                if(last_health != -1 && (time.getTime() - last_health > health_timeout)){
                        //此时即可以认为连接断开，可是设置重连或者关闭
                        $("#keeplive_box").html( "服务器没有响应." ).css({"color":"red"});
                        ws.close();
                }
                else{
                    $("#keeplive_box").html("连接正常").css({"color":"green"});
                    if(ws.bufferedAmount == 0){
                        ws.send(request_encoded);        
                    }
                }
            }

            //websocket function
            function ws_conn(to_url){
                to_url = to_url || "";
                if(to_url == ""){
                    return false;
                }
                clearInterval(heartbeat_timer);
                $("#statustxt").html("Connecting...");
                var ws = new WebSocket(to_url);
              	ws.onopen=function(){
                    $("#statustxt").html("connected.");    
                    $("#send_btn").attr("disabled", false);
                    heartbeat_timer = setInterval(function(){keepalive(ws)}, 5000*10);
                }
                ws.onerror=function(){
                    $("#statustxt").html("error.");
                    $("#send_btn").attr("disabled", true);
                    clearInterval(heartbeat_timer);
                    $("#keeplive_box").html("连接出错.").css({"color":"red"});
                }
                ws.onclose=function(){
                    $("#statustxt").html("closed.");
                    $("#send_btn").attr("disabled", true);
                    clearInterval(heartbeat_timer);
                    $("#keeplive_box").html("连接已关闭.").css({"color":"red"});
                }
                ws.onmessage=function(msg){
                    var time = new Date();
                    var msgData = msg.data;
                    if(msgData.code == "6" && msgData.data == status_code){
                        last_health = time.getTime();
                        return;
                    }
                    if(msgData.code == "9" && msgData.data == status_code){
                    	keepalive(ws);
                    }
                    
                    $("#chatbox").val($("#chatbox").val() + msgData  + "\n");
                    $("#chatbox").attr("scrollTop",$("#chatbox").attr("scrollHeight"));
                }
                return ws;
            }
    }

})(jQuery);