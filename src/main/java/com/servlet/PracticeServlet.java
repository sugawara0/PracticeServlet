package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBUtil;
import com.bean.DataBean;

/**
 * Servlet implementation class PracticeServlet
 */
@WebServlet("/table")
public class PracticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PracticeServlet() {
		super();
		initializeDatabase();
	}
	
	// DB接続
	private void initializeDatabase() {
		try (Connection conn = DBUtil.getConnection();
				Statement stmt = conn.createStatement()) {
			
			// テーブルを作成
			stmt.execute("CREATE TABLE IF NOT EXISTS sample_table (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, value TEXT)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		List<DataBean> dataBeans = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM sample_table")) 
		{
			while (rs.next()) {
				var id = rs.getInt("id");
				var name = rs.getString("name");
				var value = rs.getString("value");
				dataBeans.add(new DataBean(id, name, value));
			}
			
			request.setAttribute("dataBeans", dataBeans);
			request.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(request, response);
		} catch (Exception e) {
			throw new ServletException("Database access error", e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("add".equals(action)) {
			addRecord(request, response);
		} else if ("delete".equals(action)) {
			deleteRecord(request, response);
		}
	}

	private void addRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String value = request.getParameter("value");

		String sql = "INSERT INTO sample_table (name, value) VALUES (?, ?)";
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, value);
			pstmt.executeUpdate();
			response.sendRedirect("PracticeServlet");
			} catch (SQLException e) {
				e.printStackTrace();
				response.getWriter().println("Database access error: " + e.getMessage());
			}
		}

	private void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		String sql = "DELETE FROM sample_table WHERE id = ?";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			response.sendRedirect("PracticeServlet");
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Database access error: " + e.getMessage());
		}
	}
}
