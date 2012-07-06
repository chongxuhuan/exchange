/**
 * 
 */
package com.taobao.exchange.secondhand;

import java.util.List;



/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4 обнГ5:39:30
 *
 */
public interface ISecondhandManager {
	
	OperationResult sell(Secondhand secondhand);
	
	OperationResult update(Secondhand secondhand);
	
	OperationResult delete(String id);
	
	List<Secondhand> getSecondhandsByUser(String userId);
	
	Secondhand getSecondhandById(String id);

}
