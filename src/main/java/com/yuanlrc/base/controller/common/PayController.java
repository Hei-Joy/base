package com.yuanlrc.base.controller.common;
/**
 * 支付统一处理控制器
 */
import com.yuanlrc.base.bean.Payment;
import com.yuanlrc.base.constant.RuntimeConstant;
import com.yuanlrc.base.entity.admin.PayLog;
import com.yuanlrc.base.pay.alipay.Alipay;
import com.yuanlrc.base.service.admin.PayLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@RequestMapping("/pay")
@Controller
public class PayController {

	private Logger log = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private PayLogService payLogService;

	@RequestMapping("to_pay")
	public String toPay(@RequestParam(name = "sn", required = true)String sn, Model model) {
		PayLog payLog = payLogService.findBySn(sn);
		if (payLog == null) {
			model.addAttribute("msg", "订单编号不存在!");
			return RuntimeConstant.RUNTIME_ERROR_VIEW;
		}
		//判断支付方式
		if (payLog.getPayment() == Payment.AliPay) {
			String html = Alipay.generatePcPayHtml(payLog.getSn(), payLog.getTotalAmount(), payLog.getTitle(), payLog.getInfo());
			model.addAttribute("html", html);
			return "pay/alipay/alipay_pc";
		}
		return RuntimeConstant.RUNTIME_ERROR_VIEW;
	}

	/**
	 * 支付宝异步通知接口
	 * @param request
	 * @return
	 */
	@RequestMapping("/alipay_notify")
	@ResponseBody
	public String alipayNotify(HttpServletRequest request) {
		//检查异步通知签名是否合法
		log.info("进入支付宝异步通知接口！");
		if (!Alipay.isValid(request)) {
			log.error("支付宝签名验证失败！");
			return "fail";
		}
		//商户订单号
		String sn = request.getParameter("out_trade_no");
		//支付宝交易号
		String paySn = request.getParameter("trade_no");
		//交易金额
		String totalAmount = request.getParameter("total_amount");
		//交易状态
		String status = request.getParameter("trade_status");

		if (StringUtils.isEmpty(sn)) {
			log.error("订单编号为空！");
			return "fail";
		}
		if (StringUtils.isEmpty(paySn)) {
			log.error("支付宝交易号为空！");
			return "fail";
		}
		if (StringUtils.isEmpty(totalAmount)) {
			log.error("订单金额为空！");
			return "fail";
		}
		if (StringUtils.isEmpty(status)) {
			log.error("订单状态为空！");
			return "fail";
		}
		//到这里参数都是合法
		PayLog payLog = payLogService.findBySn(sn);
		if (payLog == null) {
			log.error("订单编号不存在【" + sn + "】");
		}
		if (payLog.getTotalAmount().compareTo(new BigDecimal(totalAmount)) != 0) {
			log.error("支付金额不相同【" + totalAmount + "】" + "，真实支付金额：【" +payLog.getTotalAmount()+ "】");
		}
		//表示一切合法
		if (payLog.getStatus() == PayLog.PAY_STATUS_WAITING) {
			payLog.setPaySn(paySn);
			payLog.setStatus(PayLog.PAY_STATUS_PAID);
			payLog.setPayTime(new Date());
			payLogService.save(payLog);
		}
		return "success";
	}

}
