package com.archivomedico.personal.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 42\u00020\u0001:\u00014B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000bH\u0002J\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u000bH\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000bH\u0002J\u0018\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u0018H\u0002J\u0012\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u0013H\u0016J\b\u0010#\u001a\u00020\u000bH\u0002J\b\u0010$\u001a\u00020\u0013H\u0002Jm\u0010%\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010\'\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020\u000b2K\u0010)\u001aG\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b+\u0012\b\b,\u0012\u0004\b\b(-\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b+\u0012\b\b,\u0012\u0004\b\b(.\u0012\u0013\u0012\u00110\u0018\u00a2\u0006\f\b+\u0012\b\b,\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u00130*H\u0002J\u000e\u0010/\u001a\b\u0012\u0004\u0012\u00020100H\u0002J\u0016\u00102\u001a\u00020\u0018*\u0004\u0018\u00010\u00182\u0006\u00103\u001a\u00020\u0018H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R \u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f\u00a8\u00065"}, d2 = {"Lcom/archivomedico/personal/ui/MedsFormDialogFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "_binding", "Lcom/archivomedico/personal/databinding/DialogMedFormBinding;", "b", "getB", "()Lcom/archivomedico/personal/databinding/DialogMedFormBinding;", "times", "", "Lkotlin/Pair;", "", "vm", "Lcom/archivomedico/personal/ui/MedsViewModel;", "getVm", "()Lcom/archivomedico/personal/ui/MedsViewModel;", "vm$delegate", "Lkotlin/Lazy;", "applyDaysToUi", "", "mask", "ensureTimesSize", "n", "format12h", "", "hour24", "minute", "labelForButton", "index", "label12", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "readDaysFromUi", "refreshTimeLabels", "showTimePicker12h", "doseIndex", "initialH24", "initialM", "onPicked", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "h24", "m", "timeButtons", "", "Landroid/widget/Button;", "orElse", "fallback", "Companion", "app_debug"})
public final class MedsFormDialogFragment extends androidx.fragment.app.DialogFragment {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy vm$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private com.archivomedico.personal.databinding.DialogMedFormBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> times = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_NAME = "ARG_NAME";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_DOSE = "ARG_DOSE";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_DAYS = "ARG_DAYS";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_TIMES_FLAT = "ARG_TIMES_FLAT";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_ACTIVE = "ARG_ACTIVE";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_ORIG_NAME = "ARG_ORIG_NAME";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_ORIG_DOSE = "ARG_ORIG_DOSE";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ARG_ORIG_DAYS = "ARG_ORIG_DAYS";
    @org.jetbrains.annotations.NotNull()
    public static final com.archivomedico.personal.ui.MedsFormDialogFragment.Companion Companion = null;
    
    public MedsFormDialogFragment() {
        super();
    }
    
    private final java.lang.String orElse(java.lang.String $this$orElse, java.lang.String fallback) {
        return null;
    }
    
    private final com.archivomedico.personal.ui.MedsViewModel getVm() {
        return null;
    }
    
    private final com.archivomedico.personal.databinding.DialogMedFormBinding getB() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.app.Dialog onCreateDialog(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final java.util.List<android.widget.Button> timeButtons() {
        return null;
    }
    
    private final void ensureTimesSize(int n) {
    }
    
    private final java.lang.String labelForButton(int index, java.lang.String label12) {
        return null;
    }
    
    private final void refreshTimeLabels() {
    }
    
    private final java.lang.String format12h(int hour24, int minute) {
        return null;
    }
    
    private final void showTimePicker12h(int doseIndex, int initialH24, int initialM, kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super java.lang.Integer, ? super java.lang.String, kotlin.Unit> onPicked) {
    }
    
    private final void applyDaysToUi(int mask) {
    }
    
    private final int readDaysFromUi() {
        return 0;
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/archivomedico/personal/ui/MedsFormDialogFragment$Companion;", "", "()V", "ARG_ACTIVE", "", "ARG_DAYS", "ARG_DOSE", "ARG_NAME", "ARG_ORIG_DAYS", "ARG_ORIG_DOSE", "ARG_ORIG_NAME", "ARG_TIMES_FLAT", "newInstance", "Lcom/archivomedico/personal/ui/MedsFormDialogFragment;", "g", "Lcom/archivomedico/personal/data/MedGroup;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.archivomedico.personal.ui.MedsFormDialogFragment newInstance(@org.jetbrains.annotations.NotNull()
        com.archivomedico.personal.data.MedGroup g) {
            return null;
        }
    }
}