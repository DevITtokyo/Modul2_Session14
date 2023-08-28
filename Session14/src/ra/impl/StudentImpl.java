package ra.impl;

import ra.color.Color;
import ra.entity.Student;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class StudentImpl {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        readDataFromFile();
        do {
            System.out.println("********************Menu*********************");
            System.out.println("1. Nhập thông tin các sinh viên");
            System.out.println("2. Tính tuổi các sinh viên");
            System.out.println("3. Tính điểm trung bình và xếp loại sinh viên");
            System.out.println("4. Sắp xếp sinh viên theo tuổi tăng dần");
            System.out.println("5. Thống kê sinh viên theo xếp loại sinh viên");
            System.out.println("6. Cập nhật thông tin sinh viên theo mã sinh viên");
            System.out.println("7. Tìm kiếm sinh viên theo tên sinh viên");
            System.out.println("8. Thoát");
            System.out.print("Lựa chọn của bạn là : ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        inputStudent();
                        writeDataToFile();
                        break;
                    case 2:
                        calAgeStudent();
                        writeDataToFile();
                        break;
                    case 3:
                        calAvgRankStudent();
                        writeDataToFile();
                        break;
                    case 4:
                        sortStudent();
                        break;
                    case 5:
                        statisticRank();
                        break;
                    case 6:
                        updateStudent();
                        break;
                    case 7:
                        searchStudent();
                        break;
                    case 8:
                        writeDataToFile();
                        System.exit(0);
                    default:
                        System.err.println("Vui lòng chọn từ 1-8");
                }
            } catch (NumberFormatException nfe) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Dữ liệu không phải là số, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Có lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        }while (true);
    }
    // 1. Nhập thông tin các sinh viên
    public static void inputStudent() {
        try {
            System.out.print("Nhập vào số lượng học sinh muốn thêm : ");
            int n = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                System.out.println("Nhập vào sinh viên thứ " + (i + 1) + ":");
                Student student = new Student();
                student.inputData(scanner, studentList);
                studentList.add(student);
            }
        } catch (NumberFormatException numberFormatException) {
            System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Định dạng số thực, vui lòng nhập lại");
        } catch (Exception e) {
            System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"lỗi chưa xác định hãy liên hệ hệ thống");
        }

    }
    // 2. Tính tuổi các sinh viên
    public static void calAgeStudent() {
        studentList.forEach(Student::calAge);
        System.out.println("Đã tính xong tuổi của sinh viên xong");
    }
    // 3. Tính điểm trung bình và xếp loại sinh viên

    public static void calAvgRankStudent() {
        studentList.forEach(Student::calAvgMark_Rank);
        System.out.println("Đã tính xong điểm trung bình và xếp loại cho tất cả các sinh viên");
    }
    // 4. Sắp xếp sinh viên theo tuổi tăng dần
    public static void sortStudent() {
        Collections.sort(studentList,Comparator.comparing(Student::getAge));
        studentList.forEach(Student::displayData);
    }
    // 5. Thống kê sinh viên theo xếp loại sinh viên
    public static void statisticRank() {
        int cntYeu = 0, cntTB = 0, cntKha = 0, cntGioi = 0, cntXuatSac = 0;
        cntYeu = (int) studentList.stream().filter(student -> student.getRank().equals("Yếu")).count();
        cntTB = (int) studentList.stream().filter(student -> student.getRank().equals("Trung bình")).count();
        cntKha = (int) studentList.stream().filter(student -> student.getRank().equals("Khá")).count();
        cntGioi = (int) studentList.stream().filter(student -> student.getRank().equals("Giỏi")).count();
        cntXuatSac = (int) studentList.stream().filter(student -> student.getRank().equals("Xuất sắc")).count();
        System.out.println("Thống kê sinh viên theo xếp loại sinh viên là :");
        // In thống kê
        System.out.println("Yếu : " + cntYeu);
        System.out.println("Trung bình : " + cntTB);
        System.out.println("Khá : " + cntKha);
        System.out.println("Giỏi : " + cntGioi);
        System.out.println("Xuất sắc : " + cntXuatSac);
    }
    // 6. Cập nhật thông tin sinh viên theo mã sinh viên
    public static void updateStudent() {
        boolean isExist = true;
        try {
            System.out.print("Nhập mã sinh viên cập nhật : ");
            String idStudent = scanner.nextLine();
            for (Student student : studentList) {
                if (student.getStudentId().equalsIgnoreCase(idStudent)) {
                    student.setStudentName(Student.validateStudentName(scanner));
                    student.setSex(Student.validateSex(scanner));
                    student.setBirthday(Student.validateBirthday(scanner));
                    student.setMark_html(Student.validateMarkHTML(scanner));
                    student.setMark_css(Student.validateMarkCss(scanner));
                    student.setMark_javascript(Student.validateMarkJavascript(scanner));
                    student.calAge();
                    student.calAvgMark_Rank();
                } else {
                    isExist = false;
                }
            }
        } catch (Exception exception) {
            System.err.println("lỗi chưa xác định hãy liên hệ hệ thống");
        }
        if (!isExist) {
            System.err.println("Mã sinh viên bạn nhập không tồn tại");
        }
    }
    // 7. Tìm kiếm sinh viên theo tên sinh viên
    public static void searchStudent() {
        boolean isNotExist = false;
        System.out.print("Nhập tên sinh viên tim kiếm : ");
        do {

            String searchStudent = scanner.nextLine();
            boolean exists = studentList.stream()
                    .anyMatch(student -> student.getStudentName().toLowerCase().contains(searchStudent.toLowerCase()));

            studentList.stream()
                    .filter(student -> student.getStudentName()
                            .toLowerCase()
                            .contains(searchStudent.toLowerCase()))
                    .forEach(Student::displayData);
            if (!exists) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Tên sinh viên" +searchStudent+" không tồn tại");
            } else {
                isNotExist = true;
            }
        } while (!isNotExist);
    }

    public static void writeDataToFile() {
        File file = new File("studentList.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            // Khởi tạo đối tượng FileOutputStream từ file - Checked Exception
            fos = new FileOutputStream(file);
            //Khởi tạo đối tượng ObjectOutputStream từ fos
            oos = new ObjectOutputStream(fos);
            oos.writeObject(studentList);
            oos.flush();
        } catch (FileNotFoundException ex1) {
            System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"File không tồn tại");
        } catch (IOException ex2) {
            System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Lỗi khi ghi dữ liệu ra file");
        } catch (Exception ex) {
            System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Xảy ra lỗi trong quá trình ghi dữ liệu ra file");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex1) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Xảy ra lỗi khi đóng các stream");
            } catch (Exception ex) {
                System.err.println(Color.YELLOW_BACKGROUND + Color.BLACK_BOLD+"Xảy ra lỗi trong quá trình đóng các stream");
            }
        }
    }
    public static void readDataFromFile() {
        File file = new File("studentList.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            studentList = (List<Student>) ois.readObject();
        } catch (FileNotFoundException ex1) {
            System.err.println("Không tồn tại file");
        } catch (IOException ex2) {
//            System.err.println("Lỗi khi đọc file");
        } catch (Exception ex) {
            System.err.println("Có lỗi trong quá trình đọc dữ liệu từ file");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex1) {
                System.err.println("Có lỗi khi đóng stream");
            } catch (Exception ex) {
                System.err.println("Có lỗi trong quá trình đóng các stream");
            }
        }
    }
}
