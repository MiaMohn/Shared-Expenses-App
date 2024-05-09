package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.JPArepositories;

import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExpenseRepository extends JpaRepository<ExpenseEntity,Long> {

    @Query(
            value = "SELECT u.name from user u, expense e where u.id = e.user_id and e.id = :id",
            nativeQuery = true
    )

    String findExpenseFriendNameById(@Param("id") Long id);

}
