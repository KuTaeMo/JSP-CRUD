package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cos.hello.config.DBconn;
import com.cos.hello.model.Users;
import com.mysql.cj.protocol.Resultset;

public class UsersDao {
	
	public int delete(int id) {
		StringBuffer sb=new StringBuffer(); //String 전용 컬렉션 (동기화)- 동시에 접근 불가능
		sb.append("DELETE FROM users WHERE id=?");
		String sql=sb.toString();
		Connection conn=DBconn.getInstance();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result=pstmt.executeUpdate(); //변경된 행의 개수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int update(Users user) {
		StringBuffer sb=new StringBuffer(); //String 전용 컬렉션 (동기화)- 동시에 접근 불가능
		sb.append("UPDATE users SET password=?, email=? WHERE id=?");
		String sql=sb.toString();
		Connection conn=DBconn.getInstance();
		try {
			PreparedStatement pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setInt(3, user.getId());
			int result=pstmt.executeUpdate(); //변경된 행의 개수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public Users selectById(int id) throws SQLException {
		String sql="SELECT id, password, username, email FROM users WHERE id=?";
		Connection conn=DBconn.getInstance();
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs=pstmt.executeQuery();
		
		if(rs.next()) {
			Users usersEntity=Users.builder()
					.id(rs.getInt("id"))
					.username(rs.getString("username"))
					.password(rs.getString("password"))
					.email(rs.getString("email"))
					.build();
			return usersEntity;
		}
		
		return null;
	}
	
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
	public Users login(Users user) throws SQLException {
		String sql="SELECT id, username, password, email FROM users WHERE username=? AND password=?";
		Connection conn=DBconn.getInstance();
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		ResultSet rs=pstmt.executeQuery();
		
		if(rs.next()) {
			Users usersEntity=Users.builder()
					.id(rs.getInt("id"))
					.username(rs.getString("username"))
					.password(rs.getString("password"))
					.email(rs.getString("email"))
					.build();
			return usersEntity;
		}
		
		return null;
	}
}
