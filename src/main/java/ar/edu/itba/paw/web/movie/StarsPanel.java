package ar.edu.itba.paw.web.movie;

import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon;

@SuppressWarnings("serial")
public class StarsPanel extends Panel{

	public StarsPanel(String id, final int score) {
		super(id);

		 add(new Loop("movie.stars", 5) {
	            @Override
	            protected void populateItem(LoopItem item) {
	                final Icon icon;
	                if (item.getIndex() < score) {
	                    icon = new Icon(GlyphIconType.star);
	                } else {
	                    icon = new Icon(GlyphIconType.starempty);
	                }
	                item.add(icon);
	            }
	        });
	
	}
	

}
