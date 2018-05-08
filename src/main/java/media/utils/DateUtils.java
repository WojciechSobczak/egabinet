package media.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
	
	public static final ZoneId getZone() {
		return ZoneId.of("Europe/Warsaw");
	}
	
	public static final LocalDateTime fromMilis(long milis) {
		return Instant.ofEpochMilli(milis).atZone(getZone()).toLocalDateTime();
	}
	
	public static final Long getMilis(LocalDateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		return dateTime.atZone(getZone()).toInstant().toEpochMilli();
	}
	public static final LocalDateTime fromDate(Date in) {
		return LocalDateTime.ofInstant(in.toInstant(), getZone());
	}
	
}
