package com.cos.hello.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;

public class UsersService {
	
	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		HttpSession session=req.getSession();
		
		Users user=(Users)session.getAttribute("sessionUser");
		UsersDao usersDao=new UsersDao();
		System.out.println(user);
		if(user!=null) {
			Users userEntity=usersDao.selectById(user.getId());
			System.out.println(userEntity);
			req.setAttribute("user", userEntity);
			RequestDispatcher dis=
					req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		}else {
			resp.sendRedirect("auth/login.jsp");
		}
	}
	
	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		HttpSession session=req.getSession();
		
		Users user=(Users)session.getAttribute("sessionUser");
		UsersDao usersDao=new UsersDao();
		
		if(user!=null) {
			Users userEntity=usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis=
					req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
		}else {
			resp.sendRedirect("auth/login.jsp");
		}
	}
	
	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		
		Users user=Users.builder()
				.username(username)
				.password(password)
				.build();
		
		UsersDao usersDao=new UsersDao();
		Users userEntity=usersDao.login(user);
		
		System.out.println("로그인 user"+user);
		System.out.println(userEntity);
		System.out.println(user.getUsername());
		System.out.println(userEntity.getUsername());

		if( (user.getUsername().equals(userEntity.getUsername()))&&(user.getPassword().equals(userEntity.getPassword()))) {
			resp.sendRedirect("index.jsp");
			HttpSession session=req.getSession();
			session.setAttribute("sessionUser", userEntity);

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
	public void 유저정보수정(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException{
		HttpSession session=req.getSession();
		
		Users user=(Users)session.getAttribute("sessionUser");
		
		UsersDao usersDao=new UsersDao();	//싱글톤으로 바꾸기
		int id=Integer.parseInt(req.getParameter("id"));
		String username=usersDao.selectById(user.getId()).getUsername();
		String password=req.getParameter("password"); 
		String email=req.getParameter("email"); 
		
		Users userEntity=Users.builder()
				.id(id)
				.username(username)
				.password(password)
				.email(email)
				.build();
		System.out.println(userEntity);
		//모델에 데이터 삽입
		
		int result=usersDao.update(userEntity);
		System.out.println(userEntity);
		if(result==1) {
			resp.sendRedirect("index.jsp");
		}else {
			// 이전 페이지로 이동 (히스토리 백)
			resp.sendRedirect("user?gubun=updateOne");
			System.out.println("실패");
		}
	}
	
	public void 유저정보삭제(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException{
		HttpSession session=req.getSession();
		Users user=(Users)session.getAttribute("sessionUser");
		
		UsersDao usersDao=new UsersDao();	//싱글톤으로 바꾸기
		int id=Integer.parseInt(req.getParameter("id"));
		
		//모델에 데이터 삽입
		
		int result=usersDao.delete(id);
		if(result==1) {
			session.invalidate();
			resp.sendRedirect("index.jsp");
		}else {
			// 이전 페이지로 이동 (히스토리 백)
			resp.sendRedirect("user?gubun=updateOne");
			System.out.println("실패");
		}
	}
	
	
}
