package com.example.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.demo.domain.UserDto;

// public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
//   @Override
//   public void initialize(final PasswordMatches constraintAnnotation) {
//       //
//   }

//   @Override
//   public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
//       final UserDto user = (UserDto) obj;
//       return user.getPassword().equals(user.getMatchingPassword());
//   }
// }

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
  @Override
  public void initialize(final PasswordMatches constraintAnnotation) {
    //
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    // TODO Auto-generated method stub
    return false;
  }
}
