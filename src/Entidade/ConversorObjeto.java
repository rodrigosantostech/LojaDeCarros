package Entidade;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConversorObjeto implements Converter
{
 
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
    if (value != null)
    {
        return this.getAttributesFrom(component).get(value);
    }
    return null;
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
    if (value != null && !"".equals(value))
    {
 
        BaseEntity entity = (BaseEntity) value;
 
        this.addAttribute(component, entity);
 
        String codigo = entity.getId();
        if (codigo != null)
        {
        return String.valueOf(codigo);
        }
    }
 
    return (String) value;
 
    }
 
    protected void addAttribute(UIComponent component, BaseEntity o)
    {
    String key = o.getId().toString();
    this.getAttributesFrom(component).put(key, o);
    }
 
    protected Map<String, Object> getAttributesFrom(UIComponent component)
    {
    return component.getAttributes();
    }
 
}
 