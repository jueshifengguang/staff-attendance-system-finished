package com.as.util;

import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import com.as.entity.Camera;

public class ShowCamera implements Runnable {
	
	public Integer sId_camera = 0;	//员工id
	public boolean is_over_camera = false;	//是否结束
	public boolean check_flag_camera = false;	//是否比对成功
	public String basePath = "";	//存储的路径

	@Override
	public void run() {
		try {
			Camera.grabber = new OpenCVFrameGrabber(0);  
		    //grabber.start();   //开始获取摄像头数据
			Camera.StartCamera();
			Camera.InitCamera = true;
		    CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
		    canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    canvas.setAlwaysOnTop(true);
		    
		    //开始拍照，比对数据库！！！！
		    GetShot getShot = new GetShot();
			getShot.sId = sId_camera;	//传参
			getShot.basePath = basePath;
			
			Thread threadShot = new Thread(getShot);
			threadShot.start();
			

		    while( !getShot.check_flag ){
		        if(!canvas.isDisplayable()){//窗口是否关闭
		            //grabber.stop();//停止抓取
		        	Camera.StopCamera();
		        	Camera.InitCamera = false;
		        	is_over_camera = true;
		        	break;
		        }
		        if(getShot.is_over){	//那边比对已经结束了，摄像头关闭，窗口关闭
		        	canvas.dispose();  //关闭窗口？？？
		        	Camera.StopCamera();
		        	Camera.InitCamera = false;
		        	is_over_camera = true;
		        	break;
		        }
		        //canvas.showImage(grabber.grab());//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
		        canvas.showImage(Camera.grabber.grab());
		        //getPhoto();	//拍照
		        
		        Thread.sleep(5);//5毫秒刷新一次图像
		    }
		    
		    if(getShot.check_flag){
		    	check_flag_camera = true;
		    	System.out.println("比对成功！");
		    }else{
		    	check_flag_camera = false;
		    	System.out.println("比对失败！！！！");
		    }
		    
		    canvas.dispose();  //关闭窗口？？？
        	Camera.StopCamera();
        	Camera.InitCamera = false;
        	is_over_camera = true;
        	//threadShot.stop();//关闭线程
        	//System.exit(2);//退出
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}