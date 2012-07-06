/**
 * 
 */
package com.taobao.exchange.secondhand;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4 下午4:23:26
 *
 */
public class Secondhand {
	
	private String id;
	private String title;
	private String cid;//类目id
	private Double price;
	private String division_id;//地区号码，宝贝所在地区id，该id对应唯一的省、市、区行政单位
	private String item_type;//商品类型。可选值:fixed(一口价),auction(拍卖)
	private String desc;
	private byte[] major_image;//描述图片
	private String tags;//商品标签，可自定义
	private String phone;
	private int stuff_status;//商品新旧程度
	private String contacter;
	private Double post_fee;//运费
	private Double org_price;//原始价格
	private int offlined;//一口价商品交易方式。可选值：0（线上交易），1(见面交易),2(两种交易方式均可)。一口价商品必须指定交易方式，拍卖商品无此属性
	private String gps;// 手机发布时候会有用
	private String attribute;
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
