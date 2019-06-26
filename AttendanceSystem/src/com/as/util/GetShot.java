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
	
	public Integer sId = 0;	//Ա��id
	public boolean check_flag = false;	//�ȶ��Ƿ�ɹ�
	public boolean is_over = false;	//�ȶ��Ƿ����������ֵ������
	public String basePath = "";	//�洢��·��

	//ԭ����������ظ�����
	static AtomicLong generator = new AtomicLong();

	public static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	
	/**
	 * ����Ŀ��·�����漰����Ŀ¼����/home/work/xiangze/xxx.jpg, ��ô home work xiangze
	 * �������ļ��ж����Զ�����
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
			while(Camera.InitCamera){	//����������У�����δ�ر�
				if(count < 0){	//�жϼ�������
					is_over = true;
					break;
				}
				Mat mat = converter.convertToMat(grabber.grabFrame());
				
				if(Camera.saveImage == Camera.Img.save) {
					//SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��");javaCV��֧������·��
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					String imgname = format.format(date) + "Num" + String.valueOf(generator.incrementAndGet());
					//System.out.println("����imgname��ʲô��" + imgname);
					//opencv_imgcodecs.imwrite(systemUtilService.getWebPath("/WEB-INF/CameraPhoto/") + imgname +".jpg", mat);
					//realPath = "D:\\AttendanceTest\\"+sId+"\\" + imgname +".jpg";
					realPath = basePath + sId + "\\" + imgname + ".jpg";
					//System.out.println("getShot���·����"+realPath);
					//����ļ���·���Ƿ����
					String staff_path = basePath + sId + "\\";
					makeDirPath(staff_path);
					//�洢ͼƬ
					//opencv_imgcodecs.imwrite("D:\\AttendanceTest\\"+sId+"\\" + imgname +".jpg", mat);
					opencv_imgcodecs.imwrite(realPath , mat);
				}
				
				//��������ʶ��
				FaceMatch faceMatch = new FaceMatch();
				check_flag = faceMatch.match(realPath , sId , basePath);
				
				//����ȶ���ȷ���رմ��ڣ�break��ѭ��
				if(check_flag){
					break;
				}
				
				count--;	//����ֵ-1
				Thread.sleep(5000);//5000���뱣��һ��
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}