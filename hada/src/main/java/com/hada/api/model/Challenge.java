package com.hada.api.model;

import java.util.Date;

public class Challenge {
	private int cno;
	private String req_email;
	private String res_email;
	private String stt_dt;
	private String end_dt;
	private String days;
	private String stt_tm;
	private String min_tm;
	private String bet_title;
	private String state;
	private Date created;
	private Date updated;
	
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public String getReq_email() {
		return req_email;
	}
	public void setReq_email(String req_email) {
		this.req_email = req_email;
	}
	public String getRes_email() {
		return res_email;
	}
	public void setRes_email(String res_email) {
		this.res_email = res_email;
	}
	public String getStt_dt() {
		return stt_dt;
	}
	public void setStt_dt(String stt_dt) {
		this.stt_dt = stt_dt;
	}
	public String getEnd_dt() {
		return end_dt;
	}
	public void setEnd_dt(String end_dt) {
		this.end_dt = end_dt;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getStt_tm() {
		return stt_tm;
	}
	public void setStt_tm(String stt_tm) {
		this.stt_tm = stt_tm;
	}
	public String getMin_tm() {
		return min_tm;
	}
	public void setMin_tm(String min_tm) {
		this.min_tm = min_tm;
	}
	public String getBet_title() {
		return bet_title;
	}
	public void setBet_title(String bet_title) {
		this.bet_title = bet_title;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}

