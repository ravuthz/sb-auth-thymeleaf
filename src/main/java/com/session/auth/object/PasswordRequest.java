package com.session.auth.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PasswordRequest {
    @JsonIgnore
    @NotEmpty
    @Size(min = 6, max = 250)
    private String password;

    @JsonIgnore
    @NotEmpty
    @Size(min = 6, max = 250)
    private String confirmPassword;
}
