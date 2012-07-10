/**
 * 
 */
package com.taobao.exchange.secondhand;

import com.taobao.exchange.util.AppClientException;

/**
 * 操作结果
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
	
	private String iid;
	private String created;
	private String detail_url;
	private AppClientException error;
	
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
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
	public AppClientException getError() {
		return error;
	}
	public void setError(AppClientException error) {
		this.error = error;
	}

}
