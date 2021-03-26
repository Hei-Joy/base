package com.yuanlrc.base.service.admin;
/**
 * 后台支付操作service
 */

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.PayLogDao;
import com.yuanlrc.base.entity.admin.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class PayLogService {
	
	@Autowired
	private PayLogDao payLogDao;

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public PayLog find(Long id){
		return payLogDao.find(id);
	}

	/**
	 * 根据支付订单编号查询
	 * @param sn
	 * @return
	 */
	public PayLog findBySn(String sn){
		return payLogDao.findBySn(sn);
	}

	/**
	 * 根据支付平台交易号查询
	 * @param paySn
	 * @return
	 */
	public PayLog findByPaySn(String paySn){
		return payLogDao.findByPaySn(paySn);
	}
	
	/**
	 * 添加/编辑,当id为空进行编辑操作
	 * @param payLog
	 * @return
	 */
	public PayLog save(PayLog payLog){
		return payLogDao.save(payLog);
	}

	/**
	 * 分页查询支付记录
	 * @param payLog
	 * @param pageBean
	 * @return
	 */
	public PageBean<PayLog> findList(PayLog payLog, PageBean<PayLog> pageBean){
		ExampleMatcher withMatcher = ExampleMatcher.matching().withMatcher("sn", ExampleMatcher.GenericPropertyMatchers.contains());
		withMatcher = withMatcher.withIgnorePaths("status", "totalRefundAmount");
		Example<PayLog> example = Example.of(payLog, withMatcher);
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(), sort);
		Page<PayLog> findAll = payLogDao.findAll(example, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}

}
