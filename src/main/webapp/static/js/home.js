$(function () {
    //会员注册切换
    $("#login_cut a").each(function () {
        $(this).click(function () {
            var id = $(this).index();
            Login_Type(id);
            Login_Remember($("#log").val());
        });
    });

    //会员注册提交
    var Register=$(".Register").Validform({
        url:'doRegister.htm',
        postonce:true,
        tiptype:2,
        showAllError:false,
        ajaxPost:true,
        callback:function(data)
        {
            if(data.code==0)
            {
                setTimeout(function () {
                    location.href='/';
                    $.Hidemsg();
                }, 1000);
            }
        }
    });
	
	//会员登录
    var userloginform = $(".userloginform").Validform({
        url: "/doLogin.htm",
        postonce: false,
        showAllError: false,
        ajaxPost: true,
        callback: function (data) {
            if (data.code == "0") {
                if ($("#remember").attr("checked") == 'checked') {
                    $.cookie("remember", "true", { expires: 7 });
                    $.cookie("account", $("#account").val(), { expires: 7 });
                    $.cookie("password", $("#password").val(), { expires: 7 });
                }
                else {
                    $.cookie("remember", "false", { expires: -1 });
                    $.cookie("account", '', { expires: -1 });
                    $.cookie("password", '', { expires: -1 });
                }
                setTimeout(function () {
                    location.href='/tour/order.htm';
                    $.Hidemsg();
                }, 1000);
            }
        }
    });
    

    //首页出港地选择专线
    $("#line_site dl").each(function () {
        $(this).click(function () {
            $("#line_site dl").removeClass("on");
            $(this).addClass("on");
        });
    });

    $(".Com_nav li").click(function(){
    	$(this).parent().find("li").removeClass("on");
    	$(this).addClass("on");
    	$(".Pcon").hide();
    	$(".Pcon").eq($(this).index()).show();
    	
    })
    
    //会员退出
    $(".user_out").click(function () {
        $.ajax({
            url: url,
            type: "post",
            dataType: "html",
            data: "act=AccountOut&v=out",
            success: function (msg) {
                if (msg == "ok") {
                    $.MessageShow("退出成功！");
                    setTimeout(function () {
                        location.reload();
                    }, 1000);
                }
            }
        });

    });
    



    //提交购物车
    var OrgForm = $("#OrgForm").Validform({
        url: "/ajax.asp",
        postonce: false,
        showAllError: false,
        ajaxPost: true,
        callback: function (data) {
            if (data.success == true) {
                setTimeout(function () {
                    location.href='/shop/cart.htm';
                    $.Hidemsg();
                }, 1000);
            }
        }
    });


    //首页出港地选择专线
    $("#line_site dl").each(function () {
        $(this).click(function () {
            $("#line_site dl").removeClass("on");
            $(this).addClass("on");
        });
    });


    //专线切换
     $("#SideNav li").click(function () {
        $("#SideNav").find("a").removeClass("sel");
        $("#ConList div").hide();
        $(this).find("a").addClass("sel");
        $("#ConList div").eq($(this).index()).show();
     });

    
    //幻灯片
    if($("#ShopMaxShow").length>0){
        $("#ShopMaxShow").kinMaxShow({
            height: 260,
            button: {
                switchEvent: 'click',
                showIndex: false,
                normal: { width: '30px', height: '8px', lineHeight: '14px'
                    , left: '15%', bottom: '10px', fontSize: '10px', background: "#666", border: "1px solid #666", color: "#666666", textAlign: 'center', marginRight: '8px', fontFamily: "Verdana", float: 'left'
                },
                focus: { background: "#C67907", border: "1px solid #C67907", color: "#000000" }
            }
        });
    };

    if($("#IndexMaxShow").length>0)
    {
        $("#IndexMaxShow").kinMaxShow({
            height: 400,
            button: {
                switchEvent: 'click',
                showIndex: false,
                normal: { width: '30px', height: '8px', lineHeight: '14px'
                    , left: '15%', bottom: '10px', fontSize: '10px', background: "#666", border: "1px solid #666", color: "#666666", textAlign: 'center', marginRight: '8px', fontFamily: "Verdana", float: 'left'
                },
                focus: { background: "#C67907", border: "1px solid #C67907", color: "#000000" }
            }
        });
    }
});


//会员登录切换
function Login_Type(id) {
    $("#login_cut a").removeClass("on");
    $("#login_cut a").eq(id).eq(0).attr("class", "on");
    $(".user_input dt").eq(0).html($("#login_cut a").eq(id).attr("title") + '：');
    $("#log").val(id);
}

function Login_Remember(id) {
    if ($.cookie("account") !=null) {
        if ($.cookie("M_T") == id) {
            $("#remember").attr("checked", true);
            $("#account").val($.cookie("account"));
            $("#password").val($.cookie("password"));
        } else {
            $("#remember").attr("checked", false);
            $("#account").val("");
            $("#password").val("");
        }
    }
}


//站点切换
function SetCity(id) {
    $.ajax({
        url: "/modifySite.htm",
        type: "post",
        datatype: "html",
        data: "siteId="+id,
        ajaxPost: true,
        headers: { 
            Accept : "application/json; charset=utf-8","Content-Type": "application/x-www-form-urlencoded; charset=utf-8"
        },
        success: function (msg) {
            location.reload();
        },
        beforeSend: function () {
     	   layer.load('正在加载数据中，请耐心等待...', 3);

         },
         complete: function () {
         	layer.load('正在加载数据中，请耐心等待...', 3);
         }
    });
}



function SetCG(id) {
    $.ajax({
        url: "/modifyChugang.htm",
        type: "post",
        datatype: "html",
        data: "chugangId="+id,
        ajaxPost: true,
        headers: { 
            Accept : "application/json; charset=utf-8","Content-Type": "application/x-www-form-urlencoded; charset=utf-8"
        },
        success: function (msg) {
            location.reload();
        },
       beforeSend: function () {
    	   layer.load('正在加载数据中，请耐心等待...', 3);

        },
        complete: function () {
        	layer.load('正在加载数据中，请耐心等待...', 3);
        }
    });
}

//定位栏目
function GetMenu(title) {
    $(".fr a").each(function () {
        if ($(this).html().indexOf(title) >-1) {
            $(this).addClass("sel");
        }
    });
}

//验证用户
function CompanyType(id) {
    $("#C_Name").attr("ajaxurl", "/nameVerify.htm?type="+id);
    $("#M_UserName").attr("ajaxurl", "/userNameVerify.htm?type="+id);
    $("#tabsUl").attr("class", "tabs" + id + "-on");
    $("#t").val(id);
}

function GetDay(d, t) {
    var day = new Date(d).getDay();
    var week;
    var arr_week = new Array("<b style='color:red;font-weight:700'>日</b>", "<font style='color:#000;'>一</font>", "<font style='color:#000;'>二</font>", "<font style='color:#000;'>三</font>", "<font style='color:#000;'>四</font>", "<font style='color:#000;'>五</font>", "<b style='color:red;font-weight:700'>六</b>");
    week = arr_week[day];
    if (t == 1) {
        return new Date(d).Format("MM-dd") + "&nbsp;&nbsp;" + week + "";
    }
    else {
        return new Date(d).Format("yyyy-MM-dd") + "&nbsp;&nbsp;" + week + "";
    }
}

function AddFavorite(title,url){try{window.external.addFavorite(url,title);}catch(e){try{window.sidebar.addPanel(title,url,"")}catch(e){alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加")}}};



function companylist(id)
{
	if($(".Pcontent div").length==2)
	{
		ajaxlist(1,id);
	}
}

function ajaxlist(page,id)
{
	$.ajax({
			url:"/company_line/"+id+".htm",
            type: "get",
            dataType: "html",
			data:"page="+page,
            success: function (html) {
				if($(".Pcontent div").length==2){
				$(".Pcontent").append('<div class="Pcon" style="display:block">'+html+'</div>')
				}else
				{
				$(".Pcontent div").eq(2).html(html)
				}
			}
		});
}