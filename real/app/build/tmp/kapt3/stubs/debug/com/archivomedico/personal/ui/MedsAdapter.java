package com.archivomedico.personal.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001cBG\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\u0002\u0010\u000bJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0002J\u0018\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u000fH\u0016J\u0018\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000fH\u0016J\u0014\u0010\u001a\u001a\u00020\u00062\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\rR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/archivomedico/personal/ui/MedsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/archivomedico/personal/ui/MedsAdapter$H;", "onEdit", "Lkotlin/Function1;", "Lcom/archivomedico/personal/data/MedGroup;", "", "onDelete", "onToggle", "Lkotlin/Function2;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)V", "items", "", "getItemCount", "", "maskToInitials", "", "mask", "onBindViewHolder", "h", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "submit", "list", "H", "app_debug"})
public final class MedsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.archivomedico.personal.ui.MedsAdapter.H> {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.archivomedico.personal.data.MedGroup, kotlin.Unit> onEdit = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.archivomedico.personal.data.MedGroup, kotlin.Unit> onDelete = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function2<com.archivomedico.personal.data.MedGroup, java.lang.Boolean, kotlin.Unit> onToggle = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.archivomedico.personal.data.MedGroup> items;
    
    public MedsAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.archivomedico.personal.data.MedGroup, kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.archivomedico.personal.data.MedGroup, kotlin.Unit> onDelete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.archivomedico.personal.data.MedGroup, ? super java.lang.Boolean, kotlin.Unit> onToggle) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.archivomedico.personal.ui.MedsAdapter.H onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.ui.MedsAdapter.H h, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void submit(@org.jetbrains.annotations.NotNull()
    java.util.List<com.archivomedico.personal.data.MedGroup> list) {
    }
    
    private final java.lang.String maskToInitials(int mask) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/archivomedico/personal/ui/MedsAdapter$H;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "b", "Lcom/archivomedico/personal/databinding/ItemMedBinding;", "(Lcom/archivomedico/personal/databinding/ItemMedBinding;)V", "getB", "()Lcom/archivomedico/personal/databinding/ItemMedBinding;", "app_debug"})
    public static final class H extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.archivomedico.personal.databinding.ItemMedBinding b = null;
        
        public H(@org.jetbrains.annotations.NotNull()
        com.archivomedico.personal.databinding.ItemMedBinding b) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.archivomedico.personal.databinding.ItemMedBinding getB() {
            return null;
        }
    }
}