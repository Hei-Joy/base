package com.yuanlrc.base.dao.admin;
/**
 * 支付记录数据库操作层
 */

import com.yuanlrc.base.entity.admin.RefundLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundLogDao extends JpaRepository<RefundLog, Long> {

	@Query("select rl from RefundLog rl where rl.id = :id")
	RefundLog find(@Param("id")Long id);

	RefundLog findByRefundSn(String refundSn);

	List<RefundLog> findByPayLogId(Long payLogId);
}
