package ar.edu.itba.paw.g4.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.user.NonArtisticName;

@Component
public class NonArtisticNameFormatter implements Formatter<NonArtisticName> {

	@Override
	public NonArtisticName parse(String arg0, Locale arg1)
			throws ParseException {
		return new NonArtisticName(arg0);
	}

	@Override
	public String print(NonArtisticName arg0, Locale arg1) {
		return arg0.getNameString();
	}

}
