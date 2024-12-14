package com.portfolio.account.blogs;

import com.portfolio.account.blogs.dto.request.CreateBlogDto;
import com.portfolio.account.blogs.dto.request.UpdateBlogDto;
import com.portfolio.account.blogs.dto.response.BlogResponseDto;
import com.portfolio.account.blogs.entity.Blog;
import com.portfolio.core.service.CrudService;
import com.portfolio.core.exception.ValidationException;
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
