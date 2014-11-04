//约定通信action
//CLIENT_REGIST(1),// Client注册服务
//CLIENT_REGIST_FAILED(2),// Client注册服务失败,可能未登录
//CLIENT_CLOSED(3),// Client取消注册服务,客户端主动关闭连接
//CLIENT_RETURN_RESULT(4),// Client处理完后返回结果
//CLIENT_SEND_MESSAGE(5)Client主动请求数据,Client发送消息
//CLIENT_HEARTBEAT(6),// Client端心跳检测
//SERVER_SEND_MESSAGE(7),// Server发送消息
//SERVER_CLOSED(8),// Server主动关闭连接
//SERVER_HEARTBEAT(9);// Server端心跳检测
//ERROR_FORMAT_MESSAGE(10);// 错误数据格式
//发送数据格式json
//{"action":6,"type":"client_heartbeat","data":"$zuobian$"}

//接收数据格式json
//(code类型SUCCESS(0)成功, ERROR(1)失败, NEED_LOGIN(2)未登录, SUBMITED(3)重复提交, FORBIDDEN(4)重定向,HEARTBEAT(6)心跳检测,ERROR_FORMAT(10)错误数据格式)
//{"code":5,"message":"","data":"[{"type":"0"},{d}]"}
	        
var host = location.host;

var heartbeat_timer = 1;//缓冲时间
var last_health = -1; 
var health_timeout = 3000*10;
var push_url="ws://" + host + "/push/msg.htm";
var status_code = "$zuobian$";
var J = $;

var dataParam = {
	  
};
var request = {   
      action: '6',  
      type: 'client_heartbeat', 
      data:status_code  //data可以是对象dataParam
};  
var request_encoded = J.toJSON(request);

$(function(){
    ws = ws_conn(push_url); 
});

function keepalive(ws){
    var time = new Date();
    if(last_health != -1 && (time.getTime() - last_health > health_timeout)){
            //此时即可以认为连接断开，可设置重连或者关闭
    	console.log("client closed webSocket!原因:超时!");
        ws.close();
    }
    else{
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
    var ws = new WebSocket(to_url);
  	ws.onopen=function(){
        heartbeat_timer = setInterval(function(){keepalive(ws);}, 2000*10);
    };
    ws.onerror=function(){
        clearInterval(heartbeat_timer);
        console.log("连接异常,请重新Socket请求");
    };
    ws.onclose=function(){
        clearInterval(heartbeat_timer);
        console.log("连接断开");
    };
    ws.onmessage=function(msg){
        var msgData = eval('('+msg.data+')');
        //失败
        if(msgData.code == "1"){
        	console.log(msgData);
            return;
        }
        //Server端心跳检测
        if(msgData.code == "6" && msgData.data == status_code){
        	console.log(msgData);
        	var time = new Date();
            last_health = time.getTime();
            return;
        }
        //未登录
        if(msgData.code == "2"){
        	keepalive(ws);
        	return;
        }
        //成功
        if(msgData.code == "0"){ 
        	var time = new Date();
            last_health = time.getTime();
        	msgformat(msgData);
    	}
    };
    return ws;
}
            
var typeMap = {  
    "newOrder" : "订单管理",  
    "newIntegral" : "积分"  
};          					
            					
function msgformat(json){
	var dt=json.data;
		
	//判断返回值不是 json 格式
	if (dt.match != undefined && dt.match != null && !dt.match("^\{(.+:.+,*){1,}\}$")){
		//普通字符串处理
		console.log(dt);
	}
	else{
		//通过这种方法可将字符串转换为对象
		var msgtotal=dt.msgTotal; //消息总数
		$(".msg").find("span:eq(0)").html('['+msgtotal+']');
		
		var msgMap=dt.msgMap; //订单发生变化的数据最新五条
		var str='';
		for(var key in msgMap) {  
            var message = msgMap[key];
            var type = typeMap[key];
            str+='<ul><li class="tit"><b>'+type+'</b> 有<b>'+message.total+'</b>个新消息</li>';
            $.each(message.list,function(i,item){
            	str+='<li><a href="/push/opt/'+item.id+'.htm?returnurl='+item.content+'">&bull; '+item.title+'</a></li>';
            	
            });
            str+='</ul>';
        } 
		$(".msg").find("div").html(str);
	}
}
            			