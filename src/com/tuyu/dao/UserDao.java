package com.tuyu.dao;

import com.tuyu.model.UserEntity;
import com.tuyu.util.JDBCutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuyu on 2016/7/10 18:15 .
 */
public class UserDao {
    private PreparedStatement pst;
    private ResultSet resultSet;
    private Statement stmt;
    private Connection conn;

    public PreparedStatement getPst() {
        return pst;
    }

    public void setPst(PreparedStatement pst) {
        this.pst = pst;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * @方法描述 判断当前用户是否注册
     * @param account 用户输入的帐号
     * @return true or false
     */
    public boolean isAccountExist(String account){
        JDBCutil db = new JDBCutil();
        try {
            conn=db.getConntion();
            String selectUserIDSQL="select * from userinfo where account=?";
            pst=conn.prepareStatement(selectUserIDSQL);
            pst.setString(1, account);
            resultSet=pst.executeQuery();
            if(resultSet.next()){
                return true;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;




    }



    /**
     * @方法描述 通过帐号主键id 查询实名认证信息
     * @return 用户实例集合
     */
    public List<UserEntity> getListUserIdentify(){
        List<UserEntity> userIdentifyList = new ArrayList<UserEntity>();
        JDBCutil db = new JDBCutil();
        try {
            conn = db.getConntion(); // 连接数据库
            stmt = conn.createStatement();
            //	int startSize =(pageNumber-1)*pageSize;
            //	System.out.println("startSize:"+startSize+",  pagesize:"+pageSize);


            String selectUserIdentifySQL="SELECT a.account,a.email,a.power,b.IDcard,b.name,b.telNumber,b.brith,b.height,"
                    + " b.weight,b.hobby,b.address,b.remarks FROM "
                    + "userinfo AS a INNER JOIN tbl_identify AS b ON a.userID=b.userID ";

            resultSet = stmt.executeQuery(selectUserIdentifySQL); // 执行sql语句返回结果集并解析
            //pst = conn.prepareStatement(selectUserIdentifySQL); //获取statment，执行sql
            //resultSet=pst.executeQuery();

            while (resultSet.next()) {
                String account = resultSet.getString("account");
                String email = resultSet.getString("email");
                int power=resultSet.getInt("power");
                String IDcard = resultSet.getString("IDcard");
                String name = resultSet.getString("name");
                String telNumber = resultSet.getString("telNumber");
                String birth=resultSet.getString("brith");
                String height = resultSet.getString("height");
                String weight = resultSet.getString("weight");
                String hobby = resultSet.getString("hobby");
                String address = resultSet.getString("address");
                String remarks = resultSet.getString("remarks");
                UserEntity userEntity=new UserEntity(account,email,power,name,IDcard,telNumber,address,birth,height,weight,hobby,remarks);
                userIdentifyList.add(userEntity);
            }
        } catch (Exception ex) {

            System.out.println("实名认证信息为获取！");
            ex.printStackTrace();
        } finally {//关闭数据库连接
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }




        return userIdentifyList;
    }

    /**
     * @方法描述 根据用户帐号查询主键ID
     * @param account 用户帐号
     * @return 主键ID
     */
    public int selectUserID(String account){
        int userid =0;
        JDBCutil db=new JDBCutil();
        try {
            conn=db.getConntion();
            String selectUserIDSQL="select userID from userinfo where account=?";
            pst=conn.prepareStatement(selectUserIDSQL);

            pst.setString(1, account);
            resultSet=pst.executeQuery();
            if(resultSet.next()){
                userid=Integer.valueOf(resultSet.getInt("UserID"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return userid;
    }


    /**
     * @方法描述 根据认证信息进行用户信息的实名认证
     * @param name 姓名
     * @param IDcard 身份证号
     * @param brithday 出生日期
     * @param height 身高
     * @param weight 体重
     * @param hobby 爱好
     * @param address 联系地址
     * @param remarks 备注
     * @return 数据库受影响的行数
     */
    public int addUserIdentify(int userid,String name, String IDcard, String telNumber,
                               String brithday,String height,String weight,String hobby,String address,String remarks){
        int row = 0;
        JDBCutil db=new JDBCutil();

        try {
            conn=db.getConntion();
            String addIdentifySQL="insert into tbl_identify(userID,IDcard,telNumber,brith,height,weight,hobby,remarks,address,name) values(?,?,?,?,?,?,?,?,?,?) ";
            pst=conn.prepareStatement(addIdentifySQL);
            pst.setInt(1,userid );
            pst.setString(2,IDcard);
            pst.setString(3,telNumber);
            pst.setString(4,brithday);
            pst.setString(5,height);
            pst.setString(6,weight);
            pst.setString(7,hobby);
            pst.setString(8,remarks);
            pst.setString(9,address);
            pst.setString(10,name);
            System.out.println(userid+name+IDcard+brithday);
            row=pst.executeUpdate();//返回数据库受影响的行数

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return row;
    }

    /**
     * @方法描述  根据用户输入的登录信息进行查询
     * @param username  帐号
     * @return 用户实例
     */
    public UserEntity UserLogin(String username){
        UserEntity userEntity=new UserEntity();
        JDBCutil db=new JDBCutil();
        try {
            conn=db.getConntion();
            String selectUserSQL="select account,password,email,power from userinfo where account=?";
            pst=conn.prepareStatement(selectUserSQL);
            pst.setString(1, username);
            resultSet=pst.executeQuery();
            if(resultSet.next()){
                userEntity.setAccount(username);
                userEntity.setPassword(resultSet.getString("password"));
                userEntity.setPower(Integer.valueOf(resultSet.getString("power")));
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();

        }finally{//关闭数据库
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userEntity;

    }


    /**
     * @方法描述 添加用户（注册）
     * @param account  帐号
     * @param password  密码
     * @param email  邮箱
     * @return 数据库受影响的行数
     */
    public int addUser(String account, String password, String email) {

        int row = 0; // 返回数据库受影响的行数
        JDBCutil db = new JDBCutil();
        try {
            conn = db.getConntion(); // 连接数据库
            String addUserSQL = "insert into userinfo(account,password,email,power) values(?,?,?,?)";
            pst = conn.prepareStatement(addUserSQL);
            pst.setObject(1, account);
            pst.setString(2,password );
            pst.setString(3, email);
            pst.setInt(4, 1);
            row = pst.executeUpdate(); // 执行sql语句
        } catch (Exception e) {
            System.out.println("添加用户失败！！！");
            // TODO: handle exception
            e.printStackTrace();
        } finally {  //关闭数据库连接
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return row;

    }



}
