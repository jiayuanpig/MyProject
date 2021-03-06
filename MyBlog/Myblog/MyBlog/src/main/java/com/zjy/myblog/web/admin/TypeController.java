package com.zjy.myblog.web.admin;

import com.zjy.myblog.po.Type;
import com.zjy.myblog.service.TypeService;
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
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService service;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        Page<Type> list = service.listType(pageable);
        model.addAttribute("page", list);
        return "admin/BG-types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/BG-type-input";
    }

    @PostMapping("/types")
    //@Valid对对象进行校验，需要在实体类中对应字段添加@NotBlank注解，BindingResult负责返回验证结果
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        //实体类验证：name为空
        if (result.hasErrors()) {
            return "admin/BG-type-input";
        }

        //Controller验证：避免name重复的情况
        Type type1 = service.getTypeByName(type.getName());
        if (type1 != null) {//添加自定义的验证信息
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }

        //数据库验证：避免保存失败
        Type type2 = service.saveType(type);
        if (type2 == null) {//是否保存成功
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    //@Valid对对象进行校验，需要在实体类中对应字段添加@NotBlank注解，BindingResult负责返回验证结果
    //注意：type和bindingResult需要挨在一起才能进行验证
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        //实体类验证：name为空
        if (result.hasErrors()) {
            return "admin/BG-type-input";
        }

        //Controller验证：避免name重复的情况
        Type type1 = service.getTypeByName(type.getName());
        if (type1 != null) {//添加自定义的验证信息
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }

        //数据库验证：避免保存失败
        Type type2 = service.updateType(id,type);
        if (type2 == null) {//是否更新成功
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", service.getType(id));
        return "admin/BG-type-input";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        service.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }


}
