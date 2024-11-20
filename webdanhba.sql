-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 20, 2024 lúc 06:56 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `webdanhba`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `idUser` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `note` varchar(255) NOT NULL DEFAULT 'Người bạn mới dễ thương',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `avatar` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL DEFAULT 'Tp. Hồ Chí Minh'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`idUser`, `username`, `email`, `phone`, `note`, `created_at`, `avatar`, `address`) VALUES
(1, 'vandeptraivlozzzzz', 'ngocvandeptraivloz@gmail.com', '0905291271', 'Người bạn mới dễ thương', '2024-11-19 19:58:29', 'https://images.pexels.com/photos/2730212/pexels-photo-2730212.jpeg?cs=srgb&dl=pexels-thainhan-2730212.jpg&fm=jpg', 'Tp. Hồ Chí Minh'),
(2, 'aaaaaaaaaaa', 'sssssssssssssss', '12345678', 'Người bạn mới dễ thương', '2024-11-20 13:43:45', '', 'Tp. Hồ Chí Minh'),
(4, 'Nguyễn Anh Tuấn', 'tuannguyen@example.com', '0912345671', 'Người bạn mới dễ thương', '2024-11-20 07:10:00', 'https://example.com/avatar1.jpg', 'Tp. Hồ Chí Minh'),
(5, 'Trần Minh Tuấn', 'minhtuan@example.com', '0912345672', 'Bạn rất thân thiện', '2024-11-20 07:11:00', 'https://example.com/avatar2.jpg', 'Tp. Hồ Chí Minh'),
(6, 'Phan Thị Lan', 'lanphan@example.com', '0912345673', 'Người bạn dễ thương', '2024-11-20 07:12:00', 'https://example.com/avatar3.jpg', 'Tp. Hồ Chí Minh'),
(7, 'Lê Hoàng Nam', 'namhoang@example.com', '0912345674', 'Chúc bạn vui vẻ!', '2024-11-20 07:13:00', 'https://example.com/avatar4.jpg', 'Tp. Hồ Chí Minh'),
(8, 'Bùi Minh Quân', 'minhquan@example.com', '0912345675', 'Người bạn tuyệt vời', '2024-11-20 07:14:00', 'https://example.com/avatar5.jpg', 'Tp. Hồ Chí Minh'),
(9, 'Đỗ Thị Mai', 'maido@example.com', '0912345676', 'Chúc bạn có một ngày tốt lành', '2024-11-20 07:15:00', 'https://example.com/avatar6.jpg', 'Tp. Hồ Chí Minh'),
(10, 'Nguyễn Quang Hieu', 'hieunguyen@example.com', '0912345677', 'Bạn rất dễ mến', '2024-11-20 07:16:00', 'https://example.com/avatar7.jpg', 'Tp. Hồ Chí Minh'),
(11, 'Phạm Văn Hưng', 'hunganpham@example.com', '0912345678', 'Rất vui khi được gặp bạn', '2024-11-20 07:17:00', 'https://example.com/avatar8.jpg', 'Tp. Hồ Chí Minh'),
(12, 'Võ Thị Kim', 'kimvo@example.com', '0912345679', 'Hy vọng gặp lại bạn sớm!', '2024-11-20 07:18:00', 'https://example.com/avatar9.jpg', 'Tp. Hồ Chí Minh'),
(13, 'Trần Quốc Toàn', 'toanquoc@example.com', '0912345680', 'Một người bạn tuyệt vời', '2024-11-20 07:19:00', 'https://example.com/avatar10.jpg', 'Tp. Hồ Chí Minh'),
(14, 'Lê Thị Mai Anh', 'maianh@example.com', '0912345681', 'Chúc bạn luôn hạnh phúc', '2024-11-20 07:20:00', 'https://example.com/avatar11.jpg', 'Tp. Hồ Chí Minh'),
(15, 'Nguyễn Phương Dung', 'phuongdung@example.com', '0912345682', 'Mong bạn thành công', '2024-11-20 07:21:00', 'https://example.com/avatar12.jpg', 'Tp. Hồ Chí Minh'),
(16, 'Phan Lê Minh', 'minhphan@example.com', '0912345683', 'Chúc bạn ngày mới vui vẻ!', '2024-11-20 07:22:00', 'https://example.com/avatar13.jpg', 'Tp. Hồ Chí Minh'),
(17, 'Bùi Thị Lệ', 'lebuithithit@example.com', '1111111111', 'con cac', '2024-11-20 07:23:00', 'https://example.com/avatar14.jpg', 'Hà nội'),
(18, 'Trương Văn Quang', 'quangtrung@example.com', '0912345685', 'Mong bạn có nhiều niềm vui', '2024-11-20 07:24:00', 'https://example.com/avatar15.jpg', 'Tp. Hồ Chí Minh'),
(19, 'Nguyễn Mai Duy', 'duymai@example.com', '0912345686', 'Cảm ơn bạn vì sự giúp đỡ!', '2024-11-20 07:25:00', 'https://example.com/avatar16.jpg', 'Tp. Hồ Chí Minh'),
(20, 'Lê Hoàng Long', 'longhoang@example.com', '0912345687', 'Bạn là một người bạn rất tốt', '2024-11-20 07:26:00', 'https://example.com/avatar17.jpg', 'Tp. Hồ Chí Minh'),
(21, 'Vũ Minh Tuấn', 'tuanvu@example.com', '0912345688', 'Hẹn gặp lại bạn sau!', '2024-11-20 07:27:00', 'https://example.com/avatar18.jpg', 'Tp. Hồ Chí Minh'),
(22, 'Đoàn Quốc Thắng', 'thangdoan@example.com', '0912345689', 'Rất vui khi làm bạn với bạn', '2024-11-20 07:28:00', 'https://example.com/avatar19.jpg', 'Tp. Hồ Chí Minh'),
(23, 'Trần Duy Hưng', 'hungduy@example.com', '0912345690', 'Hy vọng chúng ta có thể hợp tác', '2024-11-20 07:29:00', 'https://example.com/avatar20.jpg', 'Tp. Hồ Chí Minh');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
