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
        model.addAttribute("type",new Type());
        return "admin/BG-type-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //后端属性发生错误：name为空
        if(result.hasErrors()){
            return "admin/BG-type-input";
        }

        //避免name重复的情况
        Type type1 = service.getTypeByName(type.getName());
        if(type1 != null){//添加自定义的验证信息
            result.rejectValue("name","nameError","不能重复添加分类");
        }

        Type t = service.saveType(type);
        if(t == null){//是否保存成功
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

}
