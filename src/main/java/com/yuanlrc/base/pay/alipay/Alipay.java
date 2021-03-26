package com.yuanlrc.base.pay.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.yuanlrc.base.config.AlipayConfig;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Alipay {

    /**
     * 支付宝PC端支付
     * @param sn
     * @param totalAmount
     * @param title
     * @param info
     * @return
     */
    public static String generatePcPayHtml(String sn, BigDecimal totalAmount, String title, String info) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        String out_trade_no = sn;
        String total_amount = totalAmount.toString();
        String subject = title;
        String body = info;

        Map<String, String> param = new HashMap<>();
        //商户订单号，商户网站订单系统中唯一订单号，必填
        param.put("out_trade_no", sn);
        //付款金额，必填
        param.put("total_amount", totalAmount.toString());
        //订单名称，必填
        param.put("subject", title);
        //商品描述，可空
        param.put("body", info);
        param.put("product_code", "FAST_INSTANT_TRADE_PAY");

        alipayRequest.setBizContent(JSONObject.toJSONString(param));

        String html = null;
        try {
            //请求
            html = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return html;
    }

    /**
     * 验证支付宝异步通知是否合法
     * @param request
     * @return
     */
    public static boolean isValid(HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        try {
            return AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查看订单详情
     * @param sn
     * @return
     */
    public static String viewDetail(String sn) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        Map<String, String> param = new HashMap<>();
        //商户订单号，商户网站订单系统中唯一订单号
        param.put("out_trade_no", sn);

        alipayRequest.setBizContent(JSONObject.toJSONString(param));

        //请求
        try {
            return alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭订单接口
     * @param sn
     * @return
     */
    public static String closePay(String sn) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();

        Map<String, String> param = new HashMap<>();
        //商户订单号，商户网站订单系统中唯一订单号
        param.put("out_trade_no", sn);
        alipayRequest.setBizContent(JSONObject.toJSONString(param));

        //请求
        try {
            return alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 退款接口，支持部分退款
     * @param sn
     * @return
     */
    public static String refund(String sn, BigDecimal refundAmount, String refundInfo, String refundSn) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        Map<String, String> param = new HashMap<>();
        //商户订单号，商户网站订单系统中唯一订单号
        param.put("out_trade_no", sn);
        //需要退款的金额，该金额不能大于订单金额，必填
        param.put("refund_amount", refundAmount.toString());
        //退款的原因说明
        param.put("refund_reason", refundInfo);
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        param.put("out_request_no", refundSn);
        alipayRequest.setBizContent(JSONObject.toJSONString(param));

        //请求
        try {
            return alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
