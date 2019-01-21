package com.nazdaq.sms.beans;

public class StockBean implements Comparable<StockBean>{
	
	private Integer productId;
	private String date;
	private String employeeName;
	private String productName;
	private Integer generalQuantity;
	private Integer vipQuantity;
	private Integer totalStock;
	private String totalPrice;
	private Double totalPriceSum;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getGeneralQuantity() {
		return generalQuantity;
	}
	public void setGeneralQuantity(Integer generalQuantity) {
		this.generalQuantity = generalQuantity;
	}
	public Integer getVipQuantity() {
		return vipQuantity;
	}
	public void setVipQuantity(Integer vipQuantity) {
		this.vipQuantity = vipQuantity;
	}
	public Integer getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(Integer totalStock) {
		this.totalStock = totalStock;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getTotalPriceSum() {
		return totalPriceSum;
	}
	public void setTotalPriceSum(Double totalPriceSum) {
		this.totalPriceSum = totalPriceSum;
	}
	@Override
	public int compareTo(StockBean o) {
		// TODO Auto-generated method stub
		return this.productId-o.productId;
	}
	
	
	
	
	

}
