package sys;

public interface BaseHint extends SpringAnnotationDef {
    public static final String DEFAULT_CHARSETNAME_ISO = "ISO-8859-1";
    public static final String DEFAULT_CHARSETNAME_GBK = "GBK";
    public static final String DEFAULT_CHARSETNAME_UTF = "UTF";
    public static final String MESSAGE = "message";
    public static final String RESULT = "result";
    public static final boolean RESULT_FALSE = false;
    public static final boolean RESULT_TRUE = true;
    public static final String MESSAGE_SUCCESS = "提交成功!";
    public static final String MESSAGE_FAIL = "提交失败!";
    public static final String PROJECT_ERRROR = "程序异常!";
    public static final String METHOD_ERRROR = "方法内部异常!";
    public static final String CODE = "代码值:";
    public static final String FREQUENT_OPERATION = "请不要频繁操作";
    public static final String SET_SUCCESS = "设置成功";
}
