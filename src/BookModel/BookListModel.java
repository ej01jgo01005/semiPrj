package BookModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bModel.bk.BookList;
import View.BookBuy;

public class BookListModel {
	Connection con;
	
	public BookListModel() throws Exception {
		con=BookDBcon.getConnection();
	}
	

	
	public void bbInsertBook(BookList boli, int bbcount) throws Exception {
		con.setAutoCommit(false);
		
		String sql1="INSERT INTO BOOKLIST(BBNUM, BISBN,BTITLE,BWRITER,BPUBLISHER,BAGE,BCAUSE) " + 
				"VALUES(SEQ_BBO_CODE.nextval,?,?,?,?,?,?)";
		
		String sql2="INSERT INTO BOOKBUY(BBCODE,BBOCODE) "
				+ "VALUES(SEQ_BB_CODE.NEXTVAL,SEQ_BBO_CODE.CURRVAL)";
		
		PreparedStatement bps1=con.prepareStatement(sql1);
		bps1.setString(1, boli.getBISBN());
		bps1.setString(2, boli.getBTITLE());
		bps1.setString(3, boli.getBWRITER());
		bps1.setString(4, boli.getBPUBLISHER());
		bps1.setString(5, boli.getBAGE());
		bps1.setString(6, boli.getBCAUSE());
		
		PreparedStatement bps2=con.prepareStatement(sql2);
	
		int br1=bps1.executeUpdate();//sql실행
		int br2=bps2.executeUpdate();
		
		if (br1!=1||br2!=1) {
			con.rollback();
			System.out.println("롤백");
		}
		con.commit();
		bps1.close();
		bps2.close();
		con.setAutoCommit(true);
	}

	public BookList selectbyBk(int no) throws Exception {
		BookList boli=new BookList();
		String sql="SELECT * FROM BOOKLIST WHERE BBNUM="+no;
		PreparedStatement bps=con.prepareStatement(sql);
		ResultSet brs=bps.executeQuery();
		while (brs.next()) {
			boli.setBBNUM(Integer.parseInt(brs.getString("BBNUM")));
			boli.setBISBN(brs.getString("BISBN"));
			boli.setBTITLE(brs.getString("BTITLE"));
			boli.setBWRITER(brs.getString("BWRITER"));
			boli.setBPUBLISHER(brs.getString("BPUBLISHER"));
			boli.setBAGE(brs.getString("BAGE"));
			boli.setBCAUSE(brs.getString("BCAUSE"));
		}
		brs.close();
		bps.close();
		
		return boli;
	}

	public ArrayList searchBookList(int idx, String str) throws Exception {
		String[] key= {"BISBN","BWRITER","BTITLE"};//필드 이름과 동일해야함
		String sql="SELECT BBNUM,BISBN,BTITLE,BWRITER,BPUBLISHER,BAGE,BCAUSE " + 
				"FROM BOOKLIST " + 
				"WHERE "+key[idx]+" LIKE '%"+str+"%' ORDER BY BBNUM ASC";
		PreparedStatement bps=con.prepareStatement(sql);
		ResultSet brs=bps.executeQuery();
		ArrayList bdata=new ArrayList();
		while (brs.next()) {
			ArrayList btemp=new ArrayList();
			btemp.add(brs.getString("BBNUM"));
			btemp.add(brs.getString("BISBN"));
			btemp.add(brs.getString("BTITLE"));
			btemp.add(brs.getString("BWRITER"));
			btemp.add(brs.getString("BPUBLISHER"));
			btemp.add(brs.getString("BAGE"));
			btemp.add(brs.getString("BCAUSE"));
			
			bdata.add(btemp);//arraylist에 arraylist를 추가

		}

		brs.close();
		bps.close();
		
		return bdata;
			
	}



	public void deleteBookList(String bbode) throws SQLException {
		con.setAutoCommit(false);
		String bsql1="delete from booklist where BBNUM=?";
		String bsql2="delete from bookbuy where BBOCODE=?";
		
		PreparedStatement bps1=con.prepareStatement(bsql1);
		PreparedStatement bps2=con.prepareStatement(bsql2);
		
		try {
			bps1.setInt(1, Integer.parseInt(bbode));
			bps2.setInt(1, Integer.parseInt(bbode));
			
			int br2=bps2.executeUpdate();
			int br1=bps1.executeUpdate();
			
			bps1.close();
			bps2.close();
			
			if (br1!=1||br2!=1) {
				con.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		con.commit();
		con.setAutoCommit(true);
		
	}

}
