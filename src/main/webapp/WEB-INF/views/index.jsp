<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>钉钉扫码登录</title>

    <!-- Bootstrap Core CSS -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/static/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/static/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/static/js/metisMenu/metisMenu.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel" id="dingtalk-login"></div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="/static/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/static/js/bootstrap.min.js"></script>
<script src="/static/js/metisMenu/metisMenu.min.js"></script>
<!-- Custom Theme JavaScript -->
<script src="/static/js/sb-admin-2.js"></script>

<script src="//g.alicdn.com/dingding/dinglogin/0.0.2/ddLogin.js"></script>

<script>
    $(function () {
        var redirect_url = 'http://www.zhongfl.com/ddtalk/callback';
        var goto_url = 'https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=dingoacbh0reytzjlrrtwc&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=' + redirect_url;
        DDLogin({
            id: "dingtalk-login",
            "goto": encodeURIComponent(goto_url)
        });

        var hanndleMessage = function (event) {
            var data = event.data || "";
            var origin = event.origin || ""
            if (data && data.length > 0 && origin.indexOf('login.dingtalk') > -1) {
                var redirect_uri = encodeURIComponent(redirect_url);
                window.location.href = 'https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=dingoacbh0reytzjlrrtwc&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=' + redirect_uri + '&loginTmpCode=' + data;
            }
        };

        if (typeof window.addEventListener != 'undefined') {
            window.addEventListener('message', hanndleMessage, false);
        } else if (typeof window.attachEvent != 'undefined') {
            window.attachEvent('onmessage', hanndleMessage);
        }
    });
</script>
</body>
</html>
