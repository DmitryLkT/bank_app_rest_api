package org.LukDT.bank_application_rest_api.service;

import org.LukDT.bank_application_rest_api.dao.UserDAO;
import org.LukDT.bank_application_rest_api.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public List<Operation> getOperationList(long id, LocalDateTime from, LocalDateTime to) {
        return userDAO.getOperationList(id, from, to);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(long id) {
        return userDAO.getBalance(id);
    }

    @Override
    @Transactional
    public int putMoney(long id, String summa) {
        return userDAO.takeMoney(id, summa);
    }

    @Override
    @Transactional
    public int takeMoney(long id, String summa) {
        return userDAO.takeMoney(id, summa);
    }
}
