package com.nazdaq.sms.dao;

import java.util.List;

import com.nazdaq.sms.model.UserRole;

/**
 * @author abu.taleb
 *
 */
public interface UserRoleDao {

	public void addUserRole(UserRole userRole);

	public List<UserRole> listUserRole();

	public UserRole getUserRole(int userroleid);

	public void deleteUserRole(UserRole userRole);

	public List<String> getUserRoleName();
}
