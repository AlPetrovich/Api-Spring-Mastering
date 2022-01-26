package com.codmain.order.services;


import com.codmain.order.converters.UserConverter;
import com.codmain.order.dtos.LoginRequestDTO;
import com.codmain.order.dtos.LoginResponseDTO;
import com.codmain.order.entity.User;
import com.codmain.order.exceptions.GeneralServiceException;
import com.codmain.order.exceptions.NoDataFoundException;
import com.codmain.order.exceptions.ValidateServiceException;
import com.codmain.order.repository.UserRepository;
import com.codmain.order.validators.UserValidator;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class UserService {

    @Value("${jwt.password}")
    private String jwtSecret;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user){
        try {
            //validators
            UserValidator.signup(user);
            //el usuario no debe existir en la base de datos
            User existUser = userRepo.findByUsername(user.getUsername())
                    .orElse(null);
            if (existUser != null) throw new ValidateServiceException("El nombre de usuario ya existe");
            //encriptar el password
            String encoder = passwordEncoder.encode(user.getPassword());
            user.setPassword(encoder);
            //si no existe hay que guardarlo
            return userRepo.save(user);

        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public LoginResponseDTO login(LoginRequestDTO requestDTO){
        try {

            User user = userRepo.findByUsername(requestDTO.getUsername())
                    .orElseThrow(()-> new ValidateServiceException("Usuario o password incorrectos"));
            //el password encriptado machea con el password texto plano
            if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword()))
                throw new ValidateServiceException("Usuario o password incorrectos");

            String token = createToken(user);

            return new LoginResponseDTO(userConverter.fromEntity(user),token);
        }catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
    //Generador de token
    public String createToken(User user){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (1000*60*60)); //fecha de expiracion del token ms*seg*min

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }
    //parsea el token, si truena es incorrecto
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (UnsupportedJwtException e) {
            log.error("JWT in a particular format/configuration that does not match the format expected");
        }catch (MalformedJwtException e) {
            log.error(" JWT was not correctly constructed and should be rejected");
        }catch (SignatureException e) {
            log.error("Signature or verifying an existing signature of a JWT failed");
        }catch (ExpiredJwtException e) {
            log.error("JWT was accepted after it expired and must be rejected");
        }
        return false;
    }

    public String getUserNameFromToken(String jwt){
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new ValidateServiceException("Invalid Token");
        }
    }
}
