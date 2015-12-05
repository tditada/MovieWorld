package ar.edu.itba.paw.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DateFormatter implements Formatter<DateTime> {
	private static final String DATE_TIME_FORMAT = "yy-MM-dd"; /*
																 * IMPORTANT:
																 * This is the
																 * same as JS'
																 * yy-mm-dd
																 */
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat
			.forPattern(DATE_TIME_FORMAT);

	@Override
	public DateTime parse(String arg0, Locale arg1) throws ParseException {
		return dateTimeFormatter.parseDateTime(arg0);
	}

	@Override
	public String print(DateTime arg0, Locale arg1) {
		return dateTimeFormatter.print(arg0);
	}
}
