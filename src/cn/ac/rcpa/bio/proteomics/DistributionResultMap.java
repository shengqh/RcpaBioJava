package cn.ac.rcpa.bio.proteomics;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ac.rcpa.bio.database.IAccessNumberParser;
import cn.ac.rcpa.filter.IFilter;

public class DistributionResultMap extends
		LinkedHashMap<String, CountMap<String>> {
	public DistributionResultMap() {
		super();
	}

	public void increase(String key, String experimental, int count) {
		if (!this.containsKey(key)) {
			this.put(key, new CountMap<String>());
		}

		this.get(key).increase(experimental, count);
	}

	public void increase(String key, String experimental) {
		increase(key, experimental, 1);
	}

	public List<String> getClassifiedNames() {
		if (size() == 0) {
			return new ArrayList<String>();
		}

		Set<String> resultSet = new HashSet<String>();
		for (CountMap<String> value : this.values()) {
			resultSet.addAll(value.keySet());
		}

		List<String> result = new ArrayList<String>(resultSet);
		Collections.sort(result);
		return result;
	}

	public CountMap<String> getTotalCountMap() {
		final CountMap<String> result = new CountMap<String>();
		for (String key : this.keySet()) {
			result.put(key, this.get(key).getTotalCount());
		}
		return result;
	}

	public int getTotalCount() {
		int result = 0;
		for (CountMap<String> value : this.values()) {
			result += value.getTotalCount();
		}
		return result;
	}

	public int getTotalUniqueCount() {
		return size();
	}

	public int getExperimentalUniqueCount(String experimental) {
		int result = 0;
		for (CountMap<String> value : this.values()) {
			if (value.containsKey(experimental)) {
				if (value.get(experimental) > 0) {
					result++;
				}
			}
		}
		return result;
	}

	public int getExperimentalCount(String experimental) {
		int result = 0;
		for (CountMap<String> value : this.values()) {
			result += value.get(experimental);
		}
		return result;
	}

	public List<String> getSortedKeys() {
		return getTotalCountMap().getKeyListByDecendingCount();
	}

	public void classify(Map<String, String> experimantalClassfiedNameMap) {
		for (String key : this.keySet()) {
			this.put(key, this.get(key).getClassifiedCountMap(
					experimantalClassfiedNameMap));
		}
	}

	public DistributionResultMap filter(IFilter<CountMap<String>> filter) {
		DistributionResultMap result = new DistributionResultMap();
		for (String key : this.keySet()) {
			if (filter.accept(this.get(key))) {
				result.put(key, this.get(key));
			}
		}
		return result;
	}

	public void write(PrintWriter pw) throws IOException {
		write(pw, null, null);
	}

	public void write(PrintWriter pw, IAccessNumberParser parser)
			throws IOException {
		write(pw, parser, null);
	}

	public void write(PrintWriter pw, IFilter<CountMap<String>> filter)
			throws IOException {
		write(pw, null, filter);
	}

	public void write(PrintWriter pw, IAccessNumberParser parser,
			IFilter<CountMap<String>> filter) throws IOException {
		pw.print("Object");
		final List<String> classifiedNames = getClassifiedNames();
		for (String name : classifiedNames) {
			pw.print("\t" + name);
		}
		pw.println("\tTotal");

		final List<String> keys = getSortedKeys();
		for (String key : keys) {
			final CountMap<String> countMap = this.get(key);
			if (filter != null && !filter.accept(countMap)) {
				continue;
			}

			pw.print((parser == null ? key : parser.getValue(key)));

			for (String classifiedName : classifiedNames) {
				pw.print("\t" + countMap.get(classifiedName));
			}
			pw.println("\t" + countMap.getTotalCount());
		}
	}
}
