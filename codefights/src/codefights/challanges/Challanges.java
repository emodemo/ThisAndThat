package codefights.challanges;

import java.sql.Timestamp;

public class Challanges {

	int[] logParse(String[] logs) {
	    if(logs.length == 0) return new int[]{};
	    int[] result = new int[2];
		double tmpBest = Double.MAX_VALUE;
		for (String log : logs) {
			String[] split = log.split(",");
	        Timestamp timestamp = Timestamp.valueOf(split[0].replace("T"," "));
			Long time1 = timestamp.getTime() / 1000;
			Timestamp timestamp2 = Timestamp.valueOf(split[1].replace("T"," "));
			Long time2 = timestamp2.getTime() / 1000;
			int users = Integer.parseInt(split[2]);
			int profit = Integer.parseInt(split[3]);
			double res = (double)profit / ( users * (time2 - time1));
			if(res < tmpBest){
	            tmpBest = res;
				result[0] = time1.intValue();
				result[1] = time2.intValue();
			}
		}
		return result;
	}

}
