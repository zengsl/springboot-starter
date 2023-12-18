package com.ddp.res.web.controller;

import com.ddp.res.web.common.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzz
 */
@RestController
public class WelcomeController {

    @RequestMapping("/")
    public R<String> welcome(){
        return R.ok("welcome to res-web");
    }

}
