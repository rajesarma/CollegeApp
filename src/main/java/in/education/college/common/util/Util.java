package in.education.college.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {

	/**
	 * Formats given date to LocalDateTime default string format
	 *
	 * @return Eg: '2011-12-03T10:15:30+01:00'
	 * */
	public static String formatDate(Date date, ZoneId zone){

		if(date == null){
			throw new IllegalArgumentException("Date cannot be null.");
		}

		LocalDateTime timestampLocalDateTime = LocalDateTime.ofInstant(date.toInstant(), zone);

		return DateTimeFormatter.ISO_DATE_TIME.format(timestampLocalDateTime);
	}
}
