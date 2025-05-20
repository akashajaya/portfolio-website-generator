package com.luminar.snacpack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(unique = true, nullable = false)
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @Size(max = 255)
    private String description;

    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*$")
    @Size(max = 255)
    private String linkedin;
}
