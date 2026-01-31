package com.andreafilice.lavorami;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

public class NotificationScheduler {

    private static final String TAG = "Scheduler_Log";

    public static void scheduleWorkNotifications(Context context, ArrayList<EventDescriptor> eventList) {
        boolean isEnabled = DataManager.getBoolData(context, DataKeys.KEY_NOTIFICATION_SWITCH, true);

        if (!isEnabled)  return;

        Set<String> favorites = DataManager.getStringArray(context, DataKeys.KEY_FAVORITE_LINES, null);

        if (favorites == null || favorites.isEmpty()) return;

        if (eventList == null || eventList.isEmpty()) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        boolean notifyStart = DataManager.getBoolData(context, DataKeys.KEY_NOTIFICATION_STARTWORKS, true);
        boolean notifyEnd = DataManager.getBoolData(context, DataKeys.KEY_NOTIFICATION_ENDWORKS, true);

        for (EventDescriptor event : eventList) {
            boolean isMatch = false;

            if (event.lines != null) {

                for (String line : event.lines) {
                    if (favorites.contains(line)) {
                        isMatch = true;
                        break;
                    }
                }

                if (!isMatch) {
                    for (String fav : favorites) {
                        if (isMatch) break;

                        switch (fav) {
                            case "Metro":
                                for (String line : event.lines) {
                                    if (line.matches("(?i)M[1-5].*")) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                                break;

                            case "S":
                                for (String line : event.lines) {
                                    if (line.matches("(?i)^S\\d+$")) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                                break;

                            case "R":
                                for (String line : event.lines) {
                                    if (line.matches("(?i)^R\\d+$")) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                                break;

                            case "RE":
                                for (String line : event.lines) {
                                    if (line.matches("(?i)^RE\\d+$")) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                                break;

                            case "Tram":
                                for (String line : event.lines) {
                                    if (line.matches("^([1-9]|[1-2][0-9]|3[0-3])$")) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                                break;

                            case "z5":
                                for (String line : event.lines) {
                                    if (line.toLowerCase().startsWith("z5")) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                                break;

                            case "z6":
                                for (String line : event.lines) {
                                    if (line.toLowerCase().startsWith("z6")) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                                break;

                            case "Autoguidovie":
                                if (event.company != null && event.company.toLowerCase().contains("autoguidovie")) {
                                    isMatch = true;
                                }
                                break;

                            case "Bus":
                                for (String line : event.lines) {
                                    boolean isTram = line.matches("^([1-9]|[1-2][0-9]|3[0-3])$");
                                    boolean isMetro = line.matches("(?i)M[1-5].*");
                                    boolean isTreno = line.matches("(?i)^(S|R|RE|RV).*");

                                    if (!isTram && !isMetro && !isTreno) {
                                        isMatch = true;
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }
            }

            if (isMatch) {
                long startMillis = event.getDateMillis(event.startDate);
                long endMillis = event.getDateMillis(event.endDate);
                long now = System.currentTimeMillis();
                long oneDayMillis = 24 * 60 * 60 * 1000;

                if (notifyStart) {
                    if (startMillis > now) {
                        schedule(context, alarmManager, event.title.hashCode(), startMillis,
                                "Inizio Lavori", "Iniziano oggi i lavori per: " + event.title);
                    }
                    if (startMillis - oneDayMillis > now) {
                        schedule(context, alarmManager, event.title.hashCode() + 1, startMillis - oneDayMillis,
                                "Domani iniziano i lavori", "Preparati! Domani iniziano i lavori: " + event.title);
                    }
                }

                if (notifyEnd) {
                    if (endMillis > now) {
                        schedule(context, alarmManager, event.title.hashCode() + 2, endMillis,
                                "Fine Lavori", "I lavori su " + event.title + " terminano oggi.");
                    }
                }
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private static void schedule(Context context, AlarmManager am, int id, long time, String title, String msg) {
        try {
            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("message", msg);
            intent.putExtra("id", id);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (am.canScheduleExactAlarms())
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                else
                    am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            else
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

            Log.d(TAG, "Schedulato ID " + id + " per: " + title);

        } catch (SecurityException se) {
            Log.e(TAG, "Errore Permessi (SecurityException): " + se.getMessage());
            try {
                Intent intentFallback = new Intent(context, NotificationReceiver.class);
                intentFallback.putExtra("title", title);
                intentFallback.putExtra("message", msg);
                intentFallback.putExtra("id", id);
                PendingIntent piFallback = PendingIntent.getBroadcast(context, id, intentFallback, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                am.set(AlarmManager.RTC_WAKEUP, time, piFallback);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            Log.e(TAG, "Errore generico: " + e.getMessage());
        }
    }
}