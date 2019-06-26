package com.as.entity;

import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Camera {

	public static enum Img{
		save,
		nosave
	}
	
	/**
	 * �Ƿ�������������ͷ true:��������/false:�쳣����
	 */
	public static boolean InitCamera;
	
	/**
	 * ����ͷ��Դ
	 */
	public static OpenCVFrameGrabber grabber;
	
	/**
	 * �Ƿ񱣴�ͼƬ,Ĭ�ϲ�����	Ĭ�ϱ��棡����
	 */
	public static Img saveImage = Img.save;
	
	/**
	 * ��ʼ����ͷ
	 * @throws Exception ����ͷ��ʼ��ʧ�ܣ�
	 */
	public static void StartCamera() throws Exception {
			grabber.start();
	}
	
	/**
	 * ֹͣ����ͷ
	 * @throws Exception ����ͷ�ر��쳣��
	 */
	public static void StopCamera() throws Exception {
			grabber.stop();
	}
}