/*
=========== 插件可设置参数及默认值 ===========
onOff:true, //默认开启,此项一般不作设置
width:"", //宽度
height:"", //高度
cover:true, //是否遮罩，默认有
coverDiv:"floatDivCover", //遮罩层挂勾(填写元素id，此项一般不作设置)
coverColor:"#000000", //遮罩背景颜色
fade:50, //遮罩透明度
space:0, //沿边间距
level:"center", //水平排列方式(left,center,right)
vertical:"middle", //竖直排列方式(top,middle,bottom)
closeHandler:"", //关闭句柄(填写元素id)
dragHandler:"", //拖拽句柄(须为对象内元素)，默认无(支持CSS选择器语法)，若值为字符串"self"，则自身为事件句柄
dragLimit:true, //拖拽区域限制，默认限制在窗口可视区内
ifScroll:true, //是否滚动模式（层是否随滚动条滚动），默认为滚动模式
scrollBar:false //窗口滚动条是否可用（非滚动模式下有效）
====================================
插件版本：V 2.0
@作者:路卡卡
QQ:1599538531
2012-01-16
====================================
PS:
同时解决在万恶的IE6下两个问题：select不能遮住问题；改善滚动时层抖动问题(还有轻微，日后找到更好的办法再更新);
支持ESC键关闭;
注意：
1、如果浮动层里是ajax动态加载内容且层未定宽高，请在ajax回调函数里取得数据之后的位置调用本插件方法，否则层位置可能产生偏差；
2、如果在自定义方法执行过程中需要关闭请使用$("selector").floatDiv({onOff:false})，如ajax的回调[立即触发]。其它关闭如单击关闭设置closeHandler即可[事件触发]；
3、同时设置ifScroll:false和scrollBar:false将造成锁屏效果；
4、IE6的情况下，当且仅当非滚动模式（ifScroll:false）支持拖拽！
====================================
2011-12-21更新：
⊙增加：选择器判断；
	
2012-01-16更新：
⊙改进：只在IE6下监听滚动事件，非IE6下插件灵敏度提升；
⊙增加：自定义滚动模式，非滚动模式下可设置窗口滚动条是否可用；
⊙增加：浮动层拖拽功能;
	
2012-01-17更新：
⊙增加：浮动层拖拽区域控制；
⊙改进：拖拽性能改进；
====================================
*/
; (function ($) {
    $.fn.extend({
        floatDiv: function (opt) {
            if ($(this).length == 0) { return }; //选择器判断
            //参数初始化
            opt = $.extend({
                onOff: true, //默认开启
                width: "", //宽度
                height: "", //高度
                cover: false, //是否遮罩，默认有遮罩
                coverDiv: "floatDivCover", //遮罩层挂勾（遮罩模式下有效）
                coverColor: "#000000", //遮罩背景颜色（遮罩模式下有效）
                fade: 10, //遮罩透明度，默认一半一半（遮罩模式下有效）
                space: 0, //沿边间距（针对层靠近窗口边沿的情况），默认无
                level: "center", //水平排列方式，默认水平居中
                vertical: "middle", //竖直排列方式，默认垂直居中
                closeHandler: "", //关闭句柄，默认无
                dragHandler: "", //拖拽句柄(须为对象内元素)，默认无(支持CSS选择器语法)
                dragLimit: true, //拖拽区域限制，默认有
                ifScroll: true, //是否滚动模式（层是否随滚动条滚动），默认为滚动模式
                scrollBar: false //窗口滚动条是否可用（非滚动模式下有效，默认不可用）
            }, opt);

            //全局变量
            var obj = $(this),
				selfWidth, //对象宽度
				selfHeight, //对象高度
				maxZ = 0, //文档Z轴最大值
				browserVersion = null, //浏览器版本
				frame = "", //遮挡iframe[解决IE6 select Bug,你懂的]
				dragX = 0, dragY = 0, //拖拽偏移
				dragFlag = false; //拖拽标记

            //初始化操作
            var init = function () {
                //初始增加临时隐藏容器[用于两种关闭模式下正确关闭遮罩]
                if ($("#floatDivTemp").length == 0) {
                    obj.append("<input type='hidden' value='' id='floatDivTemp' />");
                };
                //IE6执行操作
                if (browserVersion == "ie6") {
                    if ($("#floatDivTemp").val() == "") {
                        //改善滚动时层抖动问题					
                        //$("head").append("<style>html{ background-image:url(about:blank); background-attachment:fixed;}</style>");
                        //第一次调用添加遮挡iframe
                        //$("body").append('<iframe id="framezd" scrolling="no" frameborder="0"></iframe>');
                        $("#floatDivTemp").val(opt.coverDiv);
                    };
                    frame = opt.cover == true ? "zdCover" : "zdDiv";
                };
                //初始把遮罩DIV的ID赋给隐藏容器
                if ($("#floatDivTemp").val() == "") {
                    $("#floatDivTemp").val(opt.coverDiv);
                };
                //取得Z轴最大值
                maxZ = getMaxZindex();
                //判断是否绑定关闭句柄
                if (opt.closeHandler != '') {
                    $("#" + opt.closeHandler).click(function () {
                        closeEvent();
                    });
                };
                selfWidth = opt.width == "" ? obj.width() : opt.width; //宽度判断
                selfHeight = opt.height == "" ? obj.height() : opt.height; //高度判断
                //层初始化
                obj.width(selfWidth).height(selfHeight).show();

                //判断是否随滚动条滚动
                if (opt.ifScroll) {
                    //层定位方式
                    if (browserVersion == "ie6") {
                        obj.css("position", "absolute");
                    }
                    else {
                        obj.css({ "position": "fixed" });
                    };
                } else {
                    obj.css({ "position": "absolute" });
                };
                //非滚动模式隐藏滚动条
                if (!opt.ifScroll && !opt.scrollBar) {
                    $("html").css("overflow-y", "hidden");
                };
                //有遮罩层执行
                if (opt.cover) {
                    //生成遮罩层[一次]
                    if ($("#" + opt.coverDiv).length == 0) {
                        $("body").append("<div id=" + opt.coverDiv + "></div>");
                    };
                    coverFix();
                }
                //没有遮罩层且为IE6执行[针对IE6 select Bug]
                else {
                    if (browserVersion == "ie6") {
                        $("#framezd").width(selfWidth).height(selfHeight).css({ "position": "absolute", "left": "0px", "top": "0px", "z-index": maxZ + 1, "opacity": 0, "filter": "alpha(opacity=0)" }).show();
                        frame = "zdDiv";
                    };
                };
                keepfix(opt.level, opt.vertical);
            };

            //遮罩定位方法
            var coverFix = function () {
                var doc = document.compatMode ? document.documentElement : document.body, //判断浏览器渲染模式
				scrollWidth = doc.scrollWidth, //文档滚动宽度
				scrollHeight = doc.scrollHeight; //文档滚动高度
                //遮罩层操作
                $("#" + opt.coverDiv).width(scrollWidth).height(scrollHeight).css({ "position": "absolute", "left": "0px", "top": "0px", "display": "none", "background": opt.coverColor, "opacity": opt.fade / 100, "filter": "alpha(opacity=" + opt.fade + ")", "z-index": maxZ - 2 }).show();
                //解决IE 6 select BUG,你懂的
                if (browserVersion == "ie6") {
                    $("#framezd").width(scrollWidth).height(scrollHeight).css({ "position": "absolute", "left": "0px", "top": "0px", "opacity": 0, "filter": "alpha(opacity=0)", "z-index": maxZ - 1 }).show();
                    frame = "zdCover";
                };
            };

            //层定位方法
            var keepfix = function (level, vertical) {
                var winWidth = $(window).width(),
				winHeight = $(window).height(),
				scrollLeft = $(window).scrollLeft(),
				scrollTop = $(window).scrollTop(),
				_left_l = opt.space, //水平左
				_left_c = (winWidth - selfWidth) / 2,
				_left_r = winWidth - selfWidth - opt.space, //水平右
				_top_t = opt.space, //垂直上
				_top_m = (winHeight - selfHeight) / 2,
				_top_b = winHeight - selfHeight - opt.space; //垂直下
                _left = level == "left" ? _left_l : (level == "center" ? _left_c : _left_r); //判断水平位置排列
                _top = vertical == "top" ? _top_t : (vertical == "middle" ? _top_m : _top_b); //判断垂直位置排列
                //IE6坑爹模式
                if (browserVersion == "ie6") {
                    _left += scrollLeft;
                    _top += scrollTop;
                    //无遮罩模式[针对IE6 select Bug]
                    if (frame == "zdDiv") {
                        $("#framezd").css({
                            "z-index": maxZ + 1,
                            "left": _left + "px",
                            "top": _top + "px"
                        });
                    };
                };
                //正常模式
                obj.css({
                    "z-index": maxZ + 3,
                    "left": _left + "px",
                    "top": _top + "px"
                });
            };

            //拖拽
            var dragEvent = function () {
                var dragHandler = opt.dragHandler == "self" ? obj : obj.find(opt.dragHandler),
					_move = false,
					posX = 0, posY = 0, x = 0, y = 0;
                //监听鼠标按下
                dragHandler.mousedown(function (e) {
                    posX = e.pageX - parseInt(obj.css("left"));
                    posY = e.pageY - parseInt(obj.css("top"));
                    _move = true;
                });
                if (opt.dragHandler != "self") {
                    dragHandler.css("cursor", "move");
                };
                //监听鼠标拖动
                $(document).mousemove(function (ev) {
                    if (_move) {
                        x = ev.pageX - posX;
                        y = ev.pageY - posY;
                        dragFlag = true;
                        //拖拽区域限制
                        if (opt.dragLimit) {
                            x = x < 0 ? 0 : x;
                            x = x > $(window).width() - obj.width() ? $(window).width() - obj.width() : x;
                            y = y < 0 ? 0 : y;
                            y = y > $(document).height() - obj.height() ? $(document).height() - obj.height() : y;
                            //锁屏情况拖拽区域限制在当前视口
                            if (!opt.ifScroll && !opt.scrollBar) {
                                y = y > $(window).height() + $(window).scrollTop() - obj.height() ? $(window).height() + $(window).scrollTop() - obj.height() : y;
                            };
                        };
                        obj.css({
                            "left": x,
                            "top": y
                        });
                        //阻止默认动作
                        ev.preventDefault();
                    };
                }).mouseup(function () {
                    if (_move) {
                        dragX = x; //赋给全局变量
                        dragY = y; //赋给全局变量
                        _move = false;
                    };
                });
            };

            //关闭事件
            var closeEvent = function () {
                obj.css("z-index", 0).hide();
                if ($("#" + $("#floatDivTemp").val()).length != 0) {
                    $("#" + $("#floatDivTemp").val()).css("z-index", 0).hide();
                };
                if ($("#framezd").length != 0) {
                    $("#framezd").css("z-index", 0).hide();
                };
                //非滚动模式还原
                if (!opt.ifScroll && !opt.scrollBar) {
                    $("html").css("overflow-y", "");
                };
            };

            //返回文档Z轴上最大的值
            var getMaxZindex = function () {
                var maxIndex = 0;
                $("*").each(function () {
                    var thisIndex = $(this).css("z-index");
                    maxIndex = maxIndex < parseInt(thisIndex) ? parseInt(thisIndex) : maxIndex;
                });
                return maxIndex;
            };

            //开关
            if (opt.onOff) {
                //检测IE6
                if (navigator.userAgent.indexOf("MSIE 6.0") != -1) {
                    browserVersion = "ie6";
                };
                //IE6且为滚动模式 监听窗口滚动
                if (browserVersion == "ie6" && opt.ifScroll) {
                    $(window).bind("scroll", function () {
                        keepfix(opt.level, opt.vertical);
                    });
                };
                //监听窗口改变
                $(window).bind("resize", function () {
                    keepfix(opt.level, opt.vertical);
                });
                //ESC键关闭
                $(document).bind("keyup", function (e) {
                    //var key=e.keyCode ? e.keyCode : e.which;
                    if (e.which == 27 && obj.is(":visible")) {
                        closeEvent();
                    };
                });
                //判断是否绑定拖拽
                if (obj.find(opt.dragHandler).length != 0 || opt.dragHandler == "self") {
                    //IE6下不支持滚动模式下拖拽！
                    if (browserVersion == "ie6") {
                        if (!opt.ifScroll) {
                            dragEvent();
                        };
                    } else {
                        dragEvent();
                    };
                };
                //开始初始化
                init();
            } else {
                closeEvent();
            };
        }
    });
})(jQuery);