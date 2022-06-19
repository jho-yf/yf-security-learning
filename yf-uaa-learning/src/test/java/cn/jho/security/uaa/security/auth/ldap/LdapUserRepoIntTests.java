package cn.jho.security.uaa.security.auth.ldap;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * LdapUserRepoIntTests
 *
 * @author JHO xu-jihong@qq.com
 * @date 2022-06-16 07:27
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
@DataLdapTest
class LdapUserRepoIntTests {

    @Autowired
    LdapUserRepo ldapUserRepo;

    @Test
    void givenUsernameAndPassword_ThenFindUserSuccess() {
        String username = "zhaoliu";
        String password = "123";

        val user = ldapUserRepo.findByUsernameAndPassword(username, password);
        assertTrue(user.isPresent());
    }

    @Test
    void givenUsernameAndWrongPassword_ThenFindUserFail() {
        String username = "zhaoliu";
        String password = "bad_password";

        val user = ldapUserRepo.findByUsernameAndPassword(username, password);
        assertTrue(user.isEmpty());
    }


}
