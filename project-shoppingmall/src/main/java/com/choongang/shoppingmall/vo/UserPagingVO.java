package com.choongang.shoppingmall.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class UserPagingVO {
	private int p=1, s=15, b=10, idx=-1;
	private int currentPage, sizeOfPage, sizeOfBlock;

	public UserPagingVO() {
		this.currentPage = p;
		this.sizeOfPage = s;
		this.sizeOfBlock = b;
	}
	public UserPagingVO(int p, int s, int b, int idx, int currentPage, int sizeOfPage, int sizeOfBlock) {
		super();
		this.p = p;
		this.s = s;
		this.b = b;
		this.idx = idx;
		this.currentPage = currentPage;
		this.sizeOfPage = sizeOfPage;
		this.sizeOfBlock = sizeOfBlock;
		this.currentPage = p;
		this.sizeOfPage = s;
		this.sizeOfBlock = b;
	}	
	public UserPagingVO(int p, int s, int b, int currentPage, int sizeOfPage, int sizeOfBlock) {
		super();
		this.p = p;
		this.s = s;
		this.b = b;
		this.currentPage = currentPage;
		this.sizeOfPage = sizeOfPage;
		this.sizeOfBlock = sizeOfBlock;
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
		if(s<1) s = 15;
		this.s = s;
		this.sizeOfPage = s;
	}
	public void setB(int b) {
		if(b<=1) b= 10;
		this.b = b;
		this.sizeOfBlock = b;
	}
	public UserPagingVO(int p, int s, int b, int idx) {
		super();
		this.p = p;
		this.s = s;
		this.b = b;
		this.idx = idx;
	}
}
