package com.zjy.myblog.web.admin;

import com.zjy.myblog.po.Tag;
import com.zjy.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService service;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        Page<Tag> list = service.listTag(pageable);
        model.addAttribute("page", list);
        return "admin/BG-tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/BG-tag-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        //实体类验证：name为空
        if (result.hasErrors()) {
            return "admin/BG-tag-input";
        }

        //Controller验证：避免name重复的情况
        Tag type1 = service.getTagByName(tag.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加标签");
        }

        //数据库验证：避免保存失败
        Tag type2 = service.saveTag(tag);
        if (type2 == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        //实体类验证：name为空
        if (result.hasErrors()) {
            return "admin/BG-tag-input";
        }

        //Controller验证：避免name重复的情况
        Tag type1 = service.getTagByName(tag.getName());
        if (type1 != null) {//添加自定义的验证信息
            result.rejectValue("name", "nameError", "不能重复添加标签");
        }

        //数据库验证：避免保存失败
        Tag type2 = service.updateTag(id,tag);
        if (type2 == null) {//是否更新成功
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", service.getTag(id));
        return "admin/BG-tag-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        service.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }


}
