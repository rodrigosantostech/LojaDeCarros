package Entidade;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConversorObjetoId implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null) {
			return this.getAttributesFrom(component).get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null && !value.equals("")) {

			BaseEntityObject entity = (BaseEntityObject) value;

			this.addAttribute(component, entity);

			Integer codigo = entity.getId();
			if (codigo != null) {
				return String.valueOf(codigo);
			}
		}
		return (String) value.toString();
	}

	protected void addAttribute(UIComponent component, BaseEntityObject o) {
		if (o.getId() != null) {
			String key = o.getId().toString();
			this.getAttributesFrom(component).put(key, o);
		}
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}

}
