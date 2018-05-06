//package pers.gene.ticketmanagement.web;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import pers.gene.ticketmanagement.domain.SimpleResponse;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RestController
//public class BrowserSecurityController {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//    private RequestCache requestCache = new HttpSessionRequestCache();
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    @Autowired
//    private SecurityProperties securityProperties;
//
//    /**
//     * 登录页面请求
//     * @param request
//     * @param response
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping("/authentication/require")
//    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
//    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//        if (savedRequest != null){
//            String redirectUrl = savedRequest.getRedirectUrl();
//            logger.info("引发跳转的请求是：" + redirectUrl);
//            if (StringUtils.endsWithIgnoreCase(redirectUrl,".html")){
//                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
//            }
//        }
//        return new SimpleResponse("访问的服务器需要身份认证，请引导用户验证登录页");
//    }
//
//}