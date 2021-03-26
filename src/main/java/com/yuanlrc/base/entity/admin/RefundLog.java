package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 退款记录实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="ylrc_refund_log")
@EntityListeners(AuditingEntityListener.class)
public class RefundLog extends BaseEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@ManyToOne
	@JoinColumn(name="pay_log_id")
	private PayLog payLog;//所属支付记录
	
	@Column(name="refund_sn",nullable=false,length=64,unique=true)
	private String refundSn;//退款编号

	@Column(name="refund_amount",length=8)
	private BigDecimal refundAmount = new BigDecimal(0);//退款金额

	@Column(name="refund_info",length=256)
	private String refundInfo;//退款原因

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public PayLog getPayLog() {
		return payLog;
	}

	public void setPayLog(PayLog payLog) {
		this.payLog = payLog;
	}

	public String getRefundSn() {
		return refundSn;
	}

	public void setRefundSn(String refundSn) {
		this.refundSn = refundSn;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(String refundInfo) {
		this.refundInfo = refundInfo;
	}

	@Override
	public String toString() {
		return "RefundLog{" +
				"payLog=" + payLog +
				", refundSn='" + refundSn + '\'' +
				", refundAmount=" + refundAmount +
				", refundInfo='" + refundInfo + '\'' +
				'}';
	}
}
