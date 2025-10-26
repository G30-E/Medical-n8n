package com.archivomedico.personal.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\rJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J.\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0012H\u0002J\u0016\u0010\u001d\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\u001e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010 R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/archivomedico/personal/data/MedsRepository;", "", "appContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dao", "Lcom/archivomedico/personal/data/MedicationDao;", "deleteGroup", "", "group", "Lcom/archivomedico/personal/data/MedGroup;", "(Lcom/archivomedico/personal/data/MedGroup;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllGrouped", "Lkotlinx/coroutines/flow/Flow;", "", "nameExists", "", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "replaceGroup", "originalName", "originalDose", "originalDaysMask", "", "updated", "(Ljava/lang/String;Ljava/lang/String;ILcom/archivomedico/personal/data/MedGroup;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "rowsKey", "s", "saveGroup", "setActive", "active", "(Lcom/archivomedico/personal/data/MedGroup;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MedsRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.NotNull()
    private final com.archivomedico.personal.data.MedicationDao dao = null;
    
    public MedsRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context appContext) {
        super();
    }
    
    private final java.lang.String rowsKey(java.lang.String s) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.archivomedico.personal.data.MedGroup>> getAllGrouped() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveGroup(@org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedGroup group, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteGroup(@org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedGroup group, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setActive(@org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedGroup group, boolean active, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object replaceGroup(@org.jetbrains.annotations.NotNull()
    java.lang.String originalName, @org.jetbrains.annotations.NotNull()
    java.lang.String originalDose, int originalDaysMask, @org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedGroup updated, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object nameExists(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}