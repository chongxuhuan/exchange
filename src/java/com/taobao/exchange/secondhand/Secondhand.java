/**
 * 
 */
package com.taobao.exchange.secondhand;

/**
 * @author fangweng
 * @email: fangweng@taobao.com
 * 2012-7-4
 *
 */
public class Secondhand {
	
	private String id;
	private String title;
	private String cid;//类目id
	private Double price;
	private String division_id;
	private String item_type;
	private String desc;
	private byte[] major_image;
	private String tags;
	private String phone;
	private int stuff_status;
	private String contacter;
	private Double post_fee;
	private Double org_price;
	private int offlined;
	private String gps;
	private String attribute;
	private boolean wwsuport;
	
	
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
