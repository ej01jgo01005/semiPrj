package bookRentalStore;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import View.BookBuy;
import View.BookManage;

public class bookStore extends JFrame{
	BookManage book;
	BookBuy booklist;
	
	public bookStore() {
		book=new BookManage();
		booklist=new BookBuy();
		
		JTabbedPane pane1 = new JTabbedPane();
		pane1.add("���� ����", book);
		pane1.addTab("���� ���� ��û", booklist);
		pane1.setSelectedIndex(0);
		add("Center",pane1);
		
		setSize(1100, 600);
		setVisible(true);
		
		
	}
	
	
	
	public static void main(String[] args) {
		new bookStore();
	}

}
