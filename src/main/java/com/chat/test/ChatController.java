package com.chat.test;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.chat.test.auth.ChatUserDetails;
import com.chat.test.websocket.Message;

@Controller
public class ChatController {

    private String uname;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("form", new FormData());
        return "home";
    }

    @PostMapping("/") 
    public String homeForm(@ModelAttribute FormData userInput){
        System.out.println(userInput);
        if(userInput != null && userInput.getUname() != null&& !userInput.getUname().isBlank()) {
            ChatUserDetails user = new ChatUserDetails(userInput.getUname());
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/chat";
        }


        return "home";
    }

    @GetMapping("/chat")
    public String chat(Authentication auth){
        uname = auth.getName();
        return "chat";
    }

    @MessageMapping("/chat")
    @SendTo("/socket/chat")
    public Message messageHandler(Message msg){
        if(msg.getSender().equals("CLIENT_SCRIPT")){
            String cmd = msg.getMessage();
            
            if(cmd.equals("REQUEST_USERNAME")){
                System.out.println("Assigning username " + uname);
                return new Message("SERVER", uname);
            }
        }
        return msg;
    }

}
