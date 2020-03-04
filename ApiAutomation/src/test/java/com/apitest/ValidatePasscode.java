package com.apitest;

import org.testng.annotations.Test;

import com.cric.cricInfo;
import com.methods.CommonMethod;

import org.testng.annotations.BeforeClass;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class ValidatePasscode {
  @Test
  public void validatePasscode_Success_200OK() throws Exception {
	  CommonMethod commons = new CommonMethod();
	  HttpResponse response = commons.validate(2);
	  Assert.assertEquals(response.getStatusLine().getStatusCode(),200);
  }
  
  @Test
  public void validate_Invalid_PassCode_400() throws Exception {
	  CommonMethod commons = new CommonMethod();
	  HttpResponse response = commons.validate(3);
	  Assert.assertEquals(response.getStatusLine().getStatusCode(),500);
  }
  

  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }

}
