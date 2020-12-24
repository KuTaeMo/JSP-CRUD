package com.cos.hello.controller;

import java.io.IOException;

import javax.servlet.ServletException;
//javax로 시작하는 패키지는 톰켓이 들고있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardController extends HttpServlet {

	// req와 res는 톰켓이 만들어줍니다. (클라이언트의 요청이 있을 때 마다)
	// req는 BufferedReader할 수 있는 ByteStream
	// res는 BufferedWriter할 수 있는 ByteStream

	// http://localhost:8000/hello/user
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("BoardController 실행됨");
		// http://localhost:8000/hello/user?gubun=login
		String gubun = req.getParameter("gubun"); // /hello/front
		System.out.println(gubun);

		if (gubun.equals("deleteOne")) {
			resp.sendRedirect("board/deleteOne.jsp");// 한번 더 request
		} else if (gubun.equals("insertOne")) {
			resp.sendRedirect("board/insertOne.jsp");// 한번 더 request
		} else if (gubun.equals("selectAll")) {
			resp.sendRedirect("board/selectAll.jsp");// 한번 더 request
		} else if (gubun.equals("updateOne")) {
			resp.sendRedirect("board/updateOne.jsp");// 한번 더 request
		}
	}
}
