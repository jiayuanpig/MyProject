package ccnu.commen.Spider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Locale;
//import java.util.PropertyResourceBundle;

/**
 * 数据库相关操作:
 * 	数据库连接与关闭
 * 	数据查询
 * 	数据修改
 * @author ADMIN-JY
 *
 */
public class ConnectDB {
	static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static String DB_URL = "jdbc:mysql://localhost:3306/moviepicker?characterEncoding=UTF8";
	static String USERNAME = "root";
	static String PASSWORD = "";
	
	/*static {
		//从配置文件中获取数据库信息
		PropertyResourceBundle configBundle = (PropertyResourceBundle) PropertyResourceBundle
				.getBundle("User",new Locale("cn", "CN"));
		
		if (configBundle == null) {
			System.out.println("文件User_cn_CN.properties读入错误");
		}
		
		JDBC_DRIVER = configBundle.getString("JDBC_DRIVER");
		DB_URL = configBundle.getString("DB_URL");
		USERNAME = configBundle.getString("USERNAME");
		PASSWORD = configBundle.getString("PASSWORD");
	}*/
	
	Connection conn = null;
	Statement state = null;
	
	/**
	 * 连接数据库
	 * @return
	 */
	public Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("已连接数据库...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 关闭所有连接
	 * @param conn
	 * @param state
	 */
	public void closeAll() {
		if(conn != null) {
			try {
				conn.close();
				System.out.println("连接已关闭...");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 执行增\删\改功能
	 * @param sql
	 */
	public void excuteUpdate(String sql) {
		
		try {
			conn = this.getConnection();
			if(conn == null) {
				System.out.println("数据库连接失败...");
			}else {
				state = conn.createStatement();
				state.executeUpdate(sql);
				System.out.println("数据更新成功!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行查询功能
	 * @param sql	
	 * @return	返回ResultSet 查询结果
	 */
	public ResultSet excuteQuery(String sql) {
		
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			state = conn.createStatement();
			resultSet = state.executeQuery(sql);
			System.out.println("数据查询成功!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
}
