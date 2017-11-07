package group7calendar;

public class currentTimeFormatter {
    public String getCurrentTime(String currentTime) {
        if (currentTime != null && !currentTime.isEmpty()) {
            if (Character.isDigit(currentTime.charAt(0))
                    && Character.isDigit(currentTime.charAt(1))) {
                int hours = Integer.parseInt(currentTime.substring(0,2));
                String remainder = currentTime.substring(2,8);

                if (hours == 12) {
                    return (hours + remainder + " PM");
                }
                if (hours == 0) {
                    return ("12" + remainder + " AM");
                }
                if (hours > 12) {
                    hours = hours - 12;
                    return (hours + remainder + " PM");
                }
                return (hours + remainder + " AM");
            }
        }
        return (currentTime);
    }
}
