package cn.wolfcode;

import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

@Configuration
public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092600597965";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCJds3hm9l9yQ0+AQRe22JYLiMj1fsJCj4Zb8liTaBUvkHBViSnlJbVegCf2jnTSQC8Bej125kYd8JlLMFgKYYXFq4A8PvyVUslttj6FaIruoQsy8mhg762RziHgj6kUWwDgR5xSVYUSAgR0L/EjgUoBrVmZiZwW7N7u/lkr9S/X54gj6KMLi8FIm+o+vRBza9Z2sQUPiX2BvaKecOgqmnHlvt5Hfq9zAGr+ITV7B+t8kBnNZufYq9dq4nOKE0wF/arQoqOQZJ+qBnhayj0V7xSzgw4RwXE8jawakS6myYl6ZuDwxyd84thyfzkzAAOkz2EP9M3rY9a9I3Q1dDL3LXdAgMBAAECggEABxiJ4iaAYJzbQSMeTaM3fWPMTpJCJiJQOFHr7FYje5ximo9OzxO9gYGNvLKwH3b6QbpwXxiZ24h7EWvxA3zBPjnee4/1AXpjqau5oZcySt2S6xkN7Hd3hcNBaMVRZHwLflbQ8nodpmhdekXzUDsgiwNlo8aMH2qsZiu64Paunb5HViMH3v8TH/FdtDCh0YJSNkakuMbWiVXAJPXv+IjOGvnnMKzKL1YPgOpsBG/akMPtDhO1lp0ewlGF7y1IPErQ00epBnE3005dv2NkjM5g/k71xTj1AD7F+4LbQSdnjIMlIVlozgRLOBqyydSTzIHPTqrBuoCCSCEZ2uAuyr7nxQKBgQDSOn22q68vaD1xwqT1T72pGnoR/aWPP3GvA2i927+GVB+7+HuiHQGKJMcVOsCWFQElLuRbdl2bn14cnBC7qFzhekDAbUXoPoWEPH5FsiSI0TdTgthB8O6l4ooB465VS+ifCXwDrmT1u86+O+MLPxL4V7AYKOOoh8pBAyGiskZgZwKBgQCnZKRsNyUqhJEFpuWfIi4DBi4liF12/kyFmdO7pnBAKpey71gyoYdu5zoks3h/CDgGaOq9X61rkOCqg1kR08cbFi6j6A8XtX0Vs14/8Txfa370UyedrIesLRWNYEviCRhlZeGIhTrMGjh4z9qH6VXBnoT0jJ0yEhiEps4pL8g9GwKBgQC0qL0lwK4gYEUFQM6/XNJQwscKNN4UluHcE2LsXS5egmZvA19Ddge/rlFDJiUgFFbSnFRzYOmadb7LeTZL/agrwAJrGryLk0br14xv2mHZDWmtI3EyYZjEB0Sv0Pgcpsn1+ZGQHEr4fXdyNUasgHyN29UhPeo+BstBSS6bgPZHmwKBgQCUIIFHqbzf1ROoFrqXiV2CG5P2bqeWxw3cI/Wz1mL6xTYch6RamYoJX+CZ3Z9BenppYapoDiSeTUQiX70QA4HrvENlk9LXPikEbQd4Ofcc0Tk05+jkpGoiuHqb8pzz1uyZvgBIGO2g84sJjZVqM9dJUQeCiHQKfOzpZdqFo9zkBQKBgDGOgIQJWTZBqGmbZEAgBjj6YxYmegeBMoZ8AWPh+7vzsTtgDqnc4+8sYoYhFpuiHJTGnHQF4tFh2rxANqvq8qEVLUSvFrMr/qS4urTbstbJKCyDxfSlmcFB7Mvf8kZ0f1YWYk07MbICTKKl1SlSy8ki+HjnRQlk/CAlmtjI3FU/";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjv1PwZsnKIt7fxGcJOQiuY+axfc/FulKUTn9JEpJye16cET/msO/f4mApsb1DJKS0aB1o8jfczs2ayor85onvtwAA3lTH/ph+Bk41bLd5Pesi+Fdh9vpmNZ77pmaAgwnxuTLyh7V00EJvSuaH8L3OU+1wPxQ+S7FgsdOnkywf9SfAdO1s5BcEYdkVqdH32uePwmUhHHaWsyAqOE2D15mcagcaKDT+ANRhuKVPXwkVjwKe+Qf0g0yw12tXreiBE8qfyTdu8MOC8zCRVqZHbeitO9r2I/N9ml/UWNE45uY+JclnzK76EvXzX+Cl+DZ6KaOiptY4buhU/oTt7KPetFEmQIDAQAB";
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://urmxk3.natappfree.cc/notify_url";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://urmxk3.natappfree.cc/return_url";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

