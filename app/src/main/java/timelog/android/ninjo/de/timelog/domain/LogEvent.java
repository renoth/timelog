package timelog.android.ninjo.de.timelog.domain;

import android.util.Log;

public class LogEvent {
    private static final Long MILLIS_PER_MINUTE = 60 * 1000L;
    private static final Long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

    private String start, end;
    private String duration;
    private LogCategory category;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
        updateDuration();
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
        updateDuration();
    }

    public LogCategory getCategory() {
        return category;
    }

    public void setCategory(LogCategory category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    private void updateDuration() {
        if (start != null && end != null) {
            try {
                Long startMilli = Long.parseLong(start);
                Long endMilli = Long.parseLong(end);

                long durationMillis = endMilli - startMilli;
                Long hours = durationMillis / MILLIS_PER_HOUR;
                Long minutes = (durationMillis % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE;
                Long seconds = (durationMillis % MILLIS_PER_MINUTE) / 1000;

                duration = String.format("%sh %sm %ss", hours, minutes, seconds);
            } catch (IllegalArgumentException e) {
                Log.e("TimeLog", "Error parsing duration", e);
            }
        }
    }
}
