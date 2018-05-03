package media.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
	
	public static final LocalDateTime fromMilis(long milis) {
		return Instant.ofEpochMilli(milis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static final Long getMilis(LocalDateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public static final LocalDateTime fromDate(Date in) {
		return LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
	}
	
}
