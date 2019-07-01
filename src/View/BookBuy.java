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
//��û���� �߰�
//�帣 ������ �ɵ�

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
		bbAddLayout();//ȭ��
		bbConnect();//����
		bbEventProc();//���ű�
	}
	
	private void bbConnect() {
		try {
			boliModel=new BookListModel();
			System.out.println("��û�� ���� ����");
		} catch (Exception e) {
			System.out.println("��û�� ���� ����");
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
		//���̾ƿ�
		//��
		tfbbNum=new JTextField();
		tfbbISBN = new JTextField();
		tfbbTitle = new JTextField();
		tfbbWriter = new JTextField();
		tfbbPublisher = new JTextField();
		
		tfbbAge = new JTextField(10);
	
		//��ư�׷�
		
		cbbbAll = new JRadioButton("��ü���ɰ�", true);
		cbbbfive = new JRadioButton("5�� �̻�");
		cbbbtwelve = new JRadioButton("12�� �̻�");
		cbbbAdult = new JRadioButton("19�� �̻�");
		
		bg=new ButtonGroup();
		bg.add(cbbbAll);
		bg.add(cbbbfive);
		bg.add(cbbbtwelve);
		bg.add(cbbbAdult);
		
		
		tabbCause = new JTextArea();
		tabbCause.setLineWrap(true);
		
		cbbbMulti = new JCheckBox("���߽�û");
		tfbbCount = new JTextField("1",5);
		
		bbbInsert = new JButton("��û");
		bbbDelete = new JButton("��û���");
		bbbReset = new JButton("�ʱ�ȭ");
		
		String[] cbbbSearch = {"ISBN","����","�۰�"};
		combbSearch = new JComboBox(cbbbSearch);
		tfbbSearch = new JTextField(15);
		
		tfbbMessage = new JTextField(40);
		
		//////////
		btboliModel= new BTListModel();
		bbtableBook=new JTable(btboliModel);
		bbtableBook.setModel(btboliModel);
		
		//ȭ�鱸��
		//west(����)�ǳ�
		JPanel pb_west=new JPanel();
		pb_west.setLayout(new BorderLayout());
		
		//���� ���
		JPanel pb_west_center=new JPanel();
		pb_west_center.setLayout(new GridLayout(3, 0));
		
		//���� ��� ���� �����Է�
		JPanel pb_west_center_north=new JPanel();
		pb_west_center_north.setLayout(new GridLayout(5, 2));
		pb_west_center_north.add(new JLabel("��û��ȣ"));
		pb_west_center_north.add(tfbbNum);
		pb_west_center_north.add(new JLabel("ISBN"));
		pb_west_center_north.add(tfbbISBN);	
		pb_west_center_north.add(new JLabel("����"));
		pb_west_center_north.add(tfbbTitle);
		pb_west_center_north.add(new JLabel("�۰�"));
		pb_west_center_north.add(tfbbWriter);
		pb_west_center_north.add(new JLabel("���ǻ�"));
		pb_west_center_north.add(tfbbPublisher);
		
		//////
		JPanel pb_west_center_north2=new JPanel();
		pb_west_center_north2.setLayout(new FlowLayout());
		
		pb_west_center_north2.add(cbbbAll);
		pb_west_center_north2.add(cbbbfive);
		pb_west_center_north2.add(cbbbtwelve);
		pb_west_center_north2.add(cbbbAdult);
		
		pb_west_center_north2.add(tfbbAge);
		
		pb_west_center_north2.setBorder(new TitledBorder("������"));
		//////
		
		//���� ��� ��� ������, �ٰŸ�
		JPanel pb_west_center_center=new JPanel();
		pb_west_center_center.setLayout(new BorderLayout());
	
		pb_west_center_center.add(new JLabel("��û ����"),BorderLayout.WEST);
		pb_west_center_center.add(tabbCause, BorderLayout.CENTER);
		
		//���� ��� �ǳڿ� �ǳ� �߰�
		pb_west_center.add(pb_west_center_north);
		pb_west_center.add(pb_west_center_north2);
		
		pb_west_center.add(pb_west_center_center);
		
		//��輱
		pb_west_center.setBorder(new TitledBorder("��û������� �����Է�"));
		
		//���� �Ʒ�
		JPanel pb_west_south = new JPanel();
		pb_west_south.setLayout(new GridLayout(2, 1));
		
		//���� �Ʒ� �ǳ�
		JPanel pb_west_south_1=new JPanel();
		pb_west_south_1.setLayout(new FlowLayout());
		pb_west_south_1.add(cbbbMulti);
		pb_west_south_1.add(tfbbCount);
		pb_west_south_1.add(new JLabel("��"));
		pb_west_south_1.setBorder(new TitledBorder("�����Է½ü���"));
		
		JPanel pb_west_south_2=new JPanel();
		pb_west_south_2.setLayout(new GridLayout(1, 2));
		pb_west_south_2.add(bbbInsert);
		pb_west_south_2.add(bbbDelete);
				
		pb_west_south.add(pb_west_south_1);
		pb_west_south.add(pb_west_south_2);
		
		pb_west.add(pb_west_center,BorderLayout.CENTER);
		pb_west.add(pb_west_south,BorderLayout.SOUTH);
		
		//east �ǳ� ����
		JPanel pb_east = new JPanel();
		pb_east.setLayout(new BorderLayout());
				
		//�������� ����
		JPanel pb_east_north=new JPanel();
		pb_east_north.add(combbSearch);
		pb_east_north.add(tfbbSearch);
		
		pb_east_north.add(bbbReset);
		
		JPanel pb_east_north_center=new JPanel();
		pb_east_north_center.add(tfbbMessage);
		
		pb_east.add(pb_east_north_center);
		
		//��輱
		pb_east_north.setBorder(new TitledBorder("��û���� �˻�"));
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
		String[] columnNames= {"��û��ȣ","ISBN","����","�۰�","���ǻ�","��󿬷�","��û����"};

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
			tfbbAge.setText("��ü���ɰ�");
			tabbCause.setEditable(false);
			tabbCause.setText(null);
		}else if(bevt==cbbbfive){
			tfbbAge.setText("5�� �̻�");
			tabbCause.setEditable(false);
			tabbCause.setText(null);
		}else if(bevt==cbbbtwelve){
			tfbbAge.setText("12�� �̻�");
			tabbCause.setEditable(false);
			tabbCause.setText(null);
		}else if(bevt==cbbbAdult){
			tfbbAge.setText("���α�����");
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
			JOptionPane.showMessageDialog(null, "��� �Ϸ�");
			bbReset();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��� ����");
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
				tfbbMessage.setText("��û ������ �������� �ʽ��ϴ�.");
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
			JOptionPane.showMessageDialog(null, "��û�Ϸ�");
			
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
			JOptionPane.showMessageDialog(null, "��û ����");
			e.printStackTrace();
		}
	}

}
