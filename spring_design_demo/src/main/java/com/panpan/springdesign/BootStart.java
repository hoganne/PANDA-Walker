package com.panpan.springdesign;

import com.panpan.springdesign.entiy.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class BootStart {
    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        Blog blog=null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            blog = (Blog) session.selectOne("com.panpan.springdesign.BlogMapper.selectBlog", 101);
        }
        System.out.println(blog);
    }
}
