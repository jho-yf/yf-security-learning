package cn.jho.security.uaa.security.auth.ldap;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * LdapMultiAuthenticationProvider
 *
 * @author JHO xu-jihong@qq.com
 * @date 2022-06-16 07:50
 */
@RequiredArgsConstructor
public class LdapMultiAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final LdapUserRepo ldapUserRepo;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // ignore
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return ldapUserRepo.findByUsernameAndPassword(username, authentication.getCredentials().toString())
                .orElseThrow(() -> new BadCredentialsException("[LDAP]用户名或密码错误！"));
    }
}
