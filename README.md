# De-Thi
Đây là project được thực hiện trong quá trình ôn thi Lịch sử Đảng và Tư tưởng Hồ Chí Minh
## Ý tưởng
- Từ ngân hàng đề thi trên word, chuyển đổi thành ứng dụng thi thử
- Ứng dụng cho phép chọn đáp án và tính điểm, hiển thị đúng sai cho người dùng
- Cho phép ôn thi hiệu quả hơn
## Hướng dẫn cài đặt
- Clone project và chạy với Eclipse
## Hướng dẫn upload dữ liệu
Để chuyển từ ngân hàng đề thi trên các file text, yêu cầu với file text:
- Các đáp án đúng phải được đánh kí hiện `*` ở đầu
- Câu hỏi phải bắt đầu bằng từ `Câu`
Nếu các đáp án đúng được không có sẵn `*` mà được bôi đậm thì có thể chuyển đổi như sau:
- Copy văn bản vào Excel liên tục vào 2 cột
- Chọn 1 cột và thực hiện chức năng Replace với điều kiện dòng có Font `Bold` thì chuyển thành `*`, còn lại chuyển thành rỗng
- Sau đó cộng 2 cột vào với nhau
