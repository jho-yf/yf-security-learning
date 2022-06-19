package cn.jho.security.uaa.security.auth.ldap;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * LDAPUserRepo
 *
 * @author JHO xu-jihong@qq.com
 * @date 2022-06-15 19:29
 */
@Repository
public interface LdapUserRepo extends LdapRepository<LdapUser> {

    Optional<LdapUser> findByUsernameAndPassword(String username, String password);

}
