$(function(){
    $(".acclogin").Validform({
        postonce: false,
        showAllError: false,
        ajaxPost: true,
        callback: function (data) {
            if (data.code == 0) {
                setTimeout(function () {
                    location.href='/account/home.htm';
                    $.Hidemsg();
                }, 20);
            }
            
        }
    });
    $(".managelogin").Validform({
        postonce: false,
        showAllError: false,
        ajaxPost: true,
        callback: function (data) {
            if (data.code == 0) {
                setTimeout(function () {
                    location.href='/zbmanlogin/m/manage.htm';
                    $.Hidemsg();
                }, 20);
            }
            
        }
    });
});