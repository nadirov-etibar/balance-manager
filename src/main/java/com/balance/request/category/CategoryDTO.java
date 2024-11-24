package com.balance.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CategoryDTO {
    private Integer id;
    private String type;
}
