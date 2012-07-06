/**
 * 
 */
package com.taobao.exchange.relation;

import java.util.List;


/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4 обнГ6:12:13
 *
 */
public interface IRelationManager {

	List<User> getFriendsByUser(User user);
	
}
