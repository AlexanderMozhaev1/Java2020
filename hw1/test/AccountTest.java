import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void withdraw() {
        Account account = new Account(1, 200);

        assertFalse(account.withdraw(0));
        assertTrue(account.withdraw(100));
        assertFalse(account.withdraw(150));
        assertTrue(account.withdraw(100));
    }

    @Test
    void add() {
        Account account = new Account(1);

        assertFalse(account.add(0));
        assertTrue(account.add(100));
        assertFalse(account.add(0));
    }
}