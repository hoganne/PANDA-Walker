package com.panpan.springdesign.dao;

import com.panpan.springdesign.entity.Blog;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2021/3/14
 **/
public interface BlogMapper {
    public Blog selectBlog(Long id);
}
