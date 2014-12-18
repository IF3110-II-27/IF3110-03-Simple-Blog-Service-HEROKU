package id.ac.itb.informatika.wbd.service;

public class SimpleBlogServiceImpl implements SimpleBlogService {

	@Override
	public String sayHi(String input) {
		System.out.println("Hello invoked : " + input);
		return String.format("Hello '%s'", input);
	}

}
