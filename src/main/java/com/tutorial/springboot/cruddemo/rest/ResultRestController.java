package com.tutorial.springboot.cruddemo.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.springboot.cruddemo.entity.Result;
import com.tutorial.springboot.cruddemo.entity.StringRequest;

@RestController
public class ResultRestController {
	
	@GetMapping("/result")
	public Result getResult(@RequestBody StringRequest textInput) throws IOException, InterruptedException {
		
		String dir = System.getProperty("user.dir");
		
		String command = "python " +dir + "/data/hs_classification.py "+ "'"+textInput.getTextInput()+"'";
		//String command = "python " +dir + "/target/data/hello.py";
		
		System.out.println(command);
		
		//execute command line
		Process process = Runtime.getRuntime().exec(command);

		StringBuilder output = new StringBuilder();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}

		int exitVal = process.waitFor();
		if (exitVal == 0) {
			System.out.println("Success!");
			System.out.println(output);
		} 
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		Result theResult;
		try {
			theResult = mapper.readValue(new File(dir+"/data/output.json"), Result.class);
			return theResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		
		}
		
		
	}
	


}	
