package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import config.DBConnection;

public class DeptDao {
	public void 추가(String username, String password, String email) {
		String sql="INSERT INTO users(username,password,emil) VALUES (?,?,?)";
		Connection conn=DBConnection.getConnection();
		//인터페이스가 적용되어 있는 버퍼
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			pstmt.setString(3,email);
			int result=pstmt.executeUpdate(); // 변경된 row count를 리턴, - 값은 오류시에만 리턴
			System.out.println("result : "+result);
			System.out.println("DB 입력완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
