/**
 * 
 */
package com.taobao.exchange.app;

/**
 * 应用请求附件定义，例如api需要上传图片，那么图片用这个对象来承载
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-9
 *
 */
public class RequestAttachment implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4229264294463354148L;
	
	String name;//附件的名称
	byte[] content;//附件的二进制内容
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	

}
