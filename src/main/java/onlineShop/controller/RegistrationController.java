package onlineShop.controller;

import onlineShop.entity.Customer;
import onlineShop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {
    @Autowired
    private CustomerService customerService;
    // controller -> service -> dao -> database

    @RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
    public ModelAndView getRegistrationForm() {
        Customer customer = new Customer();
        // 把 customer 传进去是为了 validation, 给前端的表单传一个正确的绑定对象
        return new ModelAndView("register", "customer", customer);
    }

    @RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
    // @ModelAttribute 自动的将用户在表单上传入的数据 绑定到 customer对象上
    public ModelAndView registerCustomer(@ModelAttribute Customer customer, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        // register出现异常  让重新注册
        if (result.hasErrors()) {
            modelAndView.setViewName("register");
            return modelAndView;
        }
        // register没有出问题 把customer加到数据库去
        customerService.addCustomer(customer);
        modelAndView.setViewName("login");
        modelAndView.addObject("registrationSuccess", "Registered Successfully. Login using username and password");
        return modelAndView;
    }
}
