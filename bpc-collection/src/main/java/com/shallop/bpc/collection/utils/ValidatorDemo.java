package com.shallop.bpc.collection.utils;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraintvalidators.RegexpURLValidator;
import org.hibernate.validator.spi.constraintdefinition.ConstraintDefinitionContributor;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author chenxuanlong
 * @date 2019/7/2
 */
public class ValidatorDemo {

	@Test
	public void testCarTest(){
		HibernateValidatorConfiguration configuration = Validation
				.byProvider( HibernateValidator.class )
				.configure();

		configuration.addConstraintDefinitionContributor(
				new ConstraintDefinitionContributor() {
					@Override
					public void collectConstraintDefinitions(ConstraintDefinitionBuilder builder) {
						builder.constraint(URL.class)
								.includeExistingValidators( false )
								.validatedBy( RegexpURLValidator.class );
					}
				}
		);
		Validator validator = configuration.buildValidatorFactory().getValidator();
	}
}
