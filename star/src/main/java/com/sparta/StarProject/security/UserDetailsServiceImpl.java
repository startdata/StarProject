//package com.sparta.StarProject.security;
//
//import com.hanghae.hanghaecloncodingjeongyookgak.model.User;
//import com.hanghae.hanghaecloncodingjeongyookgak.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserDetailsServiceImpl(UserRepository userRepository) {
//
//        this.userRepository = userRepository;
//    }
//
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + email));
//
//        return new UserDetailsImpl(user);
//    }
//}