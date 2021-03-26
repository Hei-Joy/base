<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>${siteName!""}|支付记录管理-${title!""}</title>
<#include "../common/header.ftl"/>
<style>
td{
	vertical-align:middle;
}
</style>
</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <aside class="lyear-layout-sidebar">
      
      <!-- logo -->
      <div id="logo" class="sidebar-header">
        <a href="index.html"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}" alt="${siteName!""}" /></a>
      </div>
      <div class="lyear-layout-sidebar-scroll"> 
        <#include "../common/left-menu.ftl"/>
      </div>
      
    </aside>
    <!--End 左侧导航-->
    
    <#include "../common/header-menu.ftl"/>
    
    <!--页面主要内容-->
    <main class="lyear-layout-content">
      
      <div class="container-fluid">
        
        <div class="row">
          <div class="col-lg-12">
            <div class="card">
              <div class="card-toolbar clearfix">
                <form class="pull-right search-bar" method="get" action="list" role="form">
                  <div class="input-group">
                    <div class="input-group-btn">
                      <button class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                      编号 <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">编号</a> </li>
                      </ul>
                    </div>
                    <input type="text" class="form-control" value="${sn!""}" name="sn" placeholder="请输入编号">
                  	<span class="input-group-btn">
                      <button class="btn btn-primary" type="submit">搜索</button>
                    </span>
                  </div>
                </form>
                <#include "../common/third-menu.ftl"/>
              </div>
              <div class="card-body">
                
                <div class="table-responsive">
                  <table class="table table-bordered">
                    <thead>
                      <tr>
                        <th>
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" id="check-all"><span></span>
                          </label>
                        </th>
                        <th>支付编号</th>
                        <th>第三方交易号</th>
                        <th>金额</th>
                        <th>状态</th>
                        <th>标题</th>
                        <th>退款金额</th>
                        <th>支付时间</th>
                        <th>创建时间</th>
                      </tr>
                    </thead>
                    <tbody>
                      <#if pageBean.content?size gt 0>
                      <#list pageBean.content as payLog>
                      <tr>
                        <td style="vertical-align:middle;">
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" name="ids[]" value="${payLog.id}" data-sn="${payLog.sn}" data-status="${payLog.status}"><span></span>
                          </label>
                        </td>
                        <td style="vertical-align:middle;">${payLog.sn}</td>
                        <td style="vertical-align:middle;">${payLog.paySn!""}</td>
                        <td style="vertical-align:middle;">${payLog.totalAmount!""}</td>
                        <td style="vertical-align:middle;">
                        	<#if payLog.status == 0>
                        	<font class="text-danger">待支付</font>
                        	<#elseif payLog.status == 1>
                        	<font class="text-warning">已支付</font>
                        	<#elseif payLog.status == 2>
                        	<font class="text-yellow">已全额退款</font>
                            <#elseif payLog.status == 20>
                              <font class="text-cyan">已部分退款</font>
                        	<#else>
                        	<font class="text-gray">已关闭</font>
                        	</#if>
                        </td>
                        <td align="center">${payLog.title}</td>
                        <td style="vertical-align:middle;">${payLog.totalRefundAmount!""}</td>
                        <td align="center">${payLog.payTime!""}</td>
                        <td style="vertical-align:middle;"><font class="text-success">${payLog.createTime}</font></td>
                      </tr>
                    </#list>
                    <#else>
                    <tr align="center"><td colspan="9">这里空空如也！</td></tr>
					</#if>                      
                    </tbody>
                  </table>
                </div>
                <#if pageBean.total gt 0>
                <ul class="pagination">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="list?sn=${sn!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                  <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                  <#else>
                  <li><a href="list?sn=${sn!""}&currentPage=${showPage}">${showPage}</a></li>
                  </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?sn=${sn!""}&currentPage=${pageBean.totalPage}">»</a></li>
                  </#if>
                  <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                </ul>
                </#if>
              </div>
            </div>
          </div>
          
        </div>
        
      </div>
      
    </main>
    <!--End 页面主要内容-->
  </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
});
function pay(){
  var checked = $("input[type='checkbox']:checked");
  if(checked.length != 1){
      showWarningMsg('请选择一条数据进行支付！');
      return;
  }
  if (checked.attr('data-status') != 0) {
    showWarningMsg('该状态不可再发起支付！');
    return;
  }
  window.location.href = '/pay/to_pay?sn=' + checked.attr('data-sn');

}
//打开编辑页面
function edit(url){
	if($("input[type='checkbox']:checked").length != 1){
		showWarningMsg('请选择一条数据进行编辑！');
		return;
	}
	window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
}
//查看详情页面
function view(url){
  var checked = $("input[type='checkbox']:checked");
  if(checked.length != 1){
    showWarningMsg('请选择一条数据进行查看！');
    return;
  }
  ajaxRequest(url, 'POST', {sn:checked.attr('data-sn')}, function (rst) {
    var data = $.parseJSON(rst.data);
    var html = "";
    data = data.alipay_trade_query_response;
    for (k in data) {
      html += '<b>' + k + '</b>:<font color="green">' + data[k] + '</font><br/>';
    }
    $.alert({
        title: '订单详情信息',
        content: html,
        buttons: {
          confirm: {
            text: '确认',
            btnClass: 'btn-primary',
            action: function(){
              //$.alert('你点击了确认!');
            }
          },
          cancel: {
            text: '取消',
            action: function () {
              //$.alert('你点击了取消!');
            }
          }
        }
    });
  })
}
//关闭订单
function closePay(url){
  var checked = $("input[type='checkbox']:checked");
  if(checked.length != 1){
    showWarningMsg('请选择一条数据进行操作！');
    return;
  }
  if(checked.attr('data-status') != 0){
    showWarningMsg('该状态下不可关闭交易！');
    return;
  }
  ajaxRequest(url, 'POST', {sn:checked.attr('data-sn')}, function (rst) {
    showSuccessMsg('操作成功！', function() {
      window.location.reload();
    })
  })
}
//关闭订单
function refund(url){
  var checked = $("input[type='checkbox']:checked");
  if (checked.length != 1){
    showWarningMsg('请选择一条数据进行操作！');
    return;
  }
  if (checked.attr('data-status') != 1 && checked.attr('data-status') != 20) {
    showWarningMsg("该状态下不可操作退款");
    return;
  }
  window.location.href = url + '?sn=' + checked.attr('data-sn');
}

//查看退款详情
function viewRefund(url){
  var checked = $("input[type='checkbox']:checked");
  if(checked.length != 1){
    showWarningMsg('请选择一条数据进行查看！');
    return;
  }
  if (checked.attr('data-status') != 2 && checked.attr('data-status') != 20) {
    showWarningMsg("该状态没有退款信息！");
    return;
  }
  ajaxRequest(url, 'POST', {sn:checked.attr('data-sn')}, function (rst) {
    var data = rst.data;
    var html = "<table class='table table-bordered'><thead><th>退款金额</th><th>退款原因</th><th>退款时间</th></thead><tbody>";
    var tr = '';
    for (var i = 0; i < data.length; i++) {
      tr += '<tr><td>' + data[i].refundAmount + '</td><td>' + data[i].refundInfo + '</td><td>' + data[i].createTime + '</td><tr>'
    }
    html += tr + '</tbody></table>'
    $.alert({
      title: '退款详情信息',
      content: html,
      buttons: {
        confirm: {
          text: '确认',
          btnClass: 'btn-primary',
          action: function(){
            //$.alert('你点击了确认!');
          }
        },
        cancel: {
          text: '取消',
          action: function () {
            //$.alert('你点击了取消!');
          }
        }
      }
    });
  })
}
</script>
</body>
</html>