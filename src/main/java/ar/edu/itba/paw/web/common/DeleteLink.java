package ar.edu.itba.paw.web.common;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

public abstract class DeleteLink<T> extends Link<T> {
	
	public DeleteLink(String id, IModel<T> model) {
		super(id, model);
//		add(new Image("imgRemove", DemoWicketApp.DELETE_ICON));
		add(new AttributeModifier("onclick", "return confirm('Esta seguro?');"));
	}
}
