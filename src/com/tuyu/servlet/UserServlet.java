package com.tuyu.servlet;

import com.tuyu.dao.UserDao;
import com.tuyu.model.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * 方法用途：
 * Created by Tuyu on 2016/7/10 21:18 .
 */
@WebServlet(name = "UserServlet")
public class UserServlet extends HttpServlet {
    private UserEntity userEntity; // 定义一个用户实例
    private UserDao userDao; // dao访问实例
    private static final long serialVersionUID = 2L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8"); // 设置字符集，以防止传入中文乱码
        String oprate = request.getParameter("oprate");
        // 如果该次请求是register，则是用户注册. 若是login，则是用户登录
        if ("register".equals(oprate)) { // 用户注册
            // 从页面中取得参数
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            //查询当前帐号是否被注册
            boolean isexist= userDao.isAccountExist(account);
            // 调用dao层方法将用户存入数据库
            if(isexist){
                request.getRequestDispatcher("/pages/userIsExist.jsp").forward(request, response);
            }else{
                int row = userDao.addUser(account, password, email);
                request.getRequestDispatcher("/pages/homepage.jsp").forward(request, response);
            }
        } else if ("login".equals(oprate)) { // 用户进行登录
            // 从页面取得参数
            String username = request.getParameter("account");
            String password = request.getParameter("password");
            // 调用dao层方法验证用户的登录信息是否正确
            userEntity = userDao.UserLogin(username);
            // 调用查询新闻集合的方法
            List<UserEntity> userEntityList = userDao.getListUserIdentify();
            String name=userEntityList.get(1).getName();
            if (userEntity.getPassword().equals(password)) {// 如果用户存在，这登录成功，进入首页
                System.out.println("验证成功！ ");
                // 设置userEntity属性并保存到session中，供后续页面调用
                userEntity.setAccount(username);
                userEntity.setPassword(password);
                int power = userEntity.getPower();
                System.out.println("user's power:" + power);
                HttpSession session = request.getSession();
                // 将登录信息存入session中
                session.setAttribute("useraccount", username);
                session.setAttribute("userpower", power);
                session.setAttribute("username", name);
                //设置cookie 保存用户的登录状态,判断用户是否登录
                Cookie cookie = new Cookie("loginstatus", "true");
                cookie.setPath("/");
                response.addCookie(cookie);


                request.getRequestDispatcher("/pages/homepage.jsp").forward(
                        request, response);

            } else if (!userEntity.getPassword().equals(password)) {
                // 验证登录信息失败
                response.sendRedirect("../pages/loginError.jsp");
            }

        } else if ("userIdentify".equals(oprate)) { // 用户实名认证

            // 从页面中取得参数
            String name = request.getParameter("name");
            String IDcard = request.getParameter("IDcard");
            String birthday = request.getParameter("birthDay");
            String height = request.getParameter("height");
            String weight = request.getParameter("weight");
            String hobby = request.getParameter("hobby");
            String address = request.getParameter("address");
            String telnumber = request.getParameter("telNumber");
            String remarks = request.getParameter("remarks");
            // 查询当前用户的主键id
            HttpSession session = request.getSession();
            String account = (String) session.getAttribute("useraccount");
            Integer userid = userDao.selectUserID(account);

            // 调用实名认证方法
            int row = userDao.addUserIdentify(userid, name, IDcard, telnumber,
                    birthday, height, weight, hobby, address, remarks);
            // System.out.println("userid:"+userid);
            System.out.println("实名认证出现问题：" + row);
            request.getRequestDispatcher("/pages/homepage.jsp").forward(
                    request, response);
            // session.setAttribute("username", name);
        } else if ("showUserIdentify".equals(oprate)) {// 查询用户详细信息
            // 调用查询新闻集合的方法
            List<UserEntity> userEntityList = userDao.getListUserIdentify();
            // 添加到reque作用域
            request.setAttribute("userList", userEntityList);

//			System.out.println(userEntityList.get(1).getAddress());
            // 转发到newslist.jsp页面
            RequestDispatcher req = request
                    .getRequestDispatcher("/pages/userList.jsp");
            req.forward(request, response);


        } else if ("adminLogin".equals(oprate)) {// 管理员登录

            // 从页面中取得参数
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            // 调用dao层方法验证用户的登录信息是否正确

            userEntity = userDao.UserLogin(account);
            System.out.println(userEntity.getPassword()+" ,  "+userEntity.getPower());
            if (userEntity.getPassword().equals(password)&&userEntity.getPower()==3) {// 如果用户存在，且power=3，则确定该用户是管理员
                System.out.println("验证成功！ ");
                // 设置userEntity属性并保存到session中，供后续页面调用
                userEntity.setAccount(account);
                //	System.out.println("Your power:" + userPower);
                HttpSession session = request.getSession();
                // 将登录信息存入session中，在页面显示
                session.setAttribute("adminaccount", account);
                //session.setAttribute("adminpower", userPower);
                //设置cookie 保存用户的登录状态
                Cookie cookie = new Cookie("loginstatus", "true");
                cookie.setPath("/");
                response.addCookie(cookie);
                //转入servlet，让用户选择接下来进行的操作
                request.getRequestDispatcher("../pages/adminManage.jsp").forward(
                        request, response);

            } else if (!userEntity.getPassword().equals(password)||userEntity.getPower()!=3) {
                // 验证登录信息失败
                response.sendRedirect("../pages/loginError.jsp");
            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
