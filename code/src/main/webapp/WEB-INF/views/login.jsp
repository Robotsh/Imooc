<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>慕课网登录</title>
    <style type="text/css">
        .loginBox {
            background: white;
            width: 100%;
            height: 700px;
            margin: auto 0;
        }

        .loginTop {
            width: 100%;
            height: 100px;
            background: #EBEFF0;
            text-align: center;
        }

        .login {
            list-style-type: none;
        }

        .login_cont {
            margin-top: 80px;
            width: 450px;
            height: 500px;
            border: 1px solid black;
            font-size: 20px;
            margin-left: 38%;
            margin-bottom: 100px;
        }

        .login_btn {
            width: 300px;
            height: 40px;
            font-size: 20px;
            border-radius: 4px;
        }

        .loginImage {
            margin: 20px;
        }

        input {
            width: 300px;
            height: 30px;
        }

        .login {
            margin-left: 50px;
        }

        a {
            font-size: 15px;
        }

        .loginBottom {
            background: #EBEFF0;
            width: 100%;
            height: 150px;
            text-align: center;
        }
    </style>
    <script type="text/javascript">
        var index = 1;

        function addImg(e) {
            var parentDiv = document.getElementById("insert");

            var topValue = 0, leftValue = 0;
            var obj = parentDiv;
            while (obj) {
                leftValue += obj.offsetLeft;
                topValue += obj.offsetTop;
                obj = obj.offsetParent;
            }
            e = e || window.event;
            var left = e.clientX + document.body.scrollLeft - document.body.clientLeft - 10;//"-110px";
            var top = e.clientY + document.body.scrollTop - document.body.clientTop - 10;//"-110px";
            var imgDivId = "img_" + index++;

            var newDiv = document.createElement("div");
            parentDiv.appendChild(newDiv);

            newDiv.id = imgDivId;
            newDiv.style.position = "relative";
            newDiv.style.zIndex = index;
            newDiv.style.width = "20px";
            newDiv.style.height = "20px";
            newDiv.style.top = top - topValue - 150 + 10 + "px";
            newDiv.style.left = left - leftValue - 300 + "px";
            newDiv.style.display = "inline";
            newDiv.setAttribute("onclick", "removeSelf('" + imgDivId + "')");

            var img = document.createElement("img");
            newDiv.appendChild(img);

            img.src = "../../assets/logo.png";
            img.style.width = "20px";
            img.style.height = "20px";
            img.style.top = "0px";
            img.style.left = "0px";
            img.style.position = "absolute";
            img.style.zIndex = index;

        }

        function removeSelf(id) {
            document.getElementById("insert").removeChild(document.getElementById(id));
        }

        function login() {
            var parentDiv = document.getElementById("insert");
            var nodes = parentDiv.childNodes;
            var result = '';
            for (var i = 0; i < nodes.length; i++) {
                var id = nodes[i].id;
                if (id && id.startsWith('img_')) {
                    var top = document.getElementById(id).style.top;
                    var left = document.getElementById(id).style.left;
                    result = result + top.replace('px', '') + ',' + left.replace('px', '') + ';';
                }
            }
            document.getElementById('location').value = result.substr(0, result.length - 1);
            document.getElementById('loginForm').submit();
        }
    </script>


</head>
<body>
<div class="loginBox">
    <div class="loginTop">
        <img src="../../assets/log.png" height="50" width="140" class="loginImage"/>
    </div>
    <div class="login_cont">
        <ul class="login">
            <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post">
                <input type="hidden" id="location" name="location">
                <li class="l_tit">邮箱/用户名/手机号</li>
                <li class="mb_10"><input type="text" name="userName" class="login_input user_icon"></li>
                <li class="l_tit">密码</li>
                <li class="mb_10"><input type="password" name="password" class="login_input user_icon"></li>
                <li>选出图片中的"${tip}"</li>
                <div id="insert">
                    <img src="${pageContext.request.contextPath}/assets/daan/${file}" height="150" width="300" onclick="addImg()" >
                </div>
                </li>
                <li><input type="button" value="登录" class="login_btn" onclick="login();"></li>
                <br/>
                <a>使用官方合作账号登录<br/>
                    QQ|网易|新浪微博|腾讯微博</a>
            </form>
        </ul>
    </div>
    <div class="loginBottom">
        <br/>
        <br/>
        慕课简介|慕课公告|招纳贤士|联系我们|客服热线：400-675-1234<br/>
        Copyright © 2006-2014 &nbsp;&nbsp;&nbsp;&nbsp;慕课版权所有 &nbsp;&nbsp;&nbsp;&nbsp;京ICP备09037834号&nbsp;&nbsp;
        京ICP证B1034-8373号&nbsp;&nbsp;&nbsp;&nbsp; 某市公安局XX分局备案编号123456789
    </div>
</div>
</body>
</html>
