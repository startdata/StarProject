//package com.sparta.StarProject.security.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Base64;
//import java.util.Date;
//
//@RequiredArgsConstructor
//@Component
//public class JwtTokenProvider {
//
//    private String secretkey = "clone";
//
//
//    //토큰 유효시간
//    private Long tokenValidTime = 24*60*60*1000L;
//
//    private final UserDetailsService userDetailsService;
//
//    @PostConstruct
//    protected  void init(){
//
//        secretkey = Base64.getEncoder().encodeToString(secretkey.getBytes());
//    }
//
//
//    //토큰 생성
//    public String createToken(String userPk, String email, String nickname){
//        Claims claims = Jwts.claims().setSubject(userPk);
//        claims.put("email", email);
//        claims.put("nickname", nickname);
//        Date now = new Date();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + tokenValidTime))
//                .signWith(SignatureAlgorithm.HS256, secretkey)
//                .compact();
//    }
//
//    // 토큰에서 회원 정보 추출
//    public String getUserPk(String token){
//        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    // 토큰에서 인증 정보 조회
//    public Authentication getAuthentication(String token){
//        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
//        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
//    }
//
//    public String resolveToken(HttpServletRequest request){
//        return request.getHeader("Authorization");
//    }
//
//    // 토큰 유효성,만료일자
//    public boolean validateToken(String jwtToken){
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(jwtToken);
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
//
//
