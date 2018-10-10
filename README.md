#Back up dữ liệu theo lịch cho trước
#Build
Dùng eclipse import vào rồi build gói jar để run
Hoặc lấy gói jar để run

#Option
<b>start</b> là thời gian bắt đầu run<br/>
<b>src</b> là thư mục nguồn mà muốn backup<br/>
<b>des</b> là thư mục đích để backup nguồn vào<br/>
<b>expired</b> là thơi gian quá hạn để xóa, đơn vị là giây. VD muốn quá 7 ngày sẽ xóa thì 7*24*60*60=604800<br/>
<b>period</b> là thời gian chạy theo lịch đơn vị là giây tính từ thời điểm bắt đầu. VD muốn chạy 1 ngày từ thời điểm bắt đầu là 24*60*60=86400<br/>
 
#Run
java -jar copyFile.jar [option]<br/>
<br/>
<b>VD: Chạy vào lúc 0h ngày 10/10/2018 sẽ copy dữ liệu từ C:\MongoDB\Data sang C:\BackUpMongo và nếu C:\BackUpMongo quá 7 ngày thì xóa, và mỗi ngày chạy 1 lần vào 0h thì sẽ chạy như sau:</b><br/>
<br/>
<b>java -jar copyFile.jar -start "2018/10/10 00:00:00" -src "C:\MongoDB\Data" -des "C:\BackUpMongo" -expired 604800 -period 86400</b><br/>