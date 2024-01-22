package com.typetaskpro.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class DefaultController {
  
  @GetMapping
  public ModelAndView redirectToReadMe() {
    return new ModelAndView("redirect:https://github.com/MartinGHaas/TypeTaskPro-Backend?tab=readme-ov-file#starting-the-application");
  }
}
