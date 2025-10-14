package org.LukDT.bank_application_rest_api.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.LukDT.bank_application_rest_api.entity.User;
import org.LukDT.bank_application_rest_api.exception.InsufficientFundsException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public int putMoney(long id, String summa) {
        try {
            User user = entityManager.find(User.class, id);
            if (user == null) {
                System.err.println("Пользователь с id не найден");
                return 0;
            }

            BigDecimal summaDecimal = new BigDecimal(summa);
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
    @Transactional
    public int takeMoney(long id, String summa) {
        try {
            User user = entityManager.find(User.class, id);
            if (user == null) {
                System.err.println("Пользователь с id не найден");
                return 0;
            }

            BigDecimal summaDecimal = new BigDecimal(summa);
            user.setBalance(user.getBalance().subtract(summaDecimal));

            if(user.getBalance().compareTo(new BigDecimal("0")) == -1) {
                throw new InsufficientFundsException();
            }

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
