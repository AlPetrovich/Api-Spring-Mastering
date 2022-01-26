package com.codmain.order.controllers;

import com.codmain.order.converters.UserConverter;
import com.codmain.order.dtos.LoginRequestDTO;
import com.codmain.order.dtos.LoginResponseDTO;
import com.codmain.order.dtos.SingupRequestDTO;
import com.codmain.order.dtos.UserDTO;
import com.codmain.order.entity.User;
import com.codmain.order.services.UserService;
import com.codmain.order.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    @PostMapping("/signup")
    public ResponseEntity<WrapperResponse<UserDTO>> signup(@RequestBody SingupRequestDTO requestDTO){
        User user =userService.createUser(userConverter.signup(requestDTO));

        return new WrapperResponse<>(true, "success", userConverter.fromEntity(user))
                .createResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO responseDTO = userService.login(requestDTO);

        return new WrapperResponse<>(true, "success", responseDTO).createResponse();
    }
}
