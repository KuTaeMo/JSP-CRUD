package com.cos.hello.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//javax로 시작하는 패키지는 톰켓이 들고있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.config.DBconn;
import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;

import config.DBConnection;

// 디스패쳐의 역할 = 분기 = 필요한 view를 응답해주는 것
public class UserController extends HttpServlet{
	
	//req와 res는 톰켓이 만들어줍니다. (클라이언트의 요청이 있을 때 마다)
	//req는 BufferedReader할 수 있는 ByteStream
	//res는 BufferedWriter할 수 있는 ByteStream

	//http://localhost:8000/hello/user
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doProcess(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			doProcess(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("UserController 실행됨");
		//http://localhost:8000/hello/user?gubun=login
		String gubun=req.getParameter("gubun");	// /hello/front
		System.out.println(gubun);
		
		route(gubun,req,resp);
	}
	
	private void route(String gubun,HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp");//한번 더 requestz
		}else if(gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp");//한번 더 request
//	}	else if(gubun.equals("selectOne")) {
//			resp.sendRedirect("user/selectOne.jsp");//한번 더 request
		}else if(gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp");//한번 더 request
		}else if(gubun.equals("joinProc")) {	// 회원가입 수행해줘
			// 데이터 원형  username=ssar&password=1234&email=ssar@nate.com
			
			// 1번 form의 input 태그에 있는 3가지 값 username, password, email 받기
			
			// getParameter() 함수는 get방식의 데이터와 post방식의 데이터를 다 받을 수 있다.
			// 단 post방식에서는 데이터 타입이 x-www-form-urlencoded 방식만 받을 수 있음.
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
			
			//추가(username, password, email);
			HttpSession session=req.getSession();
			session.setAttribute("sessionUser", user);
			
			System.out.println("=======joinProc=======");
			System.out.println(username);
			System.out.println(password);
			System.out.println(email);
			System.out.println("=======joinProc=======");
			
			
			if(result==1) {
				resp.sendRedirect("auth/login.jsp");
			}else {
				resp.sendRedirect("auth/join.jsp");
			}

			
		}else if(gubun.equals("loginProc")) {
			// 1번 전달되는 값 받기
			String username=req.getParameter("username");
			String password=req.getParameter("password");
			
			System.out.println("=======loginProc=======");
			System.out.println(username);
			System.out.println(password);
			System.out.println("=======loginProc=======");
			
			// 2번 데이터베이스 값이 있는지 select 해서 확인 - 생략
			// 3번 
//			HttpSession session = req.getSession();
//			session.setAttribute("sessionKey","9998");
//			resp.setHeader("Set-Cookie", "sessionKey=9998");
//			resp.setHeader("cookie", "9998"); 	//헤더에 담기
			// 4번 index.jsp 페이지로 이동
			resp.sendRedirect("index.jsp");
		}else if(gubun.equals("selectOne")) {
			String result;
			HttpSession session=req.getSession();
			if(session.getAttribute("sessionUser")!=null) {
				Users user=(Users)session.getAttribute("sessionUser");
				result="인증되었습니다.";
				System.out.println("인증되었습니다.");
				System.out.println("user : "+user);

			}else {
				System.out.println("인증되지 않았습니다.");
				result="인증되지않았습니다.";
			}
			//request 유지시키기
			req.setAttribute("result", result);
			RequestDispatcher dis= req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		}
	}
	public void 추가(String username, String password, String email) {
		String sql="INSERT INTO users(username,password,email) VALUES (?,?,?)";
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
