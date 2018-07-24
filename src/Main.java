

import java.lang.reflect.Field;
import java.util.List;

public class Main {
//    @Check(checkType = Check.CheckType.STRING_NOT_EMPTY, dataType = Check.DataType.STRING)
//    private String username;

    @Check(checkType = Check.CheckType.STRING_EQUAL, dataType = Check.DataType.STRING, compareString = "mopengfei")
    private String nickname;

//    @Check(checkType = Check.CheckType.STRING_IN_LIST,dataType = Check.DataType.STRING,stringList = {"mopengfei","feipengmo","lipengfei"})
//    private String password;
//
//    @Check(checkType = Check.CheckType.NUM_EQUAL,dataType = Check.DataType.NUM,compareInt = 24)
//    private int age;
//
//    @Check(checkType = Check.CheckType.NUM_GREATER_THAN,dataType = Check.DataType.NUM,compareInt = 23)
//    private int height;


    public static void main(String[] args) {
//        System.out.println("Hello World!");
        new Main().submit();
//        double dou1 = 12;
//        double dou2 = 12;
//        System.out.println((dou1 > dou2) + Double.toString(dou1) + (dou1 == dou2) + ((0.01 + 0.05) > (0.03 + 0.03))+(0.06>0.06));
    }

    public void submit() {
        nickname = "mopengfei";
        if (!checkData(this)) {
            return;
        }
    }

    public static boolean checkData(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        long fieldsLength = fields.length;
        Class<?> formatClass;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(Check.class)) {
                Check annotationCheck = field.getAnnotation(Check.class);
                Object checkObject = new Object();
                try {
                    checkObject = field.get(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                switch (annotationCheck.dataType()) {
                    case STRING:
                        String stringData = (String) checkObject;
                        switch (annotationCheck.checkType()) {
                            case STRING_NOT_EMPTY:
                                if (stringData == null || stringData.length() == 0) {
                                    fieldsLength -= 1;
                                    System.out.println(annotationCheck.toastString());
                                    return false;
                                }
                                break;
                            case STRING_EQUAL:
                                return annotationCheck.compareString().equals(stringData);
                            case STRING_IN_LIST:
                                if (annotationCheck.stringList().length == 0)
                                    return false;
                                for (String s : annotationCheck.stringList()) {
                                    if (s.equals(stringData)) {
                                        return true;
                                    }
                                }
                                return false;
                        }
                        break;
                    case NUM:
                        int numData = (int) checkObject;
                        switch (annotationCheck.checkType()) {
                            case NUM_EQUAL:
                                return numData == annotationCheck.compareInt();
                            case NUM_IN_LIST:
                                if (annotationCheck.numList().length == 0)
                                    return false;
                                for (int i1 : annotationCheck.numList()) {
                                    if (i1 == numData)
                                        return true;
                                }
                                return false;
                            case NUM_LESS_THAN:
                                return numData < annotationCheck.compareInt();
                            case NUM_GREATER_THAN:
                                return numData > annotationCheck.compareInt();
                        }
                }
            }
        }
        return true;
    }
}
