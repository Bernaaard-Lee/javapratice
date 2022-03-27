let index={
	init:function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		$("#btn-update").on("click", ()=>{  // function(){}, ()=>{}this를 바인딩하기 위해
			this.update();
		});
	},
	update:function() {
		// alert('user의 save함수 호출됨');
		let data = {
			id      : $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email   : $("#email").val()
		};

		$.ajax({
			type       : "PUT",
			url        : "/user",
			data       : JSON.stringify(data),  // http body데이터
			contentType: "application/json;charset=UTF-8",  // body데이터가 어떤 타입인지(MIME)
			dataType   : "json"   // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모두 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function (resp) {
			alert("회원정보가 수정되었습니다.");
			console.log(resp)
			location.href = "/";
		}).fail(function (error) {
			alert(JSON.stringify(error));
		});  // ajax통신을 이용해 3개의 데이터를 json으로 변경하여 insert 요청
	},

	save:function(){
		// alert('user의 save함수 호출됨');
		let data={
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		// console.log(data);

		// ajax호출시 default가 비동기 호출
		$.ajax({
			type:"POST",
			url:"/auth/joinProc",
			data:JSON.stringify(data),  // http body데이터
			contentType:"application/json;charset=UTF-8",  // body데이터가 어떤 타입인지(MIME)
			dataType:"json"   // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모두 문자열(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			console.log(resp)
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});  // ajax통신을 이용해 3개의 데이터를 json으로 변경하여 insert 요청
	},
}
index.init();