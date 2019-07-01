package BookModel;

import java.sql.Connection;
import java.sql.DriverManager;

public class BookDBcon {
	static Connection con;
	private BookDBcon() throws Exception {
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String user="scott";
		String password="123456";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		con=DriverManager.getConnection(url, user, password);
		System.out.println("접속 완료");
	}
	
	public static Connection getConnection() throws Exception {
		if (con==null) {
			new BookDBcon();
		}
		return con;
	}

}
