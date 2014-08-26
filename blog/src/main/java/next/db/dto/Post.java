package next.db.dto;

public class Post {
	
	private double id;
	private String author;
	private String title;
	private String content;
	private String password;
	
	public Post() {
	}

//	public Post(double id, String author, String title, String content) {
//		this.id = id;
//		this.title = title;
//		this.content = content;
//		this.author = author;
//	}
	
	public Post(double id, String content, String title) {
		this.id = id;
		this.content = content;
		this.title = title;
	}


//	public Post(String author, String title, String content) {
//		this.title = title;
//		this.author = author;
//		this.content = content;
//	}
//	
	
	


	

	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
