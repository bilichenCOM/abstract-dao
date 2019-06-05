package com.mateacademy.test.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.mateacademy.dao.impl.GenericDaoImpl;
import com.mateacademy.test.model.CommonUserModel;
import com.mateacademy.test.model.StringUserModel;
import com.mateacademy.test.model.User;

public class GenericDaoImplTest {

//	@Test
	public void saveTest() {
		GenericDaoImpl<User, Long> daoImpl = new GenericDaoImpl<>();
		User user = new User("Johny", "Hurricane", 23, new ArrayList<>());
		daoImpl.save(user);
		assertTrue(true);
	}

//	@Test
	public void getTest() {
		GenericDaoImpl<StringUserModel, Long> daoImpl = new GenericDaoImpl<>();
		StringUserModel model = new StringUserModel("Johny", "String", "Chinese", "Ukraine");
		daoImpl.save(model);
		StringUserModel model2 = daoImpl.get(StringUserModel.class, 1L);
		assertNotNull(model2);
		System.out.println(model2.toString());
	}

	@Test
	public void saveWithCommonDataTypes() {
		GenericDaoImpl<CommonUserModel, Long> daoImpl = new GenericDaoImpl<>();
		Long id = (long) (Math.random() * 1000);
		CommonUserModel commonUserModel = new CommonUserModel("Johny", id, 93, 2.5, 'M');
		daoImpl.save(commonUserModel);
		CommonUserModel commonUserModel2 = daoImpl.get(CommonUserModel.class, commonUserModel.getId());
		assertNotNull(commonUserModel2);
		System.out.println(commonUserModel2);
	}
}
