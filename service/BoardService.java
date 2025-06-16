package homepage.service;

import java.sql.SQLException;
import java.util.Scanner;

import homepage.dao.BoardDAO;
import homepage.dto.BoardDTO;
import homepage.dto.MemberDTO;

public class BoardService {
	public BoardDAO boardDAO = new BoardDAO();
	
	public void subMenu(Scanner sc, MemberDTO session) throws SQLException {
		  boolean subRun = true;
	        while (subRun) {
	        	System.out.println("======================[ " +  session.getMname() + "]님  게시판메뉴 ===================");
	            System.out.println("1. 모두보기 | 2. 글쓰기 | 3. 나의 글보기 | 4. 수정 | 5. 삭제 | 6. 나가기");
	            System.out.print(">>> ");
	            String subSelect = sc.next();
	            
	            switch (subSelect) {
                case "1": selectAll(); break; //전체보기
                case "2": insertBoard( sc, session); break; //글쓰기
                case "3": readOne(sc); break; //작성자 글만 보기 
                case "4": modify(sc); break; //작성자 글수정 
                case "5": deleteOne(sc); break;
                case "6": System.out.println("게시판 나가기"); subRun = false; break;
                default: System.out.println("1~6 중에서 입력하세요.");
	            }
	        }
		}



	private void selectAll() throws SQLException {	boardDAO.selectAll();	} 
	
	private void insertBoard(Scanner sc, MemberDTO session) throws SQLException {  //오류 
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBname(session.getId());//로그인한 작성자만 글을 기재가능.-> session을가져옴 
		System.out.println("게시판 작성하기 입니다.");
		System.out.print("제목:");
		boardDTO.setBtitle(sc.next());
		System.out.print("내용:");
		boardDTO.setBcontent(sc.next());
		boardDAO.insertBoard(boardDTO);
		
		
	}
	
	private void readOne(Scanner sc) throws SQLException {  // 작성자  이름으로 입력해서 작성자 이름의 게시물만 볼수있게 오류남   내가 작성한글 세부내역 보여주기
		   System.out.print("작성자 id 입력: ");
	        String writer = sc.next();
	        boardDAO.readOne(writer);
	       
	   	}
	

	private void modify(Scanner sc) throws SQLException { 
		//boardDAO.readOne(sc);
		System.out.print("수정 하려는 제목 입력: ");
		 String title = sc.next();
		//boardDAO.modify(title, sc);
			}
	

	private void deleteOne(Scanner sc) throws SQLException {
		System.out.print("삭제할 제목 입력 : ");
		String title = sc.next();
		boardDAO.deleteOne(title);
		
	}

	
	
}
