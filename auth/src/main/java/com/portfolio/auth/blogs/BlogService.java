package com.portfolio.auth.blogs;

import com.portfolio.auth.blogs.dto.request.CreateBlogDto;
import com.portfolio.auth.blogs.dto.request.UpdateBlogDto;
import com.portfolio.auth.blogs.dto.response.BlogResponseDto;
import com.portfolio.auth.blogs.entity.Blog;
import com.portfolio.auth.common.crud.CrudService;
import com.portfolio.auth.common.crud.SimpleCrudService;
import com.portfolio.auth.common.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class BlogService extends CrudService<BlogRepository, Blog, CreateBlogDto, UpdateBlogDto, BlogResponseDto, Integer> {
    public BlogService(BlogRepository repository) {
        super(repository, Blog.class, BlogResponseDto.class);
    }

    @Override
    protected void validate(Blog entity) throws ValidationException {

        super.validate(entity);
    }
}
