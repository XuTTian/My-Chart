package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Choice;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JButton;

public class MyFrame2 extends JFrame {

	private JPanel contentPane;
	private FileDialog openDia;
	private File signalFile;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame2 frame = new MyFrame2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 868, 841);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("文件");
		menuBar.add(fileMenu);
		
		openDia = new FileDialog(this,"我的打开", FileDialog.LOAD);
		JMenuItem fileMenuItem_1 = new JMenuItem("打开…");
		fileMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();//获取文件路径
				String fileName = openDia.getFile();//获取文件名称
				
				//如果打开路径 或 目录为空 则返回空
				if(dirPath == null || fileName == null)
						return ;
				signalFile = new File(dirPath,fileName);
				
				try {
					BufferedReader bufr = new BufferedReader(new FileReader(signalFile));
					String signal = null;
					String temp = null;
 
					while((temp = bufr.readLine())!= null) {
						signal += temp;
					}
					bufr.close();
					
					//对signal进行处理
					
				} catch (IOException ex) {
					throw new RuntimeException("文件读取失败！");
				}
			}
		});
		fileMenu.add(fileMenuItem_1);
		
		JMenuItem fileMenuItem_2 = new JMenuItem("关闭");
		fileMenu.add(fileMenuItem_2);
		
		JMenu helpMenu = new JMenu("帮助");
		menuBar.add(helpMenu);
		
		JMenuItem helpMenuItem_1 = new JMenuItem("操作说明");
		helpMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HelpFrame();
			}
		});
		helpMenu.add(helpMenuItem_1);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane channel_1 = new JScrollPane();
		channel_1.setBounds(5, 123, 844, 309);
		contentPane.add(channel_1);
		
		JPanel optionPanel = new JPanel();
		optionPanel.setBounds(5, 5, 844, 108);
		contentPane.add(optionPanel);
		optionPanel.setLayout(null);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("余弦化");
		rdbtnNewRadioButton.setFont(new Font("宋体", Font.PLAIN, 18));
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(116, 6, 149, 48);
		optionPanel.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("正弦化");
		rdbtnNewRadioButton_1.setFont(new Font("宋体", Font.PLAIN, 18));
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(116, 56, 149, 52);
		optionPanel.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("微分");
		rdbtnNewRadioButton_2.setFont(new Font("宋体", Font.PLAIN, 18));
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setBounds(306, 6, 149, 48);
		optionPanel.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("傅里叶变换");
		rdbtnNewRadioButton_3.setFont(new Font("宋体", Font.PLAIN, 18));
		buttonGroup.add(rdbtnNewRadioButton_3);
		rdbtnNewRadioButton_3.setBounds(306, 56, 149, 52);
		optionPanel.add(rdbtnNewRadioButton_3);
		
		JButton btnNewButton = new JButton("变更");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
		btnNewButton.setBounds(499, 56, 120, 42);
		optionPanel.add(btnNewButton);
		
		JScrollPane channel_2 = new JScrollPane();
		channel_2.setBounds(5, 445, 844, 315);
		contentPane.add(channel_2);
	}
}
