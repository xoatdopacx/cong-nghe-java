package vn.edu.eaut.lab8.repository;

import vn.edu.eaut.lab8.model.Sach;
import java.util.*;

/**
 * Repository sách - lưu trữ trong bộ nhớ.
 */
public class SachRepository {
    private static final List<Sach> data = new ArrayList<>();
    private static int autoId = 3;

    static {
        data.add(new Sach(1, "Lập trình Java", "Nguyễn Văn A", 2023, "Công nghệ"));
        data.add(new Sach(2, "Cấu trúc dữ liệu", "Trần Văn B", 2022, "Giáo trình"));
    }

    public List<Sach> findAll() {
        return data;
    }

    public void add(Sach sach) {
        sach.setId(autoId++);
        data.add(sach);
    }

    public void delete(int id) {
        data.removeIf(s -> s.getId() == id);
    }
}
