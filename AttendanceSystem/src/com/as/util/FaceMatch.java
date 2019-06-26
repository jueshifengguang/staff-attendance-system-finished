package com.as.util;

import com.as.util.HttpUtil;
import com.as.util.GsonUtils;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
* 人脸对比
*/
public class FaceMatch {
	
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

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
	public static boolean match(String realPath2 , Integer s_id , String basePath) {
        //请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
        	//String basePath = request.getRealPath("/dish_image");
        	//System.out.println("basePath:"+basePath);
        	
        	String sId_str = s_id.toString();
        	String realPath1 = basePath + sId_str+"\\1.jpg";
        	//System.out.println("FaceMatch里的realPath1："+realPath1);
        	//String realPath1 = "D:\\AttendanceTest\\"+sId_str+"\\1.jpg";
        	//String realPath1 = "D:\\AttendanceTest\\12.jpg";
        	//String realPath2 = "D:\\AttendanceTest\\13.jpg";
        	
        	//检查文件路径的文件夹是否都存在
        	//makeDirPath(realPath1);

            byte[] bytes1 = FileUtil.readFileByBytes(realPath1);
            byte[] bytes2 = FileUtil.readFileByBytes(realPath2);
            String image1 = Base64Util.encode(bytes1);
            String image2 = Base64Util.encode(bytes2);

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "LOW");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");

            images.add(map1);
            images.add(map2);

            String param = GsonUtils.toJson(images);

            //注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            AuthService authService = new AuthService();
            String accessToken = authService.getAuth();
            //String accessToken = "【调用鉴权接口获取的token】";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            
            //检测结果，默认是比对不成功
            boolean check_flag = false;
            
            //解析json数据
            JSONObject jsonObject = new JSONObject(result);
            //解析字段
            int error_code = jsonObject.getInt("error_code");
            String error_msg = jsonObject.getString("error_msg");
            if(error_code == 0){	//无错误，是活体
            	JSONObject result_json = jsonObject.getJSONObject("result");
                
                Double score = result_json.getDouble("score");
                
//                System.out.println("error_code："+error_code + 
//                		"error_msg"+error_msg + "score" + score);
                
                //根据分数，判断是否是同一个人
                if(score >= 75){
                	check_flag = true;	//比对成功！！！！
                }
            }
 
            return check_flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}