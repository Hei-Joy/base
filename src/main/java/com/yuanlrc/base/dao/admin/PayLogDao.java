package com.yuanlrc.base.dao.admin;
/**
 * 支付记录数据库操作层
 */

import com.yuanlrc.base.entity.admin.PayLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayLogDao extends JpaRepository<PayLog, Long> {
	@Query("select pl from PayLog pl where pl.id = :id")
	PayLog find(@Param("id")Long id);

	PayLog findBySn(String sn);

	PayLog findByPaySn(String paySn);
}
