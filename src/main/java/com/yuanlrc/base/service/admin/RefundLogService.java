package com.yuanlrc.base.service.admin;
/**
 * 后台支付操作service
 */

import com.yuanlrc.base.dao.admin.RefundLogDao;
import com.yuanlrc.base.entity.admin.RefundLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RefundLogService {
	
	@Autowired
	private RefundLogDao refundLogDao;

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public RefundLog find(Long id){
		return refundLogDao.find(id);
	}

	/**
	 * 根据退款编号查询
	 * @param refundSn
	 * @return
	 */
	public RefundLog findBySn(String refundSn){
		return refundLogDao.findByRefundSn(refundSn);
	}

	
	/**
	 * 添加/编辑,当id为空进行编辑操作
	 * @param RefundLog
	 * @return
	 */
	public RefundLog save(RefundLog RefundLog){
		return refundLogDao.save(RefundLog);
	}

	/**
	 * 根据支付订单号查询
	 * @param payLogId
	 * @return
	 */
	public List<RefundLog> findByPayLogId(Long payLogId){
		return refundLogDao.findByPayLogId(payLogId);
	}

}
