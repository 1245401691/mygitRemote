<!DOCTYPE html>
<html lang="en">
<head>
    <title>商品详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css" /><!-- bootstrap -->
    <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/jquery-validation/jquery.validate.min.js"></script> <!-- jquery-validator -->
    <script type="text/javascript" src="/jquery-validation/localization/messages_zh.min.js"></script>
    <script type="text/javascript" src="/layer/layer.js"></script><!-- layer -->
    <script type="text/javascript" src="/js/md5.min.js"></script><!-- md5.js -->
    <script type="text/javascript" src="/js/common.js"></script><!-- common.js -->
</head>
<body>
<div class="panel panel-default"  style="height:100%;background-color:rgba(222,222,222,0.8)">
    <div class="panel-heading">秒杀订单详情</div>
    <table class="table" id="goodslist">
        <tr>
            <td>商品名称</td>
            <td colspan="3">${good.goodName}</td>
        </tr>
        <tr>
            <td>商品图片</td>
            <td colspan="2"><img src="${good.goodImg}"  width="200" height="200" /></td>
        </tr>
        <tr>
            <td>订单价格</td>
            <td colspan="2">${order.seckillPrice}</td>
        </tr>
        <tr>
            <td>下单时间</td>
            <td id="createDate" colspan="2">${order.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
        </tr>
        <tr>
            <td>订单状态</td>
            <#if order.status==0>
                <td>
                    未支付
                </td>
                <td>
                    <button class="btn btn-primary btn-block" type="submit" id="payButton">立即支付</button>
                </td>
            <#elseif order.status==1>
                <td>
                    已支付
                </td>
                <td></td>
            </#if>
        </tr>
        <tr>
            <td>收货人</td>
            <td colspan="2">叩丁狼  13088889999</td>
        </tr>
        <tr>
            <td>收货地址</td>
            <td colspan="2">广州市天河区棠下街道涌东路大地商务港603</td>
        </tr>
    </table>
</div>
</body>
<script>
    $(function () {
        $("#payButton").click(function () {
            window.location.href="/index"
        })
    })
</script>
</html>
