package it.unical.pignolo.tests.main;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import it.unical.pignolo.dao.ProblemProxy;
import it.unical.pignolo.model.Problem;
import it.unical.pignolo.model.TestCase;

public class Test {

	public static void main(String[] args) {
		Problem p = new ProblemProxy();
		p.setId(new Long(35));
		
		List<TestCase> tl = p.getProblemTestCases();
		
		JsonArray ja = new JsonArray();
		JsonParser jp = new JsonParser();
		Gson gson = new Gson();
		
		tl.forEach(t -> {
			t.setProblem(null);
			ja.add(jp.parse(gson.toJson(t)));
		});
		
		System.out.println(ja);
	}

}
