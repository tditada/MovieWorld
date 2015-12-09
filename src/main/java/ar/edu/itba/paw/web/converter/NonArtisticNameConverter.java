package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.model.user.NonArtisticName;

@SuppressWarnings("serial")
public class NonArtisticNameConverter implements IConverter<NonArtisticName> {

	@Override
	public NonArtisticName convertToObject(String arg0, Locale arg1) {
		return new NonArtisticName(arg0);
	}

	@Override
	public String convertToString(NonArtisticName arg0, Locale arg1) {
		return arg0.getNameString();
	}

}
