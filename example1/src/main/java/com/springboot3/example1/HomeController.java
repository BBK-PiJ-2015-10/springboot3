package com.springboot3.example1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.classfile.instruction.ReturnInstruction;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
