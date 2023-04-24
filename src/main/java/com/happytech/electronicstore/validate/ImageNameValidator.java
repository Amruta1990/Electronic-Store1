package com.happytech.electronicstore.validate;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Slf4j
public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {


        private Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

                log.info("Message from isValid : {} ", value);
                //logic

                if (value.isBlank()) {
                        return true;
                } else {
                        return false;
                }

        }
}
