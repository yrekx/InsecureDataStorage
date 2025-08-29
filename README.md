# InsecureDataStorage
Các phương thức lưu trữ dữ liệu cục bộ Phổ Biến trên Android

## SharedPreferences

* **Khái niệm:** Là một cách để lưu trữ các cặp khóa/giá trị (key/value) đơn giản như chuỗi (String), số nguyên (int), số thực (float), và giá trị logic (Boolean) được lưu trong một tệp tin XML riêng tư của ứng dụng. Dễ sử dụng, phù hợp với lượng dữ liệu nhỏ.

## SQLite Databases

* **Là một hệ quản trị cơ sở dữ liệu quan hệ (relational database)** nhẹ, tự chứa, lưu trữ toàn bộ cơ sở dữ liệu trong một tệp tin duy nhất. Lý tưởng để quản lý dữ liệu có cấu trúc.Nhưng tương tự như SharedPreferences, dữ liệu trong tệp tin SQLite có thể bị truy cập và đọc nếu không được mã hóa.

## Bộ nhớ trong (Internal Storage)

* Là một không gian lưu trữ riêng tư, chỉ có thể truy cập bởi chính ứng dụng đó.Có thể tìm được bằng đường dẫn `/data/data/<tên_gói_ứng_dụng>` trên thiết bị android.
* **Ưu điểm:** Đây là nơi an toàn nhất để lưu trữ dữ liệu nhạy cảm, vì hệ điều hành Android có cơ chế bảo mật nghiêm ngặt giúp dữ liệu không bị rò rỉ. Dữ liệu sẽ bị xóa hoàn toàn khi ứng dụng bị gỡ cài đặt.

## Bộ nhớ ngoài (External Storage)

* Không gian lưu trữ công cộng, có thể truy cập và chia sẻ bởi nhiều ứng dụng (bao gồm thẻ SD và một phần bộ nhớ trong). Do tính chất "đọc và ghi được bởi mọi ứng dụng", đây là nơi không an toàn để lưu trữ dữ liệu nhạy cảm hoặc riêng tư. Nhưng nó lại nơi lý tưởng để lưu trữ các tệp tin mà người dùng muốn chia sẻ, chẳng hạn như ảnh hoặc video.


## Nguyên Nhân Gây Ra Lỗ Hổng Bảo Mật
Các vấn đề về bảo mật thường xuất phát từ:

* Lưu trữ dữ liệu nhưng không mã hóa: Người lập trình sử dụng SharedPreferences hoặc database cục bộ nhưng không mã hóa dữ liệu.
* Dữ liệu dạng plaintext: Lưu trữ dữ liệu trên bộ nhớ trong (internal storage) hoặc ngoài (external storage) dưới dạng văn bản rõ, dễ dàng bị đọc nếu thiết bị bị xâm nhập.
* Đồng bộ hóa không an toàn: Đồng bộ hóa dữ liệu với các dịch vụ đám mây (cloud) hoặc Google Drive mà không mã hóa.
* Hardcode thông tin nhạy cảm: Trong mã nguồn của chương trình có ghi trực tiếp các thông tin nhạy cảm (như API keys, mật khẩu) từ đó làm chúng bị lộ ra ngoài.
*etc.

