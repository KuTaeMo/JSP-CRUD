package com.cos.hello.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;

public class UsersService {
	
	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		
		Users user=Users.builder()
				.username(username)
				.password(password)
				.build();
		
		UsersDao usersDao=new UsersDao();
		Users userEntity=usersDao.login(user);
		
		
		
		if(user.getUsername()==userEntity.getUsername()&&user.getPassword()==userEntity.getPassword()) {
			HttpSession session=req.getSession();
			session.setAttribute("sessionUser", user);
			resp.sendRedirect("index.jsp");
			System.out.println("로그인 완료");
		}else {
			System.out.println("로그인 실패");
			resp.sendRedirect("auth/login.jsp");
		}
	}
	
	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String username=req.getParameter("username"); 
		String password=req.getParameter("password"); 
		String email=req.getParameter("email"); 
		
		Users user=Users.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
		
		//모델에 데이터 삽입
		UsersDao usersDao=new UsersDao();	//싱글톤으로 바꾸기
		int result=usersDao.insert(user);
		
		if(result==1) {
			resp.sendRedirect("auth/login.jsp");
		}else {
			resp.sendRedirect("auth/join.jsp");
		}
	}
	
	
}
