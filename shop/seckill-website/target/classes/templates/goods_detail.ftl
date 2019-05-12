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
    <script type="text/javascript" src="/layer/mobile/layer.js"></script><!-- layer -->
    <script type="text/javascript" src="/js/md5.min.js"></script><!-- md5.js -->
    <script type="text/javascript" src="/js/common.js"></script><!-- common.js -->
    <script type="text/javascript" src="/js/messages_cn.js"></script><!-- messages_cn.js -->
    <script type="text/javascript" src="/js/jquery.bootstrap.min.js"></script><!-- bootstrap.js -->
    <script type="text/javascript" src="/js/socket.js"></script><!-- bootstrap.js -->
</head>
<body>

<div class="panel panel-default">
    <div class="panel-heading">秒杀商品详情</div>
    <div class="panel-body">
        <#if !user??>
            <span> 您还没有登录，请登陆后再操作<br/></span>
        </#if>
        <span>没有收货地址的提示。。。</span>
    </div>
    <table class="table">
        <tr>
            <td>商品名称</td>
            <td colspan="3">${good.goodName}</td>
        </tr>
        <tr>
            <td>商品图片</td>
            <td colspan="3"><img src="${good.goodImg}" width="200" height="200" /></td>
        </tr>
        <tr>
            <td>秒杀开始时间</td>
            <td >${good.startDate?string('yyyy-MM-dd HH:mm:ss')}</td>
            <td id="seckillTip">

            </td>
            <td>
                <img id="verifyCodeImag">
                <input type="text" id="verifyCodeValue" style="display: none">
            </td>
            <td>
                    <button class="btn btn-primary btn-block" type="submit" id="buyButton">立即秒杀</button>
            </td>
        </tr>
        <tr>
            <td>商品原价</td>
            <td colspan="3">${good.goodPrice}</td>
        </tr>
        <tr>
            <td>秒杀价</td>
            <td colspan="3">${good.seckillPrice}</td>
        </tr>
        <tr>
            <td>库存数量</td>
            <td colspan="3">${good.stockCount}</td>
        </tr>
    </table>
</div>
</body>
<script>
    $(function () {
        var status = show();
        changeVerifyCode();
        var setTime =window.setInterval(show,1000)
        var goodId = ${good.id};
        var ws;

        //给立即秒杀绑定个点击事件
        $("#buyButton").click(function () {
            getPath(goodId);
        })

        function getPath(goodId) {
            var verifyCode = $("#verifyCodeValue").val()
            console.log(verifyCode);
            if(verifyCode==""){
                $.messager.alert("温馨提示","请输入验证码")
                return;
            }
            $.ajax({
                url:"/seckill/getPath",
                data:{goodId:goodId,verifyCode:verifyCode},
                type:"POST",
                success:function (data) {
                    if(data.code==200){
                        doskill(data.data)
                    }else{
                        $.messager.alert("温馨提示",data.msg)
                        console.log(data.msg);
                    }
                }
            })
        }

        function changeVerifyCode() {
            if(status==1&&${user.id}!=null){
                $("#verifyCodeImag").prop("src","/seckill/getVerifyCode");
                $("#verifyCodeValue").show();
            }
        }

        function doskill(path) {
            $.ajax({
                url:"/seckill/"+path+"/do_seckill",
                data:{goodId:goodId},
                type:"POST",
                success:function (data) {
                    if(data.code==200){
                        $.messager.confirm("温馨提示", "下载订单成功，点击ok前往订单详情页面",function () {
                            window.location.href="/order_detail.html?goodId="+goodId;
                            console.log(1);
                            // ws = createScoket("123456");
                            // ws.send(123456);
                        })
                      }else{
                        $.messager.alert("温馨提示",data.msg)
                        console.log(data.msg);
                    }
                }
            })
        }

        function show(){
            var startRemainDate=parseInt((startDate.getTime()-new Date().getTime())/1000);
            var endRemainDate=parseInt((endDate.getTime()-new Date().getTime())/1000);
            if(startRemainDate>0){
                $("#seckillTip").html("距离秒杀开始倒计时"+startRemainDate+"秒")
                $("#buyButton").prop("disabled",true);
                return 0;
            }else if(endRemainDate>0){
                $("#seckillTip").html("秒杀中");
                $("#buyButton").prop("disabled",false);
                return 1;
            }else{
                $("#seckillTip").html("秒杀已经结束");
                $("#buyButton").prop("disabled",true);
                window.clearInterval(setTime);
                return 2;
            }
        }
    })
        //获取到开始时间和结束时间
        var startDate = new Date("${good.startDate?string('yyyy-MM-dd HH:mm:ss')}")
        var endDate = new Date("${good.endDate?string('yyyy-MM-dd HH:mm:ss')}")
</script>
</html>
