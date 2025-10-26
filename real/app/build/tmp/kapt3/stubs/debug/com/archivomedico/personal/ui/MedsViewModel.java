package com.archivomedico.personal.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014J&\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bJ\u000e\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bJ\u001e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u001fR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/archivomedico/personal/ui/MedsViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "app", "Landroid/app/Application;", "(Landroid/app/Application;)V", "meds", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/archivomedico/personal/data/MedGroup;", "getMeds", "()Lkotlinx/coroutines/flow/StateFlow;", "repo", "Lcom/archivomedico/personal/data/MedsRepository;", "deleteGroup", "Lkotlinx/coroutines/Job;", "group", "nameExists", "", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "replaceGroup", "originalName", "originalDose", "originalDaysMask", "", "updated", "saveGroup", "setActive", "", "active", "(Lcom/archivomedico/personal/data/MedGroup;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MedsViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.archivomedico.personal.data.MedsRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.archivomedico.personal.data.MedGroup>> meds = null;
    
    public MedsViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application app) {
        super(null);
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object nameExists(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.archivomedico.personal.data.MedGroup>> getMeds() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job saveGroup(@org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedGroup group) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job deleteGroup(@org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedGroup group) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job replaceGroup(@org.jetbrains.annotations.NotNull()
    java.lang.String originalName, @org.jetbrains.annotations.NotNull()
    java.lang.String originalDose, int originalDaysMask, @org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedGroup updated) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setActive(@org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.data.MedGroup group, boolean active, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}