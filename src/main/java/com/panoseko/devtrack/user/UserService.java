//package com.panoseko.devtrack.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public List<User> getUsers() {
//        return userRepository.findAll();
//    }
//
//    public void addNewUser(User user) {
////        Optional<Member> memberByEmail = memberRepository.findMemberByEmail(member.getEmail());
////        if(memberByEmail.isPresent()){
////            throw new IllegalStateException("email taken");
////        }
////
////        Optional<Member> memberByUsername = memberRepository.findMemberByUsername(member.getUsername());
////        if(memberByUsername.isPresent()){
////            throw new IllegalStateException("username taken");
////        }
////        memberRepository.save(member);
//        System.out.println(user);
//    }
//}
