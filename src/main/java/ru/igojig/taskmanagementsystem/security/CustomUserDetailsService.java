//package ru.igojig.taskmanagementsystem.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import ru.igojig.taskmanagementsystem.entities.User;
//import ru.igojig.taskmanagementsystem.repository.UserRepository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Email: " + email + " not found"));
//
//        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
//                .map(r -> new SimpleGrantedAuthority(r.getRole().name()))
//                .toList();
//
//        var springUser = org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .authorities(authorities)
//                .accountExpired(false)
//                .accountLocked(false)
//                .disabled(false)
//                .credentialsExpired(false)
//                .build();
//        return springUser;
//    }
//}
