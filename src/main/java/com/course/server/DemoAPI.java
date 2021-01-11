package com.course.server;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class DemoAPI {

    //get
    @RequestMapping(value = "/getdemo/{userName}",method=RequestMethod.GET)
    public String getDemo(@PathVariable String userName){
        return userName;
    }


    //post
    @RequestMapping(value="/postdemo",method = RequestMethod.POST)
    public String postDemo(@RequestParam String userName){
        return "3";
    }

    //json
    @RequestMapping(value="/json",method = RequestMethod.POST)
    public @ResponseBody Student postDemo2(@RequestBody Student s){
        Student student = new Student();
        student.name = s.name;
        student.sex = s.sex;
        return student;
    }
}
