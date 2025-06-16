package homepage;
import homepage.dao.MemberDAO;
import homepage.dto.MemberDTO;
import homepage.service.*;
import java.sql.SQLException;
import java.util.Scanner;


public class homepageExam {

	public static Scanner sc = new Scanner(System.in); //전역변수로 쓸거다 
	public static MemberDTO session = null;  //로그인 하지않은 상태.전역변수 
	
	
	public static void main(String[] args) throws SQLException  {
				
		MemberDAO memberDAO = new MemberDAO();
		BoardService boardService = new BoardService();
		MemberService memberService = new MemberService();
		
		boolean run = true;		
		while (run) {
			System.out.println("=============================== 맘이베베 까페 ===============================");
			System.out.println("                      1. 로그인 | 2. 회원가입 | 3. 종료      ");
			System.out.println("=============================================================================");
			System.out.print(">>>");
			String select = sc.next();
			
			switch(select) { //로그인 
			case "1" : 
				session = memberDAO.login(sc);//memberDAO-login메서드에 DB에서 select쿼리로 조회 -> session리턴
				if(session !=null) { //만약 session이 null아니면 로그인OK
					  System.out.println("[" + session.getMname() + "] longin success!."); //dao-login갔다가 돌아온 세션.
					  MemberService.subMenu(sc, session); // 로그인후 부메뉴 이동 
				}
				break;
				
				
			case "2" : //회원가입 
				MemberDTO memberDTO = new MemberDTO();
            	memberService.insertmember(memberDTO, sc); 
                break;
			
			 case "3": 
             	System.out.println("프로그램을 종료합니다.");
                 run = false;
                 break;
                 
             default:
                 System.out.println("1~3까지만 입력 바랍니다.");    
			}
		}
	}
}

	
			
		