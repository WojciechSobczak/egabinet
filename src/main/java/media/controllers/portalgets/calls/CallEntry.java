package media.controllers.portalgets.calls;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CallEntry {
	private Long callId;
	private Long userId;
	private LocalDateTime creationDate;
	
	public CallEntry(Long callId, Long userId) {
		this.callId = callId;
		this.userId = userId;
		this.creationDate = LocalDateTime.now();
	}
}