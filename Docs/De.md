Tạo 1 ứng dụng di động viết bằng Android có chức năng đăng ký đề tài tiểu luận Học phần Phát triển ứng dụng di động. Ứng dụng có chức năng sau:
- Chọn thành viên vào nhóm:
- Chọn nhóm - Chọn đề tài - Hiển thị kết quả chọn "Nhóm-Đề tài" lên màn hình chính.
CSDL Đăng ký đề tài gồm 3 quan hệ sau:

-tblThanhVienNhom (MemID, MemName,GroupID, Role)

-tblThesis (GroupID, ThesisName)

-tblNhomDeTai 

Ứng dụng có 4 màn hình: 1 màn hình chính và 3 màn hình phụ.

- Màn hình chính (phía trên): có 4 nút lệnh Chọn Thành viên, Chọn nhóm, Chọn Đề tài và Thoát cùng 1 TextView hiển thị "Kết quả đăng ký thesis"

*Nút Chọn Thành viên: bật màn hình phụ 1 (phía trên) cho người dùng chọn thành viên vào Nhóm

*Nút chọn nhóm: bật màn hình phụ 2 cho người dùng chọn nhóm đăng ký đề tài (1 spinner chọn nhóm)



*Nút Chọn đề tài: bật màn hình phụ 3 cho người dùng chọn Tên đề tài (1 spinner chọn đề tài) _ layout tương tự màn hình chọn nhóm nhưng spinner chứa danh sách tên đề tài.

Sau khi chọn xong nhóm và đề tài trên màn hình chính hiển thị kết quả đăng ký dưới dạng: "Nhóm-Tên đề tài"

Dữ liệu cho ứng dụng lấy từ dữ liệu đăng ký tiểu luận của lớp (dữ liệu thành viên nhóm lấy đại diện 2-3 nhóm trích từ file excel đính kèm)


YÊU CẦU:
Tạo lớp CSDL kế thừa class SQLiteOpenHelper
a. Viết Constructor 
b. Override pt OnCreate  
c. Tạo các phương thức truy xuất dữ liệu.

CÁC LƯU Ý:
1. dựa vào project D:\School\NH_2026-2027\PT_UDDD\BT_TrenLop_2\EmployeeManagement để làm bài trên tại D:\School\NH_2026-2027\PT_UDDD\BT_TrenLop_2\ResearchManagement
2. project cần làm có 3 cơ sở dữ liệu: tblKetQuaDangKy, tblThesis, tblNhomDeTai
3. bổ sung đầy đủ các phương thức truy vấn dữ liệu như create, update, delete, xuất ra màn hình .... (nếu cần)
=> Hoàn thiện các yêu cầu của bài
