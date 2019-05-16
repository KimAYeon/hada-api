package com.hada.api.model;

import java.util.Date;

public class Workout {
	private int wno;
	private int cno;
	private String email;
	private String work_dt;
	private int work_th;
	private String stt_tm;
	private String end_tm;
	private double rate;
	
	public int getWno() {
		return wno;
	}
	public void setWno(int wno) {
		this.wno = wno;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWork_dt() {
		return work_dt;
	}
	public void setWork_dt(String work_dt) {
		this.work_dt = work_dt;
	}
	public int getWork_th() {
		return work_th;
	}
	public void setWork_th(int work_th) {
		this.work_th = work_th;
	}
	public String getStt_tm() {
		return stt_tm;
	}
	public void setStt_tm(String stt_tm) {
		this.stt_tm = stt_tm;
	}
	public String getEnd_tm() {
		return end_tm;
	}
	public void setEnd_tm(String end_tm) {
		this.end_tm = end_tm;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
}
