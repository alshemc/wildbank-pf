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
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.joda.time.DateTimeComparator;

@FacesValidator("validator.dateRangeValidatorEnd")
public class DateRangeValidatorEnd implements Validator {

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        System.out.println("DateRangeValidatorEnd - inside validate()");

        if (value == null || component.getAttributes().get("beginDate") == null)
        {
            System.out.println("beginDate is null");
            return;
        };

        Date fromDate = (Date) component.getAttributes().get("beginDate");
        Date toDate = (Date) value;

        System.out.println("begin date: " + fromDate );
        System.out.println("end date: " + toDate );

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(fromDate);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(toDate);

        if ( DateTimeComparator.getDateOnlyInstance().compare(calStart, calEnd) > 0 )
        {
            System.out.println("Validation failed");
            //FacesMessage message = new FacesMessage("DateRangeValidatorEnd - End date is before begin date");
            //message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //throw new ValidatorException(message);
            FacesUtils.addI18nError("message.validate.dateend");
            throw new ValidatorException(FacesUtils.getFacesContext().getMessageList());

        }

    }

}
