package com.lockbur.dingtalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangkun23 on 2016/12/12.
 */
@Controller
public class DemoController {

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

}
