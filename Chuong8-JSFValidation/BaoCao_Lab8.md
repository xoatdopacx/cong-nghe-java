# Báo cáo Lab 8 - Chuyển form sang JSF, validation và message

**Học phần:** Công nghệ Java - IT3242  
**Sinh viên:** Nguyễn Văn Hùng  
**MSSV:** 20230752  
**Lớp:** DCCNTT 14.2  

---

## 1. Mục tiêu bài lab

- Hiểu JSF/Jakarta Faces khác gì so với Servlet + JSP truyền thống
- Cấu hình FacesServlet để chạy trang .xhtml
- Chuyển form JSP sang form JSF bằng h:form, h:inputText, h:commandButton
- Tạo Managed Bean/CDI Bean để nhận dữ liệu từ giao diện JSF
- Thêm validation bằng Bean Validation và thuộc tính required của JSF
- Hiển thị lỗi và thông báo thành công bằng h:messages/FacesMessage
- Hiển thị danh sách dữ liệu bằng h:dataTable
- So sánh cách xử lý form giữa Servlet/JSP và JSF

## 2. Công nghệ sử dụng

| Công nghệ | Phiên bản | Mục đích |
|---|---|---|
| JDK | 21 | Biên dịch Java |
| Apache Maven | 3.9.16 | Quản lý project |
| Jakarta Faces (Mojarra) | 4.0.7 | JSF implementation |
| Weld CDI | 5.1.2.Final | CDI container |
| Hibernate Validator | 8.0.1.Final | Bean Validation |
| Apache Tomcat | 10.1.30 (embedded) | Web container |

## 3. Cấu trúc project

```
lab08-jsf-validation/
├── pom.xml
└── src/main/
    ├── java/vn/edu/eaut/lab8/
    │   ├── bean/
    │   │   ├── SinhVienBean.java
    │   │   ├── SachBean.java
    │   │   ├── ProductBean.java
    │   │   └── LoginBean.java
    │   ├── model/
    │   │   ├── SinhVien.java
    │   │   ├── Sach.java
    │   │   └── Product.java
    │   └── repository/
    │       ├── SinhVienRepository.java
    │       ├── SachRepository.java
    │       └── ProductRepository.java
    └── webapp/
        ├── template.xhtml          (Bài 11: Layout dùng chung)
        ├── index.xhtml             (Bài 1: Trang chủ)
        ├── sinhvien-form.xhtml     (Bài 4, 9, 12: Form + validation + selectOneMenu)
        ├── sinhvien-list.xhtml     (Bài 5, 9, 10: DataTable + search + edit)
        ├── sach-form.xhtml         (Bài 6: Form sách)
        ├── sach-list.xhtml         (Bài 6: DS sách)
        ├── product-form.xhtml      (Bài 7: Form sản phẩm)
        ├── product-list.xhtml      (Bài 7: DS sản phẩm)
        ├── login.xhtml             (Bài 8: Đăng nhập)
        └── WEB-INF/
            ├── web.xml
            └── beans.xml
```

## 4. Chi tiết bài tập

### Bài 1. Trang JSF đầu tiên (index.xhtml)

Tạo trang index.xhtml với FacesServlet, hiển thị danh sách 13 bài tập với link truy cập. Sử dụng Facelets template (ui:composition) cho layout dùng chung.

**Kết quả chạy:**

PLACEHOLDER_BAI1

### Bài 2. Model và Repository (SinhVien.java, SinhVienRepository.java)

Model SinhVien sử dụng Bean Validation annotations:
- `@NotBlank` cho mã SV, họ tên, lớp
- `@Size(min=5)` cho họ tên
- `@Email` cho email

Repository lưu trữ in-memory với CRUD + search.

### Bài 3. Managed Bean (SinhVienBean.java)

CDI Bean `@Named @SessionScoped` xử lý:
- `save()`: Lưu/cập nhật sinh viên + FacesMessage thành công
- `delete()`: Xóa sinh viên + FacesMessage
- `edit()`: Load dữ liệu lên form để sửa
- `getDsSinhVien()`: Trả danh sách (có filter theo keyword)

### Bài 4. Form JSF + Validation + Message (sinhvien-form.xhtml)

Chuyển form JSP sang JSF sử dụng:
- `h:form`, `h:inputText`, `h:commandButton`
- `h:message` hiển thị lỗi theo từng trường
- `h:messages globalOnly="true"` hiển thị thông báo thành công
- `required="true"` + `requiredMessage` cho validation JSF

**Kết quả - Form trống:**

PLACEHOLDER_BAI4_FORM

**Kết quả - Validation lỗi:**

PLACEHOLDER_BAI4_ERROR

**Kết quả - Lưu thành công:**

PLACEHOLDER_BAI4_SUCCESS

### Bài 5. h:dataTable + Xóa (sinhvien-list.xhtml)

Hiển thị danh sách sinh viên bằng `h:dataTable` với `f:facet name="header"`. Nút xóa gọi trực tiếp `#{sinhVienBean.delete(sv.id)}`.

**Kết quả chạy:**

PLACEHOLDER_BAI5

### Bài 6. Form Sách JSF (sach-form.xhtml, sach-list.xhtml)

Form sách với validation:
- Tên sách: `@NotBlank`, required
- Tác giả: `@NotBlank`, required
- Năm XB: `@Min(1900)`, `@Max(2030)`

**Kết quả - Form sách:**

PLACEHOLDER_BAI6_FORM

**Kết quả - DS sách:**

PLACEHOLDER_BAI6_LIST

### Bài 7. Form Sản phẩm JSF (product-form.xhtml, product-list.xhtml)

Form sản phẩm với validation:
- Tên SP: `@NotBlank`, required
- Giá: `@Positive` (> 0)
- Số lượng: `@PositiveOrZero` (>= 0)

**Kết quả - Form SP:**

PLACEHOLDER_BAI7_FORM

**Kết quả - DS sản phẩm:**

PLACEHOLDER_BAI7_LIST

### Bài 8. Form đăng nhập JSF (login.xhtml, LoginBean.java)

Form đăng nhập sử dụng `h:inputSecret`, kiểm tra tài khoản. Nếu sai → `FacesMessage.SEVERITY_ERROR`, nếu đúng → redirect về index.xhtml.

**Kết quả - Form đăng nhập:**

PLACEHOLDER_BAI8_LOGIN

**Kết quả - Đăng nhập sai:**

PLACEHOLDER_BAI8_ERROR

### Bài 9. Sửa sinh viên

Từ bảng danh sách, nhấn nút "Sửa" → load dữ liệu lên form, nút đổi thành "Cập nhật". Bean giữ trạng thái `editing=true` và gọi `repo.update()`.

**Kết quả:**

PLACEHOLDER_BAI9

### Bài 10. Tìm kiếm sinh viên

Thêm ô keyword + nút "Tìm kiếm" trên sinhvien-list.xhtml. Bean lọc danh sách theo họ tên, mã SV hoặc lớp.

**Kết quả:**

PLACEHOLDER_BAI10

### Bài 11. Layout dùng chung (template.xhtml)

Tạo template Facelets với:
- Header (gradient, tên lab)
- Navigation bar (links tới các trang)
- Content area (`ui:insert name="content"`)
- Footer (thông tin sinh viên)

Tất cả trang sử dụng `ui:composition template="template.xhtml"` và `ui:define name="content"`.

### Bài 12. selectOneMenu cho trường lớp

Thay `h:inputText` bằng `h:selectOneMenu` cho trường lớp trong form sinh viên. Danh sách lớp lấy từ `#{sinhVienBean.dsLop}`.

**Kết quả:**

PLACEHOLDER_BAI12

### Bài 13. Báo cáo so sánh Servlet/JSP và JSF

| Tiêu chí | Servlet/JSP (Lab 7) | JSF (Lab 8) |
|---|---|---|
| **Mô hình** | Request-based MVC | Component-based MVC |
| **Controller** | Servlet (doGet/doPost) | Managed Bean (@Named) |
| **View** | JSP + JSTL (scriptlet, EL) | Facelets XHTML (h:xxx tags) |
| **Form** | HTML `<form>` + action URL | `h:form` + EL binding |
| **Nhận dữ liệu** | `request.getParameter()` | Auto-binding qua EL `#{bean.prop}` |
| **Validation** | Code thủ công trong Servlet | Bean Validation annotations + JSF required |
| **Hiển thị lỗi** | Gửi attribute + hiển thị JSP | `h:message`, `h:messages`, FacesMessage |
| **Điều hướng** | `response.sendRedirect()`, `RequestDispatcher` | Return String outcome, `?faces-redirect=true` |
| **State** | HttpSession thủ công | CDI Scope (@SessionScoped, @RequestScoped) |
| **Layout** | JSP include | Facelets template (ui:composition, ui:insert) |
| **Data table** | `<c:forEach>` JSTL | `h:dataTable` + `f:facet` |
| **Tách code** | View biết logic hiển thị | View thuần component, Bean xử lý logic |

**Nhận xét:** JSF giúp tách biệt rõ ràng hơn giữa View và Logic so với Servlet/JSP. Validation được khai báo (declarative) thay vì viết code thủ công. Tuy nhiên, JSF có learning curve cao hơn và cần cấu hình CDI container.

## 5. Câu hỏi củng cố

### Câu 1: JSF xử lý form khác Servlet/JSP ở điểm nào?

JSF sử dụng mô hình component-based: form được bind trực tiếp vào bean property qua EL expression (`#{bean.prop}`). Dữ liệu tự động chuyển đổi và validate trước khi gọi action method. Servlet/JSP phải đọc `request.getParameter()` và validate thủ công.

### Câu 2: h:inputText, h:commandButton và h:messages có vai trò gì?

- `h:inputText`: Component nhập liệu, bind vào bean property, hỗ trợ validation
- `h:commandButton`: Nút submit, gọi action method của bean
- `h:messages`: Hiển thị tất cả FacesMessage (lỗi/thành công) trên trang

### Câu 3: Managed Bean nhận dữ liệu từ giao diện bằng cơ chế nào?

JSF framework tự động bind giá trị từ form vào bean property thông qua EL expression. Khi submit, JSF lifecycle thực hiện: Restore View → Apply Request Values → Process Validations → Update Model Values → Invoke Application → Render Response.

### Câu 4: Bean Validation khác requiredMessage của JSF ở điểm nào?

- `requiredMessage` là validation level JSF component, chỉ kiểm tra trường không rỗng
- Bean Validation (`@NotBlank`, `@Email`, `@Size`, `@Min`, `@Max`) là annotation trên Model class, tái sử dụng được ở mọi layer (service, persistence), và hỗ trợ nhiều ràng buộc phức tạp hơn

### Câu 5: Vì sao Lab 9 mới nên tích hợp JPA?

Lab 8 tập trung vào JSF UI layer (component, validation, message). Nếu thêm JPA/Entity/Repository/transaction sẽ quá tải. Lab 9 sẽ kế thừa JSF từ Lab 8 và thêm persistence layer, đúng mạch từ UI → Business Logic → Data Access.

## 6. Kết luận

Lab 8 đã hoàn thành đầy đủ 13/13 bài tập, bao gồm:
- 10 file Java (3 model, 3 repository, 4 CDI bean)
- 9 file XHTML (1 template + 8 trang)
- Validation: 4+ ràng buộc Bean Validation + JSF required
- Message: FacesMessage info/error + h:message/h:messages
- Layout: Facelets template dùng chung
- So sánh chi tiết Servlet/JSP vs JSF
