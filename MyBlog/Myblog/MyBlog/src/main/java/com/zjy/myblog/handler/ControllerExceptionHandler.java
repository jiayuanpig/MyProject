package com.zjy.myblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//拦截所有@Controller注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {//自定义前端拦截器

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL:{},Exception:{}", request.getRequestURI(), e);

        //为了避免自定义异常也被拦截，将有状态码的异常交给spring框架自行处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView modeView = new ModelAndView();
        modeView.addObject("url", request.getRequestURL());
        modeView.addObject("exception", e);
        modeView.setViewName("error/error");
        return modeView;
    }


}
