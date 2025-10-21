package org.LukDT.bank_application_rest_api.dao;

import jakarta.persistence.*;
import org.LukDT.bank_application_rest_api.entity.Operation;
import org.LukDT.bank_application_rest_api.entity.User;
import org.LukDT.bank_application_rest_api.exception.InsufficientFundsException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Operation> getOperationList(long id, LocalDateTime from, LocalDateTime to) {
        StringBuilder jpql = new StringBuilder("SELECT o FROM Operation o WHERE o.user.id = :userId");
        if(from != null && to != null) {
            jpql.append("AND o.dateOperation BETWEEN :start AND :end");
        } else if(from != null) {
            jpql.append("AND o.dateOperation BETWEEN >= :start");
        } else if(to != null) {
            jpql.append("AND o.dateOperation BETWEEN <= :end");
        }

        jpql.append("ORDER BY o.dateOperation DESC");

        TypedQuery<Operation> query = entityManager.createQuery(jpql.toString(), Operation.class);
        query.setParameter("userId", id);

        if(from != null) query.setParameter("start", from);
        if(to != null) query.setParameter("end", to);

        return query.getResultList();
    }

    @Override
    public BigDecimal getBalance(long id) {
        try {
            User user = entityManager.find(User.class, id);
            if (user == null) {
                System.err.println("Пользователь с id не найден");
                return new BigDecimal("-1");
            }

            return user.getBalance();

        } catch(Exception e) {
            System.err.println("Непредвиденная ошибка при проверке баланса: " + e.getMessage());
            return new BigDecimal("-1");
        }
    }

    @Override
    public int putMoney(long id, String summa) {
        try {
            User user = entityManager.find(User.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (user == null) {
                System.err.println("Пользователь с id не найден");
                return 0;
            }

            BigDecimal summaDecimal = new BigDecimal(summa.trim());

            Operation operation = new Operation(user, 1, summaDecimal, LocalDateTime.now());
            entityManager.persist(operation);

            user.setBalance(user.getBalance().add(summaDecimal));

            return 1;

        } catch(NumberFormatException e) {
            System.err.println("Ошибка ввода: " + e.getMessage());
            return 0;
        } catch(EntityNotFoundException e) {
            System.err.println("Ошибка при проверке баланса: " + e.getMessage());
            return 0;
        } catch(Exception e) {
            System.err.println("Непредвиденная ошибка при проверке баланса: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int takeMoney(long id, String summa) {
        try {
            User user = entityManager.find(User.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (user == null) {
                System.err.println("Пользователь с id не найден");
                return 0;
            }

            if(user.getBalance().compareTo(new BigDecimal("0")) == -1) {
                throw new InsufficientFundsException();
            }

            BigDecimal summaDecimal = new BigDecimal(summa.trim());

            Operation operation = new Operation(user, 2, summaDecimal, LocalDateTime.now());
            entityManager.persist(operation);

            user.setBalance(user.getBalance().subtract(summaDecimal));

            return 1;

        } catch(InsufficientFundsException e) {
            System.err.println("Недостаточно средств: " + e.getMessage());
            return 0;
        } catch(EntityNotFoundException e) {
            System.err.println("Ошибка при проверке баланса: " + e.getMessage());
            return 0;
        } catch(Exception e) {
            System.err.println("Непредвиденная ошибка при проверке баланса: " + e.getMessage());
            return 0;
        }
    }
}
