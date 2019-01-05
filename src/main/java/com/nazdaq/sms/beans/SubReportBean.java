package com.nazdaq.sms.beans;

public class SubReportBean {
	private String productName;
	private Integer quantity;
	private String price;
	private String singlePrice;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSinglePrice() {
		return singlePrice;
	}
	public void setSinglePrice(String singlePrice) {
		this.singlePrice = singlePrice;
	}
	
}
