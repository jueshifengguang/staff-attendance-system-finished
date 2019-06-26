package com.as.entity;

import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Camera {

	public static enum Img{
		save,
		nosave
	}
	
	/**
	 * 是否正常启动摄像头 true:正常启动/false:异常启动
	 */
	public static boolean InitCamera;
	
	/**
	 * 摄像头资源
	 */
	public static OpenCVFrameGrabber grabber;
	
	/**
	 * 是否保存图片,默认不保存	默认保存！！！
	 */
	public static Img saveImage = Img.save;
	
	/**
	 * 开始摄像头
	 * @throws Exception 摄像头初始化失败！
	 */
	public static void StartCamera() throws Exception {
			grabber.start();
	}
	
	/**
	 * 停止摄像头
	 * @throws Exception 摄像头关闭异常！
	 */
	public static void StopCamera() throws Exception {
			grabber.stop();
	}
}