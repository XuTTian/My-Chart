package tools;

import java.io.*;

import ui.MyFrame;

//该工具类用于读取二进制格式的信号文件，并转化为十进制格式

/*
100.hea文件中内容：采样率为360Hz，双通道，通道1零点整数值995，通道2零点整数值1011，每mV包含的整数个数200
100 2 360 650000
100.dat 212 200 11 1024 995 -22131 0 MLII
100.dat 212 200 11 1024 1011 20052 0 V5
# 69 M 1085 1629 x1
# Aldomet, Inderal
*/

public class ReadSignalTool {

	int sfreq = 360; //采样率
	int sample2read = 3600; //十秒采样个数
	int[] zeroValue = {995, 1011};
	float[] gain = {200, 200};
	
	
	//获取读取的时间长度，超过10s按10s进行展示
	public ReadSignalTool() {

	}
	
	public double[][] getDat(String fileName) throws IOException {
				
		byte[][] A = readFile(fileName);
		short[][] M = transform(A);
		
		double[][] value = new double[sample2read][2];
		for(int i=0; i<sample2read; i++) {	
			value[i][0] = ((double) (M[i][0] - zeroValue[0]) / gain[0]);
			value[i][1] = ((double) (M[i][1] - zeroValue[1]) / gain[1]);
		}
		
		return value;
	}
	
	public void setTime(int second) {
		if(second<=10) {
			this.sample2read = second * sfreq;
		}
	}
	
	public float getTime() {
		float time = (float) sample2read/ (float) sfreq;
		return time;
	}
	
	private byte[][] readFile (String fileName) throws IOException {
		
		byte[][] A = new byte[sample2read][3];
		
		File file = new File(fileName);
		FileInputStream bitFile = new FileInputStream(file);
		for(int i = 0; i < sample2read; i++){
			bitFile.read(A[i], 0, 3);
		}
		bitFile.close();
		
		return A;
	}

	
	private short[][] transform(byte[][] A) {
		
		short[][] M = new short[sample2read][2];
		short[] MLH = new short[sample2read]; //通道1字节高4位
		short[] SinL = new short[sample2read]; //通道1符号位
		short[] MRH = new short[sample2read]; //通道2字节高4位
		short[] SinR = new short[sample2read]; //通道2符号位
		
		for(int i=0; i<sample2read; i++) {
			MRH[i] = (short) ((A[i][1]>>>4) << 8);  //取第二字节的高四位
			MLH[i] = (short) ((A[i][1]&15) << 8);  //取第二字节的低四位
			
			SinL[i] = (short) ((A[i][1]&8) << 9);  //取出字节低四位中最高位,左移9位成为符号位
			SinR[i] = (short) ((A[i][1]&128) << 5);  //取出字节低四位中最高位，左移5位成为符号位
			
			M[i][0] = (short) ((A[i][0]&0xff) + MLH[i] - SinL[i]);
			M[i][1] = (short) ((A[i][2]&0xff) + MRH[i] - SinR[i]);

		}
		
		return M;
	}
	
	
//	public static void main(String[] args) throws IOException {
//		ReadSignalTool test = new ReadSignalTool(10);
//		float[][] M = test.getDat("D:\\EclipseWorkSpace\\MyChart\\src\\100.dat");
//		
//		
//	}
	
}
