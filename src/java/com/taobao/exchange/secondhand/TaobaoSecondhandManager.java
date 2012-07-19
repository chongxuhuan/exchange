/**
 * 
 */
package com.taobao.exchange.secondhand;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gson.Gson;
import com.taobao.exchange.app.TopAppClient;
import com.taobao.exchange.dig.SecondhandCondition;
import com.taobao.exchange.util.ServiceException;
import com.taobao.exchange.util.Constants;

/**
 * 二手商品管理接口默认实现
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-10
 *
 */
public class TaobaoSecondhandManager implements ISecondhandManager<TopAppClient> {

	private static final Log logger = LogFactory.getLog(TaobaoSecondhandManager.class);
	
	/**
	 * 二手交易的应用客户端支持
	 */
	TopAppClient appClient;

	public TopAppClient getAppClient() {
		return appClient;
	}

	public void setAppClient(TopAppClient appClient) {
		this.appClient = appClient;
	}
	
	/* 
	 * 获得二手市场类目信息
	 */
	@Override
	public Category[] getSecondhandCategory() throws ServiceException
	{
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		List<Category[]> categorys = new ArrayList<Category[]>();
		Category[] result = null;
		int count = 0;
		
		Category[] cs = innerCategoryLoad("50023878");
			
		//当前淘宝二手市场类目就两级
		if (cs != null && cs.length > 0)
		{
			categorys.add(cs);
			count += cs.length;
					
			for(Category c : cs)
			{
				if (c.getIs_parent().equals("true"))
				{
					Category[] childCategorys = innerCategoryLoad(c.getCid());
					
					if(childCategorys != null && childCategorys.length > 0)
					{
						categorys.add(childCategorys);
						count += childCategorys.length;
					}
				}
			}
			
			result = new Category[count];
			
			for (Category[] carray : categorys)
			{
				for(Category c : carray)
				{
					count -= 1;
					result[count] = c;
				}
			}
		}

		return result;
	}
	
	Category[] innerCategoryLoad(String parendCid) throws ServiceException
	{
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("fields", "cid,parent_cid,name,is_parent");
		params.put("parent_cid", parendCid);
		
		String result = appClient.api(null, "GET", "taobao.itemcats.get", null, params);
		
		if (result == null || (result != null && result.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
		{
			throw new ServiceException(result);
		}
		
		//简单写，去掉外层的几个内容，直接从数组开始获得
		if (result.indexOf("[") > 0)
			result = result.substring(result.indexOf("["), result.indexOf("]")+1);
		
		Gson gson = new Gson();
		Category[] categorys = gson.fromJson(result, Category[].class);
		
		return categorys;
	}

	@Override
	public OperationResult publish(String userId, Secondhand secondhand) throws ServiceException{
		
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("title", secondhand.getTitle());
		params.put("cat_id", secondhand.getCat_id());
		params.put("price", secondhand.getPrice());
		params.put("division_id", secondhand.getDivision_id());
		params.put("item_type", "fixed");
		params.put("desc", secondhand.getDescribe());
		
		if (secondhand.getMajor_image() != null)
			params.put("major_img", secondhand.getMajor_image());
		if (secondhand.getTags() != null)	
			params.put("tags", secondhand.getTags());
		if (secondhand.getPhone() != null)
			params.put("phone", secondhand.getPhone());
		if (secondhand.getStuff_status() > 0)
			params.put("stuff_status", secondhand.getStuff_status());
		if (secondhand.getContacter() != null)
			params.put("contacter", secondhand.getContacter());
		if (secondhand.getPost_fee() > 0)
			params.put("post_fee", secondhand.getPost_fee());
		if (secondhand.getOrg_price() > 0)
			params.put("org_price", secondhand.getOrg_price());
		if (secondhand.getOfflined() != -1)
			params.put("offlined", secondhand.getOfflined());
		if (secondhand.getGps() != null)
			params.put("gps", secondhand.getGps());
		if (secondhand.getAttribute() != null)
			params.put("attribute", secondhand.getAttribute());
		
		String result = appClient.api(userId, "POST", "taobao.idlesell.item.publish", null, params);
		
		if (result == null || (result != null && result.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
		{
			throw new ServiceException(result);
		}
		
		OperationResult operationResult = new OperationResult();	
		operationResult.loadFromString(result);
		
		return operationResult;
	}

	@Override
	public OperationResult update(String userId, Secondhand secondhand) throws ServiceException {
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("item_id", secondhand.getItem_id());
		params.put("title", secondhand.getTitle());
		params.put("cat_id", secondhand.getCat_id());
		params.put("price", secondhand.getPrice());
		params.put("division_id", secondhand.getDivision_id());
		params.put("desc", secondhand.getDescribe());
		
		if (secondhand.getMajor_image() != null)
			params.put("major_img", secondhand.getMajor_image());
		if (secondhand.getTags() != null)	
			params.put("tags", secondhand.getTags());
		if (secondhand.getPhone() != null)
			params.put("phone", secondhand.getPhone());
		if (secondhand.getStuff_status() > 0)
			params.put("stuff_status", secondhand.getStuff_status());
		if (secondhand.getContacter() != null)
			params.put("contacter", secondhand.getContacter());
		if (secondhand.getPost_fee() > 0)
			params.put("post_fee", secondhand.getPost_fee());
		if (secondhand.getOrg_price() > 0)
			params.put("org_price", secondhand.getOrg_price());
		if (secondhand.getOfflined() != -1)
			params.put("offlined", secondhand.getOfflined());
		if (secondhand.getGps() != null)
			params.put("gps", secondhand.getGps());
		if (secondhand.getAttribute() != null)
			params.put("attribute", secondhand.getAttribute());
		
		String result = appClient.api(userId, "POST", "taobao.idlesell.item.update", null, params);
		
		if (result == null || (result != null && result.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
		{
			throw new ServiceException(result);
		}
		
		OperationResult operationResult = new OperationResult();	
		operationResult.loadFromString(result);
		
		return operationResult;
	}

	@Override
	public OperationResult delete(String userId, String iid) throws ServiceException {
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("num_iid", iid);
		String result = appClient.api(userId, "POST", "taobao.item.delete", null, params);
		
		if (result == null || (result != null && result.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
		{
			throw new ServiceException(result);
		}
		
		OperationResult operationResult = new OperationResult();	
		operationResult.loadFromString(result);
		
		return operationResult;
	}

	@Override
	public boolean comment(String userId, String iid, String content,String title) throws ServiceException {
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("target_key", iid);
		params.put("type", "101");
		params.put("content", content);
		params.put("rec_user_id", userId);
		params.put("title", title);
			
		String result = appClient.api(userId, "POST", "taobao.jianghu.comments.publish.item", null, params);
		
		if (result == null || (result != null && result.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
		{
			logger.error("secondhand comment error : " + result);
			return false;
		}
		else
			return true;
	}

	@Override
	public Secondhand getSecondhandById(String iid) throws ServiceException {
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("item_id", iid);
		params.put("has_desc", "true");
		params.put("has_pic", "true");
			
		String result = appClient.api(null, "GET", "taobao.idlesell.item.get", null, params);
		
		if (result == null || (result != null && result.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
		{
			throw new ServiceException(result);
		}
		
		Gson gson = new Gson();		
		
		result = result.substring(result.indexOf("\"results\":") + "\"results\":".length(),result.lastIndexOf("}}"));
		
		Secondhand secondhand = gson.fromJson(result, Secondhand.class);
		
		return secondhand;

	}
	
	@Override
	public Secondhand[] list(String userId) throws ServiceException
	{
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		if (appClient.getAuthEntityByUid(userId) == null)
			throw new ServiceException(Constants.EXCEPTION_AUTH_USER_NOT_EXIST);
		
		//当前先做成一页面
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("page", 1);
		params.put("page_size", 100);
			
		String result = appClient.api(userId, "GET", "taobao.idleapi.items.onsale.get", null, params);
		
		if(result.indexOf("\"idle_item_d_o\":[") > 0)
		{
			Gson gson = new Gson();		
		
			result = result.substring(result.indexOf("\"idle_item_d_o\":") + "\"idle_item_d_o\":".length(),
					result.lastIndexOf("]")+1);
		
			Secondhand[] secondhands = gson.fromJson(result, Secondhand[].class);
			return secondhands;
		}
		
		return null;
		
	}
	
	@Override
	public Secondhand[] getSecondhandsByUser(String userId) throws ServiceException {
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		if (appClient.getAuthEntityByUid(userId) == null)
			throw new ServiceException(Constants.EXCEPTION_AUTH_USER_NOT_EXIST);
		
		Map<String,Object> params = new HashMap<String,Object>();
			
		params.put("seller_nick", appClient.getAuthEntityByUid(userId).getNick());
			
		String result = appClient.api(null, "GET", "taobao.idle.feeler.search", null, params);
		
		if (result == null || (result != null && result.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
		{
			throw new ServiceException(result);
		}
		
		
		if(result.indexOf("\"idle_search_d_o\":[") > 0)
		{
			Gson gson = new Gson();		
		
			result = result.substring(result.indexOf("\"idle_search_d_o\":") + "\"idle_search_d_o\":".length(),
					result.lastIndexOf("]")+1);
		
			Secondhand[] secondhands = gson.fromJson(result, Secondhand[].class);
			return secondhands;
		}
		
		return null;
	}
	
	@Override
	public Secondhand[] commonSearch(SecondhandCondition condition) throws ServiceException
	{
		Secondhand[] secondhands = null;
		
		if (appClient == null)
			throw new ServiceException(Constants.EXCEPTION_APPCLIENT_NOT_EXIST);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		if (condition.getCat_id() != -1)
		{
			params.put("cat_id", condition.getCat_id());
		}
		
		if (condition.getStuff_status() != -1)
		{
			params.put("stuff_status", condition.getStuff_status());
		}
		
		if (condition.getKeyWords() != null)
		{
			params.put("key_words", condition.getKeyWords());
		}
		
		if (condition.getSeller_nick() != null)
		{
			params.put("seller_nick", condition.getSeller_nick());
		}
		
		if (condition.getHas_phone() != -1)
		{
			params.put("has_phone", condition.getHas_phone());
		}
		
		if (condition.getHas_pic()!= -1)
		{
			params.put("has_pic", condition.getHas_pic());
		}
		if (condition.getOffline() != -1)
		{
			params.put("offline", condition.getOffline());
		}
		
		if (condition.getDivision_id()!= -1)
		{
			params.put("division_id", condition.getDivision_id());
		}
		if (condition.getStart_price() != -1)
		{
			params.put("start_price", condition.getStart_price());
		}
		
		if (condition.getEnd_price()!= -1)
		{
			params.put("end_price", condition.getEnd_price());
		}
		
		if (condition.getQuery_session() != null)
		{
			params.put("start", condition.getQuery_session());
		}
		
		if (condition.getPage_size() > 0)
		{
			params.put("rows", condition.getPage_size());
		}
		
		
		String result = appClient.api(null, "GET", "taobao.idle.feeler.search", null, params);
		
		if (result == null || (result != null && result.indexOf(Constants.EXCEPTION_SERVICE_ERROR) > 0))
		{
			throw new ServiceException(result);
		}
		
		//简单写，去掉外层的几个内容，直接从数组开始获得
		if(result.indexOf("\"idle_search_d_o\":[") > 0)
		{
			Gson gson = new Gson();		
		
			result = result.substring(result.indexOf("\"idle_search_d_o\":") + "\"idle_search_d_o\":".length(),
					result.lastIndexOf("]")+1);
		
			secondhands = gson.fromJson(result, Secondhand[].class);
			return secondhands;
		}
		
		return null;
	}

}
