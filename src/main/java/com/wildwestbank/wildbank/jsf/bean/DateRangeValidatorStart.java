/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wildwestbank.wildbank.jsf.bean;

/**
 *
 * @author Alesha
 */

import com.wildwestbank.wildbank.jsf.util.FacesUtils;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validator.dateRangeValidatorStart")
public class DateRangeValidatorStart implements Validator {

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        System.out.println("DateRangeValidatorStart - inside validate()");

        if (value == null || component.getAttributes().get("endDate") == null)
        {
            System.out.println("endDate is null");
            return;
        };

        Date toDate = (Date) component.getAttributes().get("endDate");
        Date fromDate = (Date) value;

        System.out.println("start date: " + fromDate );
        System.out.println("end date: " + toDate );

        if (toDate.before(fromDate)) {
            System.out.println("Validation failed");
            //FacesMessage message = new FacesMessage("DateRangeValidatorStart - Begindate is after start date");
            //message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //throw new ValidatorException(message);
            FacesUtils.addI18nError("message.validate.datestart");
            throw new ValidatorException(FacesUtils.getFacesContext().getMessageList());
        }

    }

}
