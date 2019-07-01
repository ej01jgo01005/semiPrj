package View;
//sql190619
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import BookModel.BookListModel;
import BookModel.BookModel;
import View.BookBuy.BTListModel;
import bModel.bk.Book;
import bModel.bk.BookList;

public class BookManage extends Panel implements ActionListener{
	JTextField tfBookNum, tfBookTitle, tfWriter, tfPublisher, tfKeyword;
	JComboBox comBookJenre;
	JTextArea taSummary;
	
	JCheckBox cbMultiInsert;
	JTextField tfInsertCount;
	
	JButton bBookInsert, bBookModify,bBookDelete;
	
	JComboBox comBookSearch;
	JTextField tfBookSearch;
	JTable tableBook;
	
	JTextField tfmessage;
	JTextField tfHowMany;
	
	JButton bBookReset;
	
	BookModel bModel;
	BookTableModel tbbModelBook;

	
	public BookManage() {
		
		bAddLayout();//화면설계
		bConnectDB();//db연결
		bEventProc();//수신기
		initStyle();//비활성화
		
	}


	private void initStyle() {
		tfBookNum.setEditable(false);
		tfInsertCount.setEditable(false);
		tfmessage.setEditable(false);
		tfHowMany.setEditable(false);
	}

	
	private void bConnectDB() {
		try {
			bModel=new BookModel();
			System.out.println("책대여점 연결 성공");
		} catch (Exception e) {
			System.out.println("책대여점 연결 실패");
			e.printStackTrace();
		}
	}

	private void bEventProc() {
		//수신기
		cbMultiInsert.addActionListener(this);
		bBookDelete.addActionListener(this);
		bBookInsert.addActionListener(this);
		bBookModify.addActionListener(this);
		tfBookSearch.addActionListener(this);
		
		bBookReset.addActionListener(this);
		
		//jtable 리스너 부착
		tableBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=tableBook.getSelectedRow();
				int col=0;
				
				String data=(String) tableBook.getValueAt(row, col);
				int no=Integer.parseInt(data);
				
				try {
					Book bo=bModel.selectbyPk(no);
					selectbyPk(bo);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		private void selectbyPk(Book bo) {
			//클릭 레코드를 텍스트필드에
			tfBookNum.setText(String.valueOf(bo.getBOOKNUM()));
			tfBookTitle.setText(bo.getTITLE());
			tfWriter.setText(bo.getWRITER());
			tfPublisher.setText(bo.getPUBLISHER());
			tfKeyword.setText(bo.getKEYWORD());
			taSummary.setText(bo.getSUMMARY());
			comBookJenre.setSelectedItem(bo.getGENRE());
		}
		});
	}

	private void bAddLayout() {
		//탭 디자인 구성
		tfBookNum = new JTextField();
		tfBookTitle = new JTextField();
		tfWriter = new JTextField();
		tfPublisher = new JTextField();
		
		tfKeyword=new JTextField();
		
		String[] cbJenreStr = {"로맨스","판타지","문학","수필","스릴러","추리","SF"};
		comBookJenre = new JComboBox(cbJenreStr);

		
		taSummary = new JTextArea();
		taSummary.setLineWrap(true);
		
		JScrollPane taSu = new JScrollPane(taSummary,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		cbMultiInsert = new JCheckBox("다중입고");
		tfInsertCount = new JTextField("1",5);
		
		//버튼
		bBookInsert=new JButton("입고");
		bBookModify=new JButton("수정");
		bBookDelete=new JButton("삭제");
		
		bBookReset=new JButton("초기화");
		
		String[] cbBookSearch= {"제목","작가","출판사","키워드"};
		comBookSearch = new JComboBox(cbBookSearch);
		tfBookSearch=new JTextField(15);
		
		
		tbbModelBook=new BookTableModel();
		tableBook=new JTable(tbbModelBook);
		tableBook.setModel(tbbModelBook);
		
		tfmessage=new JTextField(30);
		tfHowMany=new JTextField(10);
		
		//화면구성
		//west 판넬 구성
		JPanel p_west=new JPanel();
		p_west.setLayout(new BorderLayout());
		
		//왼쪽 가운데
		JPanel p_west_center=new JPanel();
		p_west_center.setLayout(new BorderLayout());
		
		//왼쪽 가운데 위쪽
		JPanel p_west_center_north=new JPanel();
		p_west_center_north.setLayout(new GridLayout(6, 2));
		p_west_center_north.add(new JLabel("책 번호"));
		p_west_center_north.add(tfBookNum);
		
		p_west_center_north.add(new Label("장르"));
		p_west_center_north.add(comBookJenre);
		
		p_west_center_north.add(new Label("제목"));
		p_west_center_north.add(tfBookTitle);

		p_west_center_north.add(new Label("작가"));
		p_west_center_north.add(tfWriter);
		
		p_west_center_north.add(new Label("출판사"));
		p_west_center_north.add(tfPublisher);
		
		p_west_center_north.add(new Label("키워드 (단어 위주로 입력)"));
		p_west_center_north.add(tfKeyword);
		
		
		//왼쪽 가운데 가운데 책 줄거리
		JPanel p_west_center_center = new JPanel();
		p_west_center_center.setLayout(new BorderLayout());
				
		p_west_center_center.add(new JLabel("줄거리"),BorderLayout.WEST);
		p_west_center_center.add(taSu, BorderLayout.CENTER);
		
		//왼쪽 가운데 판넬에 두 개의 판넬 추가
		p_west_center.add(p_west_center_north,BorderLayout.NORTH);
		p_west_center.add(p_west_center_center,BorderLayout.CENTER);
		
		//경계선
		p_west_center.setBorder(new TitledBorder("도서정보입력"));
		
		//왼쪽 아래
		JPanel p_west_south=new JPanel();
		p_west_south.setLayout(new GridLayout(2,1));
		
		//왼쪽 아래에 사용될 판넬
		JPanel p_west_south_1=new JPanel();
		p_west_south_1.setLayout(new FlowLayout());
		p_west_south_1.add(cbMultiInsert);
		p_west_south_1.add(tfInsertCount);
		p_west_south_1.add(new JLabel("권"));
		p_west_south_1.setBorder(new TitledBorder("다중입력시선택"));
				
		JPanel p_west_south_2=new JPanel();
		p_west_south_2.setLayout(new GridLayout(1, 3));
		p_west_south_2.add(bBookInsert);
		p_west_south_2.add(bBookModify);
		p_west_south_2.add(bBookDelete);
				
		p_west_south.add(p_west_south_1);
		p_west_south.add(p_west_south_2);
				
		p_west.add(p_west_center,BorderLayout.CENTER);
		p_west.add(p_west_south,BorderLayout.SOUTH);
		
		//east 판넬 구성
		JPanel p_east = new JPanel();
		p_east.setLayout(new BorderLayout());
		
		//오른쪽의 위쪽
		JPanel p_east_north=new JPanel();
		p_east_north.add(comBookSearch);
		p_east_north.add(tfBookSearch);
		
		p_east_north.add(bBookReset);
		
		//===========추가
		JPanel p_east_north_center=new JPanel();
		p_east_north_center.add(tfmessage);
		p_east_north_center.add(new JLabel("도서 수량"));
		p_east_north_center.add(tfHowMany);
		p_east_north_center.add(new JLabel("권"));
				
		p_east.add(p_east_north_center, BorderLayout.CENTER);
		

		//경계선
		p_east_north.setBorder(new TitledBorder("책 검색"));
		p_east.add(p_east_north, BorderLayout.NORTH);
		
		p_east.add(new JScrollPane(tableBook),BorderLayout.SOUTH);
	
				
		setLayout(new GridLayout(1, 2));
		add(p_west);
		add(p_east);
	}
	
	class BookTableModel extends AbstractTableModel{
		
		ArrayList data=new ArrayList();
		String[] columnNames = {"책 번호","장르","제목","작가","출판사","키워드"};

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			ArrayList temp=(ArrayList) data.get(row);
			return temp.get(col);
		}
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object evt=e.getSource();
		if (evt==bBookInsert) {//입고
			insertBook();
		}else if (evt==tfBookSearch) {
			searchBook();
		}else if(evt==bBookModify) {
			modify();
		}else if(evt==bBookDelete) {
			delete();
			
		}else if(evt==cbMultiInsert) {
		tfInsertCount.setEditable(true);
		}else if(evt==bBookReset) {
			reset();
		}
	}


	private void reset() {
		tfBookNum.setText(null);
		tfWriter.setText(null);
		tfPublisher.setText(null);
		tfBookTitle.setText(null);
		taSummary.setText(null);
		tfKeyword.setText(null);
		tfBookSearch.setText(null);
	}


	private void delete() {
		String bode = tfBookNum.getText();
		
		try {
			bModel.deleteBook(bode);
			JOptionPane.showMessageDialog(null, "삭제완료");
			reset();
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "삭제실패");
			e1.printStackTrace();
		}
		
		
	}


	private void modify() {
		Book bo = new Book();
		bo.setBOOKNUM(Integer.parseInt(tfBookNum.getText()));
		bo.setTITLE(tfBookTitle.getText());
		bo.setWRITER(tfWriter.getText());
		bo.setPUBLISHER(tfPublisher.getText());
		bo.setGENRE((String)comBookJenre.getSelectedItem());
		bo.setKEYWORD(tfKeyword.getText());
		bo.setSUMMARY(taSummary.getText());
			try {
				bModel.modifyBook(bo);
				JOptionPane.showMessageDialog(null, "수정 완료");
				
				reset();
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "수정실패");
				e1.printStackTrace();
			}
	}


	private void searchBook() {
		int idx=comBookSearch.getSelectedIndex();
		String str=tfBookSearch.getText();
		
		try {
			ArrayList data=bModel.searchBook(idx, str);
			tbbModelBook.data=data;
			tableBook.setModel(tbbModelBook);
			tbbModelBook.fireTableDataChanged();
			
			//도서수량
			tfHowMany.setText(str.valueOf(data.size()));

			if(data.size()==0) {
				tfmessage.setText("찾으시는 항목이 존재하지 않습니다. 다시 입력해주세요.");
			}
			else {
				tfmessage.setText(" ");
			}


		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
	}


	private void insertBook() {
		
		Book bo=new Book();
		bo.setGENRE((String) comBookJenre.getSelectedItem());
		bo.setPUBLISHER(tfPublisher.getText());
		bo.setWRITER(tfWriter.getText());
		bo.setSUMMARY(taSummary.getText());
		bo.setTITLE(tfBookTitle.getText());
		bo.setKEYWORD(tfKeyword.getText());
		
		int count = Integer.parseInt(tfInsertCount.getText());
		
		
		try {
			for (int i = 0; i < count; i++) {
			bModel.insertBook(bo,count);
			}
			JOptionPane.showMessageDialog(null, "["+tfBookTitle.getText()+"] 입고되었습니다.");
			
			//텍스트지우기
			tfBookNum.setText(null);
			tfWriter.setText(null);
			tfBookTitle.setText(null);
			tfPublisher.setText(null);
			taSummary.setText(null);
			tfKeyword.setText(null);
			tfInsertCount.setText("1");
			
			cbMultiInsert.setSelected(false);

			tfInsertCount.setEditable(false);
			
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "입고 실패");
			e1.printStackTrace();
		}
	}
}
