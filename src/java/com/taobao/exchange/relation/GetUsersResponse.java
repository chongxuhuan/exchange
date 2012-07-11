/**
 * 
 */
package com.taobao.exchange.relation;

/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 下午6:17:55
 *
 */
public class GetUsersResponse {
	private User[] users;
	private int next_cursor;
	private int previous_cursor;
	private int total_number;
	
	public User[] getUsers() {
		return users;
	}
	public void setUsers(User[] users) {
		this.users = users;
	}
	public int getNext_cursor() {
		return next_cursor;
	}
	public void setNext_cursor(int next_cursor) {
		this.next_cursor = next_cursor;
	}
	public int getPrevious_cursor() {
		return previous_cursor;
	}
	public void setPrevious_cursor(int previous_cursor) {
		this.previous_cursor = previous_cursor;
	}
	public int getTotal_number() {
		return total_number;
	}
	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}

}
