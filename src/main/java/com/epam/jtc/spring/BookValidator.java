package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.dto.Book;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class BookValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "",
            "Title is empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "",
            "Author is empty");
        
        //errors.rejectValue("name", "", "Username length is less than 5");
        
    }
}