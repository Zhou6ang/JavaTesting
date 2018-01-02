package com.zg.spring.aop;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class MyValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return A.class.equals(clazz) || B.class.equals(clazz) || C.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "validtor", "validtor.empty");;
		
	}

}
