package org.kami.monitor.api.common.exception;

public class ErrorCode {

	/*********** Common ************/
	public static final String COMMON_NOT_PROJECT_MEMBER = "0000";
	public static final String COMMON_JSON_PARSE_ERROR = "0001";
	public static final String COMMON_NO_USER_TOKEN = "0002";
	public static final String COMMON_ILLEGAL_STRING = "0003";
	public static final String COMMON_DATEFORMAT_ILLEGAL = "0004";
	public static final String COMMON_ENTRY_NOT_EXISTED = "0005";
	public static final String COMMON_CONFIG_LOAD_FAIL = "0006";
	public static final String COMMON_HDFS_ERROR = "0007";
	public static final String COMMON_LENGTH_EXCEED = "0008";
	public static final String COMMON_EMPTY_STRING = "0010";
	public static final String COMMON_NOT_REGEX = "0011";
	public static final String COMMON_INVALID_APPTOKEN = "0012";
	public static final String COMMON_ENTRY_ALREADY_EXISTED = "0013";

	public static final String COMMON_UNDEFINED_ERROR = "0999";

	/*********** Project ************/
	public static final String PROJECT_NOT_ADMIN = "1000";
	public static final String PROJECT_MEMBER_REMOVE_SELF = "1001";
	public static final String PROJECT_MEMBER_UPDATE_SELF = "1002";

	/*********** RawData ************/
	public static final String RAWDATA_RECURSIVELY_SHARE = "2001";
	public static final String RAWDATA_CREATE_TABLE_FAIL = "2002";
	public static final String RAWDATA_SHARED_PROJECTS_CONFLICT = "2003";

	/*********** RawDataFile ************/
	public static final String RAWDATAFILE_ILLEGAL_ACCESS = "2005";
	public static final String RAWDATAFILE_ILLEGAL_WRITE = "2006";
	public static final String RAWDATAFILE_ILLEGAL_DATE = "2007";
	public static final String RAWDATAFILE_ILLEGAL_USER = "2008";
	public static final String RAWDATAFILE_ILLEGAL_SEARCH_COLUMN = "2009";
	public static final String RAWDATAFILE_ILLEGAL_SEARCH_OPER = "2010";
	public static final String RAWDATAFILE_TOO_MANY_FILE_TO_DOWN = "2011";
	public static final String RAWDATAFILE_ID_CONFLICT = "2012";
	public static final String RAWDATAFILE_PREVIEW_FAIL = "2014";
	public static final String RAWDATAFILE_FAIL_TO_GET_GREP_JAR = "2015";
	public static final String RAWDATAFILE_DOWNLOAD_FAIL = "2016";
	public static final String RAWDATAFILE_GREP_MR_FAIL = "2017";
	public static final String RAWDATAFILE_GREP_RESULT_FAIL = "2018";
	public static final String RAWDATAFILE_ILLEGAL_DATE_7DAY = "2019";

	/*********** Table ************/
	public static final String TABLE_EMPTY_SCHEMA = "3001";
	public static final String TABLE_NOT_SUPPORTED_LINEDELIMITER = "3003";
	public static final String TABLE_SHARED_PROJECTS_CONFLICT = "3004";
	public static final String TABLE_NOT_SUPPORTED_FORMAT_TYPE = "3005";
	public static final String TABLE_FAIL_TO_READ = "3006";
	public static final String TABLE_HIVE_EXCUTER_FAIL = "3007";
	public static final String TABLE_CREATE_ILLEGAL_DATA_TYPE = "3008";
	public static final String TABLE_SERDENAME_NOT_EXIST = "3009";
	public static final String TABLE_RECURSIVELY_SHARE = "3010";
	public static final String TABLE_FIELD_DELIMITER_CHAR = "3011";
	public static final String UNSUPPORTED_SERDE_NAME = "3012";
	public static final String INAPRROPRIATE_SERDE_PROPERTIES = "3013";
	public static final String REGEXSERDE_ONLY_ACCEPT_STRING_COLUMNS = "3014";

	/*********** Job ************/
	public static final String JOB_TABLE_IN_OTHER_PROJECT = "4001";
	public static final String JOB_RAWDATA_TABLE_USED = "4002";
	public static final String JOB_TABLE_USED_IN_OTHER_JOB = "4003";
	public static final String JOB_INVALID_PERIOD = "4004";
	public static final String JOB_ALREADY_DELETED = "4005";
	public static final String JOB_SCHEDULE_FAIL = "4006";
	public static final String JOB_RESCHEDULE_FAIL = "4007";
	public static final String JOB_CHANGE_SCHEDULE_STATUS_FAIL = "4008";
	public static final String JOB_FIND_SCHEMA_FAIL = "4009";
	public static final String JOB_SHARED_TABLE_USED = "4010";
	public static final String JOB_SQL_ILLEGAL = "4011";
	public static final String JOB_COLUMN_NUMBER_NOT_MATCH_OUTTABLE = "4012";
	public static final String JOB_FAIL_TO_EXECUTE = "4013";

	/*********** Report ************/
	public static final String REPORT_BELONG_PROJECT_ILLEGAL = "5001";
	public static final String REPORT_CATEGORY_ILLEGAL = "5002";
	public static final String REPORT_RESULT_NOT_FOUND = "5003";
	public static final String REPORT_VISUALDEF_REPORT_TYPE_ILLEGAL = "5004";
	public static final String REPORT_VISUALDEF_NULL = "5005";
	public static final String REPORT_RESULTSET_TOO_LARGE = "5006";

	// report datasetDef
	public static final String REPORT_DATASETDEF_NULL = "5100";
	public static final String REPORT_DATASETDEF_ILLEGAL = "5101";
	public static final String REPORT_DATASETDEF_UDAF_ILLEGAL = "5103";
	public static final String REPORT_DATASETDEF_COLUMN_ISNULL = "5104";
	public static final String REPORT_DATASETDEF_COLUMN_ILLEGAL = "5105";
	public static final String REPORT_DATASETDEF_CATEGORY_SAME_WITH_SERIES_ERROR = "5106";
	public static final String REPORT_DATASETDEF_DUPLICATE_SERIES_NAME_ERROR = "5107";
	public static final String REPORT_DATASETDEF_SEARCHCRITERA_ILLEGAL = "5108";
	public static final String REPORT_DATASETDEF_GLOBALOPERATOR_ILLEGAL = "5109";
	public static final String REPORT_DATASETDEF_BEYOND_MAX_SERIES_SIZE = "5110";
	public static final String REPORT_DATASETDEF_DATE_NULL = "5111";
	public static final String REPORT_DATASETDEF_DATE_PATTERN_NO_SUPPORT = "5113";
}
