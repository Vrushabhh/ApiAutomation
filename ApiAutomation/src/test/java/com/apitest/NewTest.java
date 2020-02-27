package com.apitest;

import org.testng.annotations.Test;

import com.methods.CommonMethod;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class NewTest {
  @Test
  public void validate200Ok() throws Exception {
	  CommonMethod commons = new CommonMethod();
	  commons.validate();
  }
  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }

}
