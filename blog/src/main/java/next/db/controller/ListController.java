package next.db.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.db.autility.Constants;
import next.db.dao.BlogDao;
import next.db.dto.Post;
import next.db.framework.controller.MainController;
import next.db.framework.model.Model;
import next.db.framework.resource.Uri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.DB;

@Component("list")
public class ListController extends MainController {
	
	@Autowired private BlogDao blogDao;
	
	protected String defaultValue = "index";
	
	@Override
	protected String GET(HttpServletRequest request, HttpServletResponse response, Model model) {		
//		Uri uri = new Uri(request);
		
		ServletContext context = request.getServletContext();
		DB blogDB = (DB)context.getAttribute("blogDB");
		
		String paramPage = request.getParameter("page");
		String paramQuery = request.getParameter("q");
		int page;
		
		// json /search 결과 출력하기
		if (paramQuery != null) {
			
			
			return null;
		}
		
		// json / list 출력하기
		if (paramPage != null) {
			page = Integer.parseInt(paramPage);
			
			model.put("status", 200);
			model.put("posts", blogDao.findList(blogDB, page, Constants.NUM_OF_POSTS_PER_PAGE));
			model.put("page", page);
			model.put("totalPage", (blogDao.getNumberOfPosts(blogDB)/Constants.NUM_OF_POSTS_PER_PAGE) + 1);
			
			return null;
		}
		
		
		// jsp
		return this.defaultValue;
	}
	

	@Override
	protected String PUT(HttpServletRequest request, HttpServletResponse response, Model model) {
//		Uri uri = new Uri(request);
		ServletContext context = request.getServletContext();
		DB blogDB = (DB)context.getAttribute("blogDB");
		Post post = null;
		int status = 500;
		
		try {
			post = new Post(Double.parseDouble(request.getParameter("id")), request.getParameter("content"), request.getParameter("title"));
			post = blogDao.update(blogDB, post);			
			status = 200;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			model.put("status", status);
			model.put("post", post);
			
		}
		
		// 수정 결과를 전달
		model.put("result", "boolean");
		
		return null;
	}


	@Override
	protected String POST(HttpServletRequest request, HttpServletResponse response, Model model) {
		Uri uri = new Uri(request);
		
//		Post newPost = new Post(request.getParameter("author"), request.getParameter("title"), request.getParameter("content"));
		
		// 마지막 페이지 번호 찾아옴
		
//		blogDao.createPost(newPost);
		/*
		// db에서 정보 저장
		// return json {id, author, writing}
		JSONObject createObject = new JSONObject();
		createObject.put("author", request.getParameter("author"));
		createObject.put("writing", request.getParameter("content"));
		
		JSONObject jo = blogDao.create(createObject);
		
	*/
	


		
		// 수정 결과를 전달
		model.put("result", "boolean");
		
		return null;
	}

	@Override
	protected String DELETE(HttpServletRequest request, HttpServletResponse response, Model model) {
		Uri uri = new Uri(request);
		
		boolean result = false;
		try {
			int postNum = Integer.parseInt(uri.get(1));
			
			// db에 접근하여 게시물 삭제
			// remove content
			// return none
			
//			String removeId = jo.getString("wId");
//			cont.remove(coll, removeId);
			
			
			model.put("result", result);
			
		} catch (Exception e) {
			
			// do-nothing
			
		}
		
		// 수정 결과를 전달
		model.put("result", result);
		
		return null;
	}
	
	

}
