package com.example.ex4.beans;

import com.example.ex4.UserNotConnectedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActiveUserStore {

    private Map<String, LoggedUser> users;

    public ActiveUserStore() {
        users = new HashMap();
    }

    public List<LoggedUser> getActiveUsers() {
        List activeUsers = new ArrayList<LoggedUser>(users.values())
                .stream()
                .filter(user -> user.isConnected() == true)
                .collect(Collectors.toList());

        return activeUsers;
    }

    public void addUser(LoggedUser user) {
        users.put(user.getUserName(), user);
    }

    public void addUserFromSession(HttpSession session) throws UserNotConnectedException {
        Object userNameObj = session.getAttribute("userName");
        if(userNameObj == null) {
            throw new UserNotConnectedException();
        }
        String userName = (String) userNameObj;
        addUser(new LoggedUser(userName));
    }

    public boolean isUserConnected(String userName) {
        LoggedUser user = users.getOrDefault(userName, null);

        return user != null && user.isConnected();
    }

    public void removeUser(String userName) {
        users.remove(userName);
    }
}
