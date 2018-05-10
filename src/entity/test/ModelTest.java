package entity.test;

import entity.InternalUser;
import entity.Model;

public class ModelTest extends Model{
	public ModelTest() {
		this.setMyUser(new InternalUser(0, "Test"));
	}
}
