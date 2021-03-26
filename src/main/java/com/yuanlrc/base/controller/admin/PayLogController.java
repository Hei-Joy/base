package com.yuanlrc.base.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Payment;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.dao.admin.RefundLogDao;
import com.yuanlrc.base.entity.admin.PayLog;
import com.yuanlrc.base.entity.admin.RefundLog;
import com.yuanlrc.base.pay.alipay.Alipay;
import com.yuanlrc.base.service.admin.PayLogService;
import com.yuanlrc.base.service.admin.RefundLogService;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 支付记录管理控制器
 */
@RequestMapping("/pay_log")
@Controller
public class PayLogController {

    private Logger log = LoggerFactory.getLogger(PayLogController.class);

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private RefundLogService refundLogService;

    /**
     * 支付列表页面
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PayLog payLog, PageBean<PayLog> pageBean) {
        model.addAttribute("pageBean", payLogService.findList(payLog, pageBean));
        model.addAttribute("sn", payLog.getSn());
        return "admin/pay_log/list";
    }

    /**
     * 添加支付页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("paymentList", Payment.values());
        return "admin/pay_log/add";
    }

    /**
     * 添加支付
     * @param payLog
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> add(PayLog payLog) {
        if (payLog == null) {
            return Result.error(CodeMsg.DATA_ERROR);
        }
        CodeMsg validate = ValidateEntityUtil.validate(payLog);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        //生成支付编号
        payLog.setSn(StringUtil.generateSn("YXB", ""));
        //保存数据库
        payLogService.save(payLog);
        return Result.success(payLog.getSn());
    }

    /**
     * 发起退款页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    public String refund(@RequestParam(name = "sn",required = false) String sn, Model model) {
        model.addAttribute("payLog", payLogService.findBySn(sn));
        return "admin/pay_log/refund";
    }

    /**
     * 查看订单详情
     * @param sn
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> view(@RequestParam(name = "sn",required = false) String sn) {
        PayLog payLog = payLogService.findBySn(sn);
        if (payLog == null) {
            return Result.error(CodeMsg.DATA_ERROR);
        }
        //调用支付宝接口查看订单详情
        String viewDetail = Alipay.viewDetail(sn);
        log.info(viewDetail);
        return Result.success(viewDetail);
    }

    /**
     * 关闭订单
     * @param sn
     * @return
     */
    @RequestMapping(value = "/close_pay", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> closePay(@RequestParam(name = "sn",required = false) String sn) {
        PayLog payLog = payLogService.findBySn(sn);
        if (payLog == null) {
            return Result.error(CodeMsg.DATA_ERROR);
        }
        if (payLog.getStatus() != PayLog.PAY_STATUS_WAITING) {
            return Result.error(CodeMsg.PAY_CLOSE_ERROR);
        }
        //调用支付宝关闭接口
        String closePay = Alipay.closePay(sn);
        log.info(closePay);
        JSONObject jsonObject = JSONObject.parseObject(closePay);
        int code = jsonObject.getJSONObject("alipay_trade_close_response").getIntValue("code");
        if (code != 10000) {
            CodeMsg codeMsg = CodeMsg.PAY_CLOSE_ERROR;
            codeMsg.setMsg(jsonObject.getJSONObject("alipay_trade_close_response").getString("sub_msg"));
            return Result.error(codeMsg);
        }
        payLog.setStatus(PayLog.PAY_STATUS_CLOSED);
        payLogService.save(payLog);
        return Result.success(true);
    }

    /**
     * 订单退款
     * @param refundLog
     * @return
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> refund(RefundLog refundLog) {
        if (refundLog == null) {
            return Result.error(CodeMsg.DATA_ERROR);
        }
        PayLog existPayLog = payLogService.find(refundLog.getPayLog().getId());
        if (existPayLog == null) {
            return Result.error(CodeMsg.DATA_ERROR);
        }
        if (existPayLog.getStatus() != PayLog.PAY_STATUS_PAID && existPayLog.getStatus() != PayLog.PAY_STATUS_PART_REFUNDED) {
            return Result.error(CodeMsg.PAY_REFUND_ERROR);
        }
        //再次检查金额是否合法
        BigDecimal avaliableRefund = existPayLog.getTotalAmount().subtract(existPayLog.getTotalRefundAmount());
        if (refundLog.getRefundAmount().compareTo(avaliableRefund) > 0){
            CodeMsg codeMsg = CodeMsg.PAY_REFUND_ERROR;
            codeMsg.setMsg("可退金额不得超过" + avaliableRefund);
            return Result.error(codeMsg);
        }
        refundLog.setRefundSn(StringUtil.generateSn("REFUND", ""));
        //调用支付宝退款接口
        String refund = Alipay.refund(existPayLog.getSn(), refundLog.getRefundAmount(), refundLog.getRefundInfo(), refundLog.getRefundSn());
        log.info(refund);
        JSONObject jsonObject = JSONObject.parseObject(refund);
        int code = jsonObject.getJSONObject("alipay_trade_refund_response").getIntValue("code");
        if (code != 10000) {
            CodeMsg codeMsg = CodeMsg.PAY_CLOSE_ERROR;
            codeMsg.setMsg(jsonObject.getJSONObject("alipay_trade_refund_response").getString("sub_msg"));
            return Result.error(codeMsg);
        }

        existPayLog.setTotalRefundAmount(existPayLog.getTotalRefundAmount().add(refundLog.getRefundAmount()));
        if (existPayLog.getTotalRefundAmount().compareTo(existPayLog.getTotalAmount()) == 0) {
            existPayLog.setStatus(PayLog.PAY_STATUS_REFUNDED);
        } else {
            existPayLog.setStatus(PayLog.PAY_STATUS_PART_REFUNDED);
        }
        existPayLog.setRefundTime(new Date());
        refundLogService.save(refundLog);
        payLogService.save(existPayLog);
        payLogService.save(existPayLog);
        return Result.success(true);
    }

    /**
     * 查看退款详情
     * @param sn
     * @return
     */
    @RequestMapping(value = "/view_refund", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<RefundLog>> viewRefund(@RequestParam(name = "sn",required = false) String sn) {
        PayLog payLog = payLogService.findBySn(sn);
        if (payLog == null) {
            return Result.error(CodeMsg.DATA_ERROR);
        }
        return Result.success(refundLogService.findByPayLogId(payLog.getId()));
    }

}
