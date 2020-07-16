package com.baizhi.resolvers;

import com.baizhi.exception.UserNameNotFoundException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyGlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
        String message = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView();
        if(ex instanceof UserNameNotFoundException){
            modelAndView.addObject("msg",message);
            modelAndView.setViewName("main/main");
        }
        return modelAndView;
    }
}
