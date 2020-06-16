package sgma.auth.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sgma.auth.server.security.dao.OAuthUserDao;
import sgma.auth.server.security.entity.CustomUser;
import sgma.auth.server.security.entity.UserEntity;

@Service
public class CustomDetailsService implements UserDetailsService {
   @Autowired
   private OAuthUserDao oauthDao;

   @Override
   public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
      UserEntity userEntity = null;
      try {
         userEntity = oauthDao.getUserDetails(username);
         CustomUser customUser = new CustomUser(userEntity);
         return customUser;
      } catch (Exception e) {
         throw new UsernameNotFoundException("User " + username + " was not found in the database");
      }
   }
}