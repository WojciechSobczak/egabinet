package media.controllers.portalgets.calls;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class CallsRegisterHandler {
	
	private static final long EXPIRATION_TIME = 1;
	private static final long CLEAN_TIME = 5 * 60 * 1000;
	
	private static final HashMap<Long, CallEntry> REGISTER = new HashMap<>();
	private static final Object MONITOR = new Object();
	
	static {
		Thread cleanThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(CLEAN_TIME);
						synchronized(MONITOR) {
							LinkedList<Long> toRemove = new LinkedList<>();
							for (Entry<Long, CallEntry> entry : REGISTER.entrySet()) {
								LocalDateTime date = entry.getValue().getCreationDate();
								if (LocalDateTime.now().minus(EXPIRATION_TIME, ChronoUnit.MINUTES).isAfter(date)) {
									toRemove.add(entry.getKey());
								}
							}
							for (Long callId : toRemove) {
								REGISTER.remove(callId);
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		cleanThread.start();
	}
	
	private static boolean isValid(CallEntry entry) {
		return entry.getCreationDate().plus(EXPIRATION_TIME, ChronoUnit.MINUTES).isAfter(LocalDateTime.now());
	}
	
	public static CallEntry getEntry(Long callId) {
		synchronized (MONITOR) {
			CallEntry entry = REGISTER.get(callId);
			if (entry == null) {
				return null;
			}
			
			if (isValid(entry)) {
				return entry;
			} else {
				REGISTER.remove(entry.getCallId());
				return null;
			}
		}
	}
	
	public static void register(CallEntry entry) {
		synchronized (MONITOR) {
			CallEntry existingEntry = REGISTER.get(entry.getCallId());
			if (existingEntry != null) {
				if (!isValid(existingEntry)) {
					REGISTER.remove(existingEntry.getCallId());
					REGISTER.put(entry.getCallId(), entry);
				}
			} else {
				REGISTER.put(entry.getCallId(), entry);
			}
		}
	}
	
	public static synchronized void unregister(Long callId) {
		synchronized (MONITOR) {
			REGISTER.remove(callId);
		}
	}

}
