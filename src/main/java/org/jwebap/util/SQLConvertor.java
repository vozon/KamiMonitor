package org.jwebap.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * SQL�﷨������ʾ��
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date 2009-2-21
 */
public class SQLConvertor {

	// �ؼ���
	public static final String[] SQL_KEY_WORDS = { "select", "from", "update",
			"delete", "insert", "into", "where", "group", "by", "having",
			"values", "and", "or", "(", ")", "as", "like", "not", "in",
			"exists", "order", "asc", "desc", "on", "join", "outer", "set",
			"create", "table", "alter", "drop", "index" };

	// �ؼ����滻ǰ׺
	private String keywordLightPrefix = "<font color='#000080'><b>";

	// �ؼ����滻��׺
	private String keywordLightSuffix = "</b></font>";

	// �����滻ǰ׺
	private String tablesLightPrefix = "<font color='#3F7F5F'><b>";

	// �����滻��׺
	private String tablesLightSuffix = "</b></font>";

	/**
	 * �����������б�
	 * 
	 * @param sql
	 * @return
	 */
	public Collection analyseSqlTables(String sql) {
		// �ʷ�����
		String[] words = splitWord(sql, null);

		Collection tables = new ArrayList();

		// �﷨����
		tableAnalyse(new Counter(), words, tables);

		return tables;
	}

	/**
	 * ��������
	 * 
	 * @param sql
	 * @return
	 */
	public String highlightTables(String sql) {
		Collection tables = analyseSqlTables(sql);

		return null;
	}

	/**
	 * �����ؼ���
	 * 
	 * @param sql
	 * @return
	 */
	public String highlightKeywords(String sql) {
		String lightSql = this.replaceSql(sql, null, true);
		return lightSql;
	}

	/**
	 * ����sql,�����ؼ��ֺͱ���
	 * 
	 * @param sql
	 * @return
	 */
	public String highlightSql(String sql) {
		Collection tables = this.analyseSqlTables(sql);

		String lightSql = this.replaceSql(sql, tables, true);
		return lightSql;
	}

	/**
	 * ���sql�滻,����
	 * 
	 * @return
	 */
	private String replaceSql(String sql, final Collection tables,
			final boolean replaceKeyword) {
		// �滻���뱣֤��һ�δʷ�ɨ����ɣ������滻�����ݻụ��Ӱ��
		// @ TODO ����splitWord����������������һ��ɨ�裬ֻ��ģ��һ�δʷ�����

		final StringBuffer replacement = new StringBuffer(" ");

		// �ʷ���������
		LexicalAnalysisProcessor processor = new LexicalAnalysisProcessor() {

			public void process(String word) {
				int placeType = 0;

				if (replaceKeyword) {
					for (int i = 0; i < SQL_KEY_WORDS.length; i++) {
						if (SQL_KEY_WORDS[i].equals(word.toLowerCase())) {
							placeType = 1;
							break;// ���������ȼ����ڹؼ��֣��������ﲻreturn
						}
					}
				}

				if (tables != null && tables.contains(word)) {
					placeType = 2;
				}

				switch (placeType) {
				case 0://��ͨ�ʻ�
					replacement.append(word+" ");
					break;
				case 1://�ؼ���
					replacement.append(keywordLightPrefix + word
							+ keywordLightSuffix + " ");
					break;
				case 2://����
					replacement.append(tablesLightPrefix + word
							+ tablesLightSuffix + " ");
					break;
				default:
				}
			}

		};

		splitWord(sql, processor);

		return replacement.toString();
	}

	/**
	 * �ִʣ�����SQL����֮ǰ���Ƚ��дʷ��������ҳ�SQL����������Ĵʻ�
	 * 
	 * @author leadyu
	 * @param sql
	 * @return
	 */
	private String[] splitWord(String sql, LexicalAnalysisProcessor processor) {

		List words = new ArrayList();

		String[] spaceSplit = StringUtil.split(sql," ");

		for (int i = 0; i < spaceSplit.length; i++) {
			String word = spaceSplit[i];
			word = StringUtil.replaceAll(word,",", "#,#");
			word = StringUtil.replaceAll(word,"\\(", "#(#");
			word = StringUtil.replaceAll(word,"\\)", "#)#");

			String[] ws = StringUtil.split(word,"#");
			for (int c = 0; c < ws.length; c++) {
				if (ws[c] != null && !"".equals(ws[c].trim())) {
					words.add(ws[c]);
					if (processor != null)
						processor.process(ws[c]);
				}
			}

		}

		String[] result = new String[words.size()];
		words.toArray(result);
		return result;
	}

	private static class Counter {
		int num = 0;
	}

	/**
	 * �Ա������﷨����
	 * 
	 * @param words
	 * @return
	 */
	private void tableAnalyse(Counter i, String[] words, Collection tables) {

		for (; i.num < words.length; i.num++) {
			// �������'('������һ���Ӿ��﷨����
			if ("(".equals(words[i.num].trim().toLowerCase())) {
				i.num++;
				tableAnalyse(i, words, tables);
				continue;
			}
			// �������')'�˳���ǰ�Ӿ��﷨����
			if (")".equals(words[i.num].trim().toLowerCase())) {
				return;
			}

			// �������from
			if ("from".equals(words[i.num].trim().toLowerCase())) {
				// System.out.print("");
				for (i.num++; i.num < words.length; i.num++) {
					String fromNext = words[i.num];

					// �������'('������һ���Ӿ��﷨����
					if ("(".equals(fromNext.trim().toLowerCase())) {
						i.num++;
						tableAnalyse(i, words, tables);
						continue;
					}
					// �������')'�˳���ǰ�Ӿ��﷨����
					if (")".equals(fromNext.trim().toLowerCase())) {
						return;
					}
					// �������'where,into'from���ʽ����
					if ("where".equals(fromNext.trim().toLowerCase())
							|| "into".equals(fromNext.trim().toLowerCase())) {
						break;
					}
					// �����,��ǰ��ľ��Ǳ���
					if (",".equals(fromNext.trim())) {
						// ʲôҲ����
					} else {
						// �������ǰ����from����,�Լ�join˵���Ǳ���
						if ("from".equals(words[i.num - 1])
								|| ",".equals(words[i.num - 1])
								|| "join".equals(words[i.num - 1])) {
							tables.add(words[i.num]);
							// System.out.print("");
						}
					}
				}
			}

			if (i.num >= words.length)
				break;

			// �������update
			if ("update".equals(words[i.num].trim().toLowerCase())) {
				i.num++;
				if (i.num >= words.length)
					break;
				tables.add(words[i.num]);

			}

			if (i.num >= words.length)
				break;

			// �������into
			if ("into".equals(words[i.num].trim().toLowerCase())) {
				i.num++;
				if (i.num >= words.length)
					break;
				tables.add(words[i.num]);

			}

		}

	}

	public String getKeywordLightPrefix() {
		return keywordLightPrefix;
	}

	public void setKeywordLightPrefix(String keywordLightPrefix) {
		this.keywordLightPrefix = keywordLightPrefix;
	}

	public String getKeywordLightSuffix() {
		return keywordLightSuffix;
	}

	public void setKeywordLightSuffix(String keywordLightSuffix) {
		this.keywordLightSuffix = keywordLightSuffix;
	}

	public String getTablesLightPrefix() {
		return tablesLightPrefix;
	}

	public void setTablesLightPrefix(String tablesLightPrefix) {
		this.tablesLightPrefix = tablesLightPrefix;
	}

	public String getTablesLightSuffix() {
		return tablesLightSuffix;
	}

	public void setTablesLightSuffix(String tablesLightSuffix) {
		this.tablesLightSuffix = tablesLightSuffix;
	}
}

/**
 * sql�ʷ��������������Ա���һ�δʷ�ɨ����̣�Ƕ�����Ķ���
 * 
 * @author leadyu(yu-lead@163.com)
 * @since Jwebap 0.6
 * @date 2009-2-21
 */
abstract class LexicalAnalysisProcessor {
	/**
	 * 
	 * @param word
	 *            �ʻ�
	 */
	public abstract void process(String word);

}
