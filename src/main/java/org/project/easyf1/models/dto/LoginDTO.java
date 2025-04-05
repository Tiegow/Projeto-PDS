package org.project.easyf1.models.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
