package pers.gene.ticketmanagement.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.gene.ticketmanagement.domain.Customer;
import pers.gene.ticketmanagement.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//这个注解导致返回字符串
//@RestController
//@ResponseBody
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private CustomerService customerService;

    @RequestMapping(value="/register",method= RequestMethod.POST)
    public String  regist(HttpServletRequest request){
//        CustomerService service = new CustomerService();
        if (request.getParameter("passwordsignup").equals(request.getParameter("passwordsignup_confirm"))){
            Customer customer = new Customer();
            customer.setId(UUID.randomUUID().toString());
            customer.setUserName(request.getParameter("usernamesignup"));
            customer.setPassword(request.getParameter("passwordsignup"));
            customer.setEmail(request.getParameter("emailsignup"));
            customer.setCellphone(request.getParameter("cellphonesignup"));
            customerService.regist(customer);
//            confirm("注册成功")；
            return "index";
        }else {
            return "passwordfail";
        }

    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request){

        if (checkEmailFormat(request.getParameter("username"))){
            //用邮箱登录
            String email = request.getParameter("username");
            String password = request.getParameter("password");
            return customerService.loginByEmail(email, password);
        }else {
            //用用户名登录
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            return customerService.loginByUserName(userName, password);
        }
    }
    //验证邮箱格式
    boolean checkEmailFormat(String email){
        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);

        if(matcher.find()){
            return true;
        }
        return false;
    }

//    @RequestMapping("/list")
//    public List<Customer> getCustomer(){
//        LOGGER.info("从数据库读取Custom集合");
//        return customerService.getList();
//    }
}