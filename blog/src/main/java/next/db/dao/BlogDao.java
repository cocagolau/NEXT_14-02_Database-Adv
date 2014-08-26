package next.db.dao;

import java.beans.Expression;
import java.beans.Statement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import next.db.dto.Post;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository("blogDao")
public class BlogDao {
	

//	private MongoClient mongoClient;
	private static final String COLLECTION = "posts";
//	private static final String COLLECTION = "content";
	
	// create
	/*
	public JSONObject createPost(Post post) {
		MongoClient mongoClient = null;
		DBCollection coll = null;
		BasicDBObject updateIdDoc = null;
		try {
			mongoClient = new MongoClient(BlogDao.IP);
			DB db = mongoClient.getDB(BlogDao.DB);
			coll = db.getCollection(BlogDao.COLLECTION);
			
			BasicDBObject createData = new BasicDBObject()
					.append("author", post.getAuthor())
					.append("content", post.getContent());
			coll.insert(createData);

			String insertedId = createData.getString("_id");
			
			updateIdDoc = new BasicDBObject("wId", insertedId);
			BasicDBObject updateIdQuerySet = new BasicDBObject("$set", updateIdDoc);
			BasicDBObject targetData = (BasicDBObject) coll.findOne(createData);
			coll.update(targetData, updateIdQuerySet);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		return toJSON(coll.findOne(updateIdDoc));
	}
	*/
	
	
	// insert
	
	
	// select
	public List<Post> findList(DB blogDB, int offset, int limit) {
		DBCollection posts = null;
		DBCursor cursor = null;
		List<Post> result = null;
		
		// ensure complete consistency
		blogDB.requestStart();
		try {
			blogDB.requestEnsureConnection();
			posts = blogDB.getCollection(BlogDao.COLLECTION);
			
			cursor = posts.find().skip(offset-1).limit(limit);
			
			result = new ArrayList<Post>();
			while(cursor.hasNext()) {
				result.add(toPost(cursor.next()));
			}

		} finally {
			blogDB.requestDone();
			
		}
		
		return result;
	}
	

	public long getNumberOfPosts(DB blogDB) {
		DBCollection posts = null;
		long result = 0;
		
		// ensure complete consistency
		blogDB.requestStart();
		try {
			blogDB.requestEnsureConnection();
			posts = blogDB.getCollection(BlogDao.COLLECTION);
			
			result = posts.count();

		} finally {
			blogDB.requestDone();
			
		}
		
		return result;
	}
	

	public Post findPostById(DB blogDB, double id) {
		DBCollection posts = null;
		Post result = null;
		
		// ensure complete consistency
		blogDB.requestStart();
		try {
			blogDB.requestEnsureConnection();
			posts = blogDB.getCollection(BlogDao.COLLECTION);

			BasicDBObject query = new BasicDBObject("id", id);
			result = toPost(posts.findOne(query));
			

		} finally {
			blogDB.requestDone();
			
		}
		
		return result;
	}
	
	// update	
	public Post update(DB blogDB, Post post) {
		DBCollection posts = null;
		Post updatedPost = null;
		
		blogDB.requestStart();
		try {
			blogDB.requestEnsureConnection();
			posts = blogDB.getCollection(BlogDao.COLLECTION);
			
			BasicDBObject targetPost = new BasicDBObject("id", post.getId());
			
			posts.update(targetPost, toQuery("$set", post));
			updatedPost = findPostById(blogDB, post.getId());

		} finally {
			blogDB.requestDone();
			
		}

		return updatedPost;
		
	}



	private Post toPost(DBObject raw) {
		
		Set<String> keys = raw.keySet();
		Iterator<String> ir = keys.iterator();
		Post post = new Post();
		
		while (ir.hasNext()) {
			
			String key = ir.next();
			if ("_id".equals(key)) continue;
			String methodName = key.substring(0,1).toUpperCase() + key.substring(1);
		
			Statement stmt = new Statement(post, "set" + methodName, new Object[] { raw.get(key) });
			try {
				stmt.execute();
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}

		return post;
	}
	
	private BasicDBObject toQuery (String query, Post post) {
		
		BasicDBObject dbObj = new BasicDBObject();
		
		Method[] methods = post.getClass().getMethods();
		for (int i=0; i<methods.length; ++i) {
			Method m = methods[i];
			String methodName = m.getName();
			if ("get".equals(methodName.substring(0, 3)) && !"getClass".equals(methodName)) {
				
				String convertedMethodName = methodName.substring(3);
				convertedMethodName = convertedMethodName.substring(0,1).toLowerCase() + convertedMethodName.substring(1);
				Expression expr = new Expression(post, methodName, new Object[0]);
				try {
					expr.execute();
					Object value = expr.getValue();
					
					if (value != null) {
						dbObj.append(convertedMethodName, value);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		
		BasicDBObject queryObj = new BasicDBObject(query, dbObj);
		
		return queryObj;
	}


	
	
	// delete
	
	/*
	
	public JSONObject create(JSONObject createObject) {
		MongoClient mongoClient = null;
		DBCollection coll = null;
		BasicDBObject updateIdDoc = null;
		try {
			mongoClient = new MongoClient(BlogDao.IP);
			DB db = mongoClient.getDB(BlogDao.DB);
			coll = db.getCollection(BlogDao.COLLECTION);
			
			BasicDBObject createData = new BasicDBObject().append("author",
					createObject.getString("author")).append("writing",
					createObject.getString("writing"));
			coll.insert(createData);

			String insertedId = createData.getString("_id");
			
			updateIdDoc = new BasicDBObject("wId", insertedId);
			BasicDBObject updateIdQuerySet = new BasicDBObject("$set", updateIdDoc);
			BasicDBObject targetData = (BasicDBObject) coll.findOne(createData);
			coll.update(targetData, updateIdQuerySet);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		return toJSON(coll.findOne(updateIdDoc));
	}
	
	*/

//	public JSONObject update(JSONObject updateObject) {
//		MongoClient mongoClient = null;
//		DBCollection coll = null;
//		BasicDBObject id = null;
//		try {
//			mongoClient = new MongoClient(BlogDao.IP);
//			DB db = mongoClient.getDB(BlogDao.DB);
//			coll = db.getCollection(BlogDao.COLLECTION);
//			
//			BasicDBObject updateData = new BasicDBObject("writing",
//					updateObject.getString("writing"));
//			BasicDBObject updateQuerySet = new BasicDBObject("$set", updateData);
//			
//			id = new BasicDBObject("wId", updateObject.getString("wId"));
//			DBObject targetData = coll.findOne(id);
//			coll.update(targetData, updateQuerySet);
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		
//		return toJSON(coll.findOne(id));
//		
//	}
	
//	public JSONObject findList(int start, int num) {
//		MongoClient mongoClient = null;
//		DBCollection coll = null;
//		DBObject searchResult = null;
//		try {
//			mongoClient = new MongoClient(BlogDao.IP);
//			DB db = mongoClient.getDB(BlogDao.DB);
//			coll = db.getCollection(BlogDao.COLLECTION);
//			
////			BasicDBObject dbo = new BasicDBObject("wId", id);
////			searchResult = coll.findOne(dbo);
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		
//		return toJSON(searchResult);
//		
//	}

//	public JSONObject findById(String id) {
//		MongoClient mongoClient = null;
//		DBCollection coll = null;
//		DBObject searchResult = null;
//		try {
//			mongoClient = new MongoClient(BlogDao.IP);
//			DB db = mongoClient.getDB(BlogDao.DB);
//			coll = db.getCollection(BlogDao.COLLECTION);
//			
//			BasicDBObject dbo = new BasicDBObject("wId", id);
//			searchResult = coll.findOne(dbo);
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		
//		return toJSON(searchResult);
//		
//	}
//
//	public JSONArray findByAuthor(String author) {
//		MongoClient mongoClient = null;
//		DBCollection coll = null;
//		JSONArray ja = null;
//		try {
//			mongoClient = new MongoClient(BlogDao.IP);
//			DB db = mongoClient.getDB(BlogDao.DB);
//			coll = db.getCollection(BlogDao.COLLECTION);
//			
//			BasicDBObject dbo = new BasicDBObject("author", author);
//			DBCursor results = coll.find(dbo);
//			
//			ja = new JSONArray();
//			while (results.hasNext()) {
//				DBObject result = results.next();
//				ja.put(toJSON(result));
//			}
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		
//		return ja;
//		
//	}
//
//	public JSONObject toJSON(DBObject dbo) {
//		JSONObject jo = new JSONObject(dbo.toString());
//		return jo;
//	}
//
//	public void remove(String removeId) {
//		MongoClient mongoClient = null;
//		DBCollection coll = null;
//		BasicDBObject removeQuery = null;
//		try {
//			mongoClient = new MongoClient(BlogDao.IP);
//			DB db = mongoClient.getDB(BlogDao.DB);
//			coll = db.getCollection(BlogDao.COLLECTION);
//			
//			removeQuery = new BasicDBObject("wId", removeId);
//			coll.remove(removeQuery);
//			
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//
//	}
}
