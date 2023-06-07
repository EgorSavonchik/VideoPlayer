package org.kursach.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO
{
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    private Integer userId;
}
