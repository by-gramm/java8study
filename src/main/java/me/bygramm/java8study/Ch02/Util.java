package me.bygramm.java8study.Ch02;

public interface Util {

    /**
     * 주민등록번호 앞자리를 성별(gender)로 변환한다.
     */
    static String regNumToGender(String regNum) {
        int genderNum = Integer.parseInt(regNum.substring(7, 8));

        return (genderNum % 2 == 0) ? "Female" : "Male";
    }

}
