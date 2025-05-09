package org.project.easyf1.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/easyF1")
public class PagesController {

    @GetMapping("/auth")
    public String authPage() {
        return "pages/auth";
    }

    @GetMapping({"/home", "/", ""})
    public String homePage() {
        return "pages/index";
    }

    @GetMapping("/history")
    public String historyPage() {
        return "pages/history";
    }

    @GetMapping("/now")
    public String nowSessionPage() {
        return "pages/nowSession";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "pages/profile";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "pages/error";
    }
}
