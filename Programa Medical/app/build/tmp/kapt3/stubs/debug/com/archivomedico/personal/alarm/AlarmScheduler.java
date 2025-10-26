package com.archivomedico.personal.alarm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0015\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\"\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\u0016\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/archivomedico/personal/alarm/AlarmScheduler;", "", "()V", "REQ_BASE", "", "cancel", "", "ctx", "Landroid/content/Context;", "id", "", "nextTriggerMillis", "item", "Lcom/archivomedico/personal/data/MedicationSchedule;", "(Lcom/archivomedico/personal/data/MedicationSchedule;)Ljava/lang/Long;", "pending", "Landroid/app/PendingIntent;", "scheduleFor", "app_debug"})
public final class AlarmScheduler {
    private static final int REQ_BASE = 71000;
    @org.jetbrains.annotations.NotNull()
    public static final com.archivomedico.personal.alarm.AlarmScheduler INSTANCE = null;
    
    private AlarmScheduler() {
        super();
    }
    
    public final void scheduleFor(@org.jetbrains.annotations.NotNull()
    android.content.Context ctx, @org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedicationSchedule item) {
    }
    
    public final void cancel(@org.jetbrains.annotations.NotNull()
    android.content.Context ctx, long id) {
    }
    
    private final android.app.PendingIntent pending(android.content.Context ctx, long id, com.archivomedico.personal.data.MedicationSchedule item) {
        return null;
    }
    
    /**
     * Compute next trigger time in millis for the given item, within the next 2 weeks.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long nextTriggerMillis(@org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedicationSchedule item) {
        return null;
    }
}