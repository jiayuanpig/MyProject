package com.zjy.myblog.web;

import com.zjy.myblog.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Myblog")
public class IndexController {

    @GetMapping("/")
    public String index(){
//        int i = 1/0;
//        String blog = null;
//        if(blog == null){
//            throw new NotFoundException("博客不存在");
//        }
//        System.out.println("----index----");

        return "index";
    }
}
