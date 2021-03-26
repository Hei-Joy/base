package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.Payment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 后台菜单实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="ylrc_pay_log")
@EntityListeners(AuditingEntityListener.class)
public class PayLog extends BaseEntity{

	public static final int PAY_STATUS_WAITING = 0;//待支付
	public static final int PAY_STATUS_PAID = 1;//已支付
	public static final int PAY_STATUS_REFUNDED = 2;//已全额退款
	public static final int PAY_STATUS_PART_REFUNDED = 20;//已部分退款
	public static final int PAY_STATUS_CLOSED = 3;//已关闭

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="sn",nullable=false,unique=true,length=64)
	private String sn;//支付记录编号

	@Column(name="pay_sn",unique=true,length=64)
	private String paySn;//支付平台交易号

	@ValidateEntity(required=false,minValue=0,errorRequiredMsg="请填写支付金额",errorMinValueMsg="支付金额不能小于0")
	@Column(name="total_amount",length=128)
	private BigDecimal totalAmount;//支付金额

	@ValidateEntity(required=true,errorRequiredMsg="请填写订单标题",errorMaxLengthMsg="订单标题不能超过256个字符")
	@Column(name="title",nullable=false,length=256)
	private String title;//支付订单标题
	
	@ValidateEntity(required=false)
	@Column(name="info",length=128)
	private String info;//支付订单详情

	@Column(name="status",nullable=false,length=4)
	private Integer status = PAY_STATUS_WAITING;//订单支付状态

	@Column(name="payment",nullable=false,length=4)
	private Payment payment;//支付方式

	@Column(name="total_refund_amount",length=8)
	private BigDecimal totalRefundAmount = new BigDecimal(0);//退款总金额

	@Column(name="pay_time")
	private Date payTime;//支付时间

	@Column(name="refund_time")
	private Date refundTime;//退款时间


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getTotalRefundAmount() {
		return totalRefundAmount;
	}

	public void setTotalRefundAmount(BigDecimal totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	@Override
	public String toString() {
		return "PayLog{" +
				"sn='" + sn + '\'' +
				", paySn='" + paySn + '\'' +
				", totalAmount=" + totalAmount +
				", title='" + title + '\'' +
				", info='" + info + '\'' +
				", status=" + status +
				", payment=" + payment +
				", totalRefundAmount=" + totalRefundAmount +
				", payTime=" + payTime +
				", refundTime=" + refundTime +
				'}';
	}
}
