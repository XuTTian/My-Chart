package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JLabel;

public class HelpFrame extends JFrame implements ActionListener {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HelpFrame() {
		setTitle("帮助界面");
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("退出");
		button.setBounds(new Rectangle(2, 2, 21, 12));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setBounds(149, 227, 123, 31);
		button.addActionListener(this);
		
		JLabel lblNewLabel = new JLabel("这是帮助页面");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 416, 207);
		contentPane.add(lblNewLabel);
		getContentPane().add(button);
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.dispose();
	}
}
