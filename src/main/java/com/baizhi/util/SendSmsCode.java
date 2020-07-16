package com.baizhi.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.5.0</version>
</dependency>
*/
public class SendSmsCode {

    /**
     * 生成a位随机数验证码
     * @return：
     * numberCode ：返回随机生成的四位验证码
     */
    public static String getNumberCode(int a) {
        String numberCode = "";
        for (int i = 0; i < a; i++) {
            numberCode = numberCode + (int) (Math.random() * 9);
        }
        return numberCode;
    }


    /**
     * 发送验证吗
     * @param phone
     */
    public static String sendSmsCodeToPhone(String phone,String code) {
        /*
         * 生成随机数
         * */
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G7kj7Hv38nYdySmeMYk", "vjJR27b9AKPYnQX2OdTD7UWbXE06lm");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "学习小天使");
        request.putQueryParameter("TemplateCode", "SMS_195580154");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

        String message = null;

        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject data = JSON.parseObject(response.getData());
            if(data.getString("Code").equals("OK")){
                message = "执行成功";
            }else if(data.getString("Code").equals("isv.ACCOUNT_NOT_EXISTS")){
                message = "账户不存在";
            }else if(data.getString("Code").equals("isv.MOBILE_NUMBER_ILLEGAL")){
                message = "非法手机号";
            }else if(data.getString("Code").equals("isv.AMOUNT_NOT_ENOUGH")){
                message = "账户余额不足";
            }else if(data.getString("Code").equals("isv.DAY_LIMIT_CONTROL")){
                message = "触发日发送限额";
            }else{
                message = "发送失败";
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return message;
    }


    public static void main(String[] args) {
    }
}

