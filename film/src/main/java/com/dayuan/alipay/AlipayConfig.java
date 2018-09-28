package com.dayuan.alipay;

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
    public static String app_id = "2018090920391729550";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCjTkqaRAHUHz73rrh7kvm0xmsN nqjLs8IYl2qvWYLwrx28IxtT7xS7q9pFOAYYibh3kswOr9+xWExCM944F/pHJVFc0e3QmKajy7s2 7tUZsvnNkuroTN6w5RaQqBuyMp9wFX+ey38NFUq0gkNBOlwLu5XR1B5sykQOC1ay2ouF7hQ9aoZW bd7A+b8U5+jSXn3uL4AviBMI6B/N0XhvROuA3vgnnYKNK8pyosMnQost10ucGE5/0jY+FXpA2hOY Jsad23OrNaqrYULIAs3DfeOBX0+HqSUDNXHTRxZwOuILzNo2PdSyRqu7qtsWB4O+/1mhbo3Pt8qq gE9AzyYPAx0PAgMBAAECggEALLulc9xtI83rUa1qm28HN52idXighCwZXuMbNi0u0P8nBgIX2kYD R+UrRQqzoMUY4qgzfVJv1AR73yaWGId450QxpVtb6WfnPbbw8Xs6pG+CPe2gq0FjPtGB/N4PuCBl zC4XZ0gW5V6PuCJ2iceSryQTRgb0zpgZTD+O4C89SNIedGZ6+38CiajSJ9gjjufMiVLdq5avvPZE kyt8+ojC2qG7NhQ7QcOvsyRxzkJ9EH0IQ80ZPhWFLro595ta+BXEBFu9zjdsJvMx9uneKYgIPj65 wE0djy7jZY1DAKWjltrFfD6tyS5PkFgX00iNy/2ak1mSCRHwGU5kg9WB1YWbAQKBgQDNoo4YDE9m 3ICNycPp3+PPD+mfuXzbbKLM+s+pACYJ4dzjU50GtyVIrdE0CUgwhOoZDN7aP/pVnYWwSBj6o8aF 3yW64IhLlU7roOR/xnMcpHcQWdCH+j2ORd1pQVz9wL7pfCFhitPz7D0A1+AHzrezV1/xY+86gl/p SpQ67JHmXQKBgQDLTavXN37IFSN2au/P2E57+L+d2/iTDVq/wIZfF2x+jLCLIkUkXR0TAwxa1DhA 1bsa0Yst0L67mqNJfT3dz6GQOtWiSiV3S3iosP0mAuLSo099tbvlMAj24g1gUlZNTHJiexQXxsMi nDYQYjLgfn3b5Ovacai3smpawAMqaCeCWwKBgQCXDTqNpbVJ3N29yaFVEfaYqpq1jp6N3F42bmLO ROKsTbJhdpEZVr1Xhv7u14akxW3k2tR0w5xS+bQzirJ2ool42zMu9DKKOvJPGc/foFn8hz9Czobk 68f904pSAAacC9LxVhJ7QWxyeG1TSkAx3AKLBbrfacZYV1620ubR5edCrQKBgD098ovsXbasF3iu kfsMb+gMcLx+7QxvM20b895lUft1KiFfe3CO1BlMC8a4sxFk0O4tPT3Z6frdhF646ZAN4d9uubgf mdWr2HcIidmPeQB2zZVSz30k0S59vgXMB89VPgfIh6754wJkCC07R5Q0xnkdJqcxId/kUQFxYgK+ SViNAoGBAJj2AHsPmZWgZ1DKl1F7/sUxLAayl0rM9uK7ti1G1MbX0UQbjtg0z8KELjskY0mKIrMt tp+JpD5sjkNtlr3BdyaHGr4TfPp/JrJIsG1McTvrikUd/U8gdhwgoyyal0KUaoiQIpJVzAAEnLAI YetGYVfMENrcVbGWlJhJechMHatp";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4SZgs0ZcIyvfS4hv2WEY PtoLpTCmza2IQKsLWxXHWwIFgPsnDB61jvNG9Oij7pmxx/+JMOWkcdVj1ltoRShH Dy3HAelIYxXnpKnHI3x9c6PyYUIX+szZ/dlkCEEbJPQ0tQqvkauK2ue3gDTBeWw/ frSbRUh0sQu47IUX8XO0MoUZDo62/5mnRnq4S+BKltL08eV7Cb5U+d/sIoce/IuR rDe9IKzdRk+efsSGK52nttuMa5NBOXpZrOg4MdLlhepc/g5xuZAIHkn+MAlW/gRD NfKvfOHh49ghpkO8ilNCQEC3KTn6uccl1DfxWcbgm0Slk5d/4XJ0dJWkarcsTcr2 nQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://dayuansjj.frp3.chuantou.org/api/order/notifyUrl.do";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://dayuansjj.frp3.chuantou.org/paySuccess.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "http://payhub.dayuanit.com/gateway/alipay/web.do";

    // 支付宝网关
    public static String log_path = "C:\\";

}
