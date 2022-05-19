package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tools.ReadSignalTool;

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
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MyFrame3 extends JFrame {

	private JPanel contentPane;
	private FileDialog openDia;
	private File signalFile;
	private double[] channel1 = new double[3600];
	private double[] channel2 = new double[3600];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame3 frame = new MyFrame3();
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
	public MyFrame3() {
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
				
				//如果打开路径或目录为空，则返回空
				if(dirPath == null || fileName == null) {
					return ;
				}
				
				ReadSignalTool tool = new ReadSignalTool();
				try {
					double[][] data = tool.getDat(dirPath+fileName);
					int len = data.length;
					for(int i=0; i<len; i++) {
						channel1[i] = data[i][0];
						channel2[i] = data[i][1];
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		
		Coordinates coordinate1 = new Coordinates();
		coordinate1.setData(channel1);
		JScrollPane channel_1 = new JScrollPane(coordinate1);
		channel_1.setBounds(5, 46, 844, 339);
		contentPane.add(channel_1);
		
		Coordinates coordinate2 = new Coordinates();
		coordinate2.setData(channel2);
		JScrollPane channel_2 = new JScrollPane(coordinate2);
		channel_2.setBounds(5, 431, 844, 339);
		contentPane.add(channel_2);
		
		JLabel lblNewLabel = new JLabel("Channel-1");
		lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 10, 104, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblChannel = new JLabel("Channel-2");
		lblChannel.setFont(new Font("幼圆", Font.BOLD, 20));
		lblChannel.setBounds(5, 395, 104, 26);
		contentPane.add(lblChannel);
	}
	
}
