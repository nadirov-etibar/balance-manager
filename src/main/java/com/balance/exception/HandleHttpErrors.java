package com.balance.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HandleHttpErrors extends RuntimeException{
    private BindingResult bindingResult;

    public HandleHttpErrors(String message){
        super(message);
    }

    public HandleHttpErrors(String message, BindingResult bindingResult){
        super(message);
        this.bindingResult = bindingResult;
    }
}
