package com.balance.request.subcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class SubcategoryDTO {
    private Integer id;
    private String type;
}
