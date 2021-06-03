package com.example.ex4.controllers;

import com.example.ex4.UserNotConnectedException;
import com.example.ex4.beans.ActiveUserStore;
import com.example.ex4.beans.LoggedUser;
import com.example.ex4.repo.Message;
import com.example.ex4.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository repository;

    private MessageRepository getRepo() {
        return repository;
    }

    @Autowired
    private ActiveUserStore activeUserStore;

    @GetMapping("/")
    public String mainPage(HttpSession session, Model model) {
        Object userNameObj = session.getAttribute("userName");
        if (userNameObj == null) {
            return "login-form";
        }

        String userName = (String) userNameObj;
        model.addAttribute("userName", userName);
        return "chat-room";
    }

    @GetMapping("/search-page")
    public String searchPage(HttpSession session, Model model) {
        Object userNameObj = session.getAttribute("userName");
        if (userNameObj == null) {
            return "redirect:/";
        }

        String userName = (String) userNameObj;
        model.addAttribute("userName", userName);
        return "search-page";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName, HttpSession session, Model model) {
        if(activeUserStore.isUserConnected(userName)) {
            model.addAttribute("error", "The user name you chose is already connected.");
            return "login-form" ;
        }

        if (!(userName == null || userName.equals(""))) {
            session.setAttribute("userName", userName);
        }

        activeUserStore.addUser(new LoggedUser(userName));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        activeUserStore.removeUser(userName);
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping(value="/get-last-messages")
    public @ResponseBody
    List<Message> getLastMessages(HttpSession session) {
        try {
            activeUserStore.addUserFromSession(session);
        } catch (UserNotConnectedException e) {

        }

        List<Message> messagesListReversed = getRepo().findFirst5ByOrderByCreatedAtDesc();
        Collections.reverse(messagesListReversed);
        return messagesListReversed;
    }

    @GetMapping(value="/get-connected-users")
    public @ResponseBody
    List<LoggedUser> getConnectedUsers() {
        List<LoggedUser> users = activeUserStore.getActiveUsers();
        return users;
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> addMessage(@Valid Message message, BindingResult result) {
        /*System.out.println("**************");
        System.out.println(message);
        System.out.println(result);
        System.out.println("**************\n");*/

        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    result.toString(),
                    HttpStatus.BAD_REQUEST);
        }
        getRepo().save(message);

        return new ResponseEntity<>(
                "Message saved",
                HttpStatus.OK);
    }

    @GetMapping(value="/get-messages-by-username")
    public @ResponseBody
    List<Message> getMessagesByUsername(@RequestParam("userName") String userName) {
        List<Message> messages = getRepo().findAllByUserName(userName);
        return messages;
    }

    @GetMapping(value="/get-messages-by-content")
    public @ResponseBody
    List<Message> getMessagesByContent(@RequestParam("content") String content) {
        List<Message> messages = getRepo().findAllByContent(content);
        return messages;
    }
}
