package BookModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import View.BookManage;
import bModel.bk.Book;

public class BookModel {
	Connection con;
	
	public BookModel() throws Exception {
		con=BookDBcon.getConnection();
	
	}

	public void insertBook(Book bo, int count) throws SQLException {
		con.setAutoCommit(false);//자동커밋 해제
		
		String sql1="INSERT INTO BOOKSTORE(BOOKNUM,GENRE,TITLE,WRITER,PUBLISHER,KEYWORD,SUMMARY) " + 
				"VALUES(SEQ_BO_CODE.NEXTVAL,?,?,?,?,?,?)";
		
		String sql2="INSERT INTO BOOK(BCODE,BOCODE) "
				+ "VALUES(SEQ_B_CODE.NEXTVAL,SEQ_BO_CODE.CURRVAL)";
		
		PreparedStatement ps1=con.prepareStatement(sql1);
		ps1.setString(1,bo.getGENRE());
		ps1.setString(2,bo.getTITLE());
		ps1.setString(3,bo.getWRITER());
		ps1.setString(4,bo.getPUBLISHER());
		ps1.setString(5, bo.getKEYWORD());
		ps1.setString(6,bo.getSUMMARY());
		
		PreparedStatement ps2 = con.prepareStatement(sql2);
		
		
		int r1=ps1.executeUpdate();//sql실행
		int r2=ps2.executeUpdate();
		
		if (r1!=1||r2!=1) {
			con.rollback();
			System.out.println("롤백");
		}
		con.commit();
		ps1.close();
		ps2.close();
		con.setAutoCommit(true);
	}
	public ArrayList searchBook(int idx, String str) throws Exception {
		
		
		//검색기능
		String[] key= {"TITLE","WRITER","PUBLISHER","KEYWORD"};//필드 이름과 동일해야함
		String sql="SELECT BOOKNUM,GENRE,TITLE,WRITER,PUBLISHER,KEYWORD " + 
				"FROM BOOKSTORE " + 
				"WHERE "+key[idx]+" LIKE '%"+str+"%' ORDER BY BOOKNUM ASC";
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		ArrayList data=new ArrayList();
		
		while (rs.next()) {
			ArrayList temp=new ArrayList();
			temp.add(rs.getString("BOOKNUM"));
			temp.add(rs.getString("GENRE"));
			temp.add(rs.getString("TITLE"));
			temp.add(rs.getString("WRITER"));
			temp.add(rs.getString("PUBLISHER"));
			temp.add(rs.getString("KEYWORD"));
			data.add(temp);//arraylist에 arraylist를 추가

		}

		rs.close();
		ps.close();
		
		return data;
		
		
	}

	public Book selectbyPk(int no) throws Exception {
		// jtable에서 클릭한 레코드의 정보를 Book 타입으로 저장해서 return
		Book bo=new Book();
		String sql="SELECT * FROM BOOKSTORE WHERE BOOKNUM="+no;
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while (rs.next()) {
			bo.setBOOKNUM(Integer.parseInt(rs.getString("booknum")));
			bo.setGENRE(rs.getString("genre"));
			bo.setTITLE(rs.getString("title"));
			bo.setWRITER(rs.getString("writer"));
			bo.setPUBLISHER(rs.getString("publisher"));
			bo.setKEYWORD(rs.getString("KEYWORD"));
			bo.setSUMMARY(rs.getString("summary"));
		}
		rs.close();
		ps.close();
		
		return bo;
	}
//		BOOKNUM
//		GENRE
//		TITLE
//		WRITER
//		PUBLISHER
//		SUMMARY
	
	public void modifyBook(Book bo) throws SQLException {
		//수정
		String sql="UPDATE BOOKSTORE SET GENRE=?, "
				+ "TITLE=?, "
				+ "WRITER=?, "
				+ "PUBLISHER=?, "
				+ "KEYWORD=?, "
				+ "SUMMARY=? "
				+ "WHERE BOOKNUM=?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, bo.getGENRE());
		ps.setString(2, bo.getTITLE());
		ps.setString(3, bo.getWRITER());
		ps.setString(4, bo.getPUBLISHER());
		ps.setString(5, bo.getKEYWORD());
		ps.setString(6, bo.getSUMMARY());
		ps.setInt(7, bo.getBOOKNUM());
		
		ps.executeUpdate();
		ps.close();
	}

	public void deleteBook(String bode) throws SQLException {
		
		con.setAutoCommit(false);
		String sql1="delete from bookstore where BOOKNUM=?";
		String sql2="delete from book where BOCODE=?";
		
		PreparedStatement ps1=con.prepareStatement(sql1);
		PreparedStatement ps2=con.prepareStatement(sql2);
		
		try {
			ps1.setInt(1, Integer.parseInt(bode));
			ps2.setInt(1, Integer.parseInt(bode));
			
			int r2=ps2.executeUpdate();
			int r1=ps1.executeUpdate();
			
			ps1.close();
			ps2.close();
			
			if (r1!=1||r2!=1) {
				con.rollback();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		con.commit();
		con.setAutoCommit(true);
		
	}
	

}
