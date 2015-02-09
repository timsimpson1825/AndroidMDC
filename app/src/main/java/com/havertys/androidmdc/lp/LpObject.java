package com.havertys.androidmdc.lp;

import java.io.Serializable;


public class LpObject implements Serializable {
	
	private long lpNumber;
	private String item;
	private String itemDescription;
	private String vendorDescription; //??
	private String trackingStatus;
	private String collectionName;
	private String saleNumber; // divpc-reserve

	public long getLpNumber() {
		return lpNumber;
	}
	public void setLpNumber(long lpNumber) {
		this.lpNumber = lpNumber;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getVendorDescription() {
		return vendorDescription;
	}
	public void setVendorDescription(String vendorDescription) {
		this.vendorDescription = vendorDescription;
	}
	public String getTrackingStatus() {
		return trackingStatus;
	}
	public void setTrackingStatus(String trackingStatus) {
		this.trackingStatus = trackingStatus;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public String getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}
}
