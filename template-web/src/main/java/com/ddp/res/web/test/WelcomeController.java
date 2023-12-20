package com.ddp.res.web.test;

import com.ddp.res.web.common.R;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zzz
 */
@RestController
public class WelcomeController {

    @GetMapping("/")
    public R< Map<String,String>> welcome(){
        Map<String,String> result = Maps.newHashMap();
        result.put("openAPI","/v3/api-docs");
        result.put("swagger","/swagger-ui/index.html");
        return R.ok(result);
    }

}
