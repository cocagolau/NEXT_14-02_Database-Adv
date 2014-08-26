package next.db.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Cache {

	private int size;
	private int count;
	private HashMap<Integer, String> table;
	private List<Integer> list;
	private int missCount;
	private int hitCount;
	

	public Cache(int size) {
		this.size = size;
		this.count = 0;
		this.missCount = 0;
		this.hitCount = 0;
		this.table = new HashMap<Integer, String>();
		this.list = new LinkedList<Integer>();
		
	}

	public boolean has(int key) {

		return this.table.containsKey(key);
	}
	
	public String get(int key) {
		
		return this.table.get(key);
	}
	
	public int size() {
		
		return this.count;
	}


	// list에서만 사용 
	public void addFirst(int key) {
		
		// key를 제거
		int index = this.list.indexOf(key);
		this.list.remove(index);
		
		this.list.add(0, key);
		
		this.hitCount++;
//		return size();
		
	}
	
	// list와 table 모두
	public void addFirst(int key, String value) {
		// table 크기 확인
		if (++this.count >= this.size) {
			deleteLastKV();
			this.missCount++;
		}
		
		
		// data 처리
		this.table.put(key, value);
		
		//list 처음에 추가
		this.list.add(0, key);
		
		
		
		
//		return size();
		
	}

	
	private void deleteLastKV() {
		--this.count;
		
		// 마지막 key를 제거
		int key = this.list.get(this.size-2);
		this.list.remove(this.size-2);
		
		// 마지막 data 삭제
		this.table.remove(key);
		
	}

	public int getMissCount() {
		return missCount;
	}

	public int getHitCount() {
		return hitCount;
	}
	
	


}
