package com.zjy.myblog.web;

import com.zjy.myblog.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("Myblog")
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/types")
    public String types() {
        return "types";
    }

    @GetMapping("/tags")
    public String tags() {
        return "tags";
    }

    @GetMapping("/archives")
    public String archives() {
        return "archives";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
