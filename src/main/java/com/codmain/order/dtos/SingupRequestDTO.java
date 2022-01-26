package com.codmain.order.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingupRequestDTO {

    private String username;
    private String password;
}
