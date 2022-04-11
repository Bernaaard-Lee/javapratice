let index={
	init:function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		$("#btn-delete").on("click", ()=>{
			this.deleteById();
		});
		$("#btn-update").on("click", ()=>{
			this.update();
		});
	},
	update:function() {
		let id = $("#id").val();

		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		}

		$.ajax({
			type       : "PUT",
			url        : "/api/board/"+id,
			data:JSON.stringify(data),  // http body데이터
			contentType:"application/json;charset=UTF-8",  // body데이터가 어떤 타입인지(MIME)
			dataType   : "json"   // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모두 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function (resp) {
			alert("수정되었습니다.");
			console.log(resp)
			location.href = "/";
		}).fail(function (error) {
			alert(JSON.stringify(error));
		});
	},

	deleteById:function() {
		var id = $("#id").text();

		$.ajax({
			type       : "DELETE",
			url        : "/api/board/"+id,
			dataType   : "json"   // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모두 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function (resp) {
			alert("삭제가 완료되었습니다.");
			console.log(resp)
			location.href = "/";
		}).fail(function (error) {
			alert(JSON.stringify(error));
		});
	},

		save:function(){
		let data={
			title:$("#title").val(),
			content:$("#content").val(),
		};

		// ajax호출시 default가 비동기 호출
		$.ajax({
			type:"POST",
			url:"/api/board",
			data:JSON.stringify(data),  // http body데이터
			contentType:"application/json;charset=UTF-8",  // body데이터가 어떤 타입인지(MIME)
			dataType:"json"   // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모두 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			console.log(resp)
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}
index.init();