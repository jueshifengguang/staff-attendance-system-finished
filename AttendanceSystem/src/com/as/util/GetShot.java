package com.as.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;

import com.as.entity.Camera;

public class GetShot implements Runnable {
	
	public Integer sId = 0;	//员工id
	public boolean check_flag = false;	//比对是否成功
	public boolean is_over = false;	//比对是否结束（计数值结束）
	public String basePath = "";	//存储的路径

	//原子类产生不重复数据
	static AtomicLong generator = new AtomicLong();

	public static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	
	/**
	 * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg, 那么 home work xiangze
	 * 这三个文件夹都得自动创建
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}
	
	@Override
	public void run() {
		int count = 3;
		OpenCVFrameGrabber grabber = Camera.grabber;
		String realPath = "";
		try {
			while(Camera.InitCamera){	//摄像机启动中，窗口未关闭
				if(count < 0){	//判断计数次数
					is_over = true;
					break;
				}
				Mat mat = converter.convertToMat(grabber.grabFrame());
				
				if(Camera.saveImage == Camera.Img.save) {
					//SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");javaCV不支持中文路径
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					String imgname = format.format(date) + "Num" + String.valueOf(generator.incrementAndGet());
					//System.out.println("看看imgname是什么：" + imgname);
					//opencv_imgcodecs.imwrite(systemUtilService.getWebPath("/WEB-INF/CameraPhoto/") + imgname +".jpg", mat);
					//realPath = "D:\\AttendanceTest\\"+sId+"\\" + imgname +".jpg";
					realPath = basePath + sId + "\\" + imgname + ".jpg";
					//System.out.println("getShot里的路径："+realPath);
					//检查文件夹路径是否存在
					String staff_path = basePath + sId + "\\";
					makeDirPath(staff_path);
					//存储图片
					//opencv_imgcodecs.imwrite("D:\\AttendanceTest\\"+sId+"\\" + imgname +".jpg", mat);
					opencv_imgcodecs.imwrite(realPath , mat);
				}
				
				//调用人脸识别
				FaceMatch faceMatch = new FaceMatch();
				check_flag = faceMatch.match(realPath , sId , basePath);
				
				//如果比对正确，关闭窗口，break出循环
				if(check_flag){
					break;
				}
				
				count--;	//计数值-1
				Thread.sleep(5000);//5000毫秒保存一次
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}