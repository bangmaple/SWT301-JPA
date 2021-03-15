package com.bangmaple.onion_architecture_demo.repositories.impl;

import com.bangmaple.onion_architecture_demo.entites.UsersEntity;
import com.bangmaple.onion_architecture_demo.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class UsersRepositoryImpl extends UsersRepository {
    @Override
    public CompletableFuture<Optional<List<UsersEntity>>> getUsersByRole(String roleName) {
        return CompletableFuture.supplyAsync(() ->  Optional.ofNullable(getEntityManager()
                   .createQuery("SELECT t FROM UsersEntity t " +
                           "WHERE t.role = :roleName", UsersEntity.class).getResultList()));
    }
}
