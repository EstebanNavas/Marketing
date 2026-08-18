package com.marketing.Controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Controller
public class IrcaSsoController {

    @Value("${sso.secret}")
    private String ssoSecret;

    @GetMapping("/irsso")
    public String irAIrca(HttpServletRequest request) {
        Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");

        if (usuario == null) {
            return "redirect:/";
        }

        String token = Jwts.builder()
            .claim("idLocal", usuario.getIdLocal())
            .setExpiration(new Date(System.currentTimeMillis() + 30_000))
            .signWith(Keys.hmacShaKeyFor(ssoSecret.getBytes()), SignatureAlgorithm.HS256)
            .compact();

        return "redirect:https://lab.mobile-tic.com/IRCA/inicio?token=" + token;
    }
}