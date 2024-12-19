package com.gerenciamento.financeiro_pessoal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerenciamento.financeiro_pessoal.Model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

}
