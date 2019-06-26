package com.as.util;

import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import com.as.entity.Camera;

public class ShowCamera implements Runnable {
	
	public Integer sId_camera = 0;	//Ա��id
	public boolean is_over_camera = false;	//�Ƿ����
	public boolean check_flag_camera = false;	//�Ƿ�ȶԳɹ�
	public String basePath = "";	//�洢��·��

	@Override
	public void run() {
		try {
			Camera.grabber = new OpenCVFrameGrabber(0);  
		    //grabber.start();   //��ʼ��ȡ����ͷ����
			Camera.StartCamera();
			Camera.InitCamera = true;
		    CanvasFrame canvas = new CanvasFrame("����ͷ");//�½�һ������
		    canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    canvas.setAlwaysOnTop(true);
		    
		    //��ʼ���գ��ȶ����ݿ⣡������
		    GetShot getShot = new GetShot();
			getShot.sId = sId_camera;	//����
			getShot.basePath = basePath;
			
			Thread threadShot = new Thread(getShot);
			threadShot.start();
			

		    while( !getShot.check_flag ){
		        if(!canvas.isDisplayable()){//�����Ƿ�ر�
		            //grabber.stop();//ֹͣץȡ
		        	Camera.StopCamera();
		        	Camera.InitCamera = false;
		        	is_over_camera = true;
		        	break;
		        }
		        if(getShot.is_over){	//�Ǳ߱ȶ��Ѿ������ˣ�����ͷ�رգ����ڹر�
		        	canvas.dispose();  //�رմ��ڣ�����
		        	Camera.StopCamera();
		        	Camera.InitCamera = false;
		        	is_over_camera = true;
		        	break;
		        }
		        //canvas.showImage(grabber.grab());//��ȡ����ͷͼ�񲢷ŵ���������ʾ�� �����Frame frame=grabber.grab(); frame��һ֡��Ƶͼ��
		        canvas.showImage(Camera.grabber.grab());
		        //getPhoto();	//����
		        
		        Thread.sleep(5);//5����ˢ��һ��ͼ��
		    }
		    
		    if(getShot.check_flag){
		    	check_flag_camera = true;
		    	System.out.println("�ȶԳɹ���");
		    }else{
		    	check_flag_camera = false;
		    	System.out.println("�ȶ�ʧ�ܣ�������");
		    }
		    
		    canvas.dispose();  //�رմ��ڣ�����
        	Camera.StopCamera();
        	Camera.InitCamera = false;
        	is_over_camera = true;
        	//threadShot.stop();//�ر��߳�
        	//System.exit(2);//�˳�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}