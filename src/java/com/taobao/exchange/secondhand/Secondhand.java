/**
 * 
 */
package com.taobao.exchange.secondhand;

/**
 * 二手物品结构定义
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class Secondhand {
	
	private String id;
	private String title;//宝贝标题。不能超过30个汉字，受违禁词控制
	private String cid;//叶子类目id。只能发布到闲置商品的类目，该类目的根类目id为：50023878
	private Double price;//商品价格。以元为单位，精确到小数点后面两位，必须大于0元，不能超过1亿元。
	private String division_id;//宝贝所在地区id，该id对应唯一的省、市、区行政单位。当存在该id，会重置province、city、area参数，建议只传id。
							//另，id必须对应最小的行政单位，如330100对应浙江杭州，330106对应浙江杭州西湖区，则必须传330106，否则会返回错误。
	private String item_type;//商品类型。可选值:fixed(一口价),auction(拍卖)
	private String desc;//宝贝描述。字数要大于5个字符，小于25000个字符，受违禁词控制
	private byte[] major_image;//商品主图片。支持的文件类型：gif,jpg,jpeg,png,bmp
	private String tags;//宝贝标签（关键字）。逗号分割，最多5个标签，每个标签最多7个汉字。宝贝标签作用于宝贝搜索，提高搜索的相关性。受违禁词控制
	private String phone;//联系人手机。拍卖商品无此属性
	private int stuff_status;//商品新旧程度。一口价商品必须指定，可选值：10（全新），9（9成新以上），8（8成新以上），7（8成新以下）。
	private String contacter;//联系人。最多15个汉字,受违禁词控制，拍卖商品无此属性
	private Double post_fee;//商品运费，以元为单位，不能小于0元，不能超过1000元，默认为0元。
	private Double org_price;//商品原价，以元为单位，必须大于0元，不能超过1亿元。
	private int offlined;//一口价商品交易方式。可选值：0（线上交易），1(见面交易),2(两种交易方式均可)。
	//手机客户端发布时的gps坐标。gps格式：纬度,经度。其中，纬度、经度精确到小数点后6位，逗号分隔经纬度。
	//纬度区间[-90,+90],代表南纬90度到北纬90度；经度区间[-180,+180]，代表西经180度到东经180度。正确写法：+39.54,+116.28
	private String gps;
	private String attribute;//attribute,用于传递新浪等用户相关信息。
	private boolean wwsuport;//是否支持物物交换
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
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
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public byte[] getMajor_image() {
		return major_image;
	}
	public void setMajor_image(byte[] major_image) {
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
	public Double getPost_fee() {
		return post_fee;
	}
	public void setPost_fee(Double post_fee) {
		this.post_fee = post_fee;
	}
	public Double getOrg_price() {
		return org_price;
	}
	public void setOrg_price(Double org_price) {
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
	
}
