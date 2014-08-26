var $$ = CAGE.util.dom;
var Template = CAGE.util.template;

function Post(postObj) {
	this.title = postObj.title;
	this.author = postObj.author;
	this.content = postObj.content;
	this.id = postObj.id;
	this.password = postObj.password;
	
	this.el = null;
	this.spring = null; 
	
	this.isModifing = false;
}

function App() {
	this.template = null;
	this.posts = [];
	this.animateDirection = 'down';
	this.currentPage = 1; // initPost의 콜백을 통해 항상 업데이트 되어야 하는 값
	this.totalPage = 1;  // initPost의 콜백을 통해 항상 업데이트 되어야 하는 값
	
	document.querySelector(".next").addEventListener("click",(function(e){
		if(this.currentPage < this.totalPage) {
			this.initPost({
				direction: 'left',
				page: this.currentPage+1
			});		
		} else {
			this.initPost({
				direction: 'down',
				page: this.currentPage
			});			
		}			
	}).bind(this),false);

	document.querySelector(".prev").addEventListener("click",(function(e){
		if(this.currentPage <= 1) {
			this.initPost({
				direction: 'down',
				page: this.currentPage
			});			
		} else {
			this.initPost({
				direction: 'right',
				page: this.currentPage-1
			});	
		}				
	}).bind(this),false);
	
	// + 에 글쓰는 팝업이 나타나게 만들기
	document.querySelector(".plus").addEventListener("click",(function(e){
		alert("글 쓸래여?");		
	}).bind(this),false);
		
	this.initPost({
		direction: this.animateDirection,
		page: 1
	});		

}

App.prototype._getTemplate = function() {
	
	if (this.template === null) {
		this.template = Template.get({"url":"/api/templates/postcard.html"});
	}
	
	return this.template;
}

App.prototype._toggleModify = function(post) {
	if(post.isModifing == false) {
		// 일반 엘리먼트 다 블라인드 먹이고
		//// 제목에 블라인트 클래스 추가 
		$$.addClass(post.el.querySelector("h2"),"blind");
		$$.addClass(post.el.querySelector("p"),"blind");	
		$$.addClass(post.el.querySelector(".modify"),"blind");																	
		$$.removeClass(post.el.querySelector(".cancel"),"blind");
		$$.removeClass(post.el.querySelector(".save"),"blind");
		$$.removeClass(post.el.querySelector("input[type=text]"),"blind");
		$$.removeClass(post.el.querySelector("textarea"),"blind");
		
		// 인풋종류 블라인드 빼기 	
	} else {
		$$.removeClass(post.el.querySelector("h2"),"blind");
		$$.removeClass(post.el.querySelector("p"),"blind");									
		$$.removeClass(post.el.querySelector(".modify"),"blind");																	

		$$.addClass(post.el.querySelector(".cancel"),"blind");
		$$.addClass(post.el.querySelector(".save"),"blind");
		$$.addClass(post.el.querySelector("input[type=text]"),"blind");
		$$.addClass(post.el.querySelector("textarea"),"blind");
		
		// 블라인드 된 컨텐츠 내용도 원래대로 돌리기 
		post.el.querySelector("input[type=text]").value = post.el.querySelector("h2").innerHTML;
		post.el.querySelector("textarea").value = post.el.querySelector("p").innerHTML;
		
	}
	post.isModifing = !post.isModifing;
};

App.prototype.getPost = function(id) {
	for (var i in this.posts) {
		if(this.posts[i].id === parseInt(id))
			return this.posts[i];
	}
};

App.prototype.addPost = function(postObj) {
	var post = new Post(postObj);
	this.posts.push(post);
	this.template = this._getTemplate();
	var container = document.querySelector(".post-list");
	container.insertAdjacentHTML("afterbegin",Template.compiler(post, this.template));
	post.el = document.querySelector(".post-card");
	
	//// 포스트에 달려있는 버튼들에 이벤트 달기 
	// 수정버튼
	post.el.querySelector(".modify").addEventListener("click",(function(e){
		var pw = prompt("수정하거나 지우시려면 암호를 적어주세요");
		if (pw != null) {
		//debugger;
			var currentPost = this.getPost(e.target.dataset.id);
			if(currentPost.password===pw){
				this._toggleModify(currentPost);
			} else {
				alert("암호틀림요");							
			}
		}					
	}).bind(this),false);
	
	// 수정취소 버튼
	post.el.querySelector(".cancel").addEventListener("click",(function(e){
		var currentPost = this.getPost(e.target.dataset.id);
		this._toggleModify(currentPost);					
	}).bind(this),false);
	
	// 수정적용 버튼 
	post.el.querySelector(".save").addEventListener("click",(function(e){
		var currentPost = this.getPost(e.target.dataset.id);
		var newTitle = currentPost.el.querySelector("input[type=text]").value;
		var newContent = currentPost.el.querySelector("textarea").value;
		debugger;
		// 걍 아파치에서는 PUT 이 안먹어서 일단 POST로 
		CAGE.ajax.PUT({ // 원래 PUT 이다.
 			url: "/api/list", // update하면 업데이트된 포스트 정보를 보내온다.
			data: "id="+currentPost.id+"&content="+newContent+"&title="+newTitle,
			success: (function(responseObj){
				currentPost.title = responseObj.post.title;
				currentPost.content = responseObj.post.content;
				// 제목 UI적용.
				currentPost.el.querySelector(".title").innerHTML = currentPost.title;
				// 본문 UI적용.
				currentPost.el.querySelector(".content").innerHTML = currentPost.content;
				this._toggleModify(currentPost);
			}).bind(this),
			failure: function() {
				console.warn("post update failure");
			}
		});
	}).bind(this),false);	

	var springSystem = new rebound.SpringSystem();
	var spring = springSystem.createSpring(20, 9);
	spring.addListener({
		onSpringUpdate: (function(spring) {
			var val = spring.getCurrentValue();
			val = rebound.MathUtil.mapValueInRange(val, 0, 1, 1, 0.5);
			//debugger;
			this._springHandler(post.el, val);
		}).bind(this)
	});	
	post.spring = spring;
}

App.prototype.initPost = function(option) {
	this.animateDirection = option.direction;
	var page = option.page;
	var container = document.querySelector(".post-list");	
	container.innerHTML = '';
	this.posts = [];
	CAGE.ajax.GET({
		url: "./api/list?page=" + page, // 시작 페이지 넘버를 함께 "api/list?page="+page 이라고 보내야할듯
		callback: function(responseText){
			var response = JSON.parse(responseText);
			var raw_posts = response.posts;
			
			// 글 하나하나를 모델이 추가
			for (var i in raw_posts) {
				this.addPost(raw_posts[i]);
			}
			
			// 현재 페이지와 총 페이지 번호를 업데이트
			this.currentPage = response.page;
			this.totalPage = response.totalPage;
			
			// 애니메이션을 넣어서 순차적으로 나타나게하기
			setTimeout((function(){
				this.posts[4].spring.setEndValue(2);
				this.posts[4].el.setAttribute("style", "opacity: 1");
				setTimeout((function(){
					this.posts[3].spring.setEndValue(2);
					this.posts[3].el.setAttribute("style", "opacity: 1");
					setTimeout((function(){
						this.posts[2].spring.setEndValue(2);
						this.posts[2].el.setAttribute("style", "opacity: 1");
						setTimeout((function(){
							this.posts[1].spring.setEndValue(2);
							this.posts[1].el.setAttribute("style", "opacity: 1");
							setTimeout((function(){
								this.posts[0].spring.setEndValue(2);
								this.posts[0].el.setAttribute("style", "opacity: 1");
							}).bind(this),70);												
						}).bind(this),70);	
					}).bind(this),70);
				}).bind(this),70);					
			}).bind(this),70);
		}.bind(this)
	});
};

// 뿅 나타나게 스타일을 컨트롤 해주는 스프링 핸들러
App.prototype._springHandler = function(el, val){
	if(this.animateDirection === 'right') {
		el.style.webkitTransform =
		el.style.transform  = 'translate3d(' + -100*(val) + 'px,0,0)';
	} else if(this.animateDirection === 'left') {
		el.style.webkitTransform =
		el.style.transform  = 'translate3d(' + 100*(val) + 'px,0,0)';
	} else if(this.animateDirection === 'up') {
		el.style.webkitTransform =
		el.style.transform  = 'translate3d(0,' + 100*(val) + 'px,0)';
	} else if(this.animateDirection === 'down') {
		el.style.webkitTransform =
		el.style.transform  = 'translate3d(0,' + -100*(val) + 'px,0)';
	}
};