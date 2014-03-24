package com.it.epolice.web;

import com.it.epolice.domain.UserStats;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @ResponseBody
    @RequestMapping(value="description", method = RequestMethod.POST)

    //The request is
    //curl -XPOST http://localhost:8081/user/description -H "Content-Type:application/json" -d '{ "firstName":"wang", "lastName":"lei"}'
    public String getDescription(@RequestBody UserStats stats){
        return (stats.getFirstName() + " " + stats.getLastName() + " hates wacky wabbits");

    }
}
