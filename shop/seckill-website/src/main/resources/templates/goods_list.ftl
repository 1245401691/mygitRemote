<!DOCTYPE html>
<html lang="en">
<head>
    <title>商品列表</title>
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
<div class="panel panel-default">
    <div class="panel-heading">秒杀商品列表</div>
    <table class="table" id="goodslist">
        <tr><td>商品名称</td><td>商品图片</td><td>商品原价</td><td>秒杀价</td><td>库存数量</td><td>详情</td></tr>
        <#list goodsList as goods>
        <tr>
            <td>${goods.goodName}</td>
            <td ><img src="${goods.goodImg}" width="100" height="100" /></td>
            <td>${goods.goodPrice}</td>
            <td>${goods.seckillPrice}</td>
            <td>${goods.stockCount}</td>
            <td><a href="/goods/to_detail?goodsId=${goods.id}">详情</a></td>
        </tr>
        </#list>
    </table>
</div>
</body>
</html>
