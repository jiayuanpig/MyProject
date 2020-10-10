package com.zjy.myblog.web;

import com.zjy.myblog.po.Blog;
import com.zjy.myblog.po.Type;
import com.zjy.myblog.service.BlogService;
import com.zjy.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String blog(Model model, @RequestParam Long id,
                       @PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
            id = types.get(0).getId();
        }
        Blog blog = new Blog();
        Type type = new Type();
        type.setId(id);
        blog.setType(type);

        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable,blog));
        model.addAttribute("id", id);
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
