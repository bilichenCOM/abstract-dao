package com.mateacademy.abstract_dao.test.dao;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.mateacademy.abstract_dao.dao.impl.GenericDaoImpl;
import com.mateacademy.abstract_dao.test.model.CommonUserModel;
import com.mateacademy.abstract_dao.test.model.StringUserModel;
import com.mateacademy.abstract_dao.test.model.User;

public class GenericDaoImplTest {

	@Test
	public void saveTest() {
		GenericDaoImpl<User, Long> daoImpl = new GenericDaoImpl<>(User.class);
		User user = new User("Johny", "Hurricane", 23, new ArrayList<>());
		daoImpl.save(user);
		
	}

	@Test
	public void getTest() {
		GenericDaoImpl<StringUserModel, Long> daoImpl = new GenericDaoImpl<>(StringUserModel.class);
		StringUserModel model = new StringUserModel("Johny", "String", "Chinese", "Ukraine");
		daoImpl.save(model);
		StringUserModel model2 = daoImpl.get(1L);
		Assert.assertNotNull(model2);
		System.out.println(model2.toString());
	}

	@Test
	public void saveWithCommonDataTypes() {
		GenericDaoImpl<CommonUserModel, Long> daoImpl = new GenericDaoImpl<>(CommonUserModel.class);
		Long id = 1002L;
		CommonUserModel commonUserModel = new CommonUserModel("Johny", id, 93, 2.5, 'M');
		daoImpl.save(commonUserModel);
		CommonUserModel commonUserModel2 = daoImpl.get(commonUserModel.getId());
		Assert.assertNotNull(commonUserModel2);
		System.out.println(commonUserModel2);
	}

	@Test
	public void getAllTest() {
		GenericDaoImpl<CommonUserModel, Long> daoImpl = new GenericDaoImpl<>(CommonUserModel.class);
		Assert.assertNotEquals(0, daoImpl.getAll().size());
		daoImpl.getAll().forEach(System.out::println);
	}

	@Test
	public void updateTest() {
		GenericDaoImpl<CommonUserModel, Long> daoImpl = new GenericDaoImpl<>(CommonUserModel.class);
		CommonUserModel userModel = new CommonUserModel();
		userModel.setId(36L);
		userModel.setName("Modified");
		userModel.setAge(33);
		userModel.setBalance(1000.0);
		daoImpl.update(userModel);
		if (daoImpl.get(36L).getBalance() == null) {
			Assert.fail();
		}
		Assert.assertEquals(1000.0, daoImpl.get(36L).getBalance(), 0);
	}

	@Test
	public void delete() {
		GenericDaoImpl<CommonUserModel, Long> daoImpl = new GenericDaoImpl<>(CommonUserModel.class);
		daoImpl.delete(1000L);
	}
}
