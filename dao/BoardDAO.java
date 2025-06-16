package homepage.dao;
import homepage.dto.BoardDTO;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class BoardDAO {
    public BoardDTO boardDTO = new BoardDTO();

    public Connection connection = null;
    public Statement state = null;
    public PreparedStatement prepar = null;
    public ResultSet rs = null;
    public int result = 0;

    //<Board-생성자: DB연결 >
    public BoardDAO() {
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

	

    //<Board-회원전체보기 >
    public void selectAll() throws SQLException {
        try {
            //String sql = "SELECT bno, btitle, bcontent,bname, bdate FROM board ORDER BY bdate DESC";
            String sql = "SELECT bno, btitle, bname, bdate FROM board ORDER BY bdate DESC";
            state = connection.createStatement();
            rs = state.executeQuery(sql);

            System.out.println("==================게시글 목록==================");
			System.out.println("번호\t 제목\t 내용\t 작성자\t 작성일\t ");
			System.out.println("================================================");
             while (rs.next()) {
            	 
            	 	int bno = rs.getInt("bno");
					String btitle =rs.getString("btitle");
				//	String bcontent =rs.getString("bcontent");
					String bname = rs.getString("bname");
					Date bdate = rs.getDate("bdate");
					System.out.println(bno + "\t" + btitle + "\t" + bname + "\t" + bdate + "\t");
            
             }
         	System.out.println("================================================");

        } catch (SQLException e) {
            System.out.println("❌selectAll() 쿼리 오류❌");
            e.printStackTrace();
        } finally {
            rs.close();
            state.close();
        }
    }

    //<Board-회원 게시글 등록 >
    public void insertBoard(BoardDTO boardDTO) throws SQLException {
        try {
            String sql = "INSERT INTO board (bno, btitle, bcontent, bname, bdate) " +  "VALUES (board_seq.nextval, ?, ?, ?, SYSDATE)";
            prepar = connection.prepareStatement(sql);
            prepar.setString(1, boardDTO.getBtitle());
            prepar.setString(2, boardDTO.getBcontent());
            prepar.setString(3, boardDTO.getBname());
            result = prepar.executeUpdate();

            if (result > 0) {
                System.out.println("✅ " + result + "개의 게시물이 등록되었습니다.✅");
                connection.commit();
            } else {
                System.out.println("게시물 등록 실패");
                connection.rollback();
            }

        } catch (SQLException e) {
            System.out.println("insertBoard() 쿼리 오류");
            e.printStackTrace();
        } finally {
        	prepar.close();
        }
    }

    
  //<Board-내가 작성한 글보기 >
    public void readOne(String writer) throws SQLException {
        try {
            String sql = "SELECT * FROM board WHERE bname = ?"; 
            prepar = connection.prepareStatement(sql);
            prepar.setString(1, writer); //id 
            rs = prepar.executeQuery();

            if (rs.next()) {
                BoardDTO dto = new BoardDTO();
                dto.setBno(rs.getInt("bno"));
                dto.setBtitle(rs.getString("btitle"));
                dto.setBcontent(rs.getString("bcontent"));
                dto.setBname(rs.getString("bname"));
                dto.setBdate(rs.getDate("bdate"));

                System.out.println("============= 나의 게시글 상세 보기 =============");
                System.out.println("번호: " + dto.getBno());
                System.out.println("제목: " + dto.getBtitle());
                System.out.println("내용: " + dto.getBcontent());
                System.out.println("작성자: " + dto.getBname());
                System.out.println("작성일: " + dto.getBdate());
                System.out.println("=============================================");
            } else {
                System.out.println("❌해당 제목의 게시글이 존재하지 않습니다.❌");
            }

        } catch (SQLException e) {
            System.out.println("readOne() 쿼리 오류");
            e.printStackTrace();
        } finally {
            rs.close();
            prepar.close();
        }
    }

    //<Board-게시글 수정>
    public void modify(BoardDTO boardDTO) throws SQLException {
    	  try {
    	        if (boardDTO.getBtitle() == null || boardDTO.getBtitle().trim().isEmpty()) {
    	            System.out.println("❌ 제목은 비워둘 수 없습니다.");
    	            return;
    	        }

    	        String sql = "UPDATE board SET btitle = ?, bcontent = ?, bdate = SYSDATE WHERE bno = ?";
    	        prepar = connection.prepareStatement(sql);
    	        prepar.setString(1, boardDTO.getBtitle());
    	        prepar.setString(2, boardDTO.getBcontent());
    	        prepar.setInt(3, boardDTO.getBno());

    	        int result = prepar.executeUpdate();

    	        if (result > 0) {
    	            System.out.println("✅ 게시글 수정 완료");
    	            connection.commit();
    	        } else {
    	            System.out.println("❌ 게시글 번호가 존재하지 않습니다.");
    	            connection.rollback();
    	        }

    	    } catch (SQLException e) {
    	        System.out.println("❌ 예외 발생: 게시글 수정 실패");
    	        e.printStackTrace();
    	        connection.rollback();
    	    } finally {
    	        if (prepar != null) prepar.close();
    	    }
    	}
 	//<Board-게시글 삭제>
    public void deleteOne(String title) throws SQLException {
        try {
            String sql = "DELETE FROM board WHERE btitle = ?";
            prepar = connection.prepareStatement(sql);
            prepar.setString(1, title);

            result = prepar.executeUpdate();
            if (result > 0) {
                System.out.println("✅게시글 삭제 완료✅");
                connection.commit();
            } else {
                System.out.println("❌삭제 실패❌");
                connection.rollback();
            }
            selectAll(); // 삭제 후 전체 목록 출력

        } catch (SQLException e) {
            System.out.println("❌deleteOne() 쿼리 오류❌");
            e.printStackTrace();
        } finally {
        	prepar.close();
        }
    }
}