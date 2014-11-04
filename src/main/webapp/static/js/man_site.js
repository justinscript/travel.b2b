// JavaScript Document

//查看专线管理
function Show_Column(City,obj) {
		if ($(obj).attr("alt") == "cook") {
			$.ajax({
				url: "ColumnList.asp",
				type: "get",
				dataType: "html",
				data: "act=Chufa_column",
				success: function (msg) {
					$(obj).parent().parent().after(msg);
					$(obj).attr("alt", "close").html("关闭专线");
				},
		beforeSend: function () {
			//$.dialog.tips('数据正在努力加载中，请耐心等待...', 600, 'loading.gif');
		},
		complete: function () {
			//$.dialog.tips('加载完毕', 0.1, 'tips.gif', function () { });
		},
		error: function (err) { showusers(err); }
			});
		} else {
			$(obj).parent().parent().next("tr").remove();
			$(obj).attr("alt", "cook").html("查看专线");
			return false;
		}
}

function NewColumn(ToId, sId, obj)
{
	var loadi = layer.load('加载中…');
	$.ajax({
		type: "get",
		url: "columnadd.htm",
		dataType:"html",
		data:"toId="+ToId+"&sId="+sId,
		cache: false,
		success: function (html) {
			$.layer({
				type : 1,
				title : "专线管理",
				offset:['100px' , ''],
				border : [10, 0.3, '#000'],
				area : ['800px','450px'],
				page : {html :html}
			});
		},
		complete: function () {
			layer.close(loadi);
		},
		error: function (err) {  }
	});
}


function EditColumn(zId, obj)
{
	var loadi = layer.load('加载中…');
	$.ajax({
		type: "get",
		url: "columnedit.htm",
		dataType:"html",
		data:"zId="+zId,
		cache: false,
		success: function (html) {
			$.layer({
				type : 1,
				title : "专线管理",
				offset:['100px' , ''],
				border : [10, 0.3, '#000'],
				area : ['800px','450px'],
				page : {html :html}
			});
		},
		complete: function () {
			layer.close(loadi);
		},
		error: function (err) {  }
	});
}

//删除站点
function delsite(obj,id)
{
	layer.confirm('您确定要删除此数据？',function(index){
		$.ajax({
			type:"get",
			url:"sitedel.htm",
			data:"id="+id,
			datatype:"json",
			cache:false,
			headers: { 
                Accept : "application/json; charset=utf-8",
                "Content-Type": "application/x-www-form-urlencoded; charset=utf-8"
            },
			success:function(data){
				if(data.code==0){
					layer.msg(data.message,1,1);
					$(obj).parent().parent().parent().remove();
				}else{
					layer.msg(data.message,1);
				}
			}
		});
	});
}

//删除专线
function delcolumn(id){
	layer.confirm('您确定要删除此数据？',function(index){
		$.ajax({
			type:"get",
			url:"columndel.htm",
			data:"id="+id,
			datatype:"json",
			cache:false,
			success:function(data){
				var msg=eval('('+data+')');
				if(msg.code==1){
					layer.msg(msg.message,1,1);
					$("#c"+id).remove();
					layer.closeAll();
				}else{
					layer.msg(msg.message,1);
				}
			}
		});
	});
}
