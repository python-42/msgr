package com.chat.test.err;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class chatErrorController implements ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(chatErrorController.class);

    @RequestMapping("/error")
    public String error(Model model, HttpServletRequest request) {
        int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMsg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute("code", "Error code: " + status);

        if(errorMsg == ""){
            model.addAttribute("errorMsg", "Message: None");
        }else{
            model.addAttribute("errorMsg", "Message: " + errorMsg);
        }

        if(status == 404){
            model.addAttribute("userMsg", "Resource not found. Check that your URL is correct.");
        }else if(status == 500){
            model.addAttribute("userMsg", "Internal server error. You likely cannot do anything to fix this.");
        }

        logger.error("Error code " + status + " at " + request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI) + ", message: " + errorMsg + request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
        
        return "error";
    }

    public enum logLevel{
        INFO,
        WARN,
        ERROR
    }
}
