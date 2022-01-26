package com.codmain.order.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    //No es una entidad como tal, sino que se compone del usuario
    private String username;
    private String password;
}
