package com.choongang.shoppingmall.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CommVO {
	private int p=1, s=10, b=10, idx=-1;
	private int currentPage, sizeOfPage, sizeOfBlock;
	private String mode="insert";
	
	public CommVO() {
		this.currentPage = p;
		this.sizeOfPage = s;
		this.sizeOfBlock = b;
	}
	public CommVO(int p, int s, int b, int idx, int currentPage, int sizeOfPage, int sizeOfBlock, String mode) {
		super();
		this.p = p;
		this.s = s;
		this.b = b;
		this.idx = idx;
		this.currentPage = currentPage;
		this.sizeOfPage = sizeOfPage;
		this.sizeOfBlock = sizeOfBlock;
		this.mode = mode;
		this.currentPage = p;
		this.sizeOfPage = s;
		this.sizeOfBlock = b;
	}	

	public void setP(int p) {
		if(p<=0) p = 1;
		this.p = p;
		this.currentPage = p;
	}
	public void setS(int s) {
		if(s<1) s = 10;
		this.s = s;
		this.sizeOfPage = s;
	}
	public void setB(int b) {
		if(b<=1) b= 10;
		this.b = b;
		this.sizeOfBlock = b;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public void setMode(String mode) {
		if(mode==null) mode = "insert";
		this.mode = mode;
	}
	public CommVO(int p, int s, int b, int idx, String mode) {
		super();
		this.p = p;
		this.s = s;
		this.b = b;
		this.idx = idx;
		this.mode = mode;
	}
}
