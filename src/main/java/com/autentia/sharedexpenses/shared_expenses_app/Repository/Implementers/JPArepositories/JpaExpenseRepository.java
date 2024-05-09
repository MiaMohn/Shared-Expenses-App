package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories;

import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExpenseRepository extends JpaRepository<ExpenseEntity,Long> {
}
