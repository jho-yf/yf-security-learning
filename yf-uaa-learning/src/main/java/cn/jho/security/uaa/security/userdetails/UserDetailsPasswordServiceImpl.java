package cn.jho.security.uaa.security.userdetails;

import cn.jho.security.uaa.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

/**
 * UserDetailsPasswordServiceImpl
 *
 * @author JHO xu-jihong@qq.com
 * @date 2022-06-05 23:00
 */
@Service
@RequiredArgsConstructor
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {

    private final UserRepo userRepo;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return userRepo.findOptionalByUsername(user.getUsername())
                .map(u -> (UserDetails) userRepo.save(u.withPassword(newPassword)))
                .orElse(user);
    }
}
