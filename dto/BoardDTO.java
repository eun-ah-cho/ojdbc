package homepage.dto;

import java.sql.Date;

public class BoardDTO {
	//필드 
	private int bno ; 
	private String btitle ;
	private String bcontent ;
	private String bname ; //FK
	private Date bdate ;   //import java.sql.Date;
	
	//기본생성자
	
	
	//메소드 
	public int getBno() {
		return bno;
	}
	public String getBtitle() {
		return btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public String getBname() {
		return bname;
	}
	public Date getBdate() {
		return bdate;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}
	
	
	
}
