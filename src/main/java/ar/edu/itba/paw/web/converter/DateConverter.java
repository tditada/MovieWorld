package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class DateConverter implements IConverter<DateTime> {
	private static final String DATE_TIME_FORMAT = "yy-MM-dd"; /*
																 * IMPORTANT:
																 * This is the
																 * same as JS'
																 * yy-mm-dd
																 */
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_TIME_FORMAT);

	@Override
	public DateTime convertToObject(String arg0, Locale arg1) {
		return dateTimeFormatter.parseDateTime(arg0);
	}

	@Override
	public String convertToString(DateTime arg0, Locale arg1) {

		return dateTimeFormatter.print(arg0);
	}

}
