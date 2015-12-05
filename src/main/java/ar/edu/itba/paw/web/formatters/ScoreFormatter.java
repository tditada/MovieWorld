package ar.edu.itba.paw.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.model.Score;

@Component
public class ScoreFormatter implements Formatter<Score> {

	@Override
	public Score parse(String arg0, Locale arg1) throws ParseException {
		return new Score(Integer.valueOf(arg0));
	}

	@Override
	public String print(Score arg0, Locale arg1) {
		return String.valueOf(arg0.getValue());
	}

}
