package com.choongang.shoppingmall.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/*
 * 
 * CREATE SEQUENCE users_user_id_seq;


CREATE TABLE users(
	user_id NUMBER PRIMARY KEY,
	username VARCHAR2(200) NOT NULL,
	password VARCHAR2(200) NOT NULL,
	nickname VARCHAR2(50) NOT NULL,
	name VARCHAR2(200) NOT NULL,
	email VARCHAR2(200) NOT NULL,
	phone VARCHAR2(20) NOT NULL,
	regdate DATE DEFAULT sysdate NOT NULL,
	news_email VARCHAR2(200) DEFAULT '수신안함' NOT NULL,
	gender VARCHAR2(1) CHECK(gender IN('M','F')) NOT NULL,
	birth_date DATE NOT NULL,
	signout VARCHAR2(1) DEFAULT 'N' NULL,
	user_role VARCHAR2(10) NULL,
	user_level VARCHAR2(20) DEFAULT '기본등급' NOT NULL,
	user_point NUMBER DEFAULT 0 NOT NULL
);
ALTER TABLE users
ADD (
    postcode VARCHAR2(20) DEFAULT '00000' NOT NULL,  -- 우편번호 기본값
    address VARCHAR2(300) DEFAULT '미입력' NOT NULL,  -- 기본 주소 기본값
    address_detail VARCHAR2(300) DEFAULT '미입력' NOT NULL  -- 상세 주소 기본값
);
---------------------------------------------------------참고
 */


@Data
public class UserVO implements UserDetails{
	
	//security에서 VO를 사용하려면 UserDetails인터페이스를 구현
	
	private static final long serialVersionUID = 6001166772872309410L;
	
	private int user_id;		//회원고유번호
	private String username;	//회원아이디
	private String password;	//비밀번호
	private String nickname;	//닉네임
	private String name;		//이름
	private String email;		//이메일
	private String phone;		//핸드폰
	private Date regdate;		//회원가입일
	private String news_email;	//이메일수신여부
	private String gender;		//성별
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth_date;;	//생년월일
	private String signout;		//회원탈퇴
	private String user_role;	//계정권한
	private String user_level;	//등급
	private int user_point;		//포인트
	private String postcode;	//우편번호
	private String address;		//기본주소
	private String address_detail;	//상세주소
	
	private List<WishVO> wishList;
	
	private String pnm;	// 포인트 지급/차감 파라미터
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		//"ROLE_ADMIN, ROLE_USER" 형태의 스트링을 리스트로 변환하여 리턴하도록 구현
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		String[] roles = user_role.split(",");
		if(roles!=null && roles.length>0) {
			for(String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role.trim()));
			}
		}
		return authorities;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public boolean isAccountNonExpired() {	//계정이 만료되지 않았는지 리턴한다(true:만료안됨)
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {  //계정이 잠겨있는지 아닌지 리턴한다(true:잠기지 않음)
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {  //비밀번호가 만료되지 않았는지 리턴한다(true:만료안됨)
		return true;
	}
	@Override
	public boolean isEnabled() {  //계정이 활성화 여부에 대해 리턴한다(true:활성화됨)
		return true;
	}
	
	//user_id 세션 정보 추가
	public Integer getId() {
		return user_id;
	}


}
