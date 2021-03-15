package com.bangmaple.onion_architecture_demo.repositories;

import com.bangmaple.onion_architecture_demo.entites.UsersEntity;
import com.bangmaple.onion_architecture_demo.repositories.helper.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class UsersRepository extends JpaRepository<UsersEntity, String> {
    public abstract CompletableFuture<Optional<List<UsersEntity>>> getUsersByRole(String roleName);
}
