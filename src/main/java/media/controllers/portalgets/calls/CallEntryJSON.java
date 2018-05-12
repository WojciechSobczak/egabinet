package media.controllers.portalgets.calls;

import lombok.Data;
import media.utils.DateUtils;

@Data
public class CallEntryJSON {
	private Long callId;
	private Long userId;
	private long creationDate;
	
	public CallEntryJSON(CallEntry entry) {
		this.callId = entry.getCallId();
		this.userId = entry.getUserId();
		this.creationDate = DateUtils.getMilis(entry.getCreationDate());
	}
}