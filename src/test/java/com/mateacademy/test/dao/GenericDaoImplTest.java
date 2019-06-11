package com.mateacademy.test.dao;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.mateacademy.abstract_dao.dao.impl.GenericDaoImpl;
import com.mateacademy.test.model.CommonUserModel;
import com.mateacademy.test.model.StringUserModel;
import com.mateacademy.test.model.User;

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
		Long id = (long) (Math.random() * 1000);
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
}
