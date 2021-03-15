package com.bangmaple.onion_architecture_demo.repositories.helper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ICRUDRepository<K, V> {

    CompletableFuture<Void> add(K k);

    CompletableFuture<Optional<K>> find(V v);
    
    CompletableFuture<Optional<List<K>>> findAll();

    CompletableFuture<Void> update(K k);

    CompletableFuture<Void> delete(K k);

}
