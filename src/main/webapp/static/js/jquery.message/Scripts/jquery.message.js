//by minamiko
jQuery.MessageShow = function (msg) {
    $("<div id='message' class='message'>" + msg + "</div>").appendTo("body").hide().fadeIn("fast").delay(2000).fadeOut("slow", function () {
        $(this).remove();
    });
   $(".message").floatDiv();

}



jQuery.MessageShow2 = function () {
    $("<div id='message2' class='message2' ><div style='margin-top:250px;font-size:18px;font-weight:700'><a href='javascript:void(0)' onclick='$(this).parent().parent().remove();'>关闭通知</a></div></div>").appendTo("body")
    $(".message2").floatDiv();

}



