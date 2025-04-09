package org.project.easyf1.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/EasyF1")
public class AuthPageController {

    @GetMapping("/auth")
    public String authPage() {
        return "pages/auth";
    }
}
