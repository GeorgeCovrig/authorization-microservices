package user.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    List<User> userList;

    public UserController(){
        userList = new ArrayList<>();
        userList.add(new User("ion", "ion"));
        userList.add(new User("mihai", "password"));
    }

    @PostMapping("/auth")
    public ResponseEntity<Boolean> isUserAuthenticated(@RequestBody User user){
        for (User userItem: userList) {
            if(userItem.getUsername().equals(user.getUsername()) && userItem.getPassword().equals(user.getPassword())) {
                return ResponseEntity.ok(Boolean.TRUE);
            }
        }
        return ResponseEntity.ok(Boolean.FALSE);
    }

}
