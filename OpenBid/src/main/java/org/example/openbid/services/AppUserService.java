package org.example.openbid.services;

import org.example.openbid.domain.AppUser;

public interface AppUserService {

    AppUser authenticateUser(String email, String rawPassword);
    AppUser registerUser(AppUser user);
    boolean emailExists(String email);

}
