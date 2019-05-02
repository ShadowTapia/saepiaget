package com.piaget.Converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Marcelo
 */
@FacesConverter(value = "uCaseConverter")
public class UcaseConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return (string != null) ? string.toUpperCase() : null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (o != null) ? o.toString() : "";
    }
}
