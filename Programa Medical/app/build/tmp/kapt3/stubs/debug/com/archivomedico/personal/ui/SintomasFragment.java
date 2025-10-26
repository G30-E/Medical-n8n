package com.archivomedico.personal.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0004H\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J$\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0016H\u0016J\u001a\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\u0018\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0004H\u0002J\b\u0010&\u001a\u00020\u0016H\u0002J\b\u0010\'\u001a\u00020\u0016H\u0002J\u0010\u0010(\u001a\u00020\u00162\u0006\u0010)\u001a\u00020\nH\u0002J\b\u0010*\u001a\u00020\u0016H\u0002J\b\u0010+\u001a\u00020$H\u0002J\b\u0010,\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/archivomedico/personal/ui/SintomasFragment;", "Landroidx/fragment/app/Fragment;", "()V", "HISTORY_KEY", "", "PREFS_NAME_PREFIX", "_binding", "Lcom/archivomedico/personal/databinding/FragmentSintomasBinding;", "adapter", "Landroid/widget/ArrayAdapter;", "Lcom/archivomedico/personal/ui/SymptomHistoryItem;", "apiKey", "binding", "getBinding", "()Lcom/archivomedico/personal/databinding/FragmentSintomasBinding;", "gson", "Lcom/google/gson/Gson;", "historyList", "", "userEmail", "getPrefsName", "loadHistory", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "performAnalysis", "openAIApi", "Lcom/archivomedico/personal/ui/OpenAIApi;", "sintomas", "resetAnalyzeButton", "saveHistory", "sendToN8n", "historyItem", "setupListView", "setupOpenAIApi", "toggleEmptyView", "app_debug"})
public final class SintomasFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.archivomedico.personal.databinding.FragmentSintomasBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String apiKey = "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";
    private android.widget.ArrayAdapter<com.archivomedico.personal.ui.SymptomHistoryItem> adapter;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.archivomedico.personal.ui.SymptomHistoryItem> historyList = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String PREFS_NAME_PREFIX = "diagnosticos_json_";
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String HISTORY_KEY = "historial";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String userEmail = "";
    
    public SintomasFragment() {
        super();
    }
    
    private final com.archivomedico.personal.databinding.FragmentSintomasBinding getBinding() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupListView() {
    }
    
    private final void performAnalysis(com.archivomedico.personal.ui.OpenAIApi openAIApi, java.lang.String sintomas) {
    }
    
    private final java.lang.String getPrefsName() {
        return null;
    }
    
    private final void saveHistory() {
    }
    
    private final void loadHistory() {
    }
    
    private final void toggleEmptyView() {
    }
    
    private final void resetAnalyzeButton() {
    }
    
    private final com.archivomedico.personal.ui.OpenAIApi setupOpenAIApi() {
        return null;
    }
    
    private final void sendToN8n(com.archivomedico.personal.ui.SymptomHistoryItem historyItem) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}