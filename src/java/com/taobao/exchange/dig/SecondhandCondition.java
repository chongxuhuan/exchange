/**
 * 
 */
package com.taobao.exchange.dig;


/**
 * @author fangweng
 * email: fangweng@taobao.com
 * 上午9:54:26
 *
 */
public class SecondhandCondition implements IDigCondition{

	String keyWords;//关键字
	int stuff_status = -1;//一口价商品新旧程度stuffStatus=10：全新 stuffStatus=9：9成新以上 stuffStatus=8：8成新以上 stuffStatus=7：8成新以下
	int cat_id = -1;//类目id
	String seller_nick;//卖家昵称
	int has_phone = -1;//是否有联系电话，可选值：1（留有联系电话），默认包含全部
	int has_pic = -1;//是否有宝贝图片，可选值：1（有图片），默认查询全部。
	int offline = -1;//一口价商品交易方式，可选值：0（仅见面交易），1（在线交易），2（二者均可）。默认二者均可。
	double start_price = -1;//价格区间的开始
	double end_price = -1;//价格区间的结束
	int division_id = -1;//地区
	
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public int getStuff_status() {
		return stuff_status;
	}
	public void setStuff_status(int stuff_status) {
		this.stuff_status = stuff_status;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
	public String getSeller_nick() {
		return seller_nick;
	}
	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}
	public int getHas_phone() {
		return has_phone;
	}
	public void setHas_phone(int has_phone) {
		this.has_phone = has_phone;
	}
	public int getHas_pic() {
		return has_pic;
	}
	public void setHas_pic(int has_pic) {
		this.has_pic = has_pic;
	}
	public int getOffline() {
		return offline;
	}
	public void setOffline(int offline) {
		this.offline = offline;
	}
	public double getStart_price() {
		return start_price;
	}
	public void setStart_price(double start_price) {
		this.start_price = start_price;
	}
	public double getEnd_price() {
		return end_price;
	}
	public void setEnd_price(double end_price) {
		this.end_price = end_price;
	}
	public int getDivision_id() {
		return division_id;
	}
	public void setDivision_id(int division_id) {
		this.division_id = division_id;
	}
	
}
