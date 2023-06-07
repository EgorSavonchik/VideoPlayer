package org.kursach.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO
{
    @NotBlank
    private String commentText;

    @Positive
    private Integer userId;
    @Positive
    private Integer videoId;
}
