package ui;

import java.awt. * ;
import java.awt.event. * ;
import java.awt.geom. * ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing. * ;

import ui.Chart_test.MyCanvas;

public class Coordinates extends javax.swing.JPanel {
	private Dimension preferredSize = new Dimension(844, 339);
	private double scaleFactor = 1;
	private double[] data;
	
	// 框架起点坐标
	private double FREAME_X = 50;
	private double FREAME_Y = 50;
	private double FREAME_WIDTH = 600;// 横
	private double FREAME_HEIGHT = 250;// 纵
 
	// 原点坐标
	private double Origin_X = FREAME_X + 50;
	private double Origin_Y = FREAME_Y + FREAME_HEIGHT - 30;
 
	// X,Y轴终点坐标
	private double XAxis_X = FREAME_X + FREAME_WIDTH - 30;
	private double XAxis_Y = Origin_Y;
	private double YAxis_X = Origin_X;
	private double YAxis_Y = FREAME_Y + 30;
 
	// X轴上的时间分度值（1分度=40像素）
	private double TIME_INTERVAL = 50;
	private double TIME_DELTA = 1;
	private double TIME_ZERO = 0;
	// Y轴上值
	private double VOLTA_INTERVAL = 30;
	private double VOLTA_DELTA = 0.3;
	private double VOLTA_ZERO = -0.6;
	
	
	public Coordinates() { 
		// 创建一个随机数线程
		new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						repaint();
						Thread.sleep(100);
					}
				} catch (InterruptedException b) {
					b.printStackTrace();
				}
			}
 
		}).start();
		
	// mouse listener to detect scrollwheel events 
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				updatePreferredSize(e.getWheelRotation(), e.getPoint()); 
			} 
		});
	}
	
	
	private static final long serialVersionUID = 1L;
 
	public void paintComponent(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
 
		Color c = new Color(200, 70, 0);
		g.setColor(c);
		super.paintComponent(g);
		
		// 原点坐标
		Origin_X = Origin_X * scaleFactor;
		Origin_Y = Origin_Y * scaleFactor;
		// X,Y轴终点坐标
		XAxis_X = XAxis_X * scaleFactor;
		XAxis_Y = XAxis_Y * scaleFactor;
		YAxis_X = YAxis_X * scaleFactor;
		YAxis_Y = YAxis_Y * scaleFactor;
		

		// X轴上的时间分度值（1分度=40像素）
		TIME_INTERVAL = TIME_INTERVAL * scaleFactor;
		// Y轴上值
		VOLTA_INTERVAL = VOLTA_INTERVAL * scaleFactor;
 
		// 画坐标轴
		g2D.setStroke(new BasicStroke(Float.parseFloat("2.0F")));// 轴线粗度
		// X轴以及方向箭头
		g2D.draw(new Line2D.Double(Origin_X, Origin_Y, XAxis_X, XAxis_Y));
		g2D.draw(new Line2D.Double(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y - 5));
		g2D.draw(new Line2D.Double(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y + 5));
 
		// Y轴以及方向箭头
		g2D.draw(new Line2D.Double(Origin_X, Origin_Y, YAxis_X, YAxis_Y));
		g2D.draw(new Line2D.Double(YAxis_X, YAxis_Y, YAxis_X - 5, YAxis_Y + 5));
		g2D.draw(new Line2D.Double(YAxis_X, YAxis_Y, YAxis_X + 5, YAxis_Y + 5));
 
		// 画X轴上的时间刻度（从坐标轴原点起，每隔TIME_INTERVAL(时间分度)像素画一时间点，到X轴终点止）
		g2D.setColor(Color.BLUE);
		g2D.setStroke(new BasicStroke(Float.parseFloat("1.0f")));
 
		// X轴刻度依次变化情况
		for (double i = Origin_X, j = TIME_ZERO; i < XAxis_X; i += TIME_INTERVAL, j += TIME_DELTA) {
			g.drawString(" " + j, (int) i - 10, (int) Origin_Y + 20);
		}
		g2D.drawString("时间/s", (int) XAxis_X + 5, (int) XAxis_Y + 5);
 
		// 画Y轴上血压刻度（从坐标原点起，每隔10像素画一压力值，到Y轴终点止）
		for (double i = Origin_Y, j = VOLTA_ZERO; i > YAxis_Y; i -= VOLTA_INTERVAL, j += VOLTA_DELTA) {
			g.drawString(String.format("%.1f", j) + " ", (int) Origin_X - 30, (int) i + 3);
		}
		g.drawString("电压/mV", (int) YAxis_X - 5, (int) YAxis_Y - 5);// 血压刻度小箭头值

		// 画网格线
		g2D.setColor(Color.BLACK);
		
		double XEnd = XAxis_X - ((XAxis_X-Origin_X) % TIME_INTERVAL);
		double YEnd = YAxis_Y + ((Origin_Y-YAxis_Y) % VOLTA_INTERVAL);
		
		// 坐标内部横线
		for (double i = Origin_Y; i > YAxis_Y; i -= VOLTA_INTERVAL) {
			g2D.draw(new Line2D.Double(Origin_X, i, XEnd, i));
		}
		// 坐标内部竖线
		for (double i = Origin_X; i < XAxis_X; i += TIME_INTERVAL) {
			g2D.draw(new Line2D.Double(i, Origin_Y, i, YEnd));
		}
		
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int len = data.length;
		double wid = XEnd - Origin_X;
		double wid_delta = wid/len;
		double zeroValuePixel = Origin_Y - Math.abs(VOLTA_ZERO)/VOLTA_DELTA * VOLTA_INTERVAL;
		
		for(int i=0; i<len-1; i++) {
			double startX = Origin_X + wid_delta*i;
			double startY = zeroValuePixel - data[i]*VOLTA_INTERVAL/VOLTA_DELTA;
			double endX = Origin_X + wid_delta*(i+1);
			double endY = zeroValuePixel - data[i+1]*VOLTA_INTERVAL/VOLTA_DELTA;
			g2D.draw(new Line2D.Double(startX, startY, endX, endY));
		}
		
		scaleFactor = 1;
	}
	
	
	private void updatePreferredSize(int wheelRotation, Point stablePoint) { 
		scaleFactor = findScaleFactor(wheelRotation); 
		scaleBy(scaleFactor); 
		Point offset = findOffset(stablePoint, scaleFactor);
		offsetBy(offset);
		getParent().doLayout();
	} 
	
	private double findScaleFactor(int wheelRotation) { 
		double d= wheelRotation*1.08 ; return (d > 0 ) ? 1/d : -d; 
	} 
	
	private void scaleBy(double scaleFactor) { 
		int w = ( int ) (getWidth()*scaleFactor);
		int h = ( int ) (getHeight()*scaleFactor);
		preferredSize.setSize(w, h); 
	} 
	
	private Point findOffset(Point stablePoint, double scaleFactor) {
		int x = ( int ) (stablePoint.x*scaleFactor)-stablePoint.x; 
		int y = ( int ) (stablePoint.y*scaleFactor)-stablePoint.y;
		return new Point(x, y); 
	} 
	
	private void offsetBy(Point offset) { 
		Point location = getLocation();
		setLocation(location.x - offset.x, location.y - offset.y);
	} 
	
	public Dimension getPreferredSize() { 
		return preferredSize;
	}
	
	public void setData(double[] data) {
		this.data = data;
	}
	
	
}
