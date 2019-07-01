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
		
		bAddLayout();//ȭ�鼳��
		bConnectDB();//db����
		bEventProc();//���ű�
		initStyle();//��Ȱ��ȭ
		
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
			System.out.println("å�뿩�� ���� ����");
		} catch (Exception e) {
			System.out.println("å�뿩�� ���� ����");
			e.printStackTrace();
		}
	}

	private void bEventProc() {
		//���ű�
		cbMultiInsert.addActionListener(this);
		bBookDelete.addActionListener(this);
		bBookInsert.addActionListener(this);
		bBookModify.addActionListener(this);
		tfBookSearch.addActionListener(this);
		
		bBookReset.addActionListener(this);
		
		//jtable ������ ����
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
			//Ŭ�� ���ڵ带 �ؽ�Ʈ�ʵ忡
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
		//�� ������ ����
		tfBookNum = new JTextField();
		tfBookTitle = new JTextField();
		tfWriter = new JTextField();
		tfPublisher = new JTextField();
		
		tfKeyword=new JTextField();
		
		String[] cbJenreStr = {"�θǽ�","��Ÿ��","����","����","������","�߸�","SF"};
		comBookJenre = new JComboBox(cbJenreStr);

		
		taSummary = new JTextArea();
		taSummary.setLineWrap(true);
		
		JScrollPane taSu = new JScrollPane(taSummary,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		cbMultiInsert = new JCheckBox("�����԰�");
		tfInsertCount = new JTextField("1",5);
		
		//��ư
		bBookInsert=new JButton("�԰�");
		bBookModify=new JButton("����");
		bBookDelete=new JButton("����");
		
		bBookReset=new JButton("�ʱ�ȭ");
		
		String[] cbBookSearch= {"����","�۰�","���ǻ�","Ű����"};
		comBookSearch = new JComboBox(cbBookSearch);
		tfBookSearch=new JTextField(15);
		
		
		tbbModelBook=new BookTableModel();
		tableBook=new JTable(tbbModelBook);
		tableBook.setModel(tbbModelBook);
		
		tfmessage=new JTextField(30);
		tfHowMany=new JTextField(10);
		
		//ȭ�鱸��
		//west �ǳ� ����
		JPanel p_west=new JPanel();
		p_west.setLayout(new BorderLayout());
		
		//���� ���
		JPanel p_west_center=new JPanel();
		p_west_center.setLayout(new BorderLayout());
		
		//���� ��� ����
		JPanel p_west_center_north=new JPanel();
		p_west_center_north.setLayout(new GridLayout(6, 2));
		p_west_center_north.add(new JLabel("å ��ȣ"));
		p_west_center_north.add(tfBookNum);
		
		p_west_center_north.add(new Label("�帣"));
		p_west_center_north.add(comBookJenre);
		
		p_west_center_north.add(new Label("����"));
		p_west_center_north.add(tfBookTitle);

		p_west_center_north.add(new Label("�۰�"));
		p_west_center_north.add(tfWriter);
		
		p_west_center_north.add(new Label("���ǻ�"));
		p_west_center_north.add(tfPublisher);
		
		p_west_center_north.add(new Label("Ű���� (�ܾ� ���ַ� �Է�)"));
		p_west_center_north.add(tfKeyword);
		
		
		//���� ��� ��� å �ٰŸ�
		JPanel p_west_center_center = new JPanel();
		p_west_center_center.setLayout(new BorderLayout());
				
		p_west_center_center.add(new JLabel("�ٰŸ�"),BorderLayout.WEST);
		p_west_center_center.add(taSu, BorderLayout.CENTER);
		
		//���� ��� �ǳڿ� �� ���� �ǳ� �߰�
		p_west_center.add(p_west_center_north,BorderLayout.NORTH);
		p_west_center.add(p_west_center_center,BorderLayout.CENTER);
		
		//��輱
		p_west_center.setBorder(new TitledBorder("���������Է�"));
		
		//���� �Ʒ�
		JPanel p_west_south=new JPanel();
		p_west_south.setLayout(new GridLayout(2,1));
		
		//���� �Ʒ��� ���� �ǳ�
		JPanel p_west_south_1=new JPanel();
		p_west_south_1.setLayout(new FlowLayout());
		p_west_south_1.add(cbMultiInsert);
		p_west_south_1.add(tfInsertCount);
		p_west_south_1.add(new JLabel("��"));
		p_west_south_1.setBorder(new TitledBorder("�����Է½ü���"));
				
		JPanel p_west_south_2=new JPanel();
		p_west_south_2.setLayout(new GridLayout(1, 3));
		p_west_south_2.add(bBookInsert);
		p_west_south_2.add(bBookModify);
		p_west_south_2.add(bBookDelete);
				
		p_west_south.add(p_west_south_1);
		p_west_south.add(p_west_south_2);
				
		p_west.add(p_west_center,BorderLayout.CENTER);
		p_west.add(p_west_south,BorderLayout.SOUTH);
		
		//east �ǳ� ����
		JPanel p_east = new JPanel();
		p_east.setLayout(new BorderLayout());
		
		//�������� ����
		JPanel p_east_north=new JPanel();
		p_east_north.add(comBookSearch);
		p_east_north.add(tfBookSearch);
		
		p_east_north.add(bBookReset);
		
		//===========�߰�
		JPanel p_east_north_center=new JPanel();
		p_east_north_center.add(tfmessage);
		p_east_north_center.add(new JLabel("���� ����"));
		p_east_north_center.add(tfHowMany);
		p_east_north_center.add(new JLabel("��"));
				
		p_east.add(p_east_north_center, BorderLayout.CENTER);
		

		//��輱
		p_east_north.setBorder(new TitledBorder("å �˻�"));
		p_east.add(p_east_north, BorderLayout.NORTH);
		
		p_east.add(new JScrollPane(tableBook),BorderLayout.SOUTH);
	
				
		setLayout(new GridLayout(1, 2));
		add(p_west);
		add(p_east);
	}
	
	class BookTableModel extends AbstractTableModel{
		
		ArrayList data=new ArrayList();
		String[] columnNames = {"å ��ȣ","�帣","����","�۰�","���ǻ�","Ű����"};

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
		if (evt==bBookInsert) {//�԰�
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
			JOptionPane.showMessageDialog(null, "�����Ϸ�");
			reset();
			
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "��������");
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
				JOptionPane.showMessageDialog(null, "���� �Ϸ�");
				
				reset();
				
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "��������");
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
			
			//��������
			tfHowMany.setText(str.valueOf(data.size()));

			if(data.size()==0) {
				tfmessage.setText("ã���ô� �׸��� �������� �ʽ��ϴ�. �ٽ� �Է����ּ���.");
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
			JOptionPane.showMessageDialog(null, "["+tfBookTitle.getText()+"] �԰�Ǿ����ϴ�.");
			
			//�ؽ�Ʈ�����
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
			JOptionPane.showMessageDialog(null, "�԰� ����");
			e1.printStackTrace();
		}
	}
}
