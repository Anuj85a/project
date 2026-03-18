package repository;

import model.Account;

public interface AccountRepository {
    void save(Account account);
    Account findByAccountNo(int accountNo);
}
