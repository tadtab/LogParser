package com.tadtab.model;

public class BlockedIPs {
	int id;
	String ip;
	int count;
	String reasonForBlocking;
	
	public BlockedIPs(int id, String ip, int count, String reasonForBlocking) {
		this.id = id; 
		this.ip = ip;
		this.count = count;
		this.reasonForBlocking = reasonForBlocking;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReasonForBlocking() {
		return reasonForBlocking;
	}

	public void setReasonForBlocking(String reasonForBlocking) {
		this.reasonForBlocking = reasonForBlocking;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
