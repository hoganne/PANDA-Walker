package com.panpan.springdesign;

import com.panpan.springdesign.entiy.Blog;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2021/3/14
 **/
public interface BlogMapper {
    public Blog selectBlog(Long id);
}
