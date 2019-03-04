package com.community.data;

import com.community.entity.User;
import com.community.util.JdbcHelper;

import java.util.List;
import java.util.Map;

public class UserDao {

    JdbcHelper helper = JdbcHelper.getInstance();

    public int save(User user){
        //调用 JdbcHelper 的 update 方法保存user对象并返回受当前sql影响的记录数
        int i = helper.update("INSERT INTO usertable(username, password)  VALUES(?,?)",user.getUsername(),user.getPassword());
        helper.destory();
        return i;
    }

    public User login(String username){
        User user=null;
        //编写SQL语句
        String sql="SELECT * FROM usertable WHERE username=?";
        List<Map<String,Object>> list=helper.query(sql,username);
        helper.destory();
        if(list!=null&&list.size()==1) {
            Map<String, Object> map = list.get(0);
            user=wrap(map);
        }
        return user;

    }

    private User wrap(Map<String,Object> map){
        User user=null;
        if(map!=null){
            user=new User();
            user.setId((Integer)map.get("id"));
            user.setUsername((String) map.get("username"));
            user.setPassword((String) map.get("password"));
        }
        return user;
    }

    public Boolean isExistUsername(String username){
        String sql="SELECT * FROM usertable WHERE username=?";
        List<Map<String,Object>> list=helper.query(sql,username);
        helper.destory();
        return list!=null&&list.size()==1;  //如果存在数据则返回true
    }

    public boolean updatePassword(String password,Integer id){
        String sql="UPDATE usertable SET password=? WHERE id=?";
        int i=helper.update(sql,password,id);
        helper.destory();
        return i==1;
    }
}
