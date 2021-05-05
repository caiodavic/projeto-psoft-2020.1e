package com.projeto.grupo10.vacineja.service;

import com.projeto.grupo10.vacineja.filtros.TokenFilter;
import com.projeto.grupo10.vacineja.model.usuario.Cidadao;
import com.projeto.grupo10.vacineja.model.usuario.CidadaoLoginDTO;
import com.projeto.grupo10.vacineja.util.ErroLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private static final String CPF_ADM = "00000000000";

    @Autowired
    private CidadaoService cidadaoService;
    private final String TOKEN_KEY = "login correto";

    public ResponseEntity<?> autentica(CidadaoLoginDTO cidadaoLogin){
        if(!cidadaoService.validaCidadaoSenha(cidadaoLogin)){
            return ErroLogin.erroLoginSenhaUsuarioErrado();
        }
        if(cidadaoService.validaLoginComoFuncionario(cidadaoLogin)){
            return ErroLogin.erroLoginNaoAutorizadoFuncionario();
        }
        if(cidadaoService.validaLoginComoAdministrador(cidadaoLogin)){
            return ErroLogin.erroLoginNaoAutorizadoAdministrador();
        }

        String token = geraToken(cidadaoLogin.getCpfLogin(), geraTipoLogin(cidadaoLogin));
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    private Map<String,Object> geraTipoLogin(CidadaoLoginDTO cidadaoLoginDTO){
        Map mapaTipoLogin = new HashMap<String,Object>();
        mapaTipoLogin.put("tipoLogin",cidadaoLoginDTO.getTipoLogin());

        return mapaTipoLogin;
    }
    private String geraToken(String cpf, Map<String,Object> tipoLogin){
        return Jwts.builder().setHeaderParam("typ","JWT").setClaims(tipoLogin).setSubject(cpf)
                .signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)).compact();
    }

    public String getCidadaoDoToken(String authorizationHeader) throws ServletException{
        this.verificaToken(authorizationHeader);

        // Extraindo apenas o token do cabecalho.
        String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);

        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
        } catch (SignatureException e) {
            throw new ServletException("Token invalido ou expirado!");
        }
        return subject;
    }

    public String getTipoLogin(String authorizationHeader) throws ServletException {
        this.verificaToken(authorizationHeader);

        // Extraindo apenas o token do cabecalho.
        String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);

        String subject = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody();
            subject = claims.get("tipoLogin").toString();
        } catch (SignatureException e) {
            throw new ServletException("Token invalido ou expirado!");
        }
        return subject;
    }

    private void verificaToken(String authorizationHeader)  throws ServletException{
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ServletException("Token inexistente ou mal formatado!");
        }
    }

    /**
     * Verifica se o token passado é do administrador
     * @param authorizatioHeader eh o token do suposto adm
     * @throws ServletException excecao lançada se houver erro de autenticacao
     */
    public boolean verifyAdmin(String authorizatioHeader) throws ServletException{
        verificaToken(authorizatioHeader);
        String id = getCidadaoDoToken(authorizatioHeader);
        String tipoLogin = getTipoLogin(authorizatioHeader);

        if(id.equals("00000000000") && tipoLogin.equals("Administrador")) {
            System.out.println(tipoLogin);
            return true;
        }

        return false;
    }
}
