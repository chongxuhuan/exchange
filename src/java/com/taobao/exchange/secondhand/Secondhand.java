/**
 * 
 */
package com.taobao.exchange.secondhand;

import com.taobao.exchange.app.RequestAttachment;

/**
 * 二手物品结构定义
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class Secondhand implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3421962448912843604L;
	
	private String item_id;
	private String title;//宝贝标题。不能超过30个汉字，受违禁词控制
	private String cat_id;//叶子类目id。只能发布到闲置商品的类目，该类目的根类目id为：50023878
	private String price;//商品价格。以元为单位，精确到小数点后面两位，必须大于0元，不能超过1亿元。
	private String division_id;//宝贝所在地区id，该id对应唯一的省、市、区行政单位。当存在该id，会重置province、city、area参数，建议只传id。
							//另，id必须对应最小的行政单位，如330100对应浙江杭州，330106对应浙江杭州西湖区，则必须传330106，否则会返回错误。
	private String item_type;//商品类型。可选值:fixed(一口价),auction(拍卖)
	private String describe;//宝贝描述。字数要大于5个字符，小于25000个字符，受违禁词控制
	private RequestAttachment major_image;//商品主图片。支持的文件类型：gif,jpg,jpeg,png,bmp
	private String tags;//宝贝标签（关键字）。逗号分割，最多5个标签，每个标签最多7个汉字。宝贝标签作用于宝贝搜索，提高搜索的相关性。受违禁词控制
	private String phone;//联系人手机。拍卖商品无此属性
	private int stuff_status;//商品新旧程度。一口价商品必须指定，可选值：10（全新），9（9成新以上），8（8成新以上），7（8成新以下）。
	private String contacter;//联系人。最多15个汉字,受违禁词控制，拍卖商品无此属性
	private String post_fee;//商品运费，以元为单位，不能小于0元，不能超过1000元，默认为0元。
	private String org_price;//商品原价，以元为单位，必须大于0元，不能超过1亿元。
	private int offlined = -1;//一口价商品交易方式。可选值：0（线上交易），1(见面交易),2(两种交易方式均可)。
	//手机客户端发布时的gps坐标。gps格式：纬度,经度。其中，纬度、经度精确到小数点后6位，逗号分隔经纬度。
	//纬度区间[-90,+90],代表南纬90度到北纬90度；经度区间[-180,+180]，代表西经180度到东经180度。正确写法：+39.54,+116.28
	private String gps;
	private String attribute;//attribute,用于传递新浪等用户相关信息。
	private boolean wwsuport;//是否支持物物交换
	private String prov;
	private String city;
	private String area;
	private int ww_status;
	
	//为了兼容搜索接口
	private String desc;
	private int offline = -1;
	private String pic_url;
	private String seller_id;
	private String seller_nick;
	
	
	//以下是用于查询时候获得结果的属性
	private String nick;//卖家nick
	private String detail_url;//在淘宝的宝贝地址
	private String major_imageurl;//主图的地址
	private String cid_name;//属性描述，用于展示
	private String relationOwner;//关系圈中的用户信息（也是物品的owner）
	//private String bridgeRelation;//如果是间接用户，这个字段说明了桥接这两个用户的关系用户,暂时不考虑
	private boolean indirect;//是否是间接关系
	
	
	public String getRelationOwner() {
		return relationOwner;
	}

	public void setRelationOwner(String relationOwner) {
		this.relationOwner = relationOwner;
	}

	public boolean isIndirect() {
		return indirect;
	}

	public void setIndirect(boolean indirect) {
		this.indirect = indirect;
	}

	public String getCid_name() {
		return cid_name;
	}

	public void setCid_name(String cid_name) {
		this.cid_name = cid_name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getDetail_url() {
		return detail_url;
	}

	public void setDetail_url(String detail_url) {
		this.detail_url = detail_url;
	}

	public String getMajor_imageurl() {
			return major_imageurl;
	}

	public void setMajor_imageurl(String major_imageurl) {
		this.major_imageurl = major_imageurl;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDivision_id() {
		return division_id;
	}
	public void setDivision_id(String division_id) {
		this.division_id = division_id;
	}
	public String getItem_type() {
		return item_type;
	}
	public RequestAttachment getMajor_image() {
		return major_image;
	}
	public void setMajor_image(RequestAttachment major_image) {
		this.major_image = major_image;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getStuff_status() {
		return stuff_status;
	}
	public void setStuff_status(int stuff_status) {
		this.stuff_status = stuff_status;
	}
	public String getContacter() {
		return contacter;
	}
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	public String getPost_fee() {
		return post_fee;
	}
	public void setPost_fee(String post_fee) {
		this.post_fee = post_fee;
	}
	public String getOrg_price() {
		return org_price;
	}
	public void setOrg_price(String org_price) {
		this.org_price = org_price;
	}
	public int getOfflined() {
		return offlined;
	}
	public void setOfflined(int offlined) {
		this.offlined = offlined;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public boolean isWwsuport() {
		return wwsuport;
	}
	public void setWwsuport(boolean wwsuport) {
		this.wwsuport = wwsuport;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public int getWw_status() {
		return ww_status;
	}

	public void setWw_status(int ww_status) {
		this.ww_status = ww_status;
	}
	
	public String getDesc() {
		return desc;
	}

	public int getOffline() {
		return offline;
	}

	public String getPic_url() {
		return pic_url;
	}

	public String getSeller_nick() {
		return seller_nick;
	}

	public void setDesc(String desc) {
		this.desc = desc;
		this.describe = desc;
	}

	public void setOffline(int offline) {
		this.offline = offline;
		this.offlined = offline;
	}


	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
		this.major_imageurl = pic_url;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
		this.nick = seller_nick;
	}

//	public String getBridgeRelation() {
//		return bridgeRelation;
//	}
//
//	public void setBridgeRelation(String bridgeRelation) {
//		this.bridgeRelation = bridgeRelation;
//	}
	
	
}
