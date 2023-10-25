//package com.panoseko.devtrack.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(path = "api/v1/member")
//public class UserController {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping
//    public List<User> getMembers() {
//        return userService.getUsers();
//    }
//
//    @PostMapping
//    public void registerNewMember(@RequestBody User user) {
//        userService.addNewUser(user);
//    }
//}
