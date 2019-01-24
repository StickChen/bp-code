package com.longxuanme.bpc.spring.boot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenxuanlong
 * @date 2019/1/21
 */
@RestController
public class SpringBootDemoController {

    @RequestMapping("/")
    public String home() {
        return "SpringBootDemoController1";
    }

}
