package ar.edu.itba.paw.web.user;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.user.User;
import ar.edu.itba.paw.model.user.UserRepo;
import ar.edu.itba.paw.web.MovieWorldSession;
import ar.edu.itba.paw.web.base.UsersPage;

@SuppressWarnings("serial")
public class UsersListPage extends UsersPage {

	@SpringBean
	UserRepo userRepo;

	public UsersListPage() {
		IModel<List<User>> users = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				User currentUser = MovieWorldSession.get().getCurrentUser(userRepo);
				List<User> userList = userRepo.findAll();
				if (currentUser != null) {
					userList.remove(currentUser);
				}
				return userList;
			}
		};

		add(new PropertyListView<User>("users", users) {
			@Override
			protected void populateItem(final ListItem<User> item) {
				item.add(new Label("userFirstName", new PropertyModel<String>(item.getModel(), "firstName")));
				item.add(new Label("userLastName", new PropertyModel<String>(item.getModel(), "lastName")));
				item.add(new Link<Void>("userLink") {
					@Override
					public void onClick() {
						setResponsePage(new UserCommentsPage(item.getModelObject()));
					}
				});
			};
		});
	}
}
