package com.zjy.myblog.web.admin;

import com.zjy.myblog.po.Blog;
import com.zjy.myblog.po.User;
import com.zjy.myblog.service.BlogService;
import com.zjy.myblog.service.TagService;
import com.zjy.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("admin")
public class BlogController {
    private static final String INPUT = "admin/BG-blog-input";
    private static final String LIST = "admin/BG-blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(Model model, Blog blog,@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(Model model, Blog blog,@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/BG-blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog", new Blog());

        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(Model model,@PathVariable Long id){
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);

        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return INPUT;
    }


    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog blog1;
        if(blog.getId() == null){
            blog1 = blogService.saveBlog(blog);
        }else {
            blog1 = blogService.updateBlog(blog.getId(), blog);
        }

        if (blog1 == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }

        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }


}
