<!DOCTYPE html>
<html lang="en">
<head>
    <title>订单列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jquery -->
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <!-- bootstrap -->
    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css" />
    <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
    <!-- jquery-validator -->
    <script type="text/javascript" src="/jquery-validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/jquery-validation/localization/messages_zh.min.js"></script>
    <!-- layer -->
    <script type="text/javascript" src="/layer/layer.js"></script>
    <!-- md5.js -->
    <script type="text/javascript" src="/js/md5.min.js"></script>
    <!-- common.js -->
    <script type="text/javascript" src="/js/common.js"></script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">订单列表</div>
    <table class="table" id="goodslist">
        <tr><td>订单编号</td><td>商品名称</td><td>商品图片</td><td>商品数量</td><td>商品价格</td><td>订单来源</td><td>订单状态</td><td>支付时间</td><td>订单时间</td><td>操作</td></tr>
        <#list orderList as order>
        <tr>
            <td>${order.orderNo}</td>
            <td>${order.goodsName}</td>
            <td ><img src="${order.goodsImg}" width="100" height="100" /></td>
            <td>${order.goodsCount}</td>
            <td>${order.goodsPrice}</td>
            <#if order.orderChannel==1>
                <td>秒杀订单</td>
            <#else>
                 <td>普通订单</td>
            </#if>
            <#if order.status == 0>
                <td>未支付</td>
            <#elseif order.status == 1>
                <td>已支付</td>
            </#if>
            <td>${(order.payDate?string('yyyy-MM-dd HH:mm:ss'))!''}</td>
            <td>${order.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
            <#if order.status == 0>
                <td>去支付</td>
            <#elseif order.status == 1>
                <td>其他</td>
            </#if>
        </tr>
        </#list>
    </table>
</div>
</body>
</html>
