package com.crode.book_tracker_api.validation;

import com.crode.book_tracker_api.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDto user = (UserDto) obj;

        if(user.getPassword().equals(user.getMatchingPassword())) {
            return true;
        }
        else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords don't match")
                    .addPropertyNode("matchingPassword")
                    .addConstraintViolation();
            return false;
        }
    }
}