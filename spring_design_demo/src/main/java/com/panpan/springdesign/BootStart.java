package com.panpan.springdesign;
import com.panpan.springdesign.entiy.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;

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

//    public static void main(String[] args) {
//        DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(BlogMapper.class);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//    }
//
//    private static class BlogDataSourceFactory {
//        public static DataSource getBlogDataSource() {
//            return null;
//        }
//    }
}
