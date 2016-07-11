<%--
  Created by IntelliJ IDEA.
  User: tuyu
  Date: 2016/7/10
  Time: 21:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加用户</title>
</head>
<body>
<div class="main">
    <!-----start-main---->
    <h1>用户注册</h1>
    <form onsubmit="return validateUser();" action="<%=request.getContextPath()%>/servlet/manageUser" method="post">
        <input type="hidden" name="oprate" value="register">							<div class="lable">
        <h4>帐号</h4>	<input type="text" class="text" id="account" name="account" value="Account"   onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '';}" >
    </div>
        <div class="clear"> </div>
        <div class="lable-2">
            <h4>密码</h4><input type="password" class="text" id="password" name="password" value="password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '';}">
        </div>

        <div class="lable-2">
            <input type="text" class="text" id="email" name="email" value="your@email.com"  onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'your@email.com';}">
        </div>
        <div class="clear"> </div>
        <h3>已有帐号？点击此处进行 <span><a href="userLogin.jsp">登录</a> </span></h3>
        <div class="submit">
            <input type="submit" value="Create account" >
        </div>
    </form>
    <!-----//end-main---->
</div>
<!-----start-copyright---->
<div class="copy-right">
    <p> <a href="homepage.jsp" >返回首页</a></p>
</div>
<!-----//end-copyright---->

</body>
</html>
