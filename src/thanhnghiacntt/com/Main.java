/**
 * 
 */
package thanhnghiacntt.com;


import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Nguyễn Thành Nghĩa
 *
 */
public class Main {
	private BackUp backup = new BackUp();

	private void help() {
		String str = "-h help -start timestart(yyyy/MM/dd HH:mm:ss)"
				// + " -end timeend(yyyy/MM/dd HH:mm:ss)"
				+ " -expired number(days)"
				+ " -src folder-source -des folder-destination"
				+ " -period number(util second)";
		System.out.println(str);
	}

	/**
	 * Hash map
	 */
	private HashMap<String, Integer> getHash() {
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		hash.put("-h", 0);
		hash.put("-start", 1);
		hash.put("-expired", 2);
		hash.put("-src", 3);
		hash.put("-des", 4);
		hash.put("-end", 5);
		hash.put("-period", 6);
		return hash;
	}

	/**
	 * Get option
	 * @param arg
	 * @param index
	 * @return
	 */
	private String getOption(String[] arg, int index) {
		if (index < arg.length) {
			return arg[index];
		}
		return null;
	}

	/**
	 * Option
	 * @param arg
	 */
	private void option(String[] arg) {
		HashMap<String, Integer> hash = getHash();
		String temp = "";
		Date start = null;
		for (int i = 0; i < arg.length; i++) {
			String key = arg[i];
			if (hash.containsKey(key)) {
				int value = hash.get(key);
				switch (value) {
				case 0:
					backup.setHelp(true);
					break;
				case 1:
					i++;
					temp = getOption(arg, i);
					start = backup.getDate(temp);
					backup.setStart(start);
					break;

				case 2:
					i++;
					backup.setExpiredTime(Integer.parseInt(getOption(arg, i)));
					break;

				case 3:
					i++;
					backup.setSrc(getOption(arg, i));
					break;

				case 4:
					i++;
					backup.setDes(getOption(arg, i));
					break;

				case 5:
					i++;
					temp = getOption(arg, i);
					start = backup.getDate(temp);
					backup.setEnd(start);
					break;

				case 6:
					i++;
					backup.setPeriod(Integer.parseInt(getOption(arg, i)));
					break;

				default:
					break;
				}
			} else {
				help();
			}

		}
	}

	/**
	 * Run
	 */
	private void run() {
		Runnable runnable = new Runnable() {
			public void run() {
				runCopy();
				runDelete();
			}
		};

		ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		Date now = new Date();
		long initialDelay = (backup.getStart().getTime() - now.getTime()) / 1000;
		if (initialDelay < 0) {
			initialDelay = 0;
		}
		System.out.println("Delay " + initialDelay + " seconds");
		service.scheduleAtFixedRate(runnable, initialDelay, backup.getPeriod(),
				TimeUnit.SECONDS);
	}

	/**
	 * Run copy
	 */
	private void runCopy() {
		Runnable runnable = new Runnable() {
			public void run() {
				backup.copy();
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}

	/**
	 * Run delete
	 */
	private void runDelete() {
		Runnable runnable = new Runnable() {
			public void run() {
				backup.delete();
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}

	/**
	 * Start
	 * @param args
	 */
	public void start(String[] args){
		option(args);
		BackUp bu = backup;
		if (bu.isHelp() || bu.getStart() == null || bu.getSrc() == null
				|| bu.getDes() == null || bu.getExpiredTime() == 0) {
			help();
		} else {
			run();
		}
	}
}
