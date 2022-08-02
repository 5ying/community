$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	//发送AJAX请求之前，将CSRF令牌设置到请求头中
	// var token = $("meta[name='_csrf']").attr("content");
	// var header = $("meta[name='_csrf_header']").attr("content");
	// $(document).ajaxSend(function (e,xhr,optins) {
	// 	xhr.setRequestHeader(header,token);
	// });

	// 获取标题内容
	var titlt = $("#recipient-name").val();
	var conten = $("#message-text").val();
	console.log(titlt);
	console.log(conten);

	//发送异步请求
	$.post(
		 "/community/discuss/add",
			{
				"title":titlt,
				"content":conten
			},
			function (data) {
			console.log(data);
			data = $.parseJSON(data);
			//在提示框显示消息
				$("#hintBody").text(data.msg);

				$("#hintModal").modal("show");
				setTimeout(function(){
					$("#hintModal").modal("hide");
				//	刷新页面
					if (data.code == 0 ){
						// window.location.reload();
					}
				}, 2000);

			}
	);

}