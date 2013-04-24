package org.jwebap.plugin.tracer.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.jwebap.core.Trace;
import org.jwebap.plugin.tracer.TimeFilterAnalyser;
import org.jwebap.util.Assert;

/**
 * ��ʱ�켣��ͼ������
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date Feb 19, 2008
 */
public class TimeFilterViewHelper {
	/** ʱ����˹켣������ */
	private TimeFilterAnalyser analyser;

	public TimeFilterViewHelper(TimeFilterAnalyser analyser) {
		Assert.assertNotNull(analyser, "time filter analyser is null.");
		this.analyser = analyser;
	}

	public JSONObject processJson(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		/** �ӳ�ʱ�켣��������÷������ݣ�������ʾ */

		// http����ʱ�ļ�¼
		List traces = new ArrayList(analyser.getTraces());

		int pageSize = 20;
		int start = 0;
		int total = traces.size();
		String strStartNum = request.getParameter("start");
		String strPageSize = request.getParameter("limit");
		try {
			start = Integer.parseInt(strStartNum);
			pageSize = Integer.parseInt(strPageSize);
		} catch (Exception e) {
		}

		int toIndex = start + pageSize > total ? total : start + pageSize;

		// ����
		String sort = request.getParameter("sort");
		String dir = request.getParameter("dir");
		if (dir == null) {
			dir = "DESC";
		}

		if ("cost".equals(sort)) {
			Collections.sort(traces, new TraceActiveTimeComparator(dir));
		} else if ("isClosed".equals(sort)) {
			Collections.sort(traces, new TraceStateComparator(dir));
		} else {
			Collections.sort(traces, new TraceCreateTimeComparator(dir));
		}

		JSONObject json = new JSONObject();
		json.put("totalCount", total);
		json.put("traces", traces.subList(start, toIndex));
		return json;
	}
}

/**
 * ���켣�ʱ������ʱ�������ǰ
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-2-2
 */
class TraceActiveTimeComparator implements Comparator {
	private String dir;

	TraceActiveTimeComparator(String dir) {
		this.dir = dir;
	}

	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof Trace) || !(o2 instanceof Trace)) {
			return -1;
		} else {
			Trace so1 = (Trace) o1;
			Trace so2 = (Trace) o2;
			return (int) ("DESC".equals(dir) ? so2.getActiveTime()
					- so1.getActiveTime() : so1.getActiveTime()
					- so2.getActiveTime());
		}
	}
}

/**
 * ���켣����ʱ������ʱ�������ǰ
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-2-2
 */
class TraceCreateTimeComparator implements Comparator {
	private String dir;

	TraceCreateTimeComparator(String dir) {
		this.dir = dir;
	}

	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof Trace) || !(o2 instanceof Trace)) {
			return -1;
		} else {
			Trace so1 = (Trace) o1;
			Trace so2 = (Trace) o2;
			return (int) ("DESC".equals(dir) ? so2.getCreatedTime()
					- so1.getCreatedTime() : so1.getCreatedTime()
					- so2.getCreatedTime());
		}
	}
}

/**
 * ���켣״̬(��/��)������ͬ״̬�߰�����ʱ������ʱ�������ǰ
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.5
 * @date 2008-2-2
 */
class TraceStateComparator implements Comparator {
	private String dir;

	TraceStateComparator(String dir) {
		this.dir = dir;
	}

	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof Trace) || !(o2 instanceof Trace))
			return -1;
		Trace so1 = (Trace) o1;
		Trace so2 = (Trace) o2;
		int state1 = (so1.getInactiveTime() <= 0L ? -1 : 1);
		int state2 = (so2.getInactiveTime() <= 0L ? -1 : 1);

		if (state2 - state1 != 0)
			return "DESC".equals(dir) ? state2 - state1 : state1 - state2;
		else
			return (int) (so2.getCreatedTime() - so1.getCreatedTime());
	}
}