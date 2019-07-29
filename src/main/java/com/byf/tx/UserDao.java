package com.byf.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insert(){
        String sql = "insert into user(name,phone) values(?,?)";
        String user = UUID.randomUUID().toString().substring(0,5);
        jdbcTemplate.update(sql,user, 18705193697L);
        System.out.println("插入完成");
        int i = 1 /0;
    }
}
