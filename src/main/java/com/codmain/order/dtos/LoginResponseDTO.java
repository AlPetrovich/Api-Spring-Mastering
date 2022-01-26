package com.codmain.order.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private UserDTO user;
    private String token;
}
