package com.epam.jtc.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("SpringMVC/**")
public class HomeController {
    
    private static final Logger logger = LogManager
                                             .getLogger(new Object() {
                                             }.getClass().getEnclosingClass());
    
    public HomeController() {
        logger.info("Controller created");
    }
    
    @RequestMapping(value = "/test" /*,
            headers={"Content-Type=application/json",
            "Accept=application/json"}*/)
    //produces = { MediaType.APPLICATION_JSON_VALUE })
    //@ResponseBody
    public List<String> showHomePage(Map<String, Object> model,
                                     HttpServletRequest request) {
        
        logger.info("Controller Method");
        logger.info(model);
        logger.info("Req uri = {}", request.getRequestURI());
        logger.info("Req url = {}", request.getRequestURL());
        
        model.put("msg", "test"); //Добавить сообщения в модель
        
        List<String> outList = new ArrayList<>();
        outList.add("elem1");
        outList.add("elem2");
        
        return outList;
        //return "{ \"par1\":\"val1\"}";
    }
}