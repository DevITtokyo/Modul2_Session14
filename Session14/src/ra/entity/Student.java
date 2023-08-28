package ra.entity;

import ra.color.Color;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Student implements IEntity<Student>, Serializable {
    private String studentId;
    private String studentName;
    private int age;
    private String birthday;
    private boolean sex;
    private float mark_html;
    private float mark_css;
    private float mark_javascript;
    private float avgMark;
    private String rank;
    // Constructor
    public Student() {
    }
    public Student(String studentId, String studentName, int age, String birthday, boolean sex, float mark_html, float mark_css, float mark_javascript, float avgMark, String rank) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.age = age;
        this.birthday = birthday;
        this.sex = sex;
        this.mark_html = mark_html;
        this.mark_css = mark_css;
        this.mark_javascript = mark_javascript;
        this.avgMark = avgMark;
        this.rank = rank;
    }
    // Getter and Setter
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public boolean isSex() {
        return sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }
    public float getMark_html() {
        return mark_html;
    }
    public void setMark_html(float mark_html) {
        this.mark_html = mark_html;
    }
    public float getMark_css() {
        return mark_css;
    }
    public void setMark_css(float mark_css) {
        this.mark_css = mark_css;
    }
    public float getMark_javascript() {
        return mark_javascript;
    }
    public void setMark_javascript(float mark_javascript) {
        this.mark_javascript = mark_javascript;
    }
    public float getAvgMark() {
        return avgMark;
    }
    public void setAvgMark(float avgMark) {
        this.avgMark = avgMark;
    }
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    @Override
    public void inputData(Scanner scanner, List<Student> list) {
        this.studentId = validateStudentId(scanner, list);
        this.studentName = validateStudentName(scanner);
        this.birthday = validateBirthday(scanner);
        this.sex = validateSex(scanner);
        this.mark_html = validateMarkHTML(scanner);
        this.mark_css = validateMarkCss(scanner);
        this.mark_javascript = validateMarkJavascript(scanner);
    }

    @Override
    public void displayData() {
        System.out.printf("Mã sv: %-10s - Tên sv:  %-10s- ngày sinh: %-10s - tuổi: %-10d - giới tính : %-10b\n",
                this.studentId, this.studentName, this.birthday, this.age, this.sex);
        System.out.printf("điểm html: %0.1f - css: %0.2f - javascript: %0.1f - avgMark: %0.1f - rank: %s\n",
                this.mark_html, this.mark_css, this.mark_javascript, this.avgMark, this.rank);
    }

    @Override
    public void calAge() {
        LocalDate localDate = LocalDate.now();
        String[] parts = (this.birthday).split("/");
        int year = Integer.parseInt(parts[2]);
        this.age = (localDate.getYear() - year);
    }

    @Override
    public void calAvgMark_Rank() {
        this.avgMark = (mark_html + mark_css + mark_javascript) / 3;
        if (this.avgMark<5){
            this.rank = "Yếu";
        } else if (this.avgMark<7) {
            this.rank = "Trung bình";
        } else if (this.avgMark<8){
            this.rank = "Khá";
        } else if (this.avgMark<9){
            this.rank = "Giỏi";
        }else {
            this.rank = "Xuất sắc";
        }
    }
    // Validate studentId : mã sinh viên gồm 4 ký tự, bắt đầu là ký tự S và không trùng lặp
    public String validateStudentId(Scanner scanner, List<Student> list) {
        System.out.print("Nhập vào mã sinh viên : ");
        do {
            String studentId = scanner.nextLine();
            if (studentId.length() == 4) {
                if (studentId.startsWith("S")) {
                    boolean isExist = false;
                    for (Student student : list) {
                        if (student.getStudentId().equals(studentId)) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        return studentId;
                    } else {
                        System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Mã sinh viên đã tồn tại, vui lòng nhập lại");
                    }
                } else {
                    System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Mã sinh viên phải bắt đầu là ký tự S, vui lòng nhập lại");
                }
            } else {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Mã sinh viên phải gồm 4 ký tự, vui lòng nhập lại");
            }
        } while (true);
    }
    // Validate studentName : tên sinh viên có từ 10-50 ký tự
    public static String validateStudentName(Scanner scanner) {
        System.out.print("Nhập vào tên sinh viên : ");
        do {
            String studentName = scanner.nextLine();
            if (studentName.length() >= 10 && studentName.length() <= 50) {
                return studentName;
            } else {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Tên sinh viên có độ dài từ 10-50 ký tự, vui lòng nhập lại");
            }
        } while (true);
    }
    // Validate ngày sinh có năm sinh trước năm 2005
    public static String validateBirthday (Scanner scanner){
        System.out.print("Nhập vào ngày sinh của sinh viên theo định dạng dd/MM/yyyy : ");
        while (true) {
            String studentBirthday = scanner.nextLine();
            if (!Pattern.matches("^\\d{1,2}/\\d{1,2}/\\d{4}", studentBirthday)) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Ngày sinh không đúng định dạng dd/MM/yyyy, vui lòng nhập lại");
            }else{
                String[] parts = studentBirthday.split("/");
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);


                if (year >2005) {
                    System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Ngày sinh phải có năm sinh trước năm 2005 , vui lòng nhập lại");
                } else if (month == 0 || month > 12) {
                    System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Tháng sinh phải từ 1-12, vui lòng nhập lại");
                } else if (day == 0 || day > 31) {
                    System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Ngày sinh phải từ 1-31 , vui lòng nhập lại");
                } else {
                    int[] monthLength = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

                    if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
                        monthLength[1] = 29;
                    }

                    if (day > 0 && day <= monthLength[month - 1]) {
                        return studentBirthday;
                    }
                }
            }
        }
    }
    // Validate giới tính sinh viên chỉ nhận true|false
    public static boolean validateSex(Scanner scanner) {
        System.out.print("Nhập vào giới tính của sinh viên : ");
        do {
            String sex = scanner.nextLine();
            if (sex.equalsIgnoreCase("true") || sex.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(sex);
            } else {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Giới tính chỉ nhận giá trị true | false, vui lòng nhập lại");
            }
        } while (true);
    }
    // Validate điểm html, css, js có giá trị trong khoảng 0-10
    public static float validateMarkHTML(Scanner scanner) {
        System.out.print("Nhập vào điểm html : ");
        do {
            try {
                float mark_html = Float.parseFloat(scanner.nextLine());
                if (mark_html >= 0 && mark_html <= 10) {
                    return mark_html;
                } else {
                    System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Điểm HTML trong khoảng 0-10, vui lòng nhập lại");
                }
            } catch (NumberFormatException ex1) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Điểm html không phải định dạng số thực, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        } while (true);
    }
    public static float validateMarkCss(Scanner scanner) {
        System.out.print("Nhập vào điểm CSS : ");
        do {
            try {
                float mark_css = Float.parseFloat(scanner.nextLine());
                if (mark_css >= 0 && mark_css <= 10) {
                    return mark_css;
                } else {
                    System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Điểm CSS trong khoảng 0-10, vui lòng nhập lại");
                }
            } catch (NumberFormatException ex1) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Điểm CSS không phải định dạng số thực, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        } while (true);
    }
    public static float validateMarkJavascript(Scanner scanner) {
        System.out.print("Nhập vào điểm Javascript : ");
        do {
            try {
                float mark_javascript = Float.parseFloat(scanner.nextLine());
                if (mark_javascript >= 0 && mark_javascript <= 10) {
                    return mark_javascript;
                } else {
                    System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Điểm Javascript trong khoảng 0-10, vui lòng nhập lại");
                }
            } catch (NumberFormatException ex1) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Điểm Javascript không phải định dạng số thực, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        } while (true);
    }
}
