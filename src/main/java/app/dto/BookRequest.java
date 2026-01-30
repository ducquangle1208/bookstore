package app.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookRequest {
    private String title;
    private String author;
    private BigDecimal price;
}
