/**
 * 
 */
package com.taobao.exchange.secondhand;

/**
 * 二手管理接口操作结果
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class OperationResult implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 197705969719026714L;
	
	private String item_id;//二手商品id
	private String created;//创建时间
	private String detail_url;//具体的商品地址
	
	public void loadFromString(String jsonStr)
	{
		if (jsonStr == null)
			return;
		
		if (jsonStr.indexOf("\"created\":") > 0)
		{	
			created = getContentFromJsonStr(jsonStr,"\"created\":");
		}
		
		if (jsonStr.indexOf("\"item_id\":") > 0)
		{	
			item_id = getContentFromJsonStr(jsonStr,"\"item_id\":");
		}
		
		if (jsonStr.indexOf("\"num_iid\":") > 0)
		{	
			item_id = getContentFromJsonStr(jsonStr,"\"num_iid\":");
		}

		if (jsonStr.indexOf("\"detail_url\":") > 0)
		{
			detail_url = getContentFromJsonStr(jsonStr,"\"detail_url\":");
		}
		
		System.out.println(jsonStr);
	}
	
	String getContentFromJsonStr(String json,String name)
	{
		String c = json.substring(json.indexOf(name) + name.length());
		
		if (c.indexOf(",") > 0)
			c = c.substring(0,c.indexOf(","));
		else
			c = c.substring(0,c.indexOf("}"));
		
		if (c.indexOf("\"") >= 0)
			c = c.substring(1,c.length()-1);
		
		return c;
	}
	
	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getDetail_url() {
		return detail_url;
	}
	public void setDetail_url(String detail_url) {
		this.detail_url = detail_url;
	}

}
