package ccnu.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryParser.ParseException;

import ccnu.commen.Spider.EditData;
import ccnu.commen.index.CreateIndex;
import ccnu.entity.Movie;
import ccnu.service.MovieService;



public class MovieController extends HttpServlet{
	
	public void destroy() {
		super.destroy();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//��ȡ��ַ���׺
 		String mn = request.getServletPath();
 		//ȥ����׺�ġ�/��
		mn = mn.substring(1);
		
		//ͨ����׺,������÷���
		Method method;
		try {
			method = this.getClass().getDeclaredMethod(mn, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private void SimpleSearch(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, SQLException {
		String txt = "";
		List<Movie> movieList = new ArrayList<>();
		MovieService movieService = new MovieService();
		if(!request.getParameter("txt").isEmpty()) {
			txt = request.getParameter("txt");
			movieList = movieService.SimpleSearch(txt);
			request.setAttribute("movieList", movieList);
			request.setAttribute("flag", "simpleSearch");
			request.setAttribute("txt", txt);
			RequestDispatcher rd = request.getRequestDispatcher("searchResultList.jsp");
			rd.forward(request, response);
		}else {
			request.setAttribute("information", "��������������!");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
	}
	
	
	private void AdvancedSearch(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, SQLException, ParseException{
		
		//��ȡǰ̨��������
		String txt = request.getParameter("txt");
		String type = request.getParameter("type");
		String timeDown = request.getParameter("timeDown");
		String timeUp = request.getParameter("timeUp");
		String author = request.getParameter("author");
		String authorType = request.getParameter("authorType");
		String good = request.getParameter("good");
		String goodType = request.getParameter("goodType");
		
		//����ɸѡ ���� ʱ�� ���� ����
		MovieService movieService = new MovieService();
		List<Movie> movieList = new ArrayList<>();
		
		movieList = movieService.AdvanceSearch(txt,type, timeDown, timeUp, author, authorType);
		
		if(!good.isEmpty()) {
			List<Movie> movieList2 = new ArrayList<>();//ɸѡ����б�
			if(goodType.equals("1")) {
				for(Movie m:movieList) {
					if(m.getUp() > Integer.parseInt(good)) {
						movieList2.add(m);
					}
				}
			}else if(goodType.equals("2")) {
				for(Movie m:movieList) {
					if(m.getUp() == Integer.parseInt(good)) {
						movieList2.add(m);
					}
				}
			}else {
				for(Movie m:movieList) {
					if(m.getUp() < Integer.parseInt(good)) {
						movieList2.add(m);
					}
				}
			}
			request.setAttribute("movieList", movieList2);
		}else {
			request.setAttribute("movieList", movieList);
		}
			
		request.setAttribute("flag", "advancedSearch");
		request.setAttribute("txt", txt);
		
		RequestDispatcher rd = request.getRequestDispatcher("searchResultList.jsp");
		rd.forward(request, response);
	}
	
	private void FindMovie(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//��ʼ��ȡ��Դ
		EditData editData = new EditData();
		editData.saveAll();
		//��ʾ��Դ���ҳɹ�
		request.setAttribute("information", "������ȡ�ɹ�");
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}
	
	private void CreateIndexs(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, SQLException {
		
		//������������ݣ���ʾ���������ɹ�
		CreateIndex createIndex = new CreateIndex();
		if(createIndex.isFull()) {  
			createIndex.createIndex(); 
			request.setAttribute("information", "���������ɹ�!");
		} else {
			request.setAttribute("information", "�����Ѵ���!");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}
	
}