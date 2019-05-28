package in.education.college;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class PingClass {
	public enum IpStatus{
		DOWN, UP, PACKET_LOSS
	}

	private ExecutorService execService = Executors.newFixedThreadPool(10);
	Map<String, Map<String, IpStatus>> siteStatus = new HashMap<>();
	private  final String WINDOWS_PING_COMMAND_FORMAT = "ping -n %d -i %d %s";
	private  final int PING_TTL = 60;
	private  final int PING_COUNT = 8;

	private String[] siteIps1 = {"192.168.34.41", "192.168.34.42", "192.168.34.43"};
	private Site site1 = new Site("Site1", Arrays.asList(siteIps1));

	private String[] siteIps2 = {"192.168.34.44", "192.168.34.45", "192.168.34.46"};
	private Site site2 = new Site("Site2", Arrays.asList(siteIps2));

	private List<Site> sites = Arrays.asList(site1,site2);

	void checkSites(){

		for(Site site : sites) {
			checkConnectivity(site.getSiteName(), site.getIpList());
		}
	}

	 private void checkConnectivity(String siteName, List<String> ipList) {

	 	List<Future<IpStatus>> ipStatusFutures = new ArrayList<>();

		 for(String ip : ipList) {

			 Callable<IpStatus> ipStatusCallable = () -> {

				 IpStatus ipStatus = pingGeneric(String.format(WINDOWS_PING_COMMAND_FORMAT, PING_COUNT, PING_TTL, ip));
				 saveStatus(siteName, ip, ipStatus);

				 return ipStatus;
			 };

			 Future<IpStatus> futures = execService.submit(ipStatusCallable);
			 ipStatusFutures.add(futures);
		 }

		 try {
			 for (Future<IpStatus> future : ipStatusFutures) {
				 IpStatus status = future.get();
//				 System.out.println(status);
			 }
		 }catch (ExecutionException | InterruptedException e){
			e.printStackTrace();
		 }
	}

	void close() {
		execService.shutdownNow();
	}

	private IpStatus pingGeneric(String command){

		StringBuilder pingStatistics = new StringBuilder();
		try {
			Process p = Runtime.getRuntime().exec(command);
			try (BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {

				String s;
				// reading output stream of the command
				while ((s = inputStream.readLine()) != null) {
					pingStatistics.append(s).append(",");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pingStatus(pingStatistics.toString());
	}

	private void saveStatus(String siteName, String ip, IpStatus ipStatus) {

		if(!siteStatus.containsKey(siteName)) {

			siteStatus.put(siteName, new HashMap<>());
		}
		siteStatus.get(siteName).put(ip, ipStatus);
	}

	private IpStatus pingStatus(String pingStatistics){

		int packetsReceived = 0;
		int ttlExpireCount = 0;
		int MINIMUM_PACKETS = (int) (PING_COUNT * 0.8);

		String[] lines = pingStatistics.split(",");
		for(String line : lines){
			if (line.contains("Received = ")){
				String s = line.replaceAll("Received = ", "").trim();
				packetsReceived = Integer.parseInt(s);
			}

			if(line.contains("TTL expired in transit")){
				ttlExpireCount++;
			}//else the ttlExpireCount defaults to 0
		}

		int effectivePacketReceived = packetsReceived - ttlExpireCount;

		if(effectivePacketReceived == 0){
			return IpStatus.DOWN;
		} else if(effectivePacketReceived < MINIMUM_PACKETS){
			return IpStatus.PACKET_LOSS;
		} else{
			return IpStatus.UP;
		}
	}

	 class Site {

		private String siteName;

		private List<String> ipList;

		public Site() {}

		Site(String siteName, List<String> ipList) {
			this.siteName = siteName;
			this.ipList = ipList;
		}

		String getSiteName() {
			return siteName;
		}

		List<String> getIpList() {
			return ipList;
		}

		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}

		public void setIpList(List<String> ipList) {
			this.ipList = ipList;
		}

		@Override
		public String toString() {
			return "Site [siteName=" + siteName + ", ipList=" + ipList + "]";
		}
	}
}
