package com.nebula.notification.model;

public enum ReviewStatus {
	APPROVED("A"), DECLINED("D"), PENDING("P");

	private String status;

	ReviewStatus(String status) {
		this.status = status ;
	}

	String getStatus() {
		return this.status;
	}

	void setStatus(String status) {
		this.status = status;
	}

}
