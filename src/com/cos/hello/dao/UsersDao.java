package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.cos.hello.config.DBconn;
import com.cos.hello.model.Users;

public class UsersDao {
	
	public int insert(Users user) {
		StringBuffer sb=new StringBuffer(); //String 전용 컬렉션 (동기화)- 동시에 접근 불가능
		sb.append("INSERT INTO users(username,password,email) ");
		sb.append("VALUES(?,?,?)");
		String sql=sb.toString();
		Connection conn=DBconn.getInstance();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result=pstmt.executeUpdate(); //변경된 행의 개수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
}
