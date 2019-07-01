package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import BookModel.BookListModel;
import bModel.bk.Book;
import bModel.bk.BookList;
//신청사유 추가
//장르 지워도 될듯

public class BookBuy extends Panel implements ActionListener{
	ButtonGroup bg;
	
	JTextField tfbbNum, tfbbISBN, tfbbTitle, tfbbWriter, tfbbPublisher;
	JRadioButton cbbbAll, cbbbfive, cbbbtwelve,cbbbAdult;
	JTextArea tabbCause;
	
	JCheckBox cbbbMulti;
	JTextField tfbbCount;
	
	JButton bbbInsert, bbbDelete;
	
	JComboBox combbSearch;
	JTextField tfbbSearch;
	JTable bbtableBook;
	
	JTextField tfbbAge;
	
	JButton bbbReset;
	JTextField tfbbMessage;
	
	BookListModel boliModel;
	BTListModel btboliModel;

	
	public BookBuy() {
		bbAddLayout();//화면
		bbConnect();//연결
		bbEventProc();//수신기
	}
	
	private void bbConnect() {
		try {
			boliModel=new BookListModel();
			System.out.println("신청탭 연결 성공");
		} catch (Exception e) {
			System.out.println("신청탭 연결 실패");
			e.printStackTrace();
		}
	}
	
	private void bbEventProc() {
		bbbInsert.addActionListener(this);
		bbbDelete.addActionListener(this);
		tfbbSearch.addActionListener(this);
		cbbbMulti.addActionListener(this);
		cbbbAll.addActionListener(this);
		cbbbfive.addActionListener(this);
		cbbbtwelve.addActionListener(this);
		cbbbAdult.addActionListener(this);
		bbbReset.addActionListener(this);
		
		bbtableBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				int row=bbtableBook.getSelectedRow();
				int col=0;
				
				String data=(String) bbtableBook.getValueAt(row, col);
				int no=Integer.parseInt(data);
				
				try {
					BookList boli=boliModel.selectbyBk(no);
					selectbyBk(boli);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		private void selectbyBk(BookList boli) {
			tfbbNum.setText(String.valueOf(boli.getBBNUM()));
			tfbbISBN.setText(String.valueOf(boli.getBISBN()));
			tfbbTitle.setText(boli.getBTITLE());
			tfbbWriter.setText(boli.getBWRITER());
			tfbbPublisher.setText(boli.getBPUBLISHER());
			tfbbAge.setText(String.valueOf(boli.getBAGE()));
			tabbCause.setText(boli.getBCAUSE());
				
		}
	});
}

	private void bbAddLayout() {
		//레이아웃
		//탭
		tfbbNum=new JTextField();
		tfbbISBN = new JTextField();
		tfbbTitle = new JTextField();
		tfbbWriter = new JTextField();
		tfbbPublisher = new JTextField();
		
		tfbbAge = new JTextField(10);
	
		//버튼그룹
		
		cbbbAll = new JRadioButton("전체연령가", true);
		cbbbfive = new JRadioButton("5세 이상");
		cbbbtwelve = new JRadioButton("12세 이상");
		cbbbAdult = new JRadioButton("19세 이상");
		
		bg=new ButtonGroup();
		bg.add(cbbbAll);
		bg.add(cbbbfive);
		bg.add(cbbbtwelve);
		bg.add(cbbbAdult);
		
		
		tabbCause = new JTextArea();
		tabbCause.setLineWrap(true);
		
		cbbbMulti = new JCheckBox("다중신청");
		tfbbCount = new JTextField("1",5);
		
		bbbInsert = new JButton("신청");
		bbbDelete = new JButton("신청취소");
		bbbReset = new JButton("초기화");
		
		String[] cbbbSearch = {"ISBN","제목","작가"};
		combbSearch = new JComboBox(cbbbSearch);
		tfbbSearch = new JTextField(15);
		
		tfbbMessage = new JTextField(40);
		
		//////////
		btboliModel= new BTListModel();
		bbtableBook=new JTable(btboliModel);
		bbtableBook.setModel(btboliModel);
		
		//화면구성
		//west(왼쪽)판넬
		JPanel pb_west=new JPanel();
		pb_west.setLayout(new BorderLayout());
		
		//왼쪽 가운데
		JPanel pb_west_center=new JPanel();
		pb_west_center.setLayout(new GridLayout(3, 0));
		
		//왼쪽 가운데 위쪽 정보입력
		JPanel pb_west_center_north=new JPanel();
		pb_west_center_north.setLayout(new GridLayout(5, 2));
		pb_west_center_north.add(new JLabel("신청번호"));
		pb_west_center_north.add(tfbbNum);
		pb_west_center_north.add(new JLabel("ISBN"));
		pb_west_center_north.add(tfbbISBN);	
		pb_west_center_north.add(new JLabel("제목"));
		pb_west_center_north.add(tfbbTitle);
		pb_west_center_north.add(new JLabel("작가"));
		pb_west_center_north.add(tfbbWriter);
		pb_west_center_north.add(new JLabel("출판사"));
		pb_west_center_north.add(tfbbPublisher);
		
		//////
		JPanel pb_west_center_north2=new JPanel();
		pb_west_center_north2.setLayout(new FlowLayout());
		
		pb_west_center_north2.add(cbbbAll);
		pb_west_center_north2.add(cbbbfive);
		pb_west_center_north2.add(cbbbtwelve);
		pb_west_center_north2.add(cbbbAdult);
		
		pb_west_center_north2.add(tfbbAge);
		
		pb_west_center_north2.setBorder(new TitledBorder("구독가"));
		//////
		
		//왼쪽 가운데 가운데 구독가, 줄거리
		JPanel pb_west_center_center=new JPanel();
		pb_west_center_center.setLayout(new BorderLayout());
	
		pb_west_center_center.add(new JLabel("신청 사유"),BorderLayout.WEST);
		pb_west_center_center.add(tabbCause, BorderLayout.CENTER);
		
		//왼쪽 가운데 판넬에 판넬 추가
		pb_west_center.add(pb_west_center_north);
		pb_west_center.add(pb_west_center_north2);
		
		pb_west_center.add(pb_west_center_center);
		
		//경계선
		pb_west_center.setBorder(new TitledBorder("신청희망도서 정보입력"));
		
		//왼쪽 아래
		JPanel pb_west_south = new JPanel();
		pb_west_south.setLayout(new GridLayout(2, 1));
		
		//왼쪽 아래 판넬
		JPanel pb_west_south_1=new JPanel();
		pb_west_south_1.setLayout(new FlowLayout());
		pb_west_south_1.add(cbbbMulti);
		pb_west_south_1.add(tfbbCount);
		pb_west_south_1.add(new JLabel("권"));
		pb_west_south_1.setBorder(new TitledBorder("다중입력시선택"));
		
		JPanel pb_west_south_2=new JPanel();
		pb_west_south_2.setLayout(new GridLayout(1, 2));
		pb_west_south_2.add(bbbInsert);
		pb_west_south_2.add(bbbDelete);
				
		pb_west_south.add(pb_west_south_1);
		pb_west_south.add(pb_west_south_2);
		
		pb_west.add(pb_west_center,BorderLayout.CENTER);
		pb_west.add(pb_west_south,BorderLayout.SOUTH);
		
		//east 판넬 구성
		JPanel pb_east = new JPanel();
		pb_east.setLayout(new BorderLayout());
				
		//오른쪽의 위쪽
		JPanel pb_east_north=new JPanel();
		pb_east_north.add(combbSearch);
		pb_east_north.add(tfbbSearch);
		
		pb_east_north.add(bbbReset);
		
		JPanel pb_east_north_center=new JPanel();
		pb_east_north_center.add(tfbbMessage);
		
		pb_east.add(pb_east_north_center);
		
		//경계선
		pb_east_north.setBorder(new TitledBorder("신청도서 검색"));
		pb_east.add(pb_east_north, BorderLayout.NORTH);
		
		pb_east.add(new JScrollPane(bbtableBook),BorderLayout.SOUTH);
	
		setLayout(new GridLayout(1, 2));
		add(pb_west);
		add(pb_east);
		
		
		tabbCause.setEditable(false);
		tfbbNum.setEditable(false);
		tfbbCount.setEditable(false);
		tfbbMessage.setEditable(false);
	}
	
	class BTListModel extends AbstractTableModel{
		ArrayList bdata = new ArrayList();
		String[] columnNames= {"신청번호","ISBN","제목","작가","출판사","대상연령","신청사유"};

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return bdata.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			ArrayList btemp=(ArrayList)bdata.get(row);
			return btemp.get(col);
		}
		public String getColumnName(int col) {
			return columnNames[col];
		}
		
}


	@Override
	public void actionPerformed(ActionEvent be) {
		Object bevt=be.getSource();
		
		if (bevt==cbbbAll) {
			tfbbAge.setText("전체연령가");
			tabbCause.setEditable(false);
			tabbCause.setText(null);
		}else if(bevt==cbbbfive){
			tfbbAge.setText("5세 이상");
			tabbCause.setEditable(false);
			tabbCause.setText(null);
		}else if(bevt==cbbbtwelve){
			tfbbAge.setText("12세 이상");
			tabbCause.setEditable(false);
			tabbCause.setText(null);
		}else if(bevt==cbbbAdult){
			tfbbAge.setText("성인구독가");
			tabbCause.setEditable(true);
		}
		
		
		if (bevt==bbbInsert) {
			bbInsert();
		}else if (bevt==tfbbSearch) {
			bbSearch();
		}else if(bevt==bbbDelete) {
			bbDelete();
		}else if(bevt==cbbbMulti) {
			tfbbCount.setEditable(true);
		}else if(bevt==bbbReset) {
			bbReset();
		}
	}

	private void bbReset() {
		tfbbNum.setText(null);
		tfbbISBN.setText(null);
		tfbbWriter.setText(null);
		tfbbPublisher.setText(null);
		tfbbAge.setText(null);
		tfbbTitle.setText(null);
		tabbCause.setText(null);
		tfbbSearch.setText(null);
		tfbbMessage.setText(null);
	}

	private void bbDelete() {
		String bbode = tfbbNum.getText();
		
		try {
			boliModel.deleteBookList(bbode);
			JOptionPane.showMessageDialog(null, "취소 완료");
			bbReset();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "취소 실패");
			e.printStackTrace();
		}
	}
	

	private void bbSearch() {
		int idx=combbSearch.getSelectedIndex();
		String str=tfbbSearch.getText();
		
		try {
			ArrayList bdata=(ArrayList) boliModel.searchBookList(idx,str);
			btboliModel.bdata=bdata;
			bbtableBook.setModel(btboliModel);
			btboliModel.fireTableDataChanged();
			
			if(bdata.size()==0) {
				tfbbMessage.setText("신청 내역이 존재하지 않습니다.");
			}else {
				tfbbMessage.setText(" ");
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void bbInsert() {
		BookList boli = new BookList();
		boli.setBISBN(tfbbISBN.getText());
		boli.setBTITLE(tfbbTitle.getText());
		boli.setBWRITER(tfbbWriter.getText());
		boli.setBPUBLISHER(tfbbPublisher.getText());
		boli.setBAGE(tfbbAge.getText());
		boli.setBCAUSE(tabbCause.getText());

		int bbcount = Integer.parseInt(tfbbCount.getText());
		
		try {
			for (int i = 0; i < bbcount; i++) {
				boliModel.bbInsertBook(boli, bbcount);
			}
			JOptionPane.showMessageDialog(null, "신청완료");
			
			tfbbNum.setText(null);
			tfbbISBN.setText(null);
			tfbbTitle.setText(null);
			tfbbWriter.setText(null);
			tfbbPublisher.setText(null);
			tfbbAge.setText(null);
			tabbCause.setText(null);
			
			tabbCause.setEditable(false);
			tfbbCount.setEditable(false);
			
			tfbbCount.setText("1");
			
			cbbbMulti.setSelected(false);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "신청 실패");
			e.printStackTrace();
		}
	}

}
