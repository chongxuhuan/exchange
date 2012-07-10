/**
 * 
 */
package com.taobao.exchange.dig;

import java.util.List;

import com.taobao.exchange.secondhand.Secondhand;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public interface ISecondhandDigger<T extends IDigCondition> {
	
	List<Secondhand> dig(T condition);

}
