package com.zjy.myblog.dao;

import com.zjy.myblog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*继承Jpa后就不用实现增删改查的sql语句*/
public interface UserRepository extends JpaRepository<User, Long> {
    /*定义方法需要遵循命名规则*/

    User findByUsernameAndPassword(String username,String password);


}
