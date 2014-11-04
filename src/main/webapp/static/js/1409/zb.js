$(function(){
    $("#so_input").autoTip();

    if($("#index").val()=="0"){
        $(".side_box").show(); 
    }
    $(".side_menu").hover(function(){
        if($("#index").length==0){
            $(".side_box").show();  
        }
    },function(){
        if($("#index").length==0){
        $(".side_box").hide();  
    }
    });
    //城市切换
    $('.more_tip').hover(function() {
    var self = $(this);
    self.addClass('hover');
    $('#' + self.data('tip_id')).show().off().hover(function() {
        if (e) {
            clearTimeout(e);
        }
    }, function() {
        if (e) {
            clearTimeout(e);
        }
        $('#' + self.data('tip_id')).hide();
        self.removeClass('hover');
    });
    }, function() {
        var self = $(this);
        e = setTimeout(function() {
            $('#' + self.data('tip_id')).hide();
            self.removeClass('hover');
        }, 200);
    });

    //专线切换
    $("#icolumn a").click(function(){
        $(this).siblings("a").removeClass("hover");
        $(this).addClass("hover");
        $("#icolumnli li").hide().eq($(this).index()).show();
        return false;
    });

    //线路切换
    $("#ilinetit a").click(function(){
        $(this).siblings("a").removeClass("hover");
        $(this).addClass("hover");
        $("#ilineli .list-li").hide().eq($(this).index()).show();
        return false;
    });

    //切换站点
    $(".city_tag_top a").click(function(){
        $(this).siblings("a").removeClass("on");
        $(this).addClass("on");
        $(".city_tag_con div").hide().eq($(this).index()).show();
    });

    $("#search_select").hover(function(){
        $("#curtlist").show();
        $("#curtlist li").click(function(){
            $("#tn").val($(this).attr("data"));
            $(this).parent().prev().find("b").html($(this).html());
            $(this).parent().hide();
        });
    },function(){
        $("#curtlist").hide();
    });
});

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

