package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.paw.model.Score;

@SuppressWarnings("serial")
public class ScoreConverter implements IConverter<Score> {

	@Override
	public Score convertToObject(String arg0, Locale arg1) {
		return new Score(Integer.valueOf(arg0));
	}

	@Override
	public String convertToString(Score arg0, Locale arg1) {
		return String.valueOf(arg0.getValue());
	}

}
