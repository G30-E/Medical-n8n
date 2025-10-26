package com.archivomedico.personal.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\u0007"}, d2 = {"Lcom/archivomedico/personal/network/N8nApiService;", "", "sendNote", "Lretrofit2/Call;", "Ljava/lang/Void;", "note", "Lcom/archivomedico/personal/network/NoteData;", "app_debug"})
public abstract interface N8nApiService {
    
    @retrofit2.http.POST(value = "webhook-test/citas")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<java.lang.Void> sendNote(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.archivomedico.personal.network.NoteData note);
}