package htcc.common.component.log;

import htcc.common.util.StringUtil;

import java.util.HashMap;

public abstract class AbstractLog {

    static String identifyStr = "identifyKey";
    static String messageStr = "msg";

    protected String identifyKey;

    public AbstractLog() {
        identifyKey = this.getClass().getSimpleName();
    }

    protected final HashMap<String, Object> encode(Object logObj) {
        HashMap<String, Object> encodeLog = new HashMap<>();
        encodeLog.put(AbstractLog.identifyStr, this.identifyKey);
        encodeLog.put(AbstractLog.messageStr, logObj);
        // Return Encode Log
        return encodeLog;
    }

    protected final Object decode(HashMap<String, Object> encodeObj) {
        String _identifyKey = encodeObj.get(AbstractLog.identifyStr).toString();
        if (_identifyKey != this.identifyKey) {
            return null;
        }

        // Return log obj
        return encodeObj.get(AbstractLog.messageStr);
    }


    public final String makeJsonDumpLog(String logStr) {
        HashMap<String, Object> log = this.encode(logStr);
        return StringUtil.toJsonString(log);
    }

    public final boolean storeLog(String jsonDumpLog) {
        HashMap<String, Object> log = new HashMap<String, Object>();
        log =  (HashMap<String, Object>) StringUtil.fromJsonString(jsonDumpLog, log.getClass());
        Object msgObj = this.decode(log);
        if (msgObj == null) {
            return false;
        }
        String msg = (String)msgObj;

        return this.doStoreLog(msg);
    }

    protected abstract boolean doStoreLog(String msg);

}
