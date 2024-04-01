import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MyJFrame extends JFrame{
	private JPanel contentPane;
	private JPanel status;
	private String [] toolList = {"直線","橢圓形","筆刷","矩形"};
	private JComboBox<String> cbo = new JComboBox<>(toolList);
	private JCheckBox chk = new JCheckBox();
	private JButton []btn = new JButton[4];	//四個按鈕
	private JRadioButton [] rdb = new JRadioButton[3];
	private JLabel s1;	// 三個標題 : 繪圖工具,筆刷大小,填滿
	private JLabel s2;
	private JLabel s3;
	private Painter painter;
	public static String currentTool = "";
	public static JLabel statusLabel;
	public static int paintsize;// 筆刷大小
	public static boolean fillcheck,clear = false;
	public static Color foreGround = Color.BLACK;
	public static Color backGround = Color.BLACK;
	
	MyJFrame(){
		JOptionPane.showMessageDialog(null,"歡迎來到小畫家~","老鐵你好",1);	//迎賓 + JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("小畫家");
		setBounds(200,200,915,605);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		repaint();
		
		s1 = new JLabel("繪圖工具");	//標題 1
		s1.setBounds(60,0,50,17);
		s1.setLayout(null);
		
		JPanel tool = new JPanel();	//ComboBox ->下拉功能清單 e.g. 畫筆、橢圓...
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
					System.out.println("選擇"+toolList[cbo.getSelectedIndex()]);
				}
				switch(toolList[cbo.getSelectedIndex()]) {
					case "直線" :
						currentTool = toolList[0];
						chk.setEnabled(true);
						break;
					case "橢圓形" :
						currentTool = toolList[1];
						chk.setEnabled(true);
						break;
					case "筆刷" :
						currentTool = toolList[2];
						chk.setEnabled(false);
						break;
					case "矩形" :
						currentTool = toolList[3];
						chk.setEnabled(true);
						break;
				}
			//repaint();
			}
		});
		tool.setBounds(50,17,100,33);
		
		
		
		s2 = new JLabel("筆刷大小");	//2
		s2.setBounds(150,0,50,17);
		s2.setLayout(null);
		contentPane.add(s2);
		
		s3 = new JLabel("填滿");		//3
		s3.setBounds(300, 0, 50, 17);
		s3.setLayout(null);
		contentPane.add(s3);
		
		JPanel size = new JPanel();	// JRadioBox -> 小 中 大
		size.setBounds(150, 17, 130, 33);
		//size.setBackground(Color.darkGray);
		size.setLayout(new FlowLayout(FlowLayout.LEFT));
		ButtonGroup group = new ButtonGroup();
		rdb[0] = new JRadioButton("小");
		rdb[1] = new JRadioButton("中");
		rdb[2] = new JRadioButton("大");
		for(int i = 0;i < rdb.length ;i++) {
			group.add(rdb[i]);
			size.add(rdb[i]);
			rdb[i].addItemListener(new RadioButtonListener(rdb[i].getText()));
		}
		contentPane.add(size);
	
		JPanel size2 = new JPanel();	//CheckBox -> 填滿
		size2.setBounds(300, 17, 50, 33);
		size2.setLayout(new FlowLayout(FlowLayout.LEFT));
		chk.addItemListener(new checkBoxHandler());
		size2.add(chk);
		contentPane.add(size2);
		
		btn[0] = new JButton("筆刷顏色");	//四個功能按鈕
		btn[0].addActionListener(new ButtonHandler(btn[0].getText()));
		btn[0].setBounds(350, 17, 100, 30);
		btn[1] = new JButton("清除畫面");
		btn[1].addActionListener(new ButtonHandler(btn[1].getText()));
		btn[1].setBounds(460, 17, 100, 30);
		btn[2] = new JButton("橡皮擦");
		btn[2].addActionListener(new ButtonHandler(btn[2].getText()));
		btn[2].setBounds(570, 17, 100, 30);
		btn[3] = new JButton("上一步");
		btn[3].addActionListener(new ButtonHandler(btn[3].getText()));
		btn[3].setBounds(680, 17, 100, 30);
		for(int i=0 ; i<btn.length;i++) {
			contentPane.add(btn[i]);
		}
		
		painter = new Painter();
		contentPane.add(painter,BorderLayout.CENTER);
		
					
		status = new JPanel();	//滑鼠指標
		contentPane.add(status,BorderLayout.SOUTH);
		status.setBounds(0,545,900,20);
		status.setBackground(Color.LIGHT_GRAY);
		statusLabel = new JLabel();
		statusLabel.setForeground(Color.BLACK);
		status.setLayout(new BorderLayout(0,0));
		status.add(statusLabel);
		
		setVisible(true);
	}
	
	/*public void itemStateChanged(ItemEvent e) {	//action 跟 item不明為啥會輸出兩次 再注意一下
		Object show = cbo.getSelectedItem();
		//System.out.println(show);
		if(e.getStateChange() == ItemEvent.SELECTED) {
			System.out.println("選擇 "+showtool + show);
		}
	}*/
	
	private class checkBoxHandler implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("選擇填滿");
				fillcheck = true;
			}else {
				System.out.println("取消填滿");
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
				System.out.println("選擇"+size+"筆刷");
			}
			switch(size) {
				case "小" :
					paintsize = 5;
					
					break;
				case "中" :
					paintsize = 15;
					
					break;
				case "大" :
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
			System.out.println("點選"+text);
			switch(text) {
				case "筆刷顏色" :
					foreGround = JColorChooser.showDialog(painter, "這裡抽一個",foreGround);
					backGround = foreGround;
					//System.out.println("test");
					break;
				case "清除畫面" :
					clear = true;
					painter.clearCanvas();
					break;
				case "橡皮擦" :
					foreGround = Color.WHITE;
					backGround = Color.WHITE;
					break;
				case "上一步" :
					//painter.repaint();
					painter.recover();
					contentPane.repaint();
					break;
			}
		}
	}
}




