package com.xeranta.dev.approvalapp.utility;

public final class Constants {

    public Constants() {}

    public static class Code {

        public static final String HTTP = "http://";
        public static final String IP = "192.168.0.102";
        public static final String PORT = ":8080/";

        public static final String SERVER = "mobsys-2/";
        public static final String REST = "rest/task/";
        public static final String URL = HTTP+IP+PORT+SERVER+REST;

        public static final String GET_TASK_LIST = URL+"list";
        public static final String GET_TASK_BY_ID = URL+"get";
        public static final String UPDATE_TASK_BY_ID = URL+"approve";
    }
}
