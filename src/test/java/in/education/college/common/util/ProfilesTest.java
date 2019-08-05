package in.education.college.common.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProfilesTest {

	@Test
	public void isProfileDev_Test1(){
		String[] activeProfiles = new String[]{};
		boolean expected = Util.isProfileDev(activeProfiles);
		Assert.assertFalse("Active Profiles empty should return false",expected);
	}

	@Test
	public void isProfileDev_Test2(){
		String[] activeProfiles = new String[]{"prod"};
		boolean expected = Util.isProfileDev(activeProfiles);
		Assert.assertFalse("activeProfiles [prod] should return false",expected);
	}

	@Test
	public void isProfileDev_Test3(){
		String[] activeProfiles = new String[]{"dev"};
		boolean expected = Util.isProfileDev(activeProfiles);
		Assert.assertTrue("activeProfiles [dev] should return true",expected);
	}

	@Test
	public void isProfileDev_Test4(){
		String[] activeProfiles = new String[]{"test", "dev"};
		boolean expected = Util.isProfileDev(activeProfiles);
		Assert.assertFalse("activeProfiles [test, dev] should return true",expected);
	}
}
