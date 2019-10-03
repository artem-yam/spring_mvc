package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.dto.Book;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for book entity
 */
@Service("bookValidator")
public class BookValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.isAssignableFrom(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "",
            "Title is empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "",
            "Author is empty");
    }
}