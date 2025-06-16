package homepage.service;
import java.sql.SQLException;
import java.util.Scanner;
import homepage.dao.MemberDAO;
import homepage.dto.MemberDTO;


public class MemberService {
	public static MemberDAO memberDAO = new MemberDAO(); //전역변수 설정 
	
	//Exam에서 2눌러서 회원가입으로 옴 
	public void insertmember(MemberDTO memberDTO, Scanner sc) throws SQLException {
		Scanner inputLine = new Scanner(System.in); //공백없이 
		
		System.out.print("등록할 이름 : ");	memberDTO.setMname(sc.next());
        System.out.print("등록할 ID : ");	memberDTO.setId(sc.next());
        System.out.print("등록할 PW : ");	memberDTO.setPw(sc.next());
        
       memberDAO.insertmember(memberDTO);// 입력받은값을 memberDAO-insertmember로던짐 
       System.out.println("-----------" + memberDTO.getMname() + "님 ✅회원가입 완료✅-----------");
	}
	
	
	// Exam에서 회원가입 또는 로그인후  부메뉴로 진입. 
    public static void subMenu(Scanner sc, MemberDTO session) throws SQLException {
        BoardService boardService = new BoardService(); boolean subRun = true;

        while (subRun) {
            System.out.println("=========================[ " +  session.getMname() + "]님  메뉴 ======================");
            System.out.println("1. 회원내역 | 2. 회원수정 | 3. 게시판 | 4. 회원탈퇴 | 5. 나가기");
            System.out.println("=================================================================");
            System.out.print(">>> ");
            String subSelect = sc.next();

            switch (subSelect) {
                case "1": selectAll(); break;
                case "2": System.out.println("회원수정 메뉴로 진입합니다."); modify(sc, session); break;
                case "3": System.out.println("게시판으로 이동합니다."); boardService.subMenu(sc, session); break;
                case "4": System.out.println("회원 탈퇴를 진행합니다.");deleteOne(sc); subRun = false; break; // 탈퇴 후 메뉴 종료
                case "5": System.out.println("로그아웃 합니다."); subRun = false; break;
                default:  System.out.println("1~5 사이로 선택해주세요.");
            }
        }
    }

	private static void selectAll() throws SQLException { 
	memberDAO.selectAll(); } //memberdao-selectAll로던짐

	
	private static void modify(Scanner sc, MemberDTO loginSe) throws SQLException {
		String modid = loginSe.getId();
        memberDAO.modify(modid, sc); 
        } //memberdao-modify로던짐  
	

	private static void deleteOne(Scanner sc) throws SQLException {
		 System.out.print("삭제할 회원 ID 입력: "); String delid = sc.next(); memberDAO.deleteOne(delid);} //memberdao-deleteOne로던짐
		
	}
