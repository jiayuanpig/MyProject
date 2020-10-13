package ccnu.commen.Spider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Locale;
//import java.util.PropertyResourceBundle;

/**
 * ���ݿ���ز���:
 * 	���ݿ�������ر�
 * 	���ݲ�ѯ
 * 	�����޸�
 * @author ADMIN-JY
 *
 */
public class ConnectDB {
	static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static String DB_URL = "jdbc:mysql://localhost:3306/moviepicker?characterEncoding=UTF8";
	static String USERNAME = "root";
	static String PASSWORD = "";
	
	/*static {
		//�������ļ��л�ȡ���ݿ���Ϣ
		PropertyResourceBundle configBundle = (PropertyResourceBundle) PropertyResourceBundle
				.getBundle("User",new Locale("cn", "CN"));
		
		if (configBundle == null) {
			System.out.println("�ļ�User_cn_CN.properties�������");
		}
		
		JDBC_DRIVER = configBundle.getString("JDBC_DRIVER");
		DB_URL = configBundle.getString("DB_URL");
		USERNAME = configBundle.getString("USERNAME");
		PASSWORD = configBundle.getString("PASSWORD");
	}*/
	
	Connection conn = null;
	Statement state = null;
	
	/**
	 * �������ݿ�
	 * @return
	 */
	public Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("���������ݿ�...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * �ر���������
	 * @param conn
	 * @param state
	 */
	public void closeAll() {
		if(conn != null) {
			try {
				conn.close();
				System.out.println("�����ѹر�...");
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
	 * ִ����\ɾ\�Ĺ���
	 * @param sql
	 */
	public void excuteUpdate(String sql) {
		
		try {
			conn = this.getConnection();
			if(conn == null) {
				System.out.println("���ݿ�����ʧ��...");
			}else {
				state = conn.createStatement();
				state.executeUpdate(sql);
				System.out.println("���ݸ��³ɹ�!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ִ�в�ѯ����
	 * @param sql	
	 * @return	����ResultSet ��ѯ���
	 */
	public ResultSet excuteQuery(String sql) {
		
		ResultSet resultSet = null;
		try {
			conn = getConnection();
			state = conn.createStatement();
			resultSet = state.executeQuery(sql);
			System.out.println("���ݲ�ѯ�ɹ�!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
}