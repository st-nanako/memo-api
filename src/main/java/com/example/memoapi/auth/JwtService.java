package com.example.memoapi.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}") // application.propertiesにて設定した値(jwt.secret)が代入される
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // 秘密鍵を生成している
    private SecretKey getSigningKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decodedKey); //秘密鍵に変換
    }

    // JWTトークンを生成するメソッド
    public  String generateToken(String username){
        return Jwts.builder()
                .subject(username) //ユーザー名
                .issuedAt(new Date()) // 発行日
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 有効期限
                .signWith(getSigningKey(), Jwts.SIG.HS256) // 署名
                .compact();
    }

    // JWT トークンをパース(展開)してクレーム(情報)を取得
    private Claims getClaimsFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey()) //秘密鍵を取ってきて複合
                .build()
                .parseSignedClaims(token) // トークンを使用してクレームを取得
                .getPayload();
    }

    // JWT トークンからユーザー名を取得
    public String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    // JWT トークンから有効期限を取得
    public Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token).getExpiration();
    }

    // トークンの有効期限をチェック
    public Boolean isTokenExpired(String token){
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // トークンの検証
    public Boolean validateToken(String token,String username){
        String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username)) && !(isTokenExpired(token)) ;
    }
}
