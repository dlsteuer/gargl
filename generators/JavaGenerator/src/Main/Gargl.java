package Main;

import AutoGenerated.Mint;
import Generators.Generator;
import Generators.GeneratorFactory;
import TypeDefinitions.Module;
import Utilities.JCommanderParser;

import com.beust.jcommander.JCommander;

public class Gargl {
	public static void main(String[] args) {

		// Parse command line arguments (currently args is a dummy array)
		if (args.length == 0) {
			args = new String[] { "-i", "/home/mross/workspace/Gargl/lib/Mint.gtf","-l","java","-o","/home/mross/workspace/GarglTest/src/" };
			System.out.println("WARNING: Running with test defaults");
		}

		JCommanderParser jct = new JCommanderParser();
		new JCommander(jct, args);

		if (jct.inputFilename == null) {
			System.out.println("ERROR: Need to specify input filename with -i");
			System.exit(0);
		}
		
		if(jct.language == null){
			System.out.println("ERROR: Need to specify output language with -l");
			System.exit(0);
		}
		
		if(jct.outputDirectory == null){
			// default to current working directory
			jct.outputDirectory = "";
		}
		
		Mint m = new Mint();
		try {
			System.out.println(m.joesfunction2("ross.michael22@gmail.com", "badpassword"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read in file and convert to Module containing function name and Requests
		InputParser parser = new InputParser(jct.inputFilename);
		Module mod = parser.parseAndConvert();

		System.out.println("LOG: Parsed requests " + jct.inputFilename);

		// Create the necessary generator based on language selected and initialize it with the Module created from file
		Generator generator = GeneratorFactory.createGenerator(jct.language, mod);
		generator.generateClass(jct.outputDirectory);
	}
}
