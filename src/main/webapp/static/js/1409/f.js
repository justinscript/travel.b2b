$(function(){
	$("#ulogintype a").click(function(){
		$(this).siblings("a").removeClass("hover");
		$(this).addClass("hover");
		$("#utype").val($(this).attr("data-id"));
	});
});

$("#registerform").Validform({
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
        $("#registerinfo").hide();
        $("#registersuccess").show();
        $("#usercompany").html(data.cName);
        $("#username").html(data.mUserName);
      }, 1000);
    }
  }
});


$("#loginform").Validform({
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
	        location.href=data.data;
	      }, 1000);
	    }
	  }
	});




//验证用户
function CompanyType(obj) {
    $(obj).parent().parent().find("a").removeClass("on");
    $(obj).parent().parent().find("p").hide();
    $(obj).addClass("on");
    $(obj).next("p").show();
    var id=$(obj).attr("data-id");
    $("#C_Name").attr("ajaxurl", "/nameVerify.htm?type="+id);
    $("#M_UserName").attr("ajaxurl", "/userNameVerify.htm?type="+id);
    if(id==1){
        $("#typename").html("组团社");
    }else
    {
        $("#typename").html("批发商");
    }
    $("#t").val(id);
    return false;
}

