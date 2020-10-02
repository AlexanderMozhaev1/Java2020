import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void openAccount() {
        Customer customer = new Customer("Name", "LastName");

        assertTrue(customer.openAccount(1));
        assertFalse(customer.openAccount(1));
    }

    @Test
    void openAccountBalance() {
        Customer customer = new Customer("Name", "LastName");

        assertTrue(customer.openAccount(new Account(1, 100)));
        assertFalse(customer.openAccount(new Account(1, 100)));
    }

    @Test
    void closeAccount() {
        Customer customer = new Customer("Name", "LastName", new Account(1));

        assertTrue(customer.closeAccount());
        assertFalse(customer.closeAccount());
    }

    @Test
    void fullName() {
        Customer customer = new Customer("Name", "LastName");

        assertEquals("Name LastName", customer.fullName());
    }

    @Test
    void withdrawFromCurrentAccount() {
        Customer customer = new Customer("Name", "LastName", new Account(1, 1000));

        assertTrue(customer.withdrawFromCurrentAccount(100));
        assertTrue(customer.withdrawFromCurrentAccount(200));
        assertFalse(customer.withdrawFromCurrentAccount(800));
        assertTrue(customer.withdrawFromCurrentAccount(700));
    }

    @Test
    void withdrawFromCurrentNullAccount() {
        Customer customer = new Customer("Name", "LastName");

        assertFalse(customer.withdrawFromCurrentAccount(100));
    }

    @Test
    void addMoneyToCurrentAccount() {
        Customer customer = new Customer("Name", "LastName", new Account(1));

        assertTrue(customer.addMoneyToCurrentAccount(200));
    }

    @Test
    void addMoneyToCurrentNullAccount() {
        Customer customer = new Customer("Name", "LastName");

        assertFalse(customer.addMoneyToCurrentAccount(100));
    }

    @Test
    void checkExeptionEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Customer("", ""));
        assertEquals("Empty name or lastName", exception.getMessage());
    }

    @Test
    void checkExeptionEmptyAccount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Customer("", "", new Account(1, 100)));
        assertEquals("Empty name or lastName", exception.getMessage());
    }
}