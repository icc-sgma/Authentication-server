package sgma.auth.server.security.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import sgma.auth.server.security.entity.UserEntity;

@Repository
public class OAuthUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public UserEntity getUserDetails(String username) {
		Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
		String userSQLQuery = "SELECT usr.password, auths.authority FROM users usr, authorities auths WHERE usr.username=? AND usr.username=auths.username";
		List<UserEntity> list = jdbcTemplate.query(userSQLQuery, new String[] { username },
				(ResultSet rs, int rowNum) -> {
					UserEntity user = new UserEntity();
					user.setUsername(username);
					user.setPassword(rs.getString("usr.password"));
					String[] auths = rs.getString("auths.authority").split(",");
					for(int i=0;i<auths.length;i++) {
						GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(auths[i]);
						grantedAuthoritiesList.add(grantedAuthority);
						user.setGrantedAuthoritiesList(grantedAuthoritiesList);
					}
					return user;
				});
		if (list.size() > 0) return list.get(0);
		return null;
	}
}
