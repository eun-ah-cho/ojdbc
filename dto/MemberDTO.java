package homepage.dto;

import java.sql.Date;

public class MemberDTO {
	
		//필드 
		private int mno;
		private String mname;
		private String id; //PK
		private String pw;
		private Date regidate;
		
		//기본생성자
		
		
		//메서드 게터>출력 세터>입력 
		public int getMno() {
			return mno;
		}
		public String getMname() {
			return mname;
		}
		public String getId() {
			return id;
		}
		public String getPw() {
			return pw;
		}
		public Date getRegidate() {
			return regidate;
		}
		public void setMno(int mno) {
			this.mno = mno;
		}
		public void setMname(String mname) {
			this.mname = mname;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setPw(String pw) {
			this.pw = pw;
		}
		public void setRegidate(Date regidate) {
			this.regidate = regidate;
		} 
		
	
		
		
	
	
	

}
