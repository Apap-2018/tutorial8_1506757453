package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt(String password);
	void updatePassword(String username, String newPassword);
	UserRoleDb getUserRoleDb();
}
