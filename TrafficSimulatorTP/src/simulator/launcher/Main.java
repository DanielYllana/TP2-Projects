package simulator.launcher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;
import simulator.model.Event;

import javax.swing.*;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
	private static int simulationTicks;
	private static boolean enableGUI = true;
	private static boolean loadEvents = false;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);

			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator's main loop" +
				"default value is 10).").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Type of simulation").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		if (line.hasOption("i")) {
			Main._inFile = line.getOptionValue("i");
			Main.loadEvents = true;
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseTicksOption(CommandLine line) throws ParseException {
		if (line.hasOption("t")) {
			Main.simulationTicks = Integer.parseInt(line.getOptionValue("t"));
		} else {
			Main.simulationTicks = Main._timeLimitDefaultValue;
		}
	}

	private static void parseModeOption(CommandLine line) throws ParseException {

		if (line.hasOption("m") && line.getOptionValue("m").equals("console")) {
			// Console mode
			Main.enableGUI = false;
		} else if (line.hasOption("m") && !line.getOptionValue("m").equals("gui")) {
			// option -m used with invalid value
			throw new ParseException("Unkown mode type");
		} else {
			// GUI Mode DEFAULT
			if (line.hasOption("i")) {
				// load events into the sim
				Main.loadEvents = true;
			}
		}
	}


	private static void initFactories() {
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add( new RoundRobinStrategyBuilder());
		lsbs.add( new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add( new MoveFirstStrategyBuilder());
		dqbs.add( new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add( new NewJunctionEventBuilder(lssFactory, dqsFactory));
		ebs.add( new NewCityRoadEventBuilder());
		ebs.add( new NewInterCityRoadEventBuilder());
		ebs.add( new NewVehicleEventBuilder());
		ebs.add( new SetWeatherEventBuilder());
		ebs.add( new SetContClassEventBuilder());


		Main._eventsFactory = new BuilderBasedFactory<>(ebs);

	}

	private static void startBatchMode() throws IOException {
		// TODO complete this method to start the simulation

		TrafficSimulator sim = new TrafficSimulator();
		Controller controller = new Controller(sim, Main._eventsFactory);

		InputStream is = new FileInputStream(Main._inFile);
		controller.loadEvents(is);
		is.close(); // close input stream

		// Create output stream
		OutputStream out;
		if (Main._outFile == null) {
			out = System.out;
		} else {
			out = new FileOutputStream(Main._outFile);
		}

		controller.run(Main.simulationTicks, out); // run simulation

		out.close();	// close output stream

	}

	private static void startGUIMode() {
		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, Main._eventsFactory);

		SwingUtilities.invokeLater(() -> new MainWindow(ctrl));
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		if (Main.enableGUI) {
			startGUIMode();
		} else {
			startBatchMode();
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
