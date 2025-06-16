package homepage.dao;
import java.sql.*; //sql 관련import 압축 
import java.util.Date;
import java.util.Scanner;

import homepage.dto.BoardDTO;
import homepage.dto.MemberDTO;

//<Member-생성자: DB연결 >
public class MemberDAO {
			
		public MemberDTO memberDTO = new MemberDTO();	
		public Connection connection = null; //연결 
		public Statement state = null; //변수직접처리(구형)
		public PreparedStatement prepar = null; //변수로직접처리(신형)
		public ResultSet rs = null; // 결과 받는 표 객체 executeQuery (select 결과)
		public int result = 0; // 결과 받는 정수 executeUpdate (insert, update, delete)
		

	
		public MemberDAO() {

			try {
				// 예외가 발생할 수 있는 실행문 프로그램 강제종료 처리용
				Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계 ojdbc6.jar 호출
				connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.170:1521:xe", "homepage","homepage"); 
			} catch (ClassNotFoundException e) {
				System.out.println("드라이버 이름이나, ojdbc6.jar 파일이 잘못 되었습니다.");
				e.printStackTrace();
				System.exit(0); // 강제 종료
			} catch (SQLException e) {
				System.out.println("DB연결이 잘못된것 같아요 다시 확인해보세요. ");
				e.printStackTrace();
				System.exit(0); // 강제 종료
			}

		}

		
		//<Member-회원가입> 
		public void insertmember(MemberDTO memberDTO) throws SQLException {
				try {
					String sql = "insert into member(mno, mname, id, pw) "+ " values(board_seq.nextval, ?, ?, ?)";
					prepar = connection.prepareStatement(sql);
					prepar.setString(1, memberDTO.getMname()); 
					prepar.setString(2, memberDTO.getId()); 
					prepar.setString(3, memberDTO.getPw()); 				
				
					result = prepar.executeUpdate(); 
					if (result > 0) {
						System.out.println(result + "개의 회원정보가 등록 되었습니다.");
						connection.commit(); 
					} else {
						System.out.println("쿼리 실행 결과 : " + result);
						System.out.println("입력실패!!!");
						connection.rollback(); 
					}

				} catch (SQLException e) {
					System.out.println("예외발생 : insertMember()메서드에 쿼리문을 확인.");
					e.printStackTrace();

				} finally {	// 예외 발생 및 정상 실행후 무조건 처리되는 실행문
					prepar.close();
				}

			} //회원가입용 메서드 종료 
		
		
		//<Member-로그인> 
		public MemberDTO login(Scanner sc) throws SQLException {
		    System.out.print("아이디를 입력하세요: ");
		    String id = sc.next();
		    System.out.print("비밀번호를 입력하세요: ");
		    String pw = sc.next();
		    // DB에서 id, pw 확인 후 DTO 반환
		    
		    MemberDTO session = null;

		    try {
		        String sql = "SELECT * FROM member WHERE id = ? AND pw = ?";
		        prepar = connection.prepareStatement(sql);
		        prepar.setString(1, id);
		        prepar.setString(2, pw);
		        rs = prepar.executeQuery();

		        if (rs.next()) {
		            session = new MemberDTO(); // 로그인된 회원 객체 생성
		            session.setId(rs.getString("id"));
		            session.setPw(rs.getString("pw"));
		            session.setMname(rs.getString("mname"));

		            System.out.println("✅ 로그인 성공. [" + session.getMname() + "]님 환영합니다!✅");
		        } else {
		            System.out.println("❌로그인 실패❌ 아이디 또는 비밀번호가 틀렸습니다.");
		        }

		    } catch (SQLException e) {
		        System.out.println("예외발생 : login() 메서드 확인");
		        e.printStackTrace();
		    } finally {
		        if (rs != null) rs.close();
		        if (prepar != null) prepar.close();
		    }

		    return session;
		}

		//<Member-회원목록>  
		public void selectAll() throws SQLException { 
			try {
				String sql = "select *  from member order by mno desc";
		
				state = connection.createStatement(); // 쿼리문을 실행 객체 생성
				rs = state.executeQuery(sql); // 쿼리문을 실행하여 결과를 표로 받는다.
				
				System.out.println("==================회원 목록==================");
				System.out.println("번호\t 이름\t 아이디\t  가입일\t ");
				System.out.println("==============================================");
				
				while (rs.next()) {
					// 결과 표에 위에서 부터 아래까지 내려오면서 출력
					int mno = rs.getInt("mno");
					String mname =rs.getString("mname");
					String id = rs.getString("id");
					Date regidate = rs.getDate("regidate");

					System.out.println(mno + "\t" + mname + "\t" + id + "\t" + regidate);
					
				}
				System.out.println("==========================끝===========================");
				
			} catch (SQLException e) {
				// 오류발생시 예외처리문
				System.out.println("selectAll() 메서드에서 예외 발생: 쿼리를 확인.");
				e.printStackTrace();
			} finally {
				// 항상실행문
				rs.close();
				rs.close();
				// 열린 객체를 닫아야 다른 메서드도 정상 작동함.
			}
		}//가입내역 메소드종료 

		
		
		//<Member-회원수정>  
		public void modify(String modid, Scanner sc) throws SQLException {
		    MemberDTO memberDTO = new MemberDTO();

		    System.out.println("======= [회원정보 수정] =======");
		    System.out.print("수정할 아이디: ");
		    String newId = sc.next();

		    sc.nextLine(); // 버퍼 정리
		    System.out.print("수정할 비밀번호: ");
		    String newPw = sc.nextLine();

		    System.out.print("수정할 이름: ");
		    String newName = sc.nextLine();

		    memberDTO.setId(newId);
		    memberDTO.setPw(newPw);
		    memberDTO.setMname(newName);

		    try {
		        connection.setAutoCommit(false); // 트랜잭션 시작

		        // 1. board 테이블 먼저 수정 (자식)
		        if (!modid.equals(newId)) {
		            String updateBoardSql = "UPDATE board SET bname = ? WHERE bname = ?";
		            PreparedStatement psBoard = connection.prepareStatement(updateBoardSql);
		            psBoard.setString(1, newId);
		            psBoard.setString(2, modid);
		            psBoard.executeUpdate();
		            psBoard.close();
		        }

		        // 2. 그 다음 member 테이블 수정 (부모)
		        String updateMemberSql = "UPDATE member SET id = ?, pw = ?, mname = ? WHERE id = ?";
		        PreparedStatement psMember = connection.prepareStatement(updateMemberSql);
		        psMember.setString(1, newId);
		        psMember.setString(2, newPw);
		        psMember.setString(3, newName);
		        psMember.setString(4, modid);
		        int result = psMember.executeUpdate();
		        psMember.close();

		        if (result > 0) {
		            connection.commit();
		            System.out.println("✅ 회원 정보 수정 완료!");
		        } else {
		            connection.rollback();
		            System.out.println("❌ 해당 ID를 찾을 수 없습니다.");
		        }

		    } catch (SQLException e) {
		        System.out.println("❌ 예외 발생: 회원 정보 수정 실패");
		        e.printStackTrace();
		        connection.rollback();
		    } finally {
		        connection.setAutoCommit(true);
		        if (prepar != null) prepar.close();
		    }
		}
		
		
		//<Member-회원정보삭제> 
		public void deleteOne(String delid) throws SQLException {
			BoardDAO boardDAO = new BoardDAO();
			
			try {
		        String sql = "DELETE FROM member WHERE id = ?";
		        
		        prepar = connection.prepareStatement(sql);
		        prepar.setString(1, delid);
		        result = prepar.executeUpdate();

		        if (result > 0) {
		            System.out.println("✅" + result + "명의 회원정보가 삭제되었습니다.✅");
		            connection.commit(); // 회원은 삭제되지만  board의 게시글은 mname는 NULL로 자동 설정됨
		        } else {
		            System.out.println("회원정보가 삭제되지 않았습니다.");
		            connection.rollback();
		        }
		        boardDAO.selectAll(); // 삭제 후 세부게시글 리스트 

		    } catch (SQLException e) {
		        System.out.println("예외발생 : deleteOne() 메서드에서 오류");
		        e.printStackTrace();
		    } finally {
		    	if (rs != null) rs.close();
		        if (prepar != null) prepar.close();	       
		    }
		}
			
						
}//클래스종료 

	
	