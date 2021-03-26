package com.yuanlrc.base.config;
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000117627504";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC2yYno8CTLHRtRhuJICTbcYSxyVC1I8EhDSvMKJ8QAu428kjWPZEWXj/PYzBtQDxOCiNAzXlveDUKZ9OXstJiC1rWk9MKPmsCQZDVk5VaebkjCyuTm3qcIQf53cLyBJEckhAM5gpVqxi/4H8A2K+W54iI4ETJvd3onbwEJ5de/FSnXXnxd/72ZlsH8JbGvd2Ld5B9t0HB+3EQne6IPwHz8c0XRA5mUGAdnNL/ptYAozOWE4kkBlTtEVMvkW3x6dlD6Lv3QDjKU0Uz2MFL/T71z0YuMqNBUlWo2cn6G/AHWGeOqV1BTOnYYGG3aVjU80pKeSgrktpu6khJQYurM9+w5AgMBAAECggEAPKuum+PBwq1ZmPj1eMWeMQ4MC7LHCTPAOJ3TB+blFv0p8ZJyNrMoqkL8xFQTKMKoTiaNPM+wHIc4ORPhkWzsRllwF12qirHqAsNQObopwxKGEin4Sm1S8dm91fVUjGT3PDaZP2iJodRArdIUIab2bHmTtBp5LChzIk2lAZ0JvIGBCP9uip6WQJrpIyY/bHY5YeZla0HsUM9J15UfejIwe9A0Clahzy1uSc8ypn3gX0FfrsvFem0aObxDxVoOeQHs8FGQCCiYLBx2i29uuhu8LNwwdiWeIu8XteVCx+kaDC2VoHloDpi9uAfp8MtRMD9M0+NHIQPh0T2DrV2eSQvSEQKBgQDjHUWcbrvRI5VY+zr0FyTFSfIoBqCrgvZCn3iw4A55Ex3XP6rVSwQbCaAkvNp8YCNG+ThTRHDu94pDslOLh9mIZkPPfYbRhnxDBWlqi+x8u/SoESrmHxQFOKmIjVsvrBhsn6kyQDHbJ9291j59zn4RwVmqV/Pn0cVnf70wTpUypQKBgQDOCP/ZPN2BBi3r8Gm6R5tVutS0ySS8U1TMdPhxpby9eh0G+aGWo0HHlhBDmQZ6OGtOTlU8PdXOahC8o+2zFHo7WmBqHxHR3EX3qk657GvUJZmIoy+gpkG1JY0TJa3WX7iw4hcZEpzBS2p0yeH1UJjdm7qANCHFRznYMhxM1r4DBQKBgQC/jVHH9dFtPHDbYtG/S8HUsGCQh759yfBAEtatf9xVG92ATY+dZC7rSvwJ4Er6ue8nDk4GVORXeOrh3AcA2SyNEYvB9iJ+l8o4kNjvZ2KndbONCSXAf5fYK4k5xdeR5rmW7Da9Tk1+hSpN0eRVIZNNEGG6/YGXFWNEvwVnN/A4jQKBgQDEyT2svICH0mIb/sTM4cXQbSDqB0/p++F7YqFE0trhUnfVCKq4mPej7IVeOxM/uxFqelDYt+q1OwL/WccXixBwDhMWaNR5T90X/vcwh0o6Fyt8oPhFhWYu4U3MIunVXhmmAFu1W9ySljh4kbYauz82Mp5AAwraRPxPrRTwow5bDQKBgQCwwlRNu1XF/WgqhWsjxitqDO+Jvp3Jy5K9QNXy9FSMMQxHG3V2QAenYKPj1Qdit/bwRTGZcmWXyrooMxctEDHA5pCr/W39l4Bor3ref0Sls8AMv3p3hc4lno4dtSdf/GWuWemNggImgvDF8c6fisFcABlky+ULMwSoIeBUTlIutQ==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl9zQ1LdNUywLXVtnHMQ3wzBbRMGiyqS6mhUgzTgGblZEtsjEhEmtlCZA43oDlYbwfLT6RgiTq5Bq0VB5oq1oTjPG7jvIGbmfB6XL6xzvtGhxXD8d8AMRDjv6t0gnUnPjf4LMcUYekcfe94aHFOK7vO2566BzWeMU/zp2pke8g3j/+LxRkMyffJD/iskHLktdfN+GM+oLbHKktoyvoy7Xp+rvOHmcND+eDwGkh0nKRROWnFYbos4ocygpu7b32+c2C792OwSR5csOENGYqLxuoqw4n3J/sLdPZxYY/xueWKG84s+wRYQOWIQn+ZlCLN2DA3qlEZ/hCYSofp0cSiyV3wIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://yxb.free.idcfengye.com/pay/alipay_notify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://yxb.free.idcfengye.com/pay_log/list";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	

}

