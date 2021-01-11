package onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    // return to home page
    public String sayIndex() {
        return "index";
    }

    @RequestMapping("/login")
    // 正常login -> /login?error
    // login出现错误 -> /login?error
    // logout请求 -> /login?logout
    // 不写 RequestMethod 是说都可以
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView();
        // 返回给用户看的view的名字是那个  前端去找那个.jsp文件展示给用户
        modelAndView.setViewName("login");

        if (error != null) {
            modelAndView.addObject("error", "Invalid username and password");
        }

        if (logout != null) {
            modelAndView.addObject("logout", "You have logged out successfully");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
    public String sayAbout() {
        return "aboutUs";
    }
}
