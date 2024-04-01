import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MyJFrame extends JFrame{
	private JPanel contentPane;
	private JPanel status;
	private String [] toolList = {"���u","����","����","�x��"};
	private JComboBox<String> cbo = new JComboBox<>(toolList);
	private JCheckBox chk = new JCheckBox();
	private JButton []btn = new JButton[4];	//�|�ӫ��s
	private JRadioButton [] rdb = new JRadioButton[3];
	private JLabel s1;	// �T�Ӽ��D : ø�Ϥu��,����j�p,��
	private JLabel s2;
	private JLabel s3;
	private Painter painter;
	public static String currentTool = "";
	public static JLabel statusLabel;
	public static int paintsize;// ����j�p
	public static boolean fillcheck,clear = false;
	public static Color foreGround = Color.BLACK;
	public static Color backGround = Color.BLACK;
	
	MyJFrame(){
		JOptionPane.showMessageDialog(null,"�w��Ө�p�e�a~","���K�A�n",1);	//�ﻫ + JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("�p�e�a");
		setBounds(200,200,915,605);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		repaint();
		
		s1 = new JLabel("ø�Ϥu��");	//���D 1
		s1.setBounds(60,0,50,17);
		s1.setLayout(null);
		
		JPanel tool = new JPanel();	//ComboBox ->�U�ԥ\��M�� e.g. �e���B���...
		tool.add(s1);
		tool.add(cbo);
		contentPane.add(s1);
		contentPane.add(tool);
		//tool.setBackground(Color.WHITE);
		cbo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("���"+toolList[cbo.getSelectedIndex()]);
				}
				switch(toolList[cbo.getSelectedIndex()]) {
					case "���u" :
						currentTool = toolList[0];
						chk.setEnabled(true);
						break;
					case "����" :
						currentTool = toolList[1];
						chk.setEnabled(true);
						break;
					case "����" :
						currentTool = toolList[2];
						chk.setEnabled(false);
						break;
					case "�x��" :
						currentTool = toolList[3];
						chk.setEnabled(true);
						break;
				}
			//repaint();
			}
		});
		tool.setBounds(50,17,100,33);
		
		
		
		s2 = new JLabel("����j�p");	//2
		s2.setBounds(150,0,50,17);
		s2.setLayout(null);
		contentPane.add(s2);
		
		s3 = new JLabel("��");		//3
		s3.setBounds(300, 0, 50, 17);
		s3.setLayout(null);
		contentPane.add(s3);
		
		JPanel size = new JPanel();	// JRadioBox -> �p �� �j
		size.setBounds(150, 17, 130, 33);
		//size.setBackground(Color.darkGray);
		size.setLayout(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup group = new ButtonGroup();
		rdb[0] = new JRadioButton("�p");
		rdb[1] = new JRadioButton("��");
		rdb[2] = new JRadioButton("�j");
		for(int i = 0;i < rdb.length ;i++) {
			group.add(rdb[i]);
			size.add(rdb[i]);
			rdb[i].addItemListener(new RadioButtonListener(rdb[i].getText()));
		}
		contentPane.add(size);
	
		JPanel size2 = new JPanel();	//CheckBox -> ��
		size2.setBounds(300, 17, 50, 33);
		size2.setLayout(new FlowLayout(FlowLayout.LEFT));
		chk.addItemListener(new checkBoxHandler());
		size2.add(chk);
		contentPane.add(size2);
		
		btn[0] = new JButton("�����C��");	//�|�ӥ\����s
		btn[0].addActionListener(new ButtonHandler(btn[0].getText()));
		btn[0].setBounds(350, 17, 100, 30);
		btn[1] = new JButton("�M���e��");
		btn[1].addActionListener(new ButtonHandler(btn[1].getText()));
		btn[1].setBounds(460, 17, 100, 30);
		btn[2] = new JButton("�����");
		btn[2].addActionListener(new ButtonHandler(btn[2].getText()));
		btn[2].setBounds(570, 17, 100, 30);
		btn[3] = new JButton("�W�@�B");
		btn[3].addActionListener(new ButtonHandler(btn[3].getText()));
		btn[3].setBounds(680, 17, 100, 30);
		for(int i=0 ; i<btn.length;i++) {
			contentPane.add(btn[i]);
		}
		
		painter = new Painter();
		contentPane.add(painter,BorderLayout.CENTER);
		
					
		status = new JPanel();	//�ƹ�����
		contentPane.add(status,BorderLayout.SOUTH);
		status.setBounds(0,545,900,20);
		status.setBackground(Color.LIGHT_GRAY);
		statusLabel = new JLabel();
		statusLabel.setForeground(Color.BLACK);
		status.setLayout(new BorderLayout(0,0));
		status.add(statusLabel);
		
		setVisible(true);
	}
	
	/*public void itemStateChanged(ItemEvent e) {	//action �� item������ԣ�|��X�⦸ �A�`�N�@�U
		Object show = cbo.getSelectedItem();
		//System.out.println(show);
		if(e.getStateChange() == ItemEvent.SELECTED) {
			System.out.println("��� "+showtool + show);
		}
	}*/
	
	private class checkBoxHandler implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("��ܶ�");
				fillcheck = true;
			}else {
				System.out.println("������");
				fillcheck = false;
			}
		}
	}
	
	private class RadioButtonListener implements ItemListener{
		String size;
		public RadioButtonListener(String size){
			this.size = size;
		}
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("���"+size+"����");
			}
			switch(size) {
				case "�p" :
					paintsize = 5;
					
					break;
				case "��" :
					paintsize = 15;
					
					break;
				case "�j" :
					paintsize = 25;
					
					break;
			}
		}
	}
	
	private class ButtonHandler implements ActionListener{
		String text ;
		public ButtonHandler(String text){
			this.text = text;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("�I��"+text);
			switch(text) {
				case "�����C��" :
					foreGround = JColorChooser.showDialog(painter, "�o�̩�@��",foreGround);
					backGround = foreGround;
					//System.out.println("test");
					break;
				case "�M���e��" :
					clear = true;
					painter.clearCanvas();
					break;
				case "�����" :
					foreGround = Color.WHITE;
					backGround = Color.WHITE;
					break;
				case "�W�@�B" :
					//painter.repaint();
					painter.recover();
					contentPane.repaint();
					break;
			}
		}
	}
}




