package com.portfolio.cms.modules.blogs;

import com.portfolio.cms.modules.blogs.dto.request.BlogCreateUpdateDto;
import com.portfolio.cms.modules.blogs.dto.response.BlogResponseDto;
import com.portfolio.cms.modules.blogs.entity.Blog;
import com.portfolio.core.service.CrudService;
import com.portfolio.core.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class BlogService extends CrudService<BlogRepository, Blog, BlogCreateUpdateDto, BlogResponseDto, Integer> {
    public BlogService(BlogRepository repository) {
        super(repository, Blog.class, BlogResponseDto.class);
    }

    @Override
    protected void validate(Blog entity) throws ValidationException {

        super.validate(entity);
    }
}
